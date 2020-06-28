import { opine, Router } from '../deps.ts'

console.log('Start to listen')

const app = opine()

const currentRoute = Router()

currentRoute.get('/', (a, b) => {
	b.send({
		title: 'hii',
	})
})

app.use('/lala', currentRoute)
console.log('Start to listen')
app.listen({ port: 9005 }, () => console.log('Listening on port 9005'))
