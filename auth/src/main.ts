import { opine, json, Router, MongoClient } from '../deps.ts'
import { UserRepository } from './repositories/user-repository.ts'
import { dbWrapper } from './config/database-config.ts'

const app = opine()
const currentRoute = Router()
app.use(json())

currentRoute.get('/', async (a, b) => {
	const repo = new UserRepository()
  var aaa = await repo.findOne({ email: '123@' })
  var bbb = await repo.findById('5ef8db7800ad2cc300662d80')
  
  b.json({
    bbb,
    aaa
  })
})

app.use('/lala', currentRoute)

console.log('Start to listen')

const start = async () => {
	await dbWrapper.connect('mongodb://auth-mongo-srv:27017', 'users')

	const repo = new UserRepository()
	await repo.insert([
		{
			email: '123@',
			password: '321',
		},
	])

	app.listen({ port: 9005 }, () => console.log('Listening on port 9005'))
}

start()
