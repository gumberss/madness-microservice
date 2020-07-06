using System;
using System.Linq;
using Domain.Models;
using Domain.Services;
using FluentAssertions;
using Xunit;

namespace Tests.Domain
{
    public class ProductValidatorTest
    {
        private readonly ProductValidator _service;

        public ProductValidatorTest()
        {
            _service = new ProductValidator();
        }
        [Theory]
        [InlineData(null)]
        [InlineData("")]
        public void Should_return_an_error_when_a_product_has_an_invalid_title(String title)
        {
            var product = new Product
            {
                Title = title,
                Price = 20
            };

            var errors = _service.Validate(product);

            errors.Should().HaveCount(1);
            errors.First().Field.Should().Be("Title");
            errors.First().Message.Should().Be("The product title must be valid");
        }

        [Theory]
        [InlineData(-10)]
        [InlineData(0)]
        public void Should_return_an_error_when_a_product_has_an_invalid_price(decimal price)
        {
            var product = new Product
            {
                Title = "Valid",
                Price = price
            };

            var errors = _service.Validate(product);

            errors.Should().HaveCount(1);
            errors.First().Field.Should().Be("Price");
            errors.First().Message.Should().Be("The product price must be greater than 0");
        }

        [Fact]
        public void Should_return_two_error_when_a_product_have_both_price_and_title_invalid()
        {
            var product = new Product
            {
                Title = String.Empty,
                Price = 0
            };

            var errors = _service.Validate(product);

            errors.Should().HaveCount(2);
        }

        [Fact]
        public void Should_not_return_any_errors_when_the_product_is_valid()
        {
            var product = new Product
            {
                Title = "Valid",
                Price = 10
            };

            var errors = _service.Validate(product);

            errors.Should().HaveCount(0);
        }

        [Fact]
        public void Should_return_an_error_when_a_product_is_null()
        {
            Product product = null;

            var errors = _service.Validate(product);

            errors.Should().HaveCount(1);
            errors.First().Message.Should().Be("The product must be valid");
            errors.First().Field.Should().Be("Product");
        }
    }
}
