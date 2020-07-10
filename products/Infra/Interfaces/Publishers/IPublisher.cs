namespace Infra.Interfaces.Publishers
{
    public interface IPublisher<in T>
    {
        void Publish(T data);
    }
}
