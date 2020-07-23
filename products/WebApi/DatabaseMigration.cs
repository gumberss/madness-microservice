using System;
using System.Linq;
using System.Threading;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;

namespace WebApi
{
    public static class DatabaseMigration
    {
        public static IHost MigrateDatabase<T>(this IHost webHost) where T : DbContext
        {
            using (var scope = webHost.Services.CreateScope())
            {
                var services = scope.ServiceProvider;

                T context = null;
                try
                {
                    context = services.GetRequiredService<T>();
                }
                catch (Exception ex)
                {
                    var logger = services.GetRequiredService<ILogger<Program>>();
                    logger.LogError(ex, $"An error occured while getting the {typeof(T).Name} context service.");
                    Console.WriteLine($"An error occured while getting the {typeof(T).Name} context service.");
                    throw;
                }

                MigrateDatabase(services, context);
            }
            return webHost;
        }

        private static void MigrateDatabase<T>(IServiceProvider services, T context, int times = 0) where T : DbContext
        {
            try
            {
                if (context.Database.GetPendingMigrations().Any())
                {
                    context.Database.Migrate();
                    Console.WriteLine("Database migrated");
                }
                else
                {
                    Console.WriteLine("There are nothing to migrate");
                }
            }
            catch (Exception ex)
            {
                var logger = services.GetRequiredService<ILogger<Program>>();
                logger.LogError( "An error occurred while migrating the database.");
                Console.WriteLine("An error occurred while migrating the database.");

                if(times == 15)
                {
                    logger.LogError(ex, "Server tried to migrate database 15 times and failed all times.");
                    Console.WriteLine($"Server tried to migrate database 15 times and failed all times. {ex}");
                    throw;
                }

                Thread.Sleep(1500);
                MigrateDatabase(services, context, ++times);
            }
        }
    }
}
