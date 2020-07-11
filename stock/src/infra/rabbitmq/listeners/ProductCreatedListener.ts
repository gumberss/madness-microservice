import { Listener } from './Listener'
import { Message } from 'amqplib'
import { Exchanges } from '../exchanges'
import { ProductCreatedEvent } from '../events/ProductCreatedEvent'

export class ProductCreatedListener extends Listener<ProductCreatedEvent> {

	readonly exchange = Exchanges.ProductCreated

	constructor(queueName: string) {
		super(queueName)
	}

	async consume(content: ProductCreatedEvent, msg: Message): Promise<void> {
		console.log(`created: `, content)
	}
}
