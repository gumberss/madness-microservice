defmodule ClientQuery.Repo.Migrations.CreateProducts do
  use Ecto.Migration

  def change do
    create table(:products, primary_key: false) do
      add :id, :string, primary_key: true
      add :title, :string
      add :price, :decimal
      add :description, :string
      add :version, :integer

      timestamps()
    end

  end
end
