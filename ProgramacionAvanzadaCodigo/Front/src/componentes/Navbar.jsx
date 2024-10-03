import React from 'react'
import { Link } from 'react-router-dom'
import { Input } from "./ui/input"
import { Button } from "./ui/button"
import { ShoppingCart, User } from "lucide-react"

export default function Navbar({ onLoginClick, onRegisterClick }) {
  return (
    <nav className="flex items-center justify-between p-4 bg-white shadow-md">
      <Link to="/" className="text-2xl font-bold text-primary">
        Mi E-commerce
      </Link>
      
      <div className="flex-1 max-w-xl mx-4">
        <form className="relative">
          <Input
            className="w-full pl-4 pr-10"
            placeholder="Buscar productos..."
            type="search"
          />
          <Button 
            className="absolute right-0 top-0 bottom-0" 
            type="submit"
            variant="ghost"
          >
            Buscar
          </Button>
        </form>
      </div>
      
      <div className="flex items-center space-x-4">
        <div className="flex items-center space-x-2">
          <User className="h-5 w-5" />
          <div className="space-x-2">
            {/* Aquí usamos las funciones onLoginClick y onRegisterClick en lugar de los links */}
            <button onClick={onRegisterClick} className="text-sm font-medium hover:underline">
              Registrarse
            </button>
            <span>|</span>
            <button onClick={onLoginClick} className="text-sm font-medium hover:underline">
              Iniciar sesión
            </button>
          </div>
        </div>
        
        <Link to="/carrito" className="relative">
          <ShoppingCart className="h-6 w-6" />
          <span className="absolute -top-2 -right-2 bg-red-500 text-white text-xs rounded-full h-5 w-5 flex items-center justify-center">
            0
          </span>
        </Link>
      </div>
    </nav>
  )
}
