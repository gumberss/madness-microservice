import { Listener } from './Listener'
import { Message } from 'amqplib'

export class ProductCreatedListener extends Listener<string> {
	constructor(queueName: string) {
		super('product:created', queueName)
	}

	async consume(content: string, msg: Message): Promise<void> {
		console.log(`created: ${content}`)
	}
}