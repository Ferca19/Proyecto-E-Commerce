import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

const Navbar = () => {
    return (
        <nav>
            <ul className="navbar">
                <li>
                    <Link to="/src/componentes/Marcas">Marcas</Link>
                </li>
                <li>
                    <Link to="/src/componentes/Categorias">Categorías</Link>
                </li>
            </ul>
        </nav>
    );
};

export default Navbar;