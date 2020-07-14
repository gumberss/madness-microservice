import mongoose from 'mongoose'
import { updateIfCurrentPlugin } from 'mongoose-update-if-current'
import { ProductDoc } from './product'

interface StockAttrs {
	product: ProductDoc
	quantity: number
	availableQuantity: number
}

interface StockDoc extends mongoose.Document {
	product: ProductDoc
	quantity: number
	availableQuantity: number
	version: number
}

interface StockModel extends mongoose.Model<StockDoc> {
	build(attrs: StockAttrs): StockDoc
}

const { ObjectId } = mongoose.Schema.Types

const stockSchema = new mongoose.Schema(
	{
		quantity: {
			type: Number,
			required: true,
		},
		availableQuantity: {
			type: Number,
			required: true,
		},
		product: {
			type: ObjectId,
			ref: 'Product',
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

stockSchema.set('versionKey', 'version')
stockSchema.plugin(updateIfCurrentPlugin)

stockSchema.statics.build = (attrs: StockAttrs) => {
	return new Stock(attrs)
}

const Stock = mongoose.model<StockDoc, StockModel>('Stock', stockSchema)

export { Stock }
