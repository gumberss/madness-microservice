using System;

namespace Rabbit.Events
{
    public class ProductUpdatedEvent
    {
        public Guid Id { get; set; }

        public String Title { get; set; }

        public decimal Price { get; set; }

        public String Description { get; set; }

        public int Version { get; set; }
    }
}
