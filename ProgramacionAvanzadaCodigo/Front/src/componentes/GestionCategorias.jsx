import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Dialog, DialogTitle, Transition } from '@headlessui/react';
import { PencilIcon, TrashIcon } from 'lucide-react';

const GestionCategorias = () => {
  const [categorias, setCategorias] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentCategoria, setCurrentCategoria] = useState(null);
  const [confirmDeleteModalOpen, setConfirmDeleteModalOpen] = useState(false);
  const [categoriaToDelete, setCategoriaToDelete] = useState(null);

  // Fetch categorias
  const fetchCategorias = async () => {
    try {
      const response = await axios.get('http://localhost:8080/categorias/public');
      setCategorias(response.data);
    } catch (error) {
      console.error('Error al obtener categorías:', error);
    }
  };

  useEffect(() => {
    fetchCategorias();
  }, []);

  // Handle create/update
  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);

    const categoriaData = {
      nombre: formData.get('nombre') || '',
      descripcion: formData.get('descripcion') || '',
    };

    try {
      if (currentCategoria) {
        await axios.put(`http://localhost:8080/categorias/admin/${currentCategoria.id}`, categoriaData);
      } else {
        await axios.post('http://localhost:8080/categorias/admin', categoriaData);
      }
      fetchCategorias();
      closeModal();
    } catch (error) {
      console.error('Error al guardar la categoría:', error.response?.data || error.message);
    }
  };

  const handleEdit = (categoria) => {
    setCurrentCategoria(categoria);
    setIsModalOpen(true);
  };

  const handleDelete = (categoria) => {
    setCategoriaToDelete(categoria);
    setConfirmDeleteModalOpen(true);
  };

  const confirmDelete = async () => {
    try {
      await axios.delete(`http://localhost:8080/categorias/admin/${categoriaToDelete.id}`);
      fetchCategorias();
      setConfirmDeleteModalOpen(false);
    } catch (error) {
      console.error('Error al eliminar la categoría:', error.response?.data || error.message);
    }
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setCurrentCategoria(null);
  };

  return (
    <div>
      <button onClick={() => setIsModalOpen(true)} className="bg-blue-500 text-white px-4 py-2 rounded mb-4">
        Crear Nueva Categoría
      </button>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {categorias.map((categoria) => (
          <div key={categoria.id} className="border p-4 rounded">
            <h2 className="text-xl font-semibold">{categoria.nombre}</h2>
            <p>{categoria.descripcion}</p>
            <div className="mt-2">
              <button onClick={() => handleEdit(categoria)} className="bg-yellow-500 text-white px-2 py-1 rounded mr-2">
                <PencilIcon className="h-4 w-4" />
              </button>
              <button onClick={() => handleDelete(categoria)} className="bg-red-500 text-white px-2 py-1 rounded">
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
                {currentCategoria ? 'Editar Categoría' : 'Crear Nueva Categoría'}
              </DialogTitle>
              <form onSubmit={handleSubmit} className="mt-4">
                <div className="mb-4">
                  <label className="block mb-1">Nombre de la categoría</label>
                  <input
                    name="nombre"
                    defaultValue={currentCategoria?.nombre || ''}
                    placeholder="Nombre de la categoría"
                    className="w-full p-2 border rounded"
                    required
                  />
                </div>
                <div className="mb-4">
                  <label className="block mb-1">Descripción</label>
                  <textarea
                    name="descripcion"
                    defaultValue={currentCategoria?.descripcion || ''}
                    placeholder="Descripción"
                    className="w-full p-2 border rounded"
                    required
                  />
                </div>
                <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded">
                  {currentCategoria ? 'Guardar Cambios' : 'Crear Categoría'}
                </button>
              </form>
            </div>
          </div>
        </Dialog>
      </Transition>

      {/* Modal de confirmación de eliminación */}
      <Transition show={confirmDeleteModalOpen} as={React.Fragment}>
        <Dialog as="div" className="fixed inset-0 z-10 overflow-y-auto" onClose={() => setConfirmDeleteModalOpen(false)}>
          <div className="min-h-screen px-4 text-center">
            <div className="fixed inset-0 bg-black opacity-30" />
            <span className="inline-block h-screen align-middle" aria-hidden="true">&#8203;</span>
            <div className="inline-block w-full max-w-md p-6 my-8 overflow-hidden text-left align-middle transition-all transform bg-white shadow-xl rounded-2xl">
              <DialogTitle as="h3" className="text-lg font-medium leading-6 text-gray-900">
                Confirmar Eliminación
              </DialogTitle>
              <p>¿Estás seguro de que deseas eliminar esta categoría?</p>
              <button onClick={confirmDelete} className="bg-red-500 text-white px-4 py-2 rounded mt-4">
                Eliminar
              </button>
            </div>
          </div>
        </Dialog>
      </Transition>
    </div>
  );
};

export default GestionCategorias;
