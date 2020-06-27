FROM hayd/alpine-deno:1.1.1

EXPOSE 9000

WORKDIR /app

USER deno

COPY deps.ts .
RUN deno cache deps.ts

ADD . .

#RUN deno cache src/main.ts

CMD [ "run", "--allow-net", "src/main.ts"]
