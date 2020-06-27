FROM hayd/alpine-deno:1.1.1

EXPOSE 9000

WORKDIR /app

COPY .deno_plugins .

COPY deps.ts .

RUN deno run --unstable --allow-plugin --allow-net --allow-read --allow-write deps.ts

ADD . .

#RUN deno cache src/main.ts

CMD ["run", "--unstable","--allow-plugin","--allow-net", "--allow-read", "--allow-write", "src/main.ts"]
