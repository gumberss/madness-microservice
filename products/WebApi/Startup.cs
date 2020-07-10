using System;
using Domain.Services;
using Infra.Contexts;
using Infra.Interfaces.Publishers;
using Infra.Rabbit;
using Infra.Rabbit.Publishers;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using RabbitMQ.Client;

namespace WebApi
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        public void ConfigureServices(IServiceCollection services)
        {
            services.AddHealthChecks();

            services.AddControllers();

            services.AddAuthorization();

            services.AddSingleton(provider =>
                new ConnectionFactory()
                {
                    HostName = Environment.GetEnvironmentVariable("RABBITMQ_URL"),
                }
                .CreateConnection()
            );

            services.AddScoped(provider => provider.GetRequiredService<IConnection>().CreateModel());

            services.AddScoped<ProductValidator>();

            services.AddScoped<IProductUpdatedPublisher, ProductUpdatedPublisher>();
            services.AddScoped<IProductCreatedPublisher, ProductCreatedPublisher>();

            services.AddDbContext<ProductsContext>();
        }

        public void Configure(IApplicationBuilder app, IWebHostEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }

            app.UseHttpsRedirection();

            app.UseRouting();

            app.UseAuthorization();

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllers();
            });
        }
    }
}
