using System;

namespace Infra.Rabbit.Events
{
    public class ProductCreatedEvent
    {
        public Guid Id { get; set; }

        public String Title { get; set; }

        public decimal Price { get; set; }

        public String Description { get; set; }
    }
}
