using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.Json;
using System.Threading.Tasks;
using Domain.Exceptions;
using Domain.Models;
using Domain.Services;
using Infra.Contexts;
using Infra.Interfaces.Publishers;
using Infra.Rabbit.Events;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using Rabbit.Events;

namespace WebApi.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ProductsController : ControllerBase
    {
        private readonly ILogger<ProductsController> _logger;
        private readonly ProductsContext _productContext;
        private readonly ProductValidator _productValidator;
        private readonly IProductUpdatedPublisher _productUpdatedPublisher;
        private readonly IProductCreatedPublisher _productCreatedPublisher;

        public ProductsController(
            ILogger<ProductsController> logger
          , ProductsContext productContext
          , ProductValidator productValidator
          , IProductUpdatedPublisher productUpdatedPublisher
          , IProductCreatedPublisher productCreatedPublisher)
        {
            _logger = logger;
            _productContext = productContext;
            _productValidator = productValidator;
            _productUpdatedPublisher = productUpdatedPublisher;
            _productCreatedPublisher = productCreatedPublisher;
        }

        [HttpGet]
        public IActionResult Get()
        {
            var products = _productContext.Products;

            return Ok(products);
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody] Product product)
        {
            var errors = _productValidator.Validate(product);

            if (errors.Any()) return BadRequest(errors);

            await _productContext.AddAsync(product);
            await _productContext.SaveChangesAsync();
            _productCreatedPublisher.Publish(new ProductCreatedEvent
            {
                Id = product.Id,
                Title = product.Title,
                Price = product.Price,
                Description = product.Description,
                Version = product.Version
            });

            return Ok(product);
        }

        [HttpPut("{id}")]   
        public async Task<IActionResult> Put(Guid id, [FromBody] Product product)
        {
            var errors = _productValidator.Validate(product);

            if (errors.Any()) return BadRequest(errors);

            var existentProduct = _productContext.Products.Find(id);

            if (existentProduct == null)
            {
                return BadRequest(new List<BusinessError>
                {
                    new BusinessError("The product could not be found to be updated", "Product")
                });
            }

            existentProduct.Title = product.Title;
            existentProduct.Price = product.Price;
            existentProduct.Description = product.Description;
            existentProduct.Version++;

            _productContext.Update(existentProduct);
            await _productContext.SaveChangesAsync();

            _productUpdatedPublisher.Publish(new ProductUpdatedEvent
            {
                Id = existentProduct.Id,
                Title = existentProduct.Title,
                Price = existentProduct.Price,
                Description = existentProduct.Description,
                Version = existentProduct.Version
            });

            return Ok(existentProduct);
        }
    }
}
