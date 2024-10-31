import { useState, useEffect } from "react";
import { ShoppingBag, Trash2, ArrowRight, Package, CreditCard, Minus, Plus } from 'lucide-react';
import { Button } from "../componentes/ui/button";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "../componentes/ui/card";
import { Separator } from "../componentes/ui/separator";
import { Badge } from "../componentes/ui/badge";

export default function Carrito({ carritoInicial = [], totalInicial = 0, realizarPedido, vaciarCarrito }) {
  const [carrito, setCarrito] = useState(carritoInicial);
  const [total, setTotal] = useState(totalInicial);

  // Cargar el carrito desde localStorage en el montaje del componente
  useEffect(() => {
    const carritoGuardado = JSON.parse(localStorage.getItem('carrito'));
    if (carritoGuardado && carritoGuardado.length > 0) {
      setCarrito(carritoGuardado);
      recalcularTotal(carritoGuardado);
    }
  }, []);

  // Función para recalcular el total
  const recalcularTotal = (nuevoCarrito) => {
    const nuevoTotal = nuevoCarrito.reduce((acumulado, item) => acumulado + item.subtotal, 0);
    setTotal(nuevoTotal);
  };

  // Función para aumentar la cantidad de un producto
  const incrementarCantidad = (index) => {
    const nuevoCarrito = [...carrito];
    nuevoCarrito[index].cantidad += 1;
    nuevoCarrito[index].subtotal = nuevoCarrito[index].cantidad * nuevoCarrito[index].precio;
    setCarrito(nuevoCarrito);
    recalcularTotal(nuevoCarrito);
    localStorage.setItem('carrito', JSON.stringify(nuevoCarrito));
  };

  // Función para disminuir la cantidad de un producto
const disminuirCantidad = (index) => {
  const nuevoCarrito = [...carrito];
  if (nuevoCarrito[index].cantidad > 1) {
    nuevoCarrito[index].cantidad -= 1;
    nuevoCarrito[index].subtotal = nuevoCarrito[index].cantidad * nuevoCarrito[index].precio;
  } else {
    // Eliminar el producto del carrito si la cantidad llega a 0
    nuevoCarrito.splice(index, 1);
  }

  setCarrito(nuevoCarrito);
  recalcularTotal(nuevoCarrito);
  localStorage.setItem('carrito', JSON.stringify(nuevoCarrito));
};


  if (!carrito || carrito.length === 0) {
    return (
      <div className="flex flex-col items-center justify-center py-12">
        <ShoppingBag className="h-16 w-16 text-muted-foreground mb-4" />
        <h2 className="text-2xl font-semibold mb-2">Tu carrito está vacío</h2>
        <p className="text-muted-foreground mb-4">¡Agrega algunos productos para comenzar!</p>
        <Button onClick={() => window.location.href = "/"}>
          Continuar comprando
        </Button>
      </div>
    );
  }
  return (
    <div className="grid gap-8 lg:grid-cols-3">
      <div className="lg:col-span-2">
        <Card>
          <CardHeader>
            <CardTitle className="flex items-center justify-between">
              Productos en tu carrito
              <Badge variant="secondary">{carrito.length} items</Badge>
            </CardTitle>
          </CardHeader>
          <CardContent className="grid gap-6">
            {carrito.map((item, index) => (
              <div key={index} className="flex gap-4">
                <div className="h-24 w-24 rounded-lg border bg-muted overflow-hidden">
                  <img
                    src={item.imagen ? `http://localhost:8080/Imagenes/${item.imagen}` : '/placeholder.svg?height=200&width=200'}
                    alt={item.nombre}
                    className="h-full w-full object-cover"
                  />
                </div>
                <div className="flex flex-1 flex-col justify-between">
                  <div>
                    <h3 className="font-semibold text-lg">{item.nombre}</h3>
                    <div className="text-sm text-muted-foreground mt-1">
                      <span className="inline-block mr-4 text-lg">Color: {item.color}</span>
                      <span className="text-lg">Tamaño: {item.tamano}</span>
                      <span className="block mt-1 text-lg">Precio Unitario: ${item.precio.toLocaleString('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</span>
                    </div>
                    <div className="flex justify-between text-lg">
                      <span>Subtotal</span>
                      <span>${(item.precio*item.cantidad).toLocaleString('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</span>
                    </div>
                  </div>
                  <div className="flex items-center justify-between">
                    <div className="flex items-center gap-2">
                      <Button variant="outline" size="icon" className="h-8 w-8 border-red-500 text-red-500 hover:bg-red-50" onClick={() => disminuirCantidad(index)}>
                        <Minus className="h-4 w-4" />
                      </Button>
                      <div className="flex items-center gap-2">
                        <span className="text-sm text-muted-foreground">Cantidad:</span>
                        <div className="px-3 py-1 border rounded-md min-w-[40px] text-center text-lg">
                          {item.cantidad}
                        </div>
                      </div>
                      <Button variant="outline" size="icon" className="h-8 w-8 border-green-500 text-green-500 hover:bg-green-50" onClick={() => incrementarCantidad(index)}>
                        <Plus className="h-4 w-4" />
                      </Button>
                    </div>

                  </div>
                </div>
              </div>
            ))}
          </CardContent>
        </Card>
      </div>

      <div>
        <Card>
          <CardHeader>
            <CardTitle>Resumen del pedido</CardTitle>
          </CardHeader>
          <CardContent className="grid gap-4">
            <div className="flex justify-between text-sm text-muted-foreground text-lg">
              <span className="text-lg">Envío</span>
              <span className="text-lg">Calculado en el siguiente paso</span>
            </div>
            <Separator />
            <div className="flex justify-between font-medium text-lg">
              <span>Total estimado</span>
              <span>${total.toLocaleString('es-ES', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</span>
            </div>

            <div className="grid gap-2 mt-4">
              <div className="flex items-center gap-2 text-sm text-lg">
                <Package className="h-4 w-4" />
                <span>Envío gratuito en pedidos superiores a $50</span>
              </div>
              <div className="flex items-center gap-2 text-sm">
                <CreditCard className="h-4 w-4" />
                <span>Pago seguro con tarjeta</span>
              </div>
            </div>
          </CardContent>
          <CardFooter className="flex flex-col gap-4">
            <Button 
              className="w-full bg-green-600 hover:bg-green-700 " 
              onClick={realizarPedido}
            >
              <span className="text-lg">Realizar Pedido</span>
              <ArrowRight className="ml-2 h-4 w-4 text-lg" />
            </Button>
            <Button 
              variant="outline" 
              className="w-full text-red-600 border-red-600 hover:bg-red-50" 
              onClick={vaciarCarrito}
            >
              <Trash2 className="mr-2 h-4 w-4" />
              <span className="text-lg">Vaciar Carrito</span>
            </Button>
          </CardFooter>
        </Card>
      </div>
    </div>
  );
}
