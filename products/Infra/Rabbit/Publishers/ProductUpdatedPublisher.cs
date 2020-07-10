using System;
using Infra.Interfaces.Publishers;
using Rabbit.Events;
using RabbitMQ.Client;

namespace Infra.Rabbit.Publishers
{
    public class ProductUpdatedPublisher : Publisher<ProductUpdatedEvent>, IProductUpdatedPublisher
    {
        public ProductUpdatedPublisher(IModel channel) : base(ExchangesConsts.PRODUCT_UPDATED, channel)
        {
        }
    }
}
