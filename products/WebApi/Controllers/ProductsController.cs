using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Domain.Exceptions;
using Domain.Models;
using Domain.Services;
using Infra.Contexts;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;

namespace WebApi.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ProductsController : ControllerBase
    {
        private readonly ILogger<ProductsController> _logger;
        private readonly ProductsContext _productContext;
        private readonly ProductValidator _productValidator;

        public ProductsController(
            ILogger<ProductsController> logger
          , ProductsContext productContext
          , ProductValidator productValidator)
        {
            _logger = logger;
            _productContext = productContext;
            _productValidator = productValidator;
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

            return Ok(product);
        }
    }
}
