FROM elixir:latest

RUN apt-get update && \
  apt-get install -y postgresql-client

RUN mkdir /app
COPY mix.exs /app

WORKDIR /app

# Install hex package manager
RUN mix local.hex --force
RUN mix local.rebar --force

RUN mix deps.get

COPY . .

# Compile the project
RUN mix do compile

#CMD ["mix", "phx.server"]

CMD ["/app/entrypoint.sh"]