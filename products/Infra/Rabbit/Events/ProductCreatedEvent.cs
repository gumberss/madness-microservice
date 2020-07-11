using System;
using MongoDB.Bson;

namespace Infra.Rabbit.Events
{
    public class ProductCreatedEvent
    {
        public ObjectId Id { get; set; }

        public String Title { get; set; }

        public decimal Price { get; set; }

        public String Description { get; set; }

        public int Version { get; set; }
    }
}
