
using Rabbit.Events;

namespace Infra.Interfaces.Publishers
{
    public interface IProductUpdatedPublisher : IPublisher<ProductUpdatedEvent>
    {
    }
}
