import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { jwtDecode } from 'jwt-decode'; 
import Carrito from '../componentes/Carrito';
import Navbar from '../componentes/Navbar';

function CarritoPage() {
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
      }));
  
      console.log("Detalles del pedido:", detallesPedido);
      
      // Obtener el token del local storage
      const token = localStorage.getItem("token");
  
      // Enviar el pedido al backend, incluyendo el token en los headers
      const response = await axios.post(
        `http://localhost:8080/usuarios/public/${usuarioId}/registrarpedido`, 
        detallesPedido,
        {
          headers: {
            Authorization: `Bearer ${token}`, // Agregar el token aquí
          },
        }
      );
  
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

  const vaciarCarrito = () => {
    localStorage.removeItem('carrito');
    setCarrito([]);
    setTotal(0);
  };

  return (
    <div>
      <Navbar /> {/* Agregar el componente Navbar aquí */}
      <Carrito 
        carrito={carrito} 
        total={total} 
        realizarPedido={realizarPedido} 
        vaciarCarrito={vaciarCarrito} 
      />
    </div>
  );
}

export default CarritoPage;
