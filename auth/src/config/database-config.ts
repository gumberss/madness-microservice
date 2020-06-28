import { MongoClient, Database } from '../../deps.ts'

const client = new MongoClient()

class DbWrapper {
	private _db?: Database

	get db() {
		if (!this._db) {
			throw new Error('Cannot access mongo client before connecting')
		}

		return this._db
	}

	async connect(dbUri: string, dbName: string) {
		client.connectWithUri(dbUri)

		this._db = client.database(dbName)
	}
}

export const dbWrapper = new DbWrapper()
