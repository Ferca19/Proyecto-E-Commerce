import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './componentes/Navbar';
import Home from './pages/Home';
import Marcas from './componentes/Marcas';
import Categorias from './componentes/Categorias';
import PrivateRoute from './componentes/PrivateRoute';
import Login from "./componentes/Login";
import Registro from "./componentes/Register";
import Carrito from "./componentes/Carrito";
import VistaPrevia from './componentes/detalleProducto'; // Importa tu componente de detalle
import Modal from "./componentes/modal"; // Asegúrate de importar el Modal
import 'tailwindcss/tailwind.css'; // Asegúrate de que esta línea esté presente

function App() {
    const [showLogin, setShowLogin] = useState(false);  // Estado para manejar la visibilidad del login modal
    const [showRegister, setShowRegister] = useState(false);  // Estado para el registro modal

    const handleOpenLogin = () => {
        setShowLogin(true);
    };

    const handleCloseLogin = () => {
        setShowLogin(false);
    };

    const handleOpenRegister = () => {
        setShowRegister(true);
    };

    const handleCloseRegister = () => {
        setShowRegister(false);
    };

    return (
        <Router>
            <div className="min-h-screen flex flex-col">
                <Navbar 
                    onLoginClick={handleOpenLogin}    // Mostrar modal de login
                    onRegisterClick={handleOpenRegister}  // Mostrar modal de registro
                />
                <main className="flex-grow container mx-auto px-4 py-8">
                    <Routes>
                        <Route path="/" element={<Home />} />
                        <Route path="/productos/:id" element={<VistaPrevia />} />
                        <Route path="/carrito" element={<Carrito />} />
                        <Route
                            path="/marcas"
                            element={
                                <PrivateRoute>
                                    <Marcas />
                                </PrivateRoute>
                            }
                        />
                        <Route
                            path="/categorias"
                            element={
                                <PrivateRoute>
                                    <Categorias />
                                </PrivateRoute>
                            }
                        />
                    </Routes>
                </main>

                {/* Modales de Login y Registro */}
                <Modal isVisible={showLogin} onClose={handleCloseLogin}>
                    <Login isVisible={showLogin} onClose={handleCloseLogin} />
                </Modal>

                <Modal isVisible={showRegister} onClose={handleCloseRegister}>
                    <Registro 
                        isVisible={showRegister} 
                        onClose={handleCloseRegister} 
                        onOpenLogin={handleOpenLogin}  // Pasar la función para abrir el login
                    />
                </Modal>

                <footer className="bg-gray-100 py-4 text-center">
                    <p>&copy; 2024 Mi E-commerce. Todos los derechos reservados.</p>
                </footer>
            </div>
        </Router>
    );
}

export default App;
