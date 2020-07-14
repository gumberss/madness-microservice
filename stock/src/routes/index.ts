import express, { Request, Response } from 'express'
import { Stock } from '../models/stock'

const router = express.Router()

router.get('/stock', async (req: Request, res: Response) => {
	const stocks = await Stock.find()

	res.send(stocks)
})

export { router as indexStockRouter }