import React, { useState } from "react";
import { MinusIcon, PlusIcon, ShoppingCart } from "lucide-react";
import { Button } from "./ui/button"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "./ui/select"
import { Label } from "./ui/label"




export default function DetalleProducto({ producto, agregarAlCarrito }) {
  const [color, setColor] = useState(producto.color || "");
  const [tamano, setTamano] = useState(producto.tamano || "");
  const [cantidad, setCantidad] = useState(1);

  const handleCantidadChange = (increment) => {
    setCantidad((prev) => Math.max(1, prev + increment));
  };

  const handleAgregarAlCarrito = () => {
    const productoParaCarrito = {
      ...producto,
      color,
      tamano,
      cantidad,
      subtotal: cantidad * producto.precio,
    }
    agregarAlCarrito(productoParaCarrito)
  }

  return (
    <div className="space-y-6 md:space-y-8 w-full max-w-md min-h-[500px] p-4 bg-white border rounded-lg overflow-hidden">
      <h1 className="text-2xl md:text-3xl font-bold text-gray-900">{producto.nombre}</h1>
      <p className="text-xl md:text-2xl font-semibold text-gray-700">
        ${producto.precio.toLocaleString('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}
      </p>
      <p className="text-sm md:text-base text-gray-600">{producto.descripcion}</p>

      <div className="space-y-4 md:space-y-6">
        <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
          <div className="space-y-2">
            <Label htmlFor="color">Color</Label>
            <Select value={color} onValueChange={setColor}>
              <SelectTrigger id="color" className="bg-white">
                <SelectValue placeholder="Selecciona un color" />
              </SelectTrigger>
              <SelectContent className="bg-white shadow-md border border-gray-200">
                <SelectItem value={color}>{color}</SelectItem>
              </SelectContent>
            </Select>
          </div>
          <div className="space-y-2">
            <Label htmlFor="tamano">Tamaño</Label>
            <Select value={tamano} onValueChange={setTamano}>
              <SelectTrigger id="tamano" className="bg-white">
                <SelectValue placeholder="Selecciona un tamaño" />
              </SelectTrigger>
              <SelectContent className="bg-white shadow-md border border-gray-200">
                <SelectItem value={tamano}>{tamano}</SelectItem>
              </SelectContent>
            </Select>
          </div>
        </div>

        <div className="space-y-2">
          <Label>Cantidad</Label>
          <div className="flex items-center space-x-3">
            <Button 
              variant="destructive"
              size="icon"
              className="w-10 h-10 rounded-md bg-red-500 hover:bg-red-600"
              onClick={() => handleCantidadChange(-1)} 
              disabled={cantidad <= 1}
            >
              <MinusIcon className="h-4 w-4" />
            </Button>
            <span className="text-xl font-semibold">{cantidad}</span>
            <Button 
              variant="default"
              size="icon"
              className="w-10 h-10 rounded-md bg-green-500 hover:bg-green-600"
              onClick={() => handleCantidadChange(1)}
            >
              <PlusIcon className="h-4 w-4" />
            </Button>
          </div>
        </div>
      </div>

      <Button 
        onClick={handleAgregarAlCarrito} 
        className="w-full bg-blue-500 hover:bg-blue-600 text-white"
      >
        <ShoppingCart className="mr-2 h-5 w-5" />
        Agregar al Carrito
      </Button>
    </div>
  );
}