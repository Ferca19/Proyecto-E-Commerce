import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import GestionMarcas from './componentes/GestionMarcas';
import GestionCategorias from './componentes/GestionCategorias';
import GestionSubcategorias from './componentes/GestionSubcategorias';
import PrivateRoute from './componentes/PrivateRoute';
import VistaPreviaProductoPage from './pages/VistaPreviaProductoPage';
import 'tailwindcss/tailwind.css';
import HomePage from './pages/HomePage';
import CarritoPage from './pages/CarritoPage';
import AdministracionPage from './pages/AdministracionPage';

function App() {
    const [showLogin, setShowLogin] = useState(false);
    const [showRegister, setShowRegister] = useState(false);

    const handleOpenLogin = () => setShowLogin(true);
    const handleCloseLogin = () => setShowLogin(false);
    const handleOpenRegister = () => setShowRegister(true);
    const handleCloseRegister = () => setShowRegister(false);

    return (
        <Router>
            <div className="min-h-screen flex flex-col">
                <Routes>
                    <Route path="/" element={<HomePage />} />
                    <Route path="/productos/:id" element={<VistaPreviaProductoPage />} />
                    <Route path="/carrito" element={<CarritoPage />} />
                    <Route path="/administracion" element={<AdministracionPage />} />
                    <Route path="/marcas" element={<PrivateRoute><GestionMarcas /></PrivateRoute>} />
                    <Route path="/categorias" element={<PrivateRoute><GestionCategorias /></PrivateRoute>} />
                    <Route path="/subcategorias" element={<GestionSubcategorias />} />
                </Routes>

                <footer className="bg-gray-100 py-4 text-center">
                    <p>&copy; 2024 Mi E-commerce. Todos los derechos reservados.</p>
                </footer>
            </div>
        </Router>
    );
}

export default App;
