using Auth.Data;
using Auth.SharedObject;
using Microsoft.EntityFrameworkCore;

namespace Auth
{
    public class AppRunner
    {
        private string allowedOrigins = "allowedOrigins";
        private Ice.Communicator? RunIceServices(string[] args, IServiceProvider services)
        {
            try
            {
                Ice.Communicator communicator = Ice.Util.initialize(args[0]);
                var adapter = communicator.createObjectAdapter("Adapter");

                adapter.addServantLocator(new AccountLocator(services), "account");

                adapter.activate();

                return communicator;
            }
            catch (Exception e)
            {
                Console.Error.WriteLine(e);
            }

            return null;
        }

        private WebApplication BuildWebApp(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            builder.Host.ConfigureAppConfiguration((hostingContext, config) =>
            {
                config.AddJsonFile(args[1],
                                   optional: false,
                                   reloadOnChange: true);
            });

            builder.Services.AddCors(options =>
            {
                options.AddDefaultPolicy(
                    policy =>
                    {
                        policy.AllowAnyOrigin();
                    });
            });

            builder.Services.AddControllers();
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            builder.Services.AddDbContext<AuthContext>(options =>
                options.UseNpgsql(builder.Configuration.GetConnectionString("AuthPostgresConnection")),
                ServiceLifetime.Transient);

            return builder.Build();
        }

        public void Run(string[] args)
        {
            Console.WriteLine("Starting auth...");
            var app = BuildWebApp(args);

            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            var scope = app.Services.CreateScope();

            var services = scope.ServiceProvider;

            AuthContext context = services.GetRequiredService<AuthContext>();
            if (!context.Database.CanConnect())
            {
                Thread.Sleep(10000);
            }
            context.Database.EnsureCreated();
            new DbInitializer(context)
                .InitializeRoles()
                .CreateRootUser();

            var communicator = RunIceServices(args, services);

            app.UseCors();

            app.MapControllers();

            app.Run();

            if (communicator != null)
            {
                communicator.waitForShutdown();
            }
        }
    }
}
