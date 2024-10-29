import { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import DetalleProducto from "../componentes/detalleProducto"; // Importa el nuevo componente
import Navbar from "../componentes/Navbar";

export default function VistaPreviaProductoPage() {
  const { id } = useParams();
  const [producto, setProducto] = useState(null);
  const [error, setError] = useState("");

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

  const agregarAlCarrito = (producto, cantidad, color, tamano) => {
    // Obtener carrito actual del localStorage
    let carrito = JSON.parse(localStorage.getItem("carrito")) || [];

    const productoEnCarrito = {
      productoId: id,
      nombre: producto.nombre,
      precio: producto.precio,
      cantidad: cantidad,
      subtotal: producto.precio * cantidad,
      color: color,
      tamano: tamano,
      imagen: producto.imagen // Añadir la imagen del producto
    };

    // Agregar el producto al carrito
    carrito.push(productoEnCarrito);

    // Guardar en localStorage
    localStorage.setItem("carrito", JSON.stringify(carrito));

    alert(`Producto agregado al carrito: ${cantidad}x ${producto.nombre}`);
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
          <DetalleProducto producto={producto} agregarAlCarrito={agregarAlCarrito} />
        </div>
      </div>
    </div>
  );
}
