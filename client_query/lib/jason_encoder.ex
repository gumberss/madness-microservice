alias ClientQuery.Product

defimpl Jason.Encoder, for: Product do
 def encode(%{__struct__: _} = struct, options) do
   map = struct
     |> Map.from_struct

   Jason.Encoder.Map.encode(map, options)
 end

end
