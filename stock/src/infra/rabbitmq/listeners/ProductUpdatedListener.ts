import { Listener } from './Listener';
import { Message } from 'amqplib';
import { Exchanges } from '../exchanges';
import { ProductUpdatedEvent } from '../events/ProductUpdatedEvent';

export class ProductUpdatedListener extends Listener<ProductUpdatedEvent> {
  readonly exchange = Exchanges.ProductUpdated;

	constructor(queueName: string) {
		super(queueName);
	}


	async consume(content: ProductUpdatedEvent, msg: Message): Promise<void> {
		console.log(`updated: `, content);
	}
}
