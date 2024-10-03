import React, { useState } from 'react';
import axios from 'axios';
import Modal from './modal'; // Asegúrate de importar el modal
import { useNavigate } from 'react-router-dom';
import './login.css'; // Importa el CSS

function Login({ isVisible, onClose }) {
    const [email, setEmail] = useState('');
    const [contrasena, setContrasena] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();

        const loginData = {
            mail: email,
            contrasena,
        };

        try {
             // Envía la solicitud de inicio de sesión y espera la respuesta
             const response = await axios.post('http://localhost:8080/auth/login', loginData);

             // Aquí se asume que el token se encuentra en `response.data.token`
             const token = response.data.token;
 
             // Guarda el token en el local storage
             localStorage.setItem('token', token);
            setSuccess('Inicio de sesión exitoso. Redirigiendo...');
            setError('');

            // Cierra el formulario de inicio de sesión
            onClose();

            setTimeout(() => {
                navigate('/'); // Cambia esto a la ruta que necesites
            }, 2000);
        } catch (err) {
            setError('Error al iniciar sesión: ' + (err.response?.data?.message || err.message));
            setSuccess('');
        }
    };

    return (
        <Modal isVisible={isVisible} onClose={onClose}>
            <h2 className="login-header">Iniciar Sesión</h2>
            <form 
                onSubmit={handleSubmit} 
                className="login-container" // Clase del contenedor
            >
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-2">Correo Electrónico</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        className="input-field" // Clase para el input
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-2">Contraseña</label>
                    <input
                        type="password"
                        value={contrasena}
                        onChange={(e) => setContrasena(e.target.value)}
                        required
                        className="input-field" // Clase para el input
                    />
                </div>
                {error && <p className="error-message">{error}</p>}
                {success && <p className="success-message">{success}</p>}
                <button
                    type="submit"
                    className="submit-button" // Clase para el botón
                >
                    Iniciar Sesión
                </button>
            </form>
        </Modal>
    );
}

export default Login;
