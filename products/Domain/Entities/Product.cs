using System;

namespace Domain.Models
{
    public class Product
    {
        public Guid Id { get; set; }

        public String Title { get; set; }

        public decimal Price { get; set; }

        public String Description { get; set; }
    }
}
