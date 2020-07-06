using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Domain.Models;
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

        public ProductsController(ILogger<ProductsController> logger, ProductsContext productContext)
        {
            _logger = logger;
            _productContext = productContext;
        }

        [HttpGet]
        public IActionResult Get()
        {
            var products = _productContext.Set<Product>().ToList();

            return Ok(products);
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody] Product product)
        {
            await _productContext.AddAsync(product);

            await _productContext.SaveChangesAsync();

            return Ok(product);
        }
    }
}
