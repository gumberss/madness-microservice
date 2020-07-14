import mongoose from 'mongoose'
import { updateIfCurrentPlugin } from 'mongoose-update-if-current'

interface ProductAttrs {
	id: string
	title: string
	price: string
	description: string
}

export interface ProductDoc extends mongoose.Document {
	title: string
	price: string
	description: string
}

interface ProductModel extends mongoose.Model<ProductDoc> {
	build(attrs: ProductAttrs): ProductDoc,
	findByEvent(event: { id: string; version: number }): Promise<ProductDoc | null>
}

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

	const { id, title, price } = attrs

	return new Product({
		_id: id,
		title,
		price
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
