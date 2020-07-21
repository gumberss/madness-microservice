# This file is responsible for configuring your application
# and its dependencies with the aid of the Mix.Config module.
#
# This configuration file is loaded before any dependency and
# is restricted to this project.

# General application configuration
use Mix.Config

config :client_query,
  ecto_repos: [ClientQuery.Repo]

# Configures the endpoint
config :client_query, ClientQueryWeb.Endpoint,
  url: [host: "localhost"],
  secret_key_base: "zZ8niU7o7pmrsN2lxQoOZIPKDrjzEfvwb0w4GfUkY5Z8U8jsNYfVJY7c/eUAuQ0y",
  render_errors: [view: ClientQueryWeb.ErrorView, accepts: ~w(json), layout: false],
  pubsub_server: ClientQuery.PubSub,
  live_view: [signing_salt: "lkER4chA"]

# Configures Elixir's Logger
config :logger, :console,
  format: "$time $metadata[$level] $message\n",
  metadata: [:request_id]

# Use Jason for JSON parsing in Phoenix
config :phoenix, :json_library, Jason

# Import environment specific config. This must remain at the bottom
# of this file so it overrides the configuration defined above.
import_config "#{Mix.env()}.exs"
