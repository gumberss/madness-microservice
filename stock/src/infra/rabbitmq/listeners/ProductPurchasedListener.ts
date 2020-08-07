import { Listener } from './Listener'
import { Message } from 'amqplib'
import { Exchanges } from '../exchanges'
import { ProductPurchasedEvent } from '../events/ProductPurchasedEvent'
import { Product } from '../../../models/product'
import { Stock } from '../../../models/stock'
import { ProductService } from '../../../domain-services/product-service'

export class ProductPurchasedListener extends Listener<ProductPurchasedEvent> {
	readonly exchange = Exchanges.ProductPurchased

	constructor(queueName: string) {
		super(queueName)
	}

	async consume(content: ProductPurchasedEvent, msg: Message): Promise<void> {
		const { productId, quantity, type } = content

		var product = await Product.findById(productId).populate('stock')

		if (!product) throw new Error('product not found')

		const newProductsReceived = new ProductService().productUnitQuantityByType(
			quantity,
			type
		)

		product.stock.quantity += newProductsReceived
		product.stock.availableQuantity += newProductsReceived

		await product.stock.save()

		console.log(`Stock updated: ${product.stock._id}`)
	}
}
