import { useState, useEffect } from "react";
import axios from "axios";
import { useParams } from "react-router-dom";
import DetalleProducto from "../componentes/detalleProducto"; // Importa el nuevo componente
import Navbar from "../componentes/Navbar";
import { AlertCircle, Loader2 } from "lucide-react"
import { Alert, AlertDescription, AlertTitle } from "../componentes/ui/alert"
import { Button } from "../componentes/ui/button"
import { Card, CardContent } from "../componentes/ui/card"


export default function VistaPreviaProductoPage() {
  const { id } = useParams()
  const [producto, setProducto] = useState(null)
  const [error, setError] = useState("")
  const [carrito, setCarrito] = useState(() => {
    const storedCarrito = localStorage.getItem("carrito")
    return storedCarrito ? JSON.parse(storedCarrito) : []
  })

  useEffect(() => {
    const fetchProducto = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/productos/public/${id}`)
        const producto = response.data
        if (!producto) {
          throw new Error("Producto no encontrado")
        }
        setProducto(producto)
      } catch (error) {
        console.error("Error al obtener el producto:", error)
        setError("No se pudo cargar el producto. Por favor, intenta más tarde.")
      }
    }

    fetchProducto()

    // Mueve la vista al tope de la página
    window.scrollTo(0, 0)
  }, [id])

  const agregarProducto = (nuevoProducto) => {
    const productoExistenteIndex = carrito.findIndex(item => item.id === nuevoProducto.id)
    const nuevoCarrito = [...carrito]

    if (productoExistenteIndex !== -1) {
      nuevoCarrito[productoExistenteIndex].cantidad += nuevoProducto.cantidad || 1
      nuevoCarrito[productoExistenteIndex].subtotal = nuevoCarrito[productoExistenteIndex].cantidad * nuevoCarrito[productoExistenteIndex].precio
    } else {
      nuevoCarrito.push({ ...nuevoProducto, subtotal: nuevoProducto.precio * nuevoProducto.cantidad })
    }

    setCarrito(nuevoCarrito)
    localStorage.setItem("carrito", JSON.stringify(nuevoCarrito))
    alert(`Producto agregado al carrito: ${nuevoProducto.cantidad}x ${nuevoProducto.nombre}`)
  }

  if (error) {
    return (
      <div className="min-h-screen flex flex-col">
        <Navbar />
        <div className="flex-grow container mx-auto px-4 py-8">
          <Alert variant="destructive">
            <AlertCircle className="h-4 w-4" />
            <AlertTitle>Error</AlertTitle>
            <AlertDescription>{error}</AlertDescription>
          </Alert>
        </div>
      </div>
    )
  }

  if (!producto) {
    return (
      <div className="min-h-screen flex flex-col">
        <Navbar />
        <div className="flex-grow container mx-auto px-4 py-8 flex items-center justify-center">
          <Loader2 className="h-8 w-8 animate-spin" />
          <span className="ml-2">Cargando producto...</span>
        </div>
      </div>
    )
  }

  return (
    <div className="min-h-screen flex flex-col">
      <Navbar />
      <div className="flex-grow container mx-auto px-4 py-8">
        <Card className="overflow-hidden">
          <CardContent className="p-0">
          <div className="grid md:grid-cols-2 gap-8">
          <div className="relative flex items-center justify-center p-4 md:p-8">
            <div className="w-full h-0 pb-[120%] md:pb-[93%] relative overflow-hidden rounded-lg">
              <img
                src={producto.imagen ? `http://localhost:8080/Imagenes/${producto.imagen.split('\\').pop()}` : '/placeholder.svg?height=400&width=400'}
                alt={producto.nombre}
                className="absolute inset-0 w-full h-full object-cover scale-110" // Aumentar el tamaño de la imagen
              />
            </div>
          </div>
          <div className="p-6 md:p-8 flex items-center">
                <DetalleProducto producto={producto} agregarAlCarrito={agregarProducto} />
              </div>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  )
}