import React, { useState, useEffect } from 'react';
import { Dialog, DialogTitle, Transition } from '@headlessui/react';
import { PencilIcon, TrashIcon } from 'lucide-react';
import axios from 'axios';

console.log(Dialog, Transition, PencilIcon, TrashIcon);

// Configurar el interceptor para agregar el token en cada solicitud
axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token'); // Obtener el token de localStorage
    if (token) {
      config.headers.Authorization = `Bearer ${token}`; // Agregar el token al encabezado Authorization
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default function GestionMarcas() {
  const [marcas, setMarcas] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentMarca, setCurrentMarca] = useState(null);
  const [confirmDeleteModalOpen, setConfirmDeleteModalOpen] = useState(false);
  const [marcaToDelete, setMarcaToDelete] = useState(null);
  const [error, setError] = useState(null);

  const fetchMarcas = async () => {
    try {
      const response = await axios.get('http://localhost:8080/marcas/public');
      setMarcas(response.data);
    } catch (error) {
      console.error('Error al obtener marcas:', error);
    }
  };

  useEffect(() => {
    fetchMarcas();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);

    const newMarcaData = {
      denominacion: formData.get('denominacion') || null,
      observaciones: formData.get('observaciones') || null,
    };

    try {
      const response = currentMarca
        ? await axios.put(`http://localhost:8080/marcas/admin/${currentMarca.id}`, newMarcaData)
        : await axios.post('http://localhost:8080/marcas/admin', newMarcaData);

      console.log('Respuesta del servidor:', response.data);
      fetchMarcas();
      closeModal();
    } catch (error) {
      setError(error.response?.data || 'Error al guardar la marca');
      console.error('Error al guardar la marca:', error.response?.data || error.message);
    }
  };

  const handleEdit = (marca) => {
    setCurrentMarca(marca);
    setIsModalOpen(true);
  };

  const handleDelete = (marca) => {
    setMarcaToDelete(marca);
    setConfirmDeleteModalOpen(true);
  };

  const confirmDelete = async () => {
    if (!marcaToDelete) {
      console.error("No hay marca seleccionada para eliminar");
      return;
    }

    try {
      await axios.delete(`http://localhost:8080/marcas/admin/${marcaToDelete.id}`);
      fetchMarcas();
      setConfirmDeleteModalOpen(false);
      setMarcaToDelete(null);
    } catch (error) {
      console.error("Error al eliminar la marca:", error.response?.data || error.message);
    }
  };

  const closeModal = () => {
    setIsModalOpen(false);
    setCurrentMarca(null);
    setError(null);
  };

  return (
    <div>
      <button
        onClick={() => setIsModalOpen(true)}
        className="bg-blue-500 text-white px-4 py-2 rounded mb-4"
      >
        Crear Nueva Marca
      </button>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {marcas.map((marca) => (
          <div key={marca.id} className="border p-4 rounded">
            <h2 className="text-xl font-semibold">{marca.denominacion}</h2>
            <p>{marca.observaciones}</p>
            <div className="mt-2">
              <button onClick={() => handleEdit(marca)} className="bg-yellow-500 text-white px-2 py-1 rounded mr-2">
                <PencilIcon className="h-4 w-4" />
              </button>
              <button onClick={() => handleDelete(marca)} className="bg-red-500 text-white px-2 py-1 rounded">
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
                {currentMarca ? 'Editar Marca' : 'Crear Nueva Marca'}
              </DialogTitle>
              <form onSubmit={handleSubmit} className="mt-4">
                {error && (
                  <div className="bg-red-100 text-red-800 p-2 rounded mb-4">
                    {error}
                  </div>
                )}
                <div className="mb-4">
                  <label className="block mb-1">Nombre de la marca</label>
                  <input
                    name="denominacion"
                    defaultValue={currentMarca?.denominacion || ''}
                    placeholder="Nombre de la marca"
                    className="w-full p-2 border rounded"
                    required
                  />
                </div>
                <div className="mb-4">
                  <label className="block mb-1">Descripción</label>
                  <textarea
                    name="observaciones"
                    defaultValue={currentMarca?.observaciones || ''}
                    placeholder="Descripción"
                    className="w-full p-2 border rounded"
                  />
                </div>
                <div className="flex justify-end">
                  <button type="button" onClick={closeModal} className="bg-gray-500 text-white px-4 py-2 rounded mr-2">
                    Cancelar
                  </button>
                  <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded">
                    {currentMarca ? 'Actualizar' : 'Crear'}
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
              <p>¿Está seguro de que desea eliminar esta marca?</p>
              <div className="flex justify-end mt-4">
                <button onClick={() => setConfirmDeleteModalOpen(false)} className="bg-gray-500 text-white px-4 py-2 rounded mr-2">
                  Cancelar
                </button>
                <button onClick={confirmDelete} className="bg-red-500 text-white px-4 py-2 rounded">
                  Eliminar
                </button>
              </div>
            </div>
          </div>
        </Dialog>
      </Transition>
    </div>
  );
}
