FROM hayd/alpine-deno:1.1.1

EXPOSE 9005

WORKDIR /app

RUN deno install --allow-read --allow-run --allow-write --allow-net -f --unstable https://deno.land/x/denon@v2.2.0/denon.ts


COPY deps.ts .
RUN deno cache deps.ts

ADD . .
RUN deno cache src/main.ts


ENTRYPOINT ["/root/.deno/bin/denon"]
CMD ["run","--allow-net", "--allow-read", "--allow-write", "src/main.ts"]

