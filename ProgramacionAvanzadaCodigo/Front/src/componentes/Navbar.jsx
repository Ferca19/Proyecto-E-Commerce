import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { Input } from "./ui/input";
import { Button } from "./ui/button";
import { ShoppingCart, User, Search } from "lucide-react";
import { Dialog, DialogContent, DialogDescription, DialogHeader, DialogTitle, DialogTrigger,} from "./ui/dialog";
import Login from './Login';
import Registro from './Register';



export default function Navbar() {
  const [isLoginOpen, setIsLoginOpen] = useState(false);
  const [isRegisterOpen, setIsRegisterOpen] = useState(false);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    // Comprobar si hay un token en el local storage
    const token = localStorage.getItem('token');
    setIsAuthenticated(!!token); // Establecer el estado de autenticación
  }, []);

  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem('token'); // Eliminar el token del local storage
    setIsAuthenticated(false); // Actualizar el estado de autenticación
    navigate('/'); // Redirigir a la página de inicio
  };

  return (
    <nav className="w-full bg-gradient-to-r from-blue-500 to-indigo-600 text-white shadow-lg">
      <div className="container mx-auto px-4 py-3">
        <div className="flex items-center justify-between">
          <Link to="/" className="text-2xl font-bold">
            Mi E-commerce
          </Link>

          <div className="flex-1 max-w-xl mx-4">
            <form className="relative">
              <Input
                className="w-full pl-4 pr-10 py-2 bg-white text-gray-800"
                placeholder="Buscar productos..."
                type="search"
              />
              <Button 
                className="absolute right-0 top-0 bottom-0 bg-accent text-accent-foreground" 
                type="submit"
              >
                <Search className="h-5 w-5" />
              </Button>
            </form>
          </div>

          <div className="flex items-center space-x-4"> {/* Ajusta el espacio entre elementos */}
            <div className="flex items-center space-x-2">
              <Registro 
                isVisible={isRegisterOpen} 
                onClose={() => setIsRegisterOpen(false)} 
                onOpenLogin={() => setIsLoginOpen(true)} 
              />

              {!isAuthenticated ? (
                <>
                  <Dialog open={isRegisterOpen} onOpenChange={setIsRegisterOpen}>
                    <DialogTrigger asChild>
                      <Button className="flex items-center bg-white text-gray-800 rounded-lg px-4 py-2 shadow hover:bg-gray-100 transition duration-200" variant="default">
                        Registrarse
                      </Button>
                    </DialogTrigger>
                  </Dialog>

                  <Login isVisible={isLoginOpen} onClose={() => setIsLoginOpen(false)} />

                  <Dialog open={isLoginOpen} onOpenChange={setIsLoginOpen}>
                    <DialogTrigger asChild>
                      <Button className="flex items-center bg-white text-gray-800 rounded-lg px-4 py-2 shadow hover:bg-gray-100 transition duration-200" variant="default" onClick={() => setIsLoginOpen(true)}>
                        Iniciar sesión
                      </Button>
                    </DialogTrigger>
                  </Dialog>
                </>
              ) : (
                <div className="flex items-center space-x-6"> {/* Espaciado entre íconos */}
                  <Link to="/perfil" className="flex items-center">
                    <User className="h-6 w-6" />
                  </Link>
                  <Button onClick={handleLogout} className="bg-red-500 text-white px-4 py-2 rounded-lg">
                    Cerrar sesión
                  </Button>
                </div>
              )}
            </div>

            <Link to="/carrito" className="relative">
              <Button variant="ghost" className="p-2">
                <ShoppingCart className="h-6 w-6" />
                <span className="absolute -top-2 -right-2 bg-accent text-accent-foreground text-xs rounded-full h-5 w-5 flex items-center justify-center">
                  0
                </span>
              </Button>
            </Link>
          </div>
        </div>
      </div>
    </nav>
  );
}