import mongoose from 'mongoose'
import { updateIfCurrentPlugin } from 'mongoose-update-if-current'
import { StockDoc } from './stock'

interface ProductAttrs {
	id: string
	title: string
	price: number
	description: string
	stock: StockDoc
}

export interface ProductDoc extends mongoose.Document {
	title: string
	price: number
	description: string
	stock: StockDoc
}

interface ProductModel extends mongoose.Model<ProductDoc> {
	build(attrs: ProductAttrs): ProductDoc,
	findByEvent(event: { id: string; version: number }): Promise<ProductDoc | null>
}

const { ObjectId } = mongoose.Schema.Types

const ProductSchema = new mongoose.Schema(
	{
		title: {
			type: String,
			required: true,
		},
		price: {
			type: Number,
			required: true,
		},
		description: {
			type: String,
			required: false,
		},
		stock: {
			type: ObjectId,
			ref: 'Stock',
		},
	},
	{
		toJSON: {
			transform(doc, ret) {
				ret.id = ret._id
				delete ret._id
			},
		},
	}
)

ProductSchema.set('versionKey', 'version')
ProductSchema.plugin(updateIfCurrentPlugin)

ProductSchema.statics.build = (attrs: ProductAttrs) => {

	const { id, title, price, stock } = attrs

	return new Product({
		_id: id,
		title,
		price,
		stock
	})
}

ProductSchema.statics.findByEvent = async (event: {
	id: string
	version: number
}) => {
	return Product.findOne({
		_id: event.id,
		version: event.version - 1,
	})
}

const Product = mongoose.model<ProductDoc, ProductModel>(
	'Product',
	ProductSchema
)

export { Product }
