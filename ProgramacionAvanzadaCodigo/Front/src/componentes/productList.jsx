import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import FiltroProductos from './FiltroProductos';

export default function ProductList() {
  const [products, setProducts] = useState([]); // Estado para almacenar los productos
  const [loading, setLoading] = useState(true); // Estado para manejar la carga
  const [currentPage, setCurrentPage] = useState(1); // Página actual
  const productsPerPage = 12; // Número de productos por página

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

  const indexOfLastProduct = currentPage * productsPerPage;
  const indexOfFirstProduct = indexOfLastProduct - productsPerPage;
  const currentProducts = products.slice(indexOfFirstProduct, indexOfLastProduct);

  const totalPages = Math.ceil(products.length / productsPerPage);

  const goToNextPage = () => {
    setCurrentPage((prevPage) => Math.min(prevPage + 1, totalPages));
  };

  const goToPreviousPage = () => {
    setCurrentPage((prevPage) => Math.max(prevPage - 1, 1));
  };

  return (
    <div className="px-4 sm:px-6 lg:px-8"> {/* Padding en los laterales */}
      <h2 className="text-3xl font-semibold mb-8 text-center text-gray-800">Nuestros Productos</h2>
      
      <div className="flex w-full space-x-4"> {/* Contenedor flexible de ancho completo */}
        
        <div className="w-1/9"> {/* Ajuste de ancho para ocupar una quinta parte */}
          <FiltroProductos />
        </div>
        
        <div className="w-4/1 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 lg:grid-cols-4 gap-6"> {/* Ajuste de ancho para ocupar el resto */}
          {currentProducts.map((product) => ( // Cambiado a currentProducts
            <div key={product.id} className="bg-white rounded-lg shadow-md overflow-hidden">
              <img
                src={product.imagen ? `http://localhost:8080/Imagenes/${product.imagen}` : '/placeholder.svg?height=200&width=200'}
                alt={product.nombre}
                className="w-full h-48 object-cover"
              />
              <div className="p-4 flex flex-col justify-between h-40">
                <h3 className="text-lg font-semibold mb-2 truncate" style={{ maxHeight: '3rem', overflow: 'hidden' }}>
                  {product.nombre}
                </h3>
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
      </div>

      {/* Controles de Paginación */}
      <div className="flex justify-center mt-8">
        <button onClick={goToPreviousPage} disabled={currentPage === 1} className="px-4 py-2 mx-1 bg-gray-300 rounded">
          Anterior
        </button>
        <span className="px-4 py-2 mx-1">{`Página ${currentPage} de ${totalPages}`}</span>
        <button onClick={goToNextPage} disabled={currentPage === totalPages} className="px-4 py-2 mx-1 bg-gray-300 rounded">
          Siguiente
        </button>
      </div>
      
    </div>
  );
}
