import { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import { MinusIcon, PlusIcon, ShoppingCart } from "lucide-react";

function Button({ children, onClick, disabled }) {
  return (
    <button
      onClick={onClick}
      disabled={disabled}
      className={`px-4 py-2 rounded ${disabled ? "bg-gray-300" : "bg-indigo-600 text-white"} hover:bg-indigo-700`}
    >
      {children}
    </button>
  );
}

function Select({ label, value, onChange, options }) {
  return (
    <div>
      <label className="block text-sm font-medium text-gray-700 mb-1">{label}</label>
      <select
        value={value}
        onChange={(e) => onChange(e.target.value)}
        className="w-full border-gray-300 rounded-lg"
      >
        {options.map((option) => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </select>
    </div>
  );
}

export default function VistaPrevia() {
  const { id } = useParams();
  const [producto, setProducto] = useState(null);
  const [color, setColor] = useState("");
  const [tamano, setTamano] = useState("");
  const [cantidad, setCantidad] = useState(1);
  const [error, setError] = useState("");

  useEffect(() => {
    const fetchProducto = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/productos/${id}`);
        const producto = response.data;
        if (!producto) {
          throw new Error("Producto no encontrado");
        }
        setProducto(producto);
        setColor(producto.color || "");
        setTamano(producto.tamano || "");
      } catch (error) {
        console.error("Error al obtener el producto:", error);
        setError("No se pudo cargar el producto. Por favor, intenta más tarde.");
      }
    };

    fetchProducto();
  }, [id]);

  const handleCantidadChange = (increment) => {
    setCantidad((prev) => Math.max(1, prev + increment));
  };

  const agregarAlCarrito = () => {
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
    <div className="container mx-auto px-4 py-8">
      <div className="grid md:grid-cols-2 gap-8">
        <div className="relative aspect-square">
          <img
            src={producto.imagen ? `http://localhost:8080/Imagenes/${producto.imagen.split('\\').pop()}` : '/placeholder.svg?height=200&width=200'}
            alt={producto.nombre}
            className="rounded-lg object-cover w-full h-full"
          />
        </div>
        <div className="space-y-6">
          <h1 className="text-3xl font-bold text-gray-900">{producto.nombre}</h1>
          <p className="text-2xl font-semibold text-gray-700">${producto.precio.toLocaleString('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</p>
          <p className="text-gray-600">{producto.descripcion}</p>

          <div className="space-y-4">
            <Select label="Color" value={color} onChange={setColor} options={[color]} />
            <Select label="Tamaño" value={tamano} onChange={setTamano} options={[tamano]} />

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">Cantidad</label>
              <div className="flex items-center space-x-3">
                <Button onClick={() => handleCantidadChange(-1)} disabled={cantidad <= 1}>
                  <MinusIcon className="h-4 w-4" />
                </Button>
                <span className="text-xl font-semibold">{cantidad}</span>
                <Button onClick={() => handleCantidadChange(1)}>
                  <PlusIcon className="h-4 w-4" />
                </Button>
              </div>
            </div>
          </div>

          <Button onClick={agregarAlCarrito} className="w-full">
            <ShoppingCart className="mr-2 h-5 w-5" />
            Agregar al Carrito
          </Button>
        </div>
      </div>
    </div>
  );
}
