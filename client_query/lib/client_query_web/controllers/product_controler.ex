defmodule ClientQueryWeb.ProductController do
  use ClientQueryWeb, :controller
  alias ClientQuery.{Repo, Product}

  def index(conn, _params) do
    products = Repo.all(Product)
    json conn, products
  end
 end
