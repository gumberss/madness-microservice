import mongoose from 'mongoose'
import amqp from 'amqplib/callback_api'
import { rabbitMq } from './config/rabbitmq'

import { app } from './app'

const start = async () => {
	console.log('Starting up....')

	// if (!process.env.JWT_KEY) {
	// 	throw new Error('JWT_KEY must be defined')
	// }
	try {
		if (!process.env.MONGO_URI) {
			throw new Error('MONGO_URI must be defined')
		}

		if (!process.env.RABBITMQ_QUEUE_NAME) {
			throw new Error('RABBITMQ_QUEUE_NAME must be defined')
		}

		if (!process.env.RABBITMQ_URL) {
			throw new Error('RABBITMQ_URL must be defined')
		}

		await rabbitMq.connect(process.env.RABBITMQ_URL)
		await rabbitMq.registerQueue('ex','q')
		rabbitMq.channel.consume('q', msg => {
			console.log(msg)
		})

		await mongoose.connect(process.env.MONGO_URI, {
			useNewUrlParser: true,
			useUnifiedTopology: true,
			useCreateIndex: true,
		})
		console.log('Connected to database')
	} catch (err) {
		console.log(err)
	}

	app.listen(3000, () => console.log('Listening on port 3000!!!'))
}

start()
