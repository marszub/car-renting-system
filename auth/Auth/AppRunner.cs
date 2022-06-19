﻿using Auth.Data;
using Auth.SharedObject;
using Microsoft.EntityFrameworkCore;

namespace Auth
{
    public class AppRunner
    {
        private Ice.Communicator? RunIceServices(string[] args, AuthContext context)
        {
            try
            {
                Ice.Communicator communicator = Ice.Util.initialize(ref args);
                var adapter = communicator.createObjectAdapter("Adapter");

                adapter.addDefaultServant(new AccountDefault(context), "account");

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

            builder.Services.AddControllers();
            builder.Services.AddEndpointsApiExplorer();
            builder.Services.AddSwaggerGen();

            builder.Services.AddDbContext<AuthContext>(options =>
                options.UseNpgsql(builder.Configuration.GetConnectionString("AuthPostgresConnection")));

            return builder.Build();
        }

        public void Run(string[] args)
        {
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
            DbInitializer.Initialize(context);

            var communicator = RunIceServices(args, context);

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