using Infra.Rabbit.Events;

namespace Infra.Interfaces.Publishers
{
    public interface IProductCreatedPublisher: IPublisher<ProductCreatedEvent>
    {
    }
}
