
import React, { useEffect, useState } from 'react'
import axios from 'axios'
import { Card, CardContent, CardHeader, CardTitle } from "./ui/card"
import { Skeleton } from "./ui/skeleton"
import { Alert, AlertDescription, AlertTitle } from "./ui/alert"
import { BarChart, Bar, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts'
import { AlertCircle, Package } from 'lucide-react'

export default function InformeDashboard() {
  const [informe, setInforme] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    const fetchInforme = async () => {
      try {
        const response = await axios.get('http://localhost:8080/pedidos/informe')
        setInforme(response.data)
      } catch (err) {
        setError('Error al cargar el informe.')
      } finally {
        setLoading(false)
      }
    }

    fetchInforme()
  }, [])

  if (loading) {
    return (
      <Card className="w-full max-w-3xl mx-auto">
        <CardHeader>
          <CardTitle>Informe de Pedidos</CardTitle>
        </CardHeader>
        <CardContent>
          <Skeleton className="h-[200px] w-full mb-4" />
          <Skeleton className="h-4 w-3/4 mb-2" />
          <Skeleton className="h-4 w-1/2" />
        </CardContent>
      </Card>
    )
  }

  if (error) {
    return (
      <Alert variant="destructive" className="w-full max-w-3xl mx-auto">
        <AlertCircle className="h-4 w-4" />
        <AlertTitle>Error</AlertTitle>
        <AlertDescription>{error}</AlertDescription>
      </Alert>
    )
  }

  const chartData = Object.entries(informe.productosMasVendidos).map(([producto, cantidad]) => ({
    nombre: producto,
    cantidad: cantidad,
  }))

  return (
    <Card className="w-[1500px]  mx-auto">
      <CardHeader>
        <CardTitle className="text-2xl font-bold w-[1500px] text-center">Informe de Pedidos</CardTitle>
      </CardHeader>
      <CardContent>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <Card>
            <CardHeader>
              <CardTitle className="text-xl font-semibold flex items-center">
                <Package className="mr-2" />
                Pedidos Totales
              </CardTitle>
            </CardHeader>
            <CardContent>
              <p className="text-4xl font-bold text-center">{informe.totalPedidos}</p>
            </CardContent>
          </Card>
          <Card className="p-6 shadow-lg w-[600px] max-w-4xl mx-auto">
            <CardHeader>
                <CardTitle className="text-2xl font-bold">Productos Más Vendidos</CardTitle>
            </CardHeader>
            <CardContent>
                <ResponsiveContainer width="100%" height={400}>
                <BarChart data={chartData} margin={{ top: 20, right: 30, left: 10, bottom: 10 }}>
                    <CartesianGrid strokeDasharray="3 3" />
                    <XAxis dataKey="nombre" />
                    <YAxis />
                    <Tooltip />
                    <Bar dataKey="cantidad" fill="#8884d8" barSize={50} />
                </BarChart>
                </ResponsiveContainer>
            </CardContent>
            </Card>

        </div>
      </CardContent>
    </Card>
  )
}

