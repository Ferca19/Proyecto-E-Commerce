import { useState } from 'react'
import Slider from "./ui/slider"
import  Checkbox  from "./ui/checkbox"
import { Label } from "./ui/label"
import { Accordion, AccordionContent, AccordionItem, AccordionTrigger } from "./ui/accordion"

export default function FiltroProductos() {
  const [priceRange, setPriceRange] = useState([0, 1000])

  const brands = ['Marca A', 'Marca B', 'Marca C']
  const categories = ['Categoría 1', 'Categoría 2', 'Categoría 3']
  const subcategories = ['Subcategoría 1', 'Subcategoría 2', 'Subcategoría 3']

  return (
    <div className="w-64 bg-white shadow-lg rounded-lg p-6">
      <h2 className="text-xl font-semibold mb-4">Filtros</h2>

      <Accordion type="single" collapsible className="w-full">
        <AccordionItem value="brand">
          <AccordionTrigger>Marca</AccordionTrigger>
          <AccordionContent>
            {brands.map((brand) => (
              <div key={brand} className="flex items-center space-x-2 mb-2">
                <Checkbox id={brand} />
                <Label htmlFor={brand}>{brand}</Label>
              </div>
            ))}
          </AccordionContent>
        </AccordionItem>

        <AccordionItem value="category">
          <AccordionTrigger>Categoría</AccordionTrigger>
          <AccordionContent>
            {categories.map((category) => (
              <div key={category} className="flex items-center space-x-2 mb-2">
                <Checkbox id={category} />
                <Label htmlFor={category}>{category}</Label>
              </div>
            ))}
          </AccordionContent>
        </AccordionItem>

        <AccordionItem value="subcategory">
          <AccordionTrigger>Subcategoría</AccordionTrigger>
          <AccordionContent>
            {subcategories.map((subcategory) => (
              <div key={subcategory} className="flex items-center space-x-2 mb-2">
                <Checkbox id={subcategory} />
                <Label htmlFor={subcategory}>{subcategory}</Label>
              </div>
            ))}
          </AccordionContent>
        </AccordionItem>
      </Accordion>

      <div className="mt-6">
        <h3 className="text-lg font-semibold mb-2">Rango de Precio</h3>
        <Slider
          min={0}
          max={1000}
          step={10}
          value={priceRange}
          onValueChange={setPriceRange}
          className="mb-2"
        />
        <div className="flex justify-between text-sm text-gray-600">
          <span>${priceRange[0]}</span>
          <span>${priceRange[1]}</span>
        </div>
      </div>
    </div>
  )
}