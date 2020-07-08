import express from 'express'
import { json } from 'body-parser'
import 'express-async-errors'
import cookieSession from 'cookie-session'
import { errorHandler, NotFoundError, currentUser } from '@gtickets/common'


const app = express()

app.set('trust proxy', true) // nginx proxy the connection
app.use(json())
app.use(
	cookieSession({
		signed: false,
		secure: false, // because my domain is not secure (http)
		//secure: process.env.NODE_ENV !== 'test', //share cookies only with https connections
	})
)

app.use(currentUser)

app.all('*', async (req, res) => {
	throw new NotFoundError()
})

app.use(errorHandler)

export { app }