import React, { useState } from 'react';
import { motion } from 'framer-motion'
import GestionProductos from '../componentes/GestionProductos';
import GestionCategorias from '../componentes/GestionCategorias';
import GestionSubcategorias from '../componentes/GestionSubcategorias';
import GestionMarcas from '../componentes/GestionMarcas';
import Navbar from '../componentes/Navbar';
import { Layers, ShoppingBag, Tag, Grid, BarChart2, Users, Settings } from 'lucide-react'
import { Card, CardContent, CardHeader, CardTitle } from "../componentes/ui/card"
import { Button } from "../componentes/ui/button"
import InformeDashboard from '../componentes/Informes';

// Placeholder components - replace these with your actual components
//const GestionCategorias = () => <div>Gestión de Categorías</div>
//const GestionMarcas = () => <div>Gestión de Marcas</div>
//const GestionSubcategorias = () => <div>Gestión de Subcategorías</div>

export default function AdministracionPage() {
  const [activeSection, setActiveSection] = useState('productos')

  const renderSection = () => {
    switch (activeSection) {
      case 'productos':
        return <GestionProductos />
      case 'categorias':
        return <GestionCategorias />
      case 'marcas':
        return <GestionMarcas />
      case 'subcategorias':
        return <GestionSubcategorias />
      case 'Informes':
        return <InformeDashboard/>; 
      default:
        return <GestionProductos />
    }
  }

  return (
    <div className="min-h-screen flex flex-col bg-gradient-to-br from-blue-100 to-indigo-200">
      <Navbar />
      <div className="flex-grow flex flex-col md:flex-row p-4 md:p-6 lg:p-8">
        {/* Sidebar */}
        <motion.aside 
          className="w-full md:w-64 bg-white rounded-lg shadow-lg mb-6 md:mb-0 md:mr-6"
          initial={{ opacity: 0, x: -50 }}
          animate={{ opacity: 1, x: 0 }}
          transition={{ duration: 0.5 }}
        >
          <nav className="p-4">
            <ul className="space-y-2">
              {['productos', 'categorias', 'marcas', 'subcategorias', 'Informes'].map((section) => (
                <li key={section}>
                  <Button
                    variant={activeSection === section ? "default" : "outline"}
                    className="w-full justify-start text-left capitalize transition duration-200 hover:bg-indigo-100"
                    onClick={() => setActiveSection(section)}
                  >
                    {section === 'productos' && <ShoppingBag className="mr-2 h-4 w-4" />}
                    {section === 'categorias' && <Layers className="mr-2 h-4 w-4" />}
                    {section === 'marcas' && <Tag className="mr-2 h-4 w-4" />}
                    {section === 'subcategorias' && <Grid className="mr-2 h-4 w-4" />}
                    {section === 'Informes' && <BarChart2 className="mr-2 h-4 w-4" />}
                    {section}
                  </Button>
                </li>
              ))}
            </ul>
          </nav>
        </motion.aside>

        {/* Main content */}
        <motion.main 
          className="flex-grow bg-white rounded-lg shadow-lg p-6"
          initial={{ opacity: 0, y: 50 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.5, delay: 0.2 }}
        >
          <h1 className="text-3xl font-bold mb-6 text-gray-800">
            Panel de Administración
          </h1>
          
          

          {/* Active Section Content */}
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ duration: 0.5, delay: 0.6 }}
          >
            <Card className="bg-white border-none shadow-md">
              <CardHeader>
                <CardTitle className="text-xl text-gray-800 capitalize">{activeSection}</CardTitle>
              </CardHeader>
              <CardContent>
                {renderSection()}
              </CardContent>
            </Card>
          </motion.div>
        </motion.main>
      </div>

      <footer className="bg-gray-800 text-white py-6 px-4 mt-8">
        <div className="max-w-7xl mx-auto flex flex-wrap justify-between">
          <div className="w-full sm:w-1/2 lg:w-1/3 mb-6 sm:mb-0">
            <h3 className="text-lg font-bold mb-4">Acerca de Nosotros</h3>
            <p className="text-gray-300 text-sm">Administración eficiente para tu e-commerce.</p>
          </div>
          <div className="w-full sm:w-1/2 lg:w-1/3 mb-6 sm:mb-0">
            <h3 className="text-lg font-bold mb-4">Enlaces Rápidos</h3>
            <ul className="space-y-2">
              <li><a href="#" className="text-gray-300 hover:text-white transition duration-200 text-sm">Dashboard</a></li>
              <li><a href="#" className="text-gray-300 hover:text-white transition duration-200 text-sm">Configuración</a></li>
              <li><a href="#" className="text-gray-300 hover:text-white transition duration-200 text-sm">Ayuda</a></li>
            </ul>
          </div>
          <div className="w-full sm:w-1/2 lg:w-1/3 mt-6 sm:mt-0">
            <h3 className="text-lg font-bold mb-4">Contacto</h3>
            <p className="text-gray-300 text-sm">soporte@tuecommerce.com</p>
          </div>
        </div>
      </footer>
    </div>
  )
}