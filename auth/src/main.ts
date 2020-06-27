import { Application, Router } from '../deps.ts'

const router = new Router()

router.get('/',context => {
  context.response.body = "Hi there!"
})

const app = new Application()

app.use(router.routes())
app.use(router.allowedMethods())

await app.listen({ port: 9000 })
