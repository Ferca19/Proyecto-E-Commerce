import React, { useState, useEffect } from 'react';
import { Dialog, DialogTitle, Transition } from '@headlessui/react';
import { PencilIcon, TrashIcon } from 'lucide-react';
import axios from 'axios';

console.log(Dialog, Transition, PencilIcon, TrashIcon);

// Configurar el interceptor para agregar el token en cada solicitud
axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default function GestionSubcategorias() {
  const [subcategorias, setSubcategorias] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentSubcategoria, setCurrentSubcategoria] = useState(null);
  const [categorias, setCategorias] = useState([]);
  const [confirmDeleteModalOpen, setConfirmDeleteModalOpen] = useState(false);
  const [subcategoriaToDelete, setSubcategoriaToDelete] = useState(null);
  const [error, setError] = useState(null);

  const fetchSubcategorias = async () => {
    try {
      const response = await axios.get('http://localhost:8080/subcategorias/public');
      setSubcategorias(response.data);
    } catch (error) {
      console.error('Error al obtener subcategorías:', error);
    }
  };

  const fetchCategorias = async () => {
    try {
      const response = await axios.get('http://localhost:8080/categorias/public');
      setCategorias(response.data);
    } catch (error) {
      console.error('Error al obtener categorías:', error);
    }
  };

  useEffect(() => {
    fetchSubcategorias();
    fetchCategorias();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);

    const newSubcategoria = {
      nombre: formData.get('nombre') || null,
      descripcion: formData.get('descripcion') || null,
      categoriaId: formData.get('categoria') || null,
    };

    try {
      const response = currentSubcategoria
        ? await axios.put(`http://localhost:8080/subcategorias/admin/${currentSubcategoria.id}`, newSubcategoria)
        : await axios.post('http://localhost:8080/subcategorias/admin', newSubcategoria);

      console.log('Respuesta del servidor:', response.data);
      fetchSubcategorias();
      closeModal();
    } catch (error) {
      setError(error.response?.data || 'Error al guardar la subcategoría');
      console.error('Error al guardar la subcategoría:', error.response?.data || error.message);
    }
  };

  const handleEdit = (subcategoria) => {
    setCurrentSubcategoria(subcategoria);
    setIsModalOpen(true);
  };

  const handleDelete = (subcategoriaId) => {
    setSubcategoriaToDelete(subcategoriaId);
    setConfirmDeleteModalOpen(true);
  };

  const confirmDelete = async () => {
    if (!subcategoriaToDelete) {
      console.error("No hay subcategoría seleccionada para eliminar");
      return;
    }

    try {
      await axios.delete(`http://localhost:8080/subcategorias/admin/${subcategoriaToDelete}`);
      fetchSubcategorias();
      setConfirmDeleteModalOpen(false);
      setSubcategoriaToDelete(null);
    } catch (error) {
      console.error("Error al eliminar la subcategoría:", error.response?.data || error.message);
    }
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setCurrentSubcategoria(null);
  };

  return (
    <div>
      <button onClick={() => setIsModalOpen(true)} className="bg-blue-500 text-white px-4 py-2 rounded mb-4">
        Crear Nueva Subcategoría
      </button>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {subcategorias.map((subcategoria) => (
          <div key={subcategoria.id} className="border p-4 rounded">
            <h2 className="text-xl font-semibold">{subcategoria.nombre}</h2>
            <p>{subcategoria.descripcion}</p>
            <div className="mt-2">
              <button onClick={() => handleEdit(subcategoria)} className="bg-yellow-500 text-white px-2 py-1 rounded mr-2">
                <PencilIcon className="h-4 w-4" />
              </button>
              <button onClick={() => handleDelete(subcategoria.id)} className="bg-red-500 text-white px-2 py-1 rounded">
                <TrashIcon className="h-4 w-4" />
              </button>
            </div>
          </div>
        ))}
      </div>

      <Transition show={isModalOpen} as={React.Fragment}>
        <Dialog as="div" className="fixed inset-0 z-10 overflow-y-auto" onClose={closeModal}>
          <div className="min-h-screen px-4 text-center">
            <div className="fixed inset-0 bg-black opacity-30" />
            <span className="inline-block h-screen align-middle" aria-hidden="true">&#8203;</span>
            <div className="inline-block w-full max-w-md p-6 my-8 overflow-hidden text-left align-middle transition-all transform bg-white shadow-xl rounded-2xl">
              <DialogTitle as="h3" className="text-lg font-medium leading-6 text-gray-900">
                {currentSubcategoria ? 'Editar Subcategoría' : 'Crear Nueva Subcategoría'}
              </DialogTitle>
              <form onSubmit={handleSubmit} className="mt-4">
                {error && <div className="bg-red-100 text-red-800 p-2 rounded mb-4">{error}</div>}
                <div className="mb-4">
                  <label className="block mb-1">Nombre de la subcategoría</label>
                  <input
                    name="nombre"
                    defaultValue={currentSubcategoria?.nombre || ''}
                    placeholder="Nombre de la subcategoría"
                    className="w-full p-2 border rounded"
                    required
                  />
                </div>
                <div className="mb-4">
                  <label className="block mb-1">Descripción</label>
                  <textarea
                    name="descripcion"
                    defaultValue={currentSubcategoria?.descripcion || ''}
                    placeholder="Descripción"
                    className="w-full p-2 border rounded"
                    required
                  />
                </div>
                <div className="mb-4">
                  <label className="block mb-1">Categoría</label>
                  <select name="categoria" required className="w-full p-2 border rounded">
                    <option value="" disabled selected>Selecciona una categoría</option>
                    {categorias.map((categoria) => (
                      <option key={categoria.id} value={categoria.id}>
                        {categoria.nombre}
                      </option>
                    ))}
                  </select>
                </div>
                <div className="mt-4">
                  <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded">
                    {currentSubcategoria ? 'Guardar Cambios' : 'Crear Subcategoría'}
                  </button>
                  <button onClick={closeModal} type="button" className="ml-2 bg-gray-500 text-white px-4 py-2 rounded">
                    Cancelar
                  </button>
                </div>
              </form>
            </div>
          </div>
        </Dialog>
      </Transition>

      <Transition show={confirmDeleteModalOpen} as={React.Fragment}>
        <Dialog as="div" className="fixed inset-0 z-10 overflow-y-auto" onClose={() => setConfirmDeleteModalOpen(false)}>
          <div className="min-h-screen px-4 text-center">
            <div className="fixed inset-0 bg-black opacity-30" />
            <span className="inline-block h-screen align-middle" aria-hidden="true">&#8203;</span>
            <div className="inline-block w-full max-w-md p-6 my-8 overflow-hidden text-left align-middle transition-all transform bg-white shadow-xl rounded-2xl">
              <DialogTitle as="h3" className="text-lg font-medium leading-6 text-gray-900">
                Confirmar Eliminación
              </DialogTitle>
              <div className="mt-4">¿Estás seguro de que deseas eliminar esta subcategoría?</div>
              <div className="mt-4">
                <button onClick={confirmDelete} className="bg-red-500 text-white px-4 py-2 rounded">
                  Eliminar
                </button>
                <button onClick={() => setConfirmDeleteModalOpen(false)} className="ml-2 bg-gray-500 text-white px-4 py-2 rounded">
                  Cancelar
                </button>
              </div>
            </div>
          </div>
        </Dialog>
      </Transition>
    </div>
  );
}
