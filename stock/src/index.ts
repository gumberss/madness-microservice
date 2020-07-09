import mongoose from 'mongoose'
import { rabbitMq } from './config/rabbitmq'

import { app } from './app'
import { ProductCreatedListener } from './events/listeners/ProductCreatedListener'
import { ProductUpdatedListener } from './events/listeners/ProductUpdatedListener'

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

		await mongoose.connect(process.env.MONGO_URI, {
			useNewUrlParser: true,
			useUnifiedTopology: true,
			useCreateIndex: true,
		})
		await rabbitMq.connect(process.env.RABBITMQ_URL)

		const listeners = [
			new ProductCreatedListener(
				`product:created:${process.env.RABBITMQ_QUEUE_NAME}`
			).listen(),
			new ProductUpdatedListener(
				`product:updated:${process.env.RABBITMQ_QUEUE_NAME}`
			).listen(),
		]

		await Promise.all(listeners)

		console.log('Connected to database')
	} catch (err) {
		console.log(err)
	}

	app.listen(3000, () => console.log('Listening on port 3000!!!'))
}

start()
