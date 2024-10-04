import React, { useState, useEffect } from 'react';
import axios from 'axios';

function AdminProductos() {
  const [productos, setProductos] = useState([]);
  const [productoSeleccionado, setProductoSeleccionado] = useState(null);
  const [nombre, setNombre] = useState('');
  const [precio, setPrecio] = useState('');
  const [cantidad, setCantidad] = useState('');
  const [imagen, setImagen] = useState('');
  const [mensaje, setMensaje] = useState('');

  // Obtener productos al cargar el componente
  useEffect(() => {
    const obtenerProductos = async () => {
      try {
        const response = await axios.get('http://localhost:8080/productos');
        setProductos(response.data);
      } catch (error) {
        console.error('Error al obtener productos:', error);
        setMensaje('Error al cargar productos');
      }
    };

    obtenerProductos();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const nuevoProducto = {
      nombre,
      precio: parseFloat(precio),
      cantidad: parseInt(cantidad),
      imagen,
    };

    try {
      if (productoSeleccionado) {
        // Modificar producto existente
        await axios.put(`http://localhost:8080/productos/${productoSeleccionado.id}`, nuevoProducto);
        setMensaje('Producto modificado con éxito');
      } else {
        // Crear nuevo producto
        await axios.post('http://localhost:8080/productos', nuevoProducto);
        setMensaje('Producto creado con éxito');
      }

      // Reiniciar formulario y recargar productos
      setNombre('');
      setPrecio('');
      setCantidad('');
      setImagen('');
      setProductoSeleccionado(null);
      await obtenerProductos();
    } catch (error) {
      console.error('Error al guardar el producto:', error);
      setMensaje('Error al guardar el producto');
    }
  };

  const seleccionarProducto = (producto) => {
    setProductoSeleccionado(producto);
    setNombre(producto.nombre);
    setPrecio(producto.precio);
    setCantidad(producto.cantidad);
    setImagen(producto.imagen);
  };

  const eliminarProducto = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/productos/${id}`);
      setMensaje('Producto eliminado con éxito');
      await obtenerProductos(); // Recargar la lista de productos
    } catch (error) {
      console.error('Error al eliminar el producto:', error);
      setMensaje('Error al eliminar el producto');
    }
  };

  return (
    <div className="container mx-auto px-4 py-12">
      <h2 className="text-4xl font-bold mb-8">Administración de Productos</h2>

      <form onSubmit={handleSubmit} className="mb-8">
        <h3 className="text-2xl">{productoSeleccionado ? 'Modificar Producto' : 'Crear Nuevo Producto'}</h3>
        {mensaje && <p className="text-red-500">{mensaje}</p>}
        <div>
          <label>Nombre:</label>
          <input 
            type="text" 
            value={nombre} 
            onChange={(e) => setNombre(e.target.value)} 
            required 
          />
        </div>
        <div>
          <label>Precio:</label>
          <input 
            type="number" 
            value={precio} 
            onChange={(e) => setPrecio(e.target.value)} 
            required 
            step="0.01" 
          />
        </div>
        <div>
          <label>Cantidad:</label>
          <input 
            type="number" 
            value={cantidad} 
            onChange={(e) => setCantidad(e.target.value)} 
            required 
          />
        </div>
        <div>
          <label>Imagen URL:</label>
          <input 
            type="text" 
            value={imagen} 
            onChange={(e) => setImagen(e.target.value)} 
          />
        </div>
        <button type="submit" className="bg-blue-600 text-white px-4 py-2 mt-4">
          {productoSeleccionado ? 'Modificar Producto' : 'Crear Producto'}
        </button>
      </form>

      <h3 className="text-2xl mb-4">Lista de Productos</h3>
      <ul>
        {productos.map((producto) => (
          <li key={producto.id} className="flex justify-between items-center mb-4 border-b py-2">
            <span>{producto.nombre} - ${producto.precio} - Cantidad: {producto.cantidad}</span>
            <div>
              <button onClick={() => seleccionarProducto(producto)} className="bg-yellow-500 text-white px-2 py-1 mx-2">
                Editar
              </button>
              <button onClick={() => eliminarProducto(producto.id)} className="bg-red-500 text-white px-2 py-1">
                Eliminar
              </button>
            </div>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default AdminProductos;
