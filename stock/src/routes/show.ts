import express, { Request, Response } from 'express'
import { Stock } from '../models/stock'
import { NotFoundError } from '@gtickets/common'

const router = express.Router()

router.get('/stock/:id', async (req: Request, res: Response) => {
  const stock = await Stock.findById(req.params.id)
  
  if(!stock){
    throw new NotFoundError()
  }

  res.send(stock)
})

export { router as showStockRouter }