using Infra.Interfaces.Publishers;
using Infra.Rabbit.Events;
using RabbitMQ.Client;

namespace Infra.Rabbit.Publishers
{
    public class ProductCreatedPublisher : Publisher<ProductCreatedEvent>, IProductCreatedPublisher
    {
        public ProductCreatedPublisher(IModel channel) : base(ExchangesConsts.PRODUCT_CREATED, channel)
        {
        }
    }
}
