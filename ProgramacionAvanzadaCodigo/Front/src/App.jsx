import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import Marcas from './componentes/Marcas.jsx';
import Categorias from './componentes/Categorias.jsx';

function App() {
    return (
        <Router>
            <div>
                {/* Navbar */}
                <nav className="navbar">
                    <ul>
                        <li>
                            <Link to="/marcas">Marcas</Link>
                        </li>
                        <li>
                            <Link to="/categorias">Categorías</Link>
                        </li>
                    </ul>
                </nav>

                {/* Rutas */}
                <Routes>
                    <Route path="/marcas" element={<Marcas />} />
                    <Route path="/categorias" element={<Categorias />} />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
