import React, { useState } from "react";
import { MinusIcon, PlusIcon, ShoppingCart } from "lucide-react";

function Button({ children, onClick, disabled }) {
  return (
    <button
      onClick={onClick}
      disabled={disabled}
      className={`px-4 py-2 rounded ${disabled ? "bg-gray-300" : "bg-indigo-600 text-white"} hover:bg-indigo-700`}
    >
      {children}
    </button>
  );
}

function Select({ label, value, onChange, options }) {
  return (
    <div>
      <label className="block text-sm font-medium text-gray-700 mb-1">{label}</label>
      <select
        value={value}
        onChange={(e) => onChange(e.target.value)}
        className="w-full border-gray-300 rounded-lg"
      >
        {options.map((option) => (
          <option key={option} value={option}>
            {option}
          </option>
        ))}
      </select>
    </div>
  );
}

export default function DetalleProducto({ producto, agregarAlCarrito }) {
  const [color, setColor] = useState(producto.color || "");
  const [tamano, setTamano] = useState(producto.tamano || "");
  const [cantidad, setCantidad] = useState(1);

  const handleCantidadChange = (increment) => {
    setCantidad((prev) => Math.max(1, prev + increment));
  };

  const handleAgregarAlCarrito = () => {
    agregarAlCarrito(producto, cantidad, color, tamano);
  };

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold text-gray-900">{producto.nombre}</h1>
      <p className="text-2xl font-semibold text-gray-700">${producto.precio.toLocaleString('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</p>
      <p className="text-gray-600">{producto.descripcion}</p>

      <div className="space-y-4">
        <Select label="Color" value={color} onChange={setColor} options={[color]} />
        <Select label="Tamaño" value={tamano} onChange={setTamano} options={[tamano]} />

        <div>
          <label className="block text-sm font-medium text-gray-700 mb-1">Cantidad</label>
          <div className="flex items-center space-x-3">
            <Button onClick={() => handleCantidadChange(-1)} disabled={cantidad <= 1}>
              <MinusIcon className="h-4 w-4" />
            </Button>
            <span className="text-xl font-semibold">{cantidad}</span>
            <Button onClick={() => handleCantidadChange(1)}>
              <PlusIcon className="h-4 w-4" />
            </Button>
          </div>
        </div>
      </div>

      <Button onClick={handleAgregarAlCarrito} className="w-full">
        <ShoppingCart className="mr-2 h-5 w-5" />
        Agregar al Carrito
      </Button>
    </div>
  );
}
