import React from 'react';
import GestionProductos from '../componentes/GestionProductos';
import Navbar from '../componentes/Navbar';

export default function AdministracionPage() {
    return (
        <div className="min-h-screen flex flex-col">
          <Navbar /> {/* Aquí se agrega la barra de navegación */}
          <h1 className="text-2xl font-bold mb-4">Administración de Productos</h1>
          <GestionProductos />
        </div>
    );
}
