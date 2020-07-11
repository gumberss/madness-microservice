using System;
using Domain.Models;
using Microsoft.EntityFrameworkCore;

namespace Infra.Contexts
{
    public class ProductsContext : DbContext
    {
        public ProductsContext(DbContextOptions options) : base(options)
        {
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            var dbUri = Environment.GetEnvironmentVariable("DB_URI") ?? "Server=127.0.0.1,6000;Database=Products;User Id=sa;Password=P@ssw0rd!;";
            var saPassword = Environment.GetEnvironmentVariable("SA_PASSWORD") ?? "P@ssw0rd!";

            if (dbUri == null)
                throw new Exception("DB_URI must be defined");

            if (saPassword == null)
                throw new Exception("SA_PASSWORD must be defined");

            var connString = dbUri.Replace("[[PASSWORD]]", saPassword);

            optionsBuilder
                .UseSqlServer(connString, providerOptions => providerOptions.CommandTimeout(60))
                .UseQueryTrackingBehavior(QueryTrackingBehavior.TrackAll);
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            //model configurations
        }

        public DbSet<Product> Products { get; set; }
    }
}
