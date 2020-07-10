using System;
using System.Text;
using Infra.Interfaces.Publishers;
using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;
using RabbitMQ.Client;

namespace Infra.Rabbit.Publishers
{
    public abstract class Publisher<T> : IPublisher<T>
    {
        private readonly string _exchange;
        private readonly IModel _channel;

        protected Publisher(String exchange, IModel channel)
        {
            _exchange = exchange;
            _channel = channel;

            _channel.ExchangeDeclare(exchange, "fanout", false, false, null);
        }

        public void Publish(T data)
        {
            _channel.ExchangeDeclare(_exchange, "fanout", false, false, null);

            string message = JsonConvert.SerializeObject(data, new JsonSerializerSettings
            {
                ContractResolver = new CamelCasePropertyNamesContractResolver()
            });

            var body = Encoding.UTF8.GetBytes(message);

            _channel.BasicPublish(_exchange, "", null, body);
        }
    }
}
