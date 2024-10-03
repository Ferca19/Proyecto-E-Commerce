import React from 'react';
import ProductList from '../componentes/productList';

function Home() {
    return (
        <div>
            <h1 className="text-3xl font-bold mb-6">Bienvenido a Mi E-commerce</h1>
            <p className="mb-8">Encuentra los mejores productos al mejor precio.</p>
            <h2 className="text-2xl font-semibold mb-4">Nuestros Productos</h2>
            <ProductList />
        </div>
    );
}

export default Home;