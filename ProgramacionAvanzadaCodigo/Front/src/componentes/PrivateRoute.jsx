import { Navigate } from 'react-router-dom';
import PropTypes from 'prop-types';  // Importar PropTypes

function PrivateRoute({ children }) {
    const token = localStorage.getItem('token');

    if (!token) {
        // Si no hay token, redirigir al login
        return <Navigate to="/login" />;
    }

    // Si hay token, permitir acceso
    return children;
}

// Validar que 'children' es requerido y de tipo 'node'
PrivateRoute.propTypes = {
    children: PropTypes.node.isRequired,
};

export default PrivateRoute;
