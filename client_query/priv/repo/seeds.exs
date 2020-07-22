# Script for populating the database. You can run it as:
#
#     mix run priv/repo/seeds.exs
#
# Inside the script, you can read and write to any of your
# repositories directly:
#
#     ClientQuery.Repo.insert!(%ClientQuery.SomeSchema{})
#
# We recommend using the bang functions (`insert!`, `update!`
# and so on) as they will fail if something goes wrong.
alias ClientQuery.{Repo, Product}

Repo.insert! %Product{
 id: "5f0cfdb238eea000f3ff3f1605050505",
 title: "Product 1",
 description: "The product",
 price: 10,
 version: 0
}
