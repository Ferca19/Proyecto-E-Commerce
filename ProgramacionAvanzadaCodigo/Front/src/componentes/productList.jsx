import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

export default function ProductList() {
  const [products, setProducts] = useState([]); // Estado para almacenar los productos
  const [loading, setLoading] = useState(true); // Estado para manejar la carga

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await axios.get('http://localhost:8080/productos/public'); // Cambia la URL si es necesario
        console.log('Respuesta de productos:', response.data); // Agregar esta línea para ver la respuesta
        setProducts(response.data); // Establece el estado con los datos obtenidos
      } catch (error) {
        console.error('Error al obtener los productos:', error);
      } finally {
        setLoading(false); // Cambia el estado de carga
      }
    };

    fetchProducts();
  }, []);

  // Renderiza un mensaje de carga mientras se obtienen los productos
  if (loading) {
    return <div>Cargando productos...</div>;
  }

  // Si no hay productos, puedes mostrar un mensaje
  if (products.length === 0) {
    return <div>No hay productos disponibles.</div>;
  }

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      {products.map((product) => (
        <div key={product.id} className="bg-white rounded-lg shadow-md overflow-hidden">
          <img
            src={product.imagen ? `http://localhost:8080/Imagenes/${product.imagen}` : '/placeholder.svg?height=200&width=200'} // Ahora solo usamos la imagen directamente
            alt={product.nombre}
            className="w-full h-48 object-cover"
          />
          <div className="p-4">
            <h3 className="text-lg font-semibold mb-2">{product.nombre}</h3>
            <p className="text-gray-600 mb-2">${product.precio.toLocaleString('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</p>
            <Link 
              to={`/productos/${product.id}`} 
              className="block w-full text-center bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition duration-300"
            >
              Ver Detalles
            </Link>
          </div>
        </div>
      ))}
    </div>
  );
}
