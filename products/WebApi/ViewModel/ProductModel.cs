using System;
using Domain.Extensions;
using Domain.Models;

namespace WebApi.ViewModel
{
    public class ProductModel
    {
        private ProductModel() { }

        public ProductModel(Product product)
        {
            Id = product.Id.AsObjectId().ToString();
            Description = product.Description;
            Price = product.Price;
            Title = product.Title;
            Version = product.Version;
        }

        public String Id { get; set; }

        public String Title { get; set; }

        public decimal Price { get; set; }

        public String Description { get; set; }

        public int Version { get; set; }

        public static implicit operator Product(ProductModel product)
        {
            if (product is null) return null;

            return new Product
            {
                Id = product.Id?.AsObjectId().AsGuid() ?? Guid.Empty,
                Description = product.Description,
                Price = product.Price,
                Title = product.Title,
                Version = product.Version
            };
        }
    }
}
