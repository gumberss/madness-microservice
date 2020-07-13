import { rabbitMq } from '../rabbitmq'
import { Message } from 'amqplib'
import { Exchanges } from '../exchanges'

export abstract class Listener<T> {
	abstract readonly exchange: Exchanges

	constructor(protected queue: string) {}

	async listen() {
		await rabbitMq.registerQueue(this.exchange, this.queue)

		rabbitMq.channel.consume(
			this.queue,
			async msg => {
				if (!msg)
					throw new Error(
						`The message received from exchange ${this.exchange} to queue ${this.queue} is not valid: ${msg}`
					)

				const contentString = msg.content.toString()
				const content = JSON.parse(contentString)

				//open mongo transaction

				try {
					await this.consume(content as T, msg)

					rabbitMq.channel.ack(msg, true)
				} catch (err) {
					console.log(
						`An error occurred. Exchange: ${this.exchange} | Queue: ${this.queue} | Error: ${err}`
					)
					rabbitMq.channel.ack(msg, false)
					//rollback mongo transaction
				}
			}
		)
	}

	abstract async consume(content: T, msg: Message): Promise<void>
}
