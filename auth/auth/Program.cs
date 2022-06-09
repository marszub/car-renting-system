using auth.Data;
using Microsoft.EntityFrameworkCore;

namespace auth
{
    public class Program
    {
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

                var context = services.GetRequiredService<AuthContext>();
                if (!context.Database.CanConnect())
                {
                    Thread.Sleep(10000);
                }
                context.Database.EnsureCreated();
                DbInitializer.Initialize(context);
            }

            app.UseCors();

            app.MapControllers();

            app.Run();
        }
    }
}
