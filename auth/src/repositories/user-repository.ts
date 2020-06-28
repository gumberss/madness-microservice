import RepositoryBase from './repository-base.ts'
import { User } from '../models/user.ts'

export class UserRepository extends RepositoryBase<User> {
	constructor() {
		super('users')
	}
}
