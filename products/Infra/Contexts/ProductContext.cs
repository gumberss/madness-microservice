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
            optionsBuilder
                .UseSqlServer(Environment.GetEnvironmentVariable("DB_URI"), providerOptions => providerOptions.CommandTimeout(60))
                .UseQueryTrackingBehavior(QueryTrackingBehavior.TrackAll);
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            //model configurations
        }

        public DbSet<Product> Products { get; set; }
    }
}
