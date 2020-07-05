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
                //.UseSqlServer(Environment.GetEnvironmentVariable("DB_URI"), providerOptions => providerOptions.CommandTimeout(60))
                .UseSqlServer(@"Server=localhost\sqlexpress;Database=Products;User Id=sa;Password=P@ssw0rd!;", providerOptions => providerOptions.CommandTimeout(60)) // just for test
                .UseQueryTrackingBehavior(QueryTrackingBehavior.TrackAll);
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            //model configurations
        }

        public DbSet<Product> Products { get; set; }
    }
}
