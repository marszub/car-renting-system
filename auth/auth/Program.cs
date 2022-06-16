using Auth.Data;
using Auth.SharedObject;
using Microsoft.EntityFrameworkCore;

namespace Auth
{
    public class Program
    {
        private static void RunIceServices(string[] args, AuthContext context)
        {
            try
            {
                using (Ice.Communicator communicator = Ice.Util.initialize(ref args))
                {
                    var adapter = communicator.createObjectAdapter("Adapter");

                    adapter.addDefaultServant(new AccountDefault(context), "account");

                    adapter.activate();
                    communicator.waitForShutdown();
                }
            }
            catch (Exception e)
            {
                Console.Error.WriteLine(e);
            }
        }

        public static void Main(string[] args)
        {
            var builder = WebApplication.CreateBuilder(args);

            builder.Services.AddControllers();
            // Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            builder.Services.AddDbContext<AuthContext>(options =>
                options.UseNpgsql(builder.Configuration.GetConnectionString("AuthPostgresConnection")));


            var app = builder.Build();

            // Configure the HTTP request pipeline.
            if (app.Environment.IsDevelopment())
            {
                app.UseSwagger();
                app.UseSwaggerUI();
            }

            using (var scope = app.Services.CreateScope())
            {
                var services = scope.ServiceProvider;

                AuthContext context = services.GetRequiredService<AuthContext>();
                if (!context.Database.CanConnect())
                {
                    Thread.Sleep(10000);
                }
                context.Database.EnsureCreated();
                DbInitializer.Initialize(context);

                RunIceServices(args, context);
            }

            app.UseCors();

            app.MapControllers();

            app.Run();
        }
    }
}
