import {
	Router,
	validator,
	makeJwt,
	setExpiration,
	Jose,
	Payload,
	validateJwt,
} from '../../deps.ts'
import { RequestValidationError } from '../errors/request-validation-errors.ts'
import { BusinessError } from '../errors/business-error.ts'
import { User } from '../models/user.ts'
import { UserRepository } from '../repositories/user-repository.ts'
import { BadRequestError } from '../errors/bad-request-error.ts'

const router = Router()

const header: Jose = {
	alg: 'HS256',
	typ: 'JWT',
}

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

	const userRepository = new UserRepository()

	const user = await userRepository.findOne({ email })
	const key = '1'

	if (user) throw new BadRequestError('Email in use')

	//todo: waiting for cryto in deno before inserting

	const userInserted = await userRepository.insertOne({
		email,
		password,
	})

	const responseUser = {
		id: userInserted.$oid,
		email,
	}

	const payload: Payload = {
		iss: JSON.stringify(responseUser),
		exp: setExpiration(new Date().getTime() + 60 * 60 * 1000), // 1 hour
	}

	res.cookie({
		name: 'jwt',
		value: makeJwt({ header, payload, key }),
	})

	res.json(responseUser)
})

export { router as SignupRoute }
