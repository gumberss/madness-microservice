import { json, opine } from '../deps.ts'
import { dbWrapper } from './config/database-config.ts'
import { SignupRoute } from './routes/signup.ts'
import { errorHandler } from './middlewares/error-handler.ts'

export const app = opine()
app.use(json())

app.use(SignupRoute)
app.use(errorHandler)

const start = async () => {
	await dbWrapper.connect('mongodb://auth-mongo-srv:27017', 'users')

	app.listen({ port: 9005 }, () => console.log('Listening on port 9005'))
}

start()
