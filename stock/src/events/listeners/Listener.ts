import { rabbitMq } from '../../config/rabbitmq'
import { Message } from 'amqplib'

export abstract class Listener<T> {
	constructor(protected exchange: string, protected queue: string) {}

	async listen() {
		await rabbitMq.registerQueue(this.exchange, this.queue)

		rabbitMq.channel.consume(this.queue, async msg => {
			if (!msg)
				throw new Error(
					`The message received from exchange ${this.exchange} to queue ${this.queue} is not valid: ${msg}`
				)

			const content = msg.content.toString()

			//open mongo transaction

			try {
				await this.consume(content, msg)
			} catch (err) {
				console.log(
					`An error occurred. Exchange: ${this.exchange} | Queue: ${this.queue} | Error: ${err}`
				)
				//rollback
			}
		})
	}

	abstract async consume(content: string, msg: Message): Promise<void>
}
