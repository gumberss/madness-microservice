import { CustomError } from '../errors/custom-error.ts'
import { Request, Response, NextFunction } from '../../deps.ts'

export const errorHandler =  (
	err: CustomError,
	req: Request,
	res: Response,
	next: NextFunction
) => {
	res.setStatus(err.statusCode).send(err)
}
