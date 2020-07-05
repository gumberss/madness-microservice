using System.Collections.Generic;
using System.Threading.Tasks;
using Domain.Models;
using Infra.Contexts;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;

namespace WebApi.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class ProductController : ControllerBase
    {
        private readonly ILogger<ProductController> _logger;
        private readonly ProductContext _productContext;

        public ProductController(ILogger<ProductController> logger, ProductContext productContext)
        {
            _logger = logger;
            _productContext = productContext;
        }

        [HttpGet]
        public IEnumerable<Product> Get()
        {
            return new List<Product>{
            new Product(){
                Description = "teste",

            }
            };
        }

        [HttpPost]
        public async Task<IActionResult> Post([FromBody] Product product)
        {
            await _productContext.AddAsync(product);

            return Ok(product);
        }
    }
}
