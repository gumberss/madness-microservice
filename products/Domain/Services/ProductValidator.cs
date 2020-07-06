using System;
using System.Collections.Generic;
using Domain.Exceptions;
using Domain.Models;

namespace Domain.Services
{
    public class ProductValidator
    {
        public List<BusinessError> Validate(Product product)
        {
            var errors = new List<BusinessError>(2);

            if (String.IsNullOrEmpty(product.Title))
            {
                errors.Add(new BusinessError("The product title must be valid", "Title"));
            }

            if (product.Price <= 0)
            {
                errors.Add(new BusinessError("The product price must be greater than 0", "Price"));
            }

            return errors;
        }
    }
}
