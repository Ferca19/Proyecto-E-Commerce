import React, { useState, useEffect, useCallback } from 'react';
import { useDropzone } from 'react-dropzone';
import { Dialog,DialogTitle , Transition } from '@headlessui/react';
import { PencilIcon, TrashIcon } from 'lucide-react';
import axios from 'axios';

console.log(Dialog, Transition, PencilIcon, TrashIcon);

export default function AdminProducts() {
  const [products, setProducts] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentProduct, setCurrentProduct] = useState(null);
  const [image, setImage] = useState(null);
  const [imageName, setImageName] = useState(null);
  const [categorias, setCategorias] = useState([]);
  const [subcategorias, setSubcategorias] = useState([]);
  const [marcas, setMarcas] = useState([]);

  const onDrop = useCallback((acceptedFiles) => {
    if (acceptedFiles.length > 0) {
      const selectedFile = acceptedFiles[0];
      setImage(selectedFile);
      setImageName(selectedFile.name);
      
      // Imprimir información del archivo en la consola
      console.log('Archivo seleccionado:', selectedFile);
      console.log('Nombre del archivo:', selectedFile.name);
      console.log('Tamaño del archivo:', selectedFile.size);
      console.log('Tipo de archivo:', selectedFile.type);
    }
  }, []);

  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

  const fetchCategorias = async () => {
    try {
        const response = await axios.get('http://localhost:8080/categorias/public'); // Asegúrate de que esta ruta sea correcta
        setCategorias(response.data);
    } catch (error) {
        console.error('Error al obtener categorías:', error);
    }
  };

  const fetchSubcategorias = async () => {
      try {
          const response = await axios.get('http://localhost:8080/subcategorias/public'); // Asegúrate de que esta ruta sea correcta
          setSubcategorias(response.data);
      } catch (error) {
          console.error('Error al obtener subcategorías:', error);
      }
  };

  const fetchMarcas = async () => {
      try {
          const response = await axios.get('http://localhost:8080/marcas/public'); // Asegúrate de que esta ruta sea correcta
          setMarcas(response.data);
      } catch (error) {
          console.error('Error al obtener marcas:', error);
      }
  };

  // Función para obtener los productos desde la API
  const fetchProducts = async () => {
    try {
      const response = await axios.get('http://localhost:8080/productos/public');
      setProducts(response.data);
    } catch (error) {
      console.error('Error al obtener productos:', error);
    }
  };

  useEffect(() => {
    fetchProducts();  // Obtener los productos cuando el componente se monte
    fetchCategorias(); // Obtener categorías
    fetchSubcategorias(); // Obtener subcategorías
    fetchMarcas(); // Obtener marcas
  }, []);

  // Manejo del submit del formulario
  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);

    // Crear el objeto que se enviará como JSON en el campo 'producto'
    const newProductModificar = {
        nombre: formData.get('nombre') || null,
        descripcion: formData.get('descripcion') || null,
        color: formData.get('color') || null,
        tamano: formData.get('tamano') || null,
        precio: formData.get('precio') ? parseFloat(formData.get('precio')) : null,
    };

    // Crear un FormData para enviar la imagen (si existe) y el producto en formato JSON
    const requestFormDataModificar = new FormData();
    const requestFormDataCrear = new FormData();

    // Si hay una imagen nueva seleccionada, añadirla
    if (imageName && currentProduct && imageName !== currentProduct.imagen) {
      requestFormDataModificar.append('imagen', image);
    }

    requestFormDataCrear.append('imagen', image);

    // Convertir el producto a JSON y añadirlo a formData bajo la key 'producto'
    requestFormDataModificar.append('producto', JSON.stringify(newProductModificar));

    const newProductCrear = {
      nombre: formData.get('nombre') || null,
      descripcion: formData.get('descripcion') || null,
      color: formData.get('color') || null,
      tamano: formData.get('tamano') || null,
      precio: formData.get('precio') ? parseFloat(formData.get('precio')) : null,
      stock: formData.get('stock') ? parseInt(formData.get('stock')) : null,
      categoriaId: formData.get('categoria') || null, // ID de la categoría
      subcategoriaId: formData.get('subcategoria') || null, // ID de la subcategoría
      marcaId: formData.get('marca') || null, // ID de la marca
    };

    

    requestFormDataCrear.append('producto', JSON.stringify(newProductCrear));

  

    try {
        const response = currentProduct
            ? await axios.put(`http://localhost:8080/productos/admin/${currentProduct.id}`, requestFormDataModificar, {
                headers: {
                    'Content-Type': 'multipart/form-data', // Especifica que el contenido es multipart/form-data
                },
            })
            : await axios.post('http://localhost:8080/productos/admin', requestFormDataCrear, {
                headers: {
                    'Content-Type': 'multipart/form-data',
                },
            });

        console.log('Respuesta del servidor:', response.data);
        fetchProducts();
        closeModal();
    } catch (error) {
        console.error('Error al guardar el producto:', error.response?.data || error.message);
    }
};

// Abrir modal para edición
const handleEdit = (product) => {
    setCurrentProduct(product);
    
    setIsModalOpen(true);
};

// Manejar selección de nueva imagen
const handleImageChange = (e) => {
  const file = e.target.files[0];
  setImage(file);
  setImageName(file.name); // Guarda solo el nombre de la imagen
};


  // Eliminar producto
  const handleDelete = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/productos/admin/${id}`);
      fetchProducts();  // Refrescar la lista de productos después de eliminar
    } catch (error) {
      console.error('Error al eliminar el producto:', error);
    }
  };

  // Cerrar modal y resetear estado
  const closeModal = () => {
    setIsModalOpen(false);
    setCurrentProduct(null);
    setImage(null);
    setImageName(null);
  };

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-2xl font-bold mb-4">Administración de Productos</h1>
      <button
        onClick={() => setIsModalOpen(true)}
        className="bg-blue-500 text-white px-4 py-2 rounded mb-4"
      >
        Crear Nuevo Producto
      </button>
  
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {products.map((product) => (
          <div key={product.id} className="border p-4 rounded">
            <img
              src={product.imagen ? `http://localhost:8080/Imagenes/${product.imagen}` : '/placeholder.svg?height=200&width=200'}
              alt={product.nombre}
              className="w-full h-64 object-cover mb-2"
            />
            <h2 className="text-xl font-semibold">{product.nombre}</h2>
            <p>{product.descripcion}</p>
            <p>Color: {product.color}</p>
            <p>Tamaño: {product.tamano}</p>
            <p>Precio: ${product.precio.toLocaleString('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</p>
            <p>Stock: {product.stock}</p>
            <div className="mt-2">
              <button onClick={() => handleEdit(product)} className="bg-yellow-500 text-white px-2 py-1 rounded mr-2">
                <PencilIcon className="h-4 w-4" />
              </button>
              <button onClick={() => handleDelete(product.id)} className="bg-red-500 text-white px-2 py-1 rounded">
                <TrashIcon className="h-4 w-4" />
              </button>
            </div>
          </div>
        ))}
      </div>
  
      <Transition show={isModalOpen} as={React.Fragment}>
        <Dialog
          as="div"
          className="fixed inset-0 z-10 overflow-y-auto"
          onClose={closeModal}
        >
          <div className="min-h-screen px-4 text-center">
            <div className="fixed inset-0 bg-black opacity-30" />
            <span className="inline-block h-screen align-middle" aria-hidden="true">&#8203;</span>
            <div className="inline-block w-full max-w-md p-6 my-8 overflow-hidden text-left align-middle transition-all transform bg-white shadow-xl rounded-2xl">
              <DialogTitle as="h3" className="text-lg font-medium leading-6 text-gray-900">
                {currentProduct ? 'Editar Producto' : 'Crear Nuevo Producto'}
              </DialogTitle>
              <form onSubmit={handleSubmit} className="mt-4">
                {/* Campos comunes para crear y editar */}
                <div className="mb-4">
                  <label className="block mb-1">Nombre del producto</label>
                  <input
                    name="nombre"
                    defaultValue={currentProduct?.nombre || ''}
                    placeholder="Nombre del producto"
                    className="w-full p-2 border rounded"
                    required
                  />
                </div>
                <div className="mb-4">
                  <label className="block mb-1">Descripción</label>
                  <textarea
                    name="descripcion"
                    defaultValue={currentProduct?.descripcion || ''}
                    placeholder="Descripción"
                    className="w-full p-2 border rounded"
                    required
                  />
                </div>
                <div className="mb-4">
                  <label className="block mb-1">Color</label>
                  <input
                    name="color"
                    defaultValue={currentProduct?.color || ''}
                    placeholder="Color"
                    className="w-full p-2 border rounded"
                    required
                  />
                </div>
                <div className="mb-4">
                  <label className="block mb-1">Tamaño</label>
                  <input
                    name="tamano"
                    defaultValue={currentProduct?.tamano || ''}
                    placeholder="Tamaño"
                    className="w-full p-2 border rounded"
                    required
                  />
                </div>
                <div className="mb-4">
                  <label className="block mb-1">Precio</label>
                  <input
                    name="precio"
                    type="number"
                    step="500"
                    defaultValue={currentProduct ? currentProduct.precio : 0}
                    placeholder="Precio"
                    className="w-full p-2 border rounded"
                    required
                  />
                </div>
  
                {/* Campos solo para crear */}
                {!currentProduct && (
                  <>
                    <div className="mb-4">
                      <label className="block mb-1">Stock</label>
                      <input
                        name="stock"
                        type="number"
                        step="5"
                        defaultValue={0}
                        placeholder="Stock"
                        className="w-full p-2 border rounded"
                        required
                      />
                    </div>
  
                    {/* Selección de categoría */}
                    <div className="mb-4">
                      <label className="block mb-1">Categoría</label>
                      <select name="categoria" required className="w-full p-2 border rounded">
                        <option value="" disabled selected>Selecciona una categoría</option>
                        {categorias.map((category) => (
                          <option key={category.id} value={category.id}>
                            {category.nombre}
                          </option>
                        ))}
                      </select>
                    </div>
  
                    {/* Selección de subcategoría */}
                    <div className="mb-4">
                      <label className="block mb-1">Subcategoría</label>
                      <select name="subcategoria" required className="w-full p-2 border rounded">
                        <option value="" disabled selected>Selecciona una subcategoría</option>
                        {subcategorias.map((subcategory) => (
                          <option key={subcategory.id} value={subcategory.id}>
                            {subcategory.nombre}
                          </option>
                        ))}
                      </select>
                    </div>
  
                    {/* Selección de marca */}
                    <div className="mb-4">
                      <label className="block mb-1">Marca</label>
                      <select name="marca" required className="w-full p-2 border rounded">
                        <option value="" disabled selected>Selecciona una marca</option>
                        {marcas.map((brand) => (
                          <option key={brand.id} value={brand.id}>
                            {brand.denominacion}
                          </option>
                        ))}
                      </select>
                    </div>
                  </>
                )}
  
                {/* Campo de imagen */}
                <div className="border-2 border-dashed border-gray-300 p-4 text-center cursor-pointer mb-4">
                  <label className="block mb-1">Imagen</label>
                  <input 
                    type="file"
                    name="imagen"
                    accept="image/*"
                    onChange={handleImageChange}  // Vinculando el evento de cambio de imagen
                  />
                  {image && <img src={URL.createObjectURL(image)} alt="Preview" className="mt-2 max-h-40 mx-auto" />}
                </div>
  
                <div className="mt-4">
                  <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded mr-2">
                    {currentProduct ? 'Actualizar' : 'Crear'}
                  </button>
                  <button type="button" onClick={closeModal} className="bg-gray-300 px-4 py-2 rounded">
                    Cancelar
                  </button>
                </div>
              </form>
            </div>
          </div>
        </Dialog>
      </Transition>
    </div>
  );
  
}
