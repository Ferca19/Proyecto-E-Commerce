import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import Marcas from './componentes/Marcas.jsx';
import Categorias from './componentes/Categorias.jsx';
import PrivateRoute from './componentes/PrivateRoute';
import Login from "./componentes/Login.jsx";

function App() {
    return (
        <Router>
            <div>
                {/* Navbar */}
                <nav className="navbar">
                    <ul>
                        <li>
                            <Link to="/login">Login</Link> {/* Enlace para el login */}
                        </li>
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
                    <Route path="/login" element={<Login />} />
                    <Route
                        path="/marcas"
                        element={
                        <PrivateRoute>
                            <Marcas />
                        </PrivateRoute>
                        }
                    />
                    <Route
                        path="/categorias"
                        element={
                            <PrivateRoute>
                                <Categorias />
                            </PrivateRoute>
                        }
                    />
                </Routes>
            </div>
        </Router>
    );
}

export default App;
