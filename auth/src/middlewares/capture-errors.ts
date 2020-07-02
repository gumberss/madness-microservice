import { Router, Request, Response, NextFunction } from '../../deps.ts'

const router = Router()

router.use(async (req: Request, res: Response, next: NextFunction) => {
	try {
    next()
	} catch (err) {
		next(err)
	}
})

export { router as CaptureErrors }
