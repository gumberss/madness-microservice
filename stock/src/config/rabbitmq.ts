import amqp from 'amqplib/callback_api'
import { Error } from 'mongoose'

class Rabbit {
	private _connection?: amqp.Connection
	private _channel?: amqp.Channel

	get connection() {
		if (!this._connection) throw new Error('The connect method must be called')

		return this._connection
	}

	get channel() {
		if (!this._channel) throw new Error('The connect method must be called')

		return this._channel
	}

	async connect(uri: string): Promise<void> {
		const connection = await this.createConnection(uri)
		const channel = await this.createChannel(connection)

		this._connection = connection
		this._channel = channel
	}

	private async createConnection(uri: string): Promise<amqp.Connection> {
		return new Promise((resolve, reject) => {
			amqp.connect(uri, function (error, connection) {
				if (error) reject(error)
				else resolve(connection)
			})
		})
	}

	private async createChannel(
		connection: amqp.Connection
	): Promise<amqp.Channel> {
		return new Promise((resolve, reject) => {
			connection.createChannel(function (error, channel) {
				if (error) reject(error)
				else resolve(channel)
			})
		})
	}

	private async createQueue(queue: string): Promise<amqp.Replies.AssertQueue> {
		return new Promise((resolve, reject) => {
			this.channel.assertQueue(queue, { exclusive: true }, function (
				error,
				queue
			) {
				if (error) reject(error)
				else resolve(queue)
			})
		})
	}

	async registerQueue(exchange: string, queueName: string): Promise<void> {
		this.channel.assertExchange(exchange, 'fanout', {
			durable: false,
		})

		const queue = await this.createQueue(queueName)

		this.channel.bindQueue(queue.queue, exchange, '')
	}
}

export const rabbitMq = new Rabbit()
