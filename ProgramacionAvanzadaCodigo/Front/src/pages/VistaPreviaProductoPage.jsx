import { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import DetalleProducto from "../componentes/detalleProducto"; // Importa el nuevo componente
import Navbar from "../componentes/Navbar";

export default function VistaPreviaProductoPage() {
  const { id } = useParams();
  const [producto, setProducto] = useState(null);
  const [error, setError] = useState("");
  const [carrito, setCarrito] = useState(() => {
    // Cargar carrito desde localStorage al inicializar el estado
    const storedCarrito = localStorage.getItem("carrito");
    return storedCarrito ? JSON.parse(storedCarrito) : [];
  });

  useEffect(() => {
    const fetchProducto = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/productos/public/${id}`);
        const producto = response.data;
        if (!producto) {
          throw new Error("Producto no encontrado");
        }
        setProducto(producto);
      } catch (error) {
        console.error("Error al obtener el producto:", error);
        setError("No se pudo cargar el producto. Por favor, intenta más tarde.");
      }
    };

    fetchProducto();
  }, [id]);

  const agregarProducto = (nuevoProducto) => {
    const productoExistenteIndex = carrito.findIndex(item => item.id === nuevoProducto.id);
    const nuevoCarrito = [...carrito];

    if (productoExistenteIndex !== -1) {
      // Si el producto ya existe, aumentar cantidad
      nuevoCarrito[productoExistenteIndex].cantidad += nuevoProducto.cantidad || 1;
      nuevoCarrito[productoExistenteIndex].subtotal = nuevoCarrito[productoExistenteIndex].cantidad * nuevoCarrito[productoExistenteIndex].precio;
    } else {
      // Si no existe, añadirlo con la cantidad proporcionada
      nuevoCarrito.push({ ...nuevoProducto, subtotal: nuevoProducto.precio * nuevoProducto.cantidad });
    }

    setCarrito(nuevoCarrito);
    localStorage.setItem("carrito", JSON.stringify(nuevoCarrito));
    alert(`Producto agregado al carrito: ${nuevoProducto.cantidad}x ${nuevoProducto.nombre}`);
  };

  if (error) {
    return <div className="text-red-600">{error}</div>;
  }

  if (!producto) {
    return <div>Cargando producto...</div>;
  }

  return (
    <div className="min-h-screen flex flex-col">
      <Navbar /> {/* Añadiendo el componente Navbar aquí */}
      <div className="flex-grow container mx-auto px-4 py-8">
        <div className="grid md:grid-cols-2 gap-8">
          <div className="relative aspect-square">
            <img
              src={producto.imagen ? `http://localhost:8080/Imagenes/${producto.imagen.split('\\').pop()}` : '/placeholder.svg?height=200&width=200'}
              alt={producto.nombre}
              className="rounded-lg object-cover w-full h-full"
            />
          </div>
          <DetalleProducto producto={producto} agregarAlCarrito={agregarProducto} />
        </div>
      </div>
    </div>
  );
}
