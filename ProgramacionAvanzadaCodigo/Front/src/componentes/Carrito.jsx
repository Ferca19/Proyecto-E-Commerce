import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode'; 

function Carrito() {
  const [carrito, setCarrito] = useState([]);
  const [total, setTotal] = useState(0);
  const [usuarioId, setUsuarioId] = useState(null);
  const navigate = useNavigate(); // Hook para navegar

  useEffect(() => {
    const token = localStorage.getItem("token");
  
    if (!token) {
      navigate("/");
      return;
    }
  
    try {
      const decodedToken = jwtDecode(token);
      setUsuarioId(decodedToken.userId); 
  
      const carritoGuardado = JSON.parse(localStorage.getItem("carrito")) || [];
      setCarrito(carritoGuardado);
  
      const totalCalculado = carritoGuardado.reduce((acc, item) => acc + item.subtotal, 0);
      setTotal(totalCalculado);
    } catch (error) {
      console.error("Error decodificando el token:", error);
      navigate("/");
    }
  }, [navigate]);

  const realizarPedido = async () => {
    try {
      // Preparar los datos del pedido
      const detallesPedido = carrito.map(item => ({
        productoId: item.productoId,
        cantidad: item.cantidad,
        subtotal: item.subtotal,
      }));

      console.log("Detalles del pedido:", detallesPedido);
      // Enviar el pedido al backend
      const response = await axios.post(`http://localhost:8080/usuarios/${usuarioId}/registrarpedido`, detallesPedido);

      if (response.status === 201) {
        alert('Pedido realizado con éxito');
        // Limpiar el carrito después de realizar el pedido
        localStorage.removeItem('carrito');
        setCarrito([]);
        setTotal(0);
      } else {
        alert('Hubo un problema al realizar el pedido');
      }
    } catch (error) {
      console.error('Error al realizar el pedido:', error);
      alert('Error al procesar el pedido. Intenta de nuevo.');
    }
  };

  if (carrito.length === 0) {
    return <div className="text-2xl">No hay productos en el carrito.</div>;
  }


  const vaciarCarrito = () => {
    localStorage.removeItem('carrito');
    setCarrito([]);
    setTotal(0);
  };

  if (carrito.length === 0) {
    return <div className="text-2xl">No hay productos en el carrito.</div>;
  }


  return (
    <div className="container mx-auto px-4 py-12">
      <h2 className="text-4xl font-bold mb-8">Carrito de Compras</h2>
      <ul className="space-y-6">
        {carrito.map((item, index) => (
          <li key={index} className="border-b-2 py-6 flex items-center">
            <img
              src={item.imagen ? `http://localhost:8080/Imagenes/${item.imagen}` : '/placeholder.svg?height=200&width=200'}
              alt={item.nombre}
              className="w-32 h-32 object-cover mr-8 rounded-lg"
            />
            <div className="text-xl">
              <p>{item.nombre} - {item.cantidad} x ${item.precio.toFixed(2)} = ${item.subtotal.toFixed(2)}</p>
              <p>Color: {item.color}, Tamaño: {item.tamano}</p>
            </div>
          </li>
        ))}
      </ul>
      <div className="mt-8 text-3xl font-semibold">
        Total: ${total.toFixed(2)}
      </div>

      <button
        onClick={realizarPedido}
        className="mt-10 px-8 py-4 bg-green-600 text-white text-2xl rounded-lg hover:bg-green-700 w-full"
      >
        Realizar Pedido
      </button>

      <button
        onClick={vaciarCarrito}
        className="mt-4 px-8 py-4 bg-red-600 text-white text-2xl rounded-lg hover:bg-red-700 w-full"
      >
        Vaciar Carrito
      </button>
    </div>
  );
}

export default Carrito;