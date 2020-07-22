defmodule ClientQuery.Product do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key {:id, :string, autogenerate: false}

  schema "products" do
    field :description, :string
    field :price, :decimal
    field :title, :string
    field :version, :integer

    timestamps()
  end

  @doc false
  def changeset(product, attrs) do
    product
    |> cast(attrs, [:title, :price, :description, :version])
    |> validate_required([:title, :price, :description, :version])
  end
end
