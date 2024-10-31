import { useState, useEffect } from 'react'
import { jwtDecode } from 'jwt-decode'
import axios from 'axios'
import { ShoppingCart, Trash2, ArrowRight, Package, CreditCard } from 'lucide-react'

import { Button } from "../componentes/ui/button"
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "../componentes/ui/card"
import { Separator } from "../componentes/ui/separator"
import { Alert, AlertDescription } from "../componentes/ui/alert"
import Navbar from '../componentes/Navbar'
import Carrito from '../componentes/Carrito'

export default function CarritoPage() {
  const [carrito, setCarrito] = useState([])
  const [total, setTotal] = useState(0)
  const [usuarioId, setUsuarioId] = useState(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState("")

  useEffect(() => {
    const token = localStorage.getItem("token")
    
    if (!token) {
      window.location.href = "/"
      return
    }
  
    try {
      const decodedToken = jwtDecode(token)
      setUsuarioId(decodedToken.userId)
  
      const carritoGuardado = JSON.parse(localStorage.getItem("carrito")) || []
      setCarrito(carritoGuardado)
  
      const totalCalculado = carritoGuardado.reduce((acc, item) => acc + item.subtotal, 0)
      setTotal(totalCalculado)
    } catch (error) {
      console.error("Error decodificando el token:", error)
      window.location.href = "/"
    }
  }, [])

  const realizarPedido = async () => {
    setLoading(true)
    setError("")
    
    try {
      const token = localStorage.getItem("token")
      const detallesPedido = carrito.map(item => ({
        productoId: item.id,
        cantidad: item.cantidad,
      }))

      console.log("Detalles del pedido:", detallesPedido); // Añade esta línea para depuración

      
      const response = await axios.post(
        `http://localhost:8080/usuarios/public/${usuarioId}/registrarpedido`,
        detallesPedido,
        {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        }
      )
  
      if (response.status === 201) {
        localStorage.removeItem('carrito')
        setCarrito([])
        setTotal(0)
        alert('¡Pedido realizado con éxito!')
      }
    } catch (error) {
      console.error('Error al realizar el pedido:', error)
      setError('Error al procesar el pedido. Por favor, intenta de nuevo.')
    } finally {
      setLoading(false)
    }
  }

  const vaciarCarrito = () => {
    if (window.confirm('¿Estás seguro de que deseas vaciar el carrito?')) {
      localStorage.removeItem('carrito')
      setCarrito([])
      setTotal(0)
    }
  }

  return (
    <div>
      <Navbar />
      {carrito.length === 0 ? (
        <div className="container mx-auto px-4 py-8">
          <div className="text-center">
            <ShoppingCart className="mx-auto h-12 w-12 text-muted-foreground" />
            <h2 className="mt-4 text-lg font-semibold">Tu carrito está vacío</h2>
            <p className="mt-2 text-muted-foreground">¡Agrega algunos productos para comenzar!</p>
            <Button className="mt-4" onClick={() => window.location.href = "/"}>
              Continuar comprando
            </Button>
          </div>
        </div>
      ) : (
        <div className="container mx-auto px-4 py-8">
          <Carrito 
            carrito={carrito}
            total={total}
            realizarPedido={realizarPedido}
            vaciarCarrito={vaciarCarrito}
          />
          {error && (
            <Alert variant="destructive" className="mt-4">
              <AlertDescription>{error}</AlertDescription>
            </Alert>
          )}
        </div>
      )}
    </div>
  )
}