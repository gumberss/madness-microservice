using System;

namespace Domain.Exceptions
{
    public class BusinessError
    {
        public BusinessError(string message, string field)
        {
            Message = message;
            Field = field;
        }

        public String Message { get; set; }
        public String Field { get; set; }
    }
}
