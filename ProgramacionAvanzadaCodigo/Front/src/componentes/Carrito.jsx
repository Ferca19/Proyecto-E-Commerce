import React, { useState, useEffect } from 'react';

function Carrito() {
  const [carrito, setCarrito] = useState([]);
  const [total, setTotal] = useState(0);

  useEffect(() => {
    // Obtener carrito del localStorage
    const carritoGuardado = JSON.parse(localStorage.getItem("carrito")) || [];
    setCarrito(carritoGuardado);

    // Calcular el total
    const totalCalculado = carritoGuardado.reduce((acc, item) => acc + item.subtotal, 0);
    setTotal(totalCalculado);
  }, []);

  if (carrito.length === 0) {
    return <div>No hay productos en el carrito.</div>;
  }

  return (
    <div>
      <h2 className="text-2xl font-bold mb-4">Carrito de Compras</h2>
      <ul>
        {carrito.map((item, index) => (
          <li key={index} className="border-b py-2">
            <p>{item.nombre} - {item.cantidad} x ${item.precio.toFixed(2)} = ${item.subtotal.toFixed(2)}</p>
            <p>Color: {item.color}, Tamaño: {item.tamano}</p>
          </li>
        ))}
      </ul>
      <div className="mt-4 text-lg font-semibold">
        Total: ${total.toFixed(2)}
      </div>
    </div>
  );
}

export default Carrito;
