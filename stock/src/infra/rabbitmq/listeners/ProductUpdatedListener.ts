import { Listener } from './Listener'
import { Message } from 'amqplib'
import { Exchanges } from '../exchanges'
import { ProductUpdatedEvent } from '../events/ProductUpdatedEvent'
import { Product } from '../../../models/product'
import { Error } from 'mongoose'

export class ProductUpdatedListener extends Listener<ProductUpdatedEvent> {
	readonly exchange = Exchanges.ProductUpdated

	constructor(queueName: string) {
		super(queueName)
	}

	async consume(content: ProductUpdatedEvent, msg: Message): Promise<void> {
		const product = await Product.findByEvent(content)

		if (!product) {
			throw new Error('Product not found')
		}

		const { title, description, price } = content

		product.set({
			title,
			description,
			price,
		})

		await product.save()

		console.log(`Product updated: ${product.id}`)
	}
}
