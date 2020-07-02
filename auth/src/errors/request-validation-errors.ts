import { CustomError } from './custom-error.ts'
import { BusinessError } from './business-error.ts'

export class RequestValidationError extends CustomError {
	statusCode = 400

  constructor(public errors: BusinessError[]) {
		super('Invalid request parameters')

		Object.setPrototypeOf(this, RequestValidationError.prototype)
	}

	serializeErrors = () =>  this.errors
}
