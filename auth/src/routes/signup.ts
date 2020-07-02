import { Router, validator } from '../../deps.ts'
import { RequestValidationError } from '../errors/request-validation-errors.ts'
import { BusinessError } from '../errors/business-error.ts'
import { User } from '../models/user.ts'
import { UserRepository } from '../repositories/user-repository.ts'
import { BadRequestError } from '../errors/bad-request-error.ts'

const router = Router()

router.post('/api/users/signup', async (req, res) => {
	const { email, password } = req.parsedBody as User

	let errors: BusinessError[] = []

	if (!validator.isEmail(email)) {
		errors.push({
			message: 'Email must be valid',
			field: 'email',
		})
	}

	if (!password || password.length < 4 || password.length > 20) {
		errors.push({
			message: 'Password must be between 4 and 20 characters',
			field: 'password',
		})
	}

	if (errors.length) throw new RequestValidationError(errors)

	const user = await new UserRepository().findOne({ email })

	if (user) throw new BadRequestError('Email in use')

	res.json({
		ok: 'ok',
	})
})

export { router as SignupRoute }
