import { Listener } from './Listener'
import { Message } from 'amqplib'

export class ProductUpdatedListener extends Listener<string> {
	constructor(queueName: string) {
		super('product:updated', queueName)
	}

	async consume(content: string, msg: Message): Promise<void> {
		console.log(`updated: ${content}`)
	}
}
