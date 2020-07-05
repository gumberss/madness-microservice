using System;
using Domain.Models;
using Microsoft.EntityFrameworkCore;

namespace Infra.Contexts
{
    public class ProductContext : DbContext
    {
        public ProductContext(DbContextOptions options) : base(options)
        {
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            var dbUri = Environment.GetEnvironmentVariable("DB_URI");
            var saPassword = Environment.GetEnvironmentVariable("SA_PASSWORD");

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
