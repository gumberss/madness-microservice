import mongoose from 'mongoose'
import { rabbitMq } from './infra/rabbitmq/rabbitmq'

import { app } from './app'
import { ProductCreatedListener } from './infra/rabbitmq/listeners/ProductCreatedListener'
import { ProductUpdatedListener } from "./infra/rabbitmq/listeners/ProductUpdatedListener"
import { ProductPurchasedListener } from './infra/rabbitmq/listeners/ProductPurchasedListener'

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
		console.log('Connected to database')
		
		await rabbitMq.connect(process.env.RABBITMQ_URL)
		console.log('Connected to RabbitMq')

		const listeners = [
			new ProductCreatedListener(
				`product:created:${process.env.RABBITMQ_QUEUE_NAME}`
			).listen(),
			new ProductUpdatedListener(
				`product:updated:${process.env.RABBITMQ_QUEUE_NAME}`
			).listen(),
			new ProductPurchasedListener(
				`product:purchased:${process.env.RABBITMQ_QUEUE_NAME}`
			).listen(),
		]

		await Promise.all(listeners)

		console.log('Listening all RabbitMq queues...')

	} catch (err) {
		console.log(err)
	}

	app.listen(3000, () => console.log('Listening on port 3000!!!'))
}

start()
