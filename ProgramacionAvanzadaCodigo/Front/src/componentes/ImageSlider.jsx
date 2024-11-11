import axios from 'axios'
import React, { useState, useEffect } from 'react'
import { ChevronLeft, ChevronRight } from 'lucide-react'
import { motion, AnimatePresence } from 'framer-motion'

export default function ImageSlider() {
    const [images, setImages] = useState([]) // Estado para almacenar las imágenes de los productos
    const [currentIndex, setCurrentIndex] = useState(0)
  
    // Función para ir al siguiente slide
    const nextSlide = () => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length)
    }
  
    // Función para ir al slide anterior
    const prevSlide = () => {
      setCurrentIndex((prevIndex) => (prevIndex - 1 + images.length) % images.length)
    }
  
    // ========================================= Mostrar imagenes de los productos en el banner =========================================
    /*
    useEffect(() => {
      const fetchImages = async () => {
        try {
          const response = await axios.get('http://localhost:8080/productos/public')
          const products = response.data
  
          // Extrae las imágenes de los productos y crea la lista de URLs
          const productImages = products.map(product => ({
            src: product.imagen ? `http://localhost:8080/Imagenes/${product.imagen}` : '/placeholder.svg?height=400&width=800',
            alt: product.nombre,
          }))
  
          setImages(productImages)
        } catch (error) {
          console.error('Error al obtener las imágenes de los productos:', error)
        }
      }
  
      fetchImages()
    }, [])

    */

    // ========================================= Mostrar imagenes especificas para el banner =========================================

    useEffect(() => {
      const fetchBannerImages = async () => {
        try {
          // Petición a la ruta que devuelve la lista de archivos de banners
          const response = await axios.get('http://localhost:8080/productos/public/imagenes/banner')
          const files = response.data
      
          // Mapea los nombres de archivo a URLs completas
          const bannerImages = files.map(file => ({
            src: `http://localhost:8080/Imagenes/${file}`,
            alt: `Banner ${file}`,
          }))
      
          setImages(bannerImages)
        } catch (error) {
          console.error('Error al obtener las imágenes de banners:', error)
        }
      }
    
      fetchBannerImages()
    }, [])


    // Auto-slide cada 5 segundos
    useEffect(() => {
      const interval = setInterval(nextSlide, 5000)
      return () => clearInterval(interval)
    }, [images])
  
    // Si no hay imágenes, muestra un mensaje de carga
    if (images.length === 0) {
      return <div>Cargando imágenes...</div>
    }
  
    return (
      <div className="relative w-full h-[400px] overflow-hidden">
        <AnimatePresence initial={false}>
          <motion.div
            key={currentIndex}
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
            transition={{ duration: 0.5 }}
            className="absolute inset-0"
          >
            <img
              src={images[currentIndex].src}
              alt={images[currentIndex].alt}
              className="w-full h-full object-cover"
            />
          </motion.div>
        </AnimatePresence>
        <button
          onClick={prevSlide}
          className="absolute left-4 top-1/2 transform -translate-y-1/2 bg-black bg-opacity-50 text-white p-2 rounded-full"
          aria-label="Imagen anterior"
        >
          <ChevronLeft size={24} />
        </button>
        <button
          onClick={nextSlide}
          className="absolute right-4 top-1/2 transform -translate-y-1/2 bg-black bg-opacity-50 text-white p-2 rounded-full"
          aria-label="Siguiente imagen"
        >
          <ChevronRight size={24} />
        </button>
        <div className="absolute bottom-4 left-1/2 transform -translate-x-1/2 flex space-x-2">
          {images.map((_, index) => (
            <button
              key={index}
              onClick={() => setCurrentIndex(index)}
              className={`w-3 h-3 rounded-full ${
                index === currentIndex ? 'bg-white' : 'bg-gray-400'
              }`}
              aria-label={`Ir a la imagen ${index + 1}`}
            />
          ))}
        </div>
      </div>
    )
  }