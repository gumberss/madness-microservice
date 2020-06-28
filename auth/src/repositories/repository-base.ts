import { Collection, Database } from '../../deps.ts'
import { dbWrapper } from '../config/database-config.ts'

export default abstract class Repository<T> {
	protected colection: Collection
	protected db: Database

	constructor(colectionName: string) {
		this.db = dbWrapper.db
		this.colection = this.db.collection(colectionName)
	}

	insertOne = async (data: T) => await this.colection.insertOne(data)

	insert = async (data: T[]) => await this.colection.insertMany(data)

	find = async (filter: object) => (await this.colection.find(filter)) as T[]

	findOne = async (filter: T) => (await this.colection.findOne(filter)) as T

	findById = async (id: string) =>
		await this.colection.findOne({ _id: { $oid: id } })

	updateOne = async (filter: object, update: object) =>
		await this.colection.updateOne(filter, update)

	update = async (filter: object, update: object) =>
		await this.colection.updateMany(filter, update)

	delete = async (filter: object) => await this.colection.deleteOne(filter)

	deleteOne = async (filter: object) => await this.colection.deleteOne(filter)
}
