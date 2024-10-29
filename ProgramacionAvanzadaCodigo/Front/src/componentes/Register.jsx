import React, { useState } from 'react';
import axios from 'axios';
import Modal from './modal';
import { useNavigate } from 'react-router-dom';
import './Register.css'; // Importa el CSS

function Registro({ isVisible, onClose, onOpenLogin }) {
    const [nombre, setNombre] = useState('');
    const [apellido, setApellido] = useState('');
    const [email, setEmail] = useState('');
    const [contrasena, setContrasena] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const navigate = useNavigate();

    const resetForm = () => {
        setNombre('');
        setApellido('');
        setEmail('');
        setContrasena('');
        setError('');
        setSuccess('');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const registerData = {
            nombre,
            apellido,
            mail: email,
            contrasena,
        };

        try {
            await axios.post('http://localhost:8080/auth/register', registerData);
            setSuccess('Registro exitoso. Abriendo el formulario de inicio de sesión...');
            setError('');

            // Reinicia el formulario
            resetForm();

            // Cierra el formulario de registro
            onClose();

            // Redirige a la página de inicio
            navigate('/');

            // Abre el formulario de inicio de sesión
            onOpenLogin();

        } catch (err) {
            setError('Error al registrar el usuario: ' + (err.response?.data?.message || err.message));
            setSuccess('');
        }
    };

    return (
        <Modal isVisible={isVisible} onClose={onClose}>
            <h2 className="text-2xl font-bold mb-4">Registro</h2>
            <form 
                onSubmit={handleSubmit} 
                className="form-container" // Aplica la clase de contenedor
            >
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-2">Nombre</label>
                    <input
                        type="text"
                        value={nombre}
                        onChange={(e) => setNombre(e.target.value)}
                        required
                        className="input-field" // Aplica la clase de input
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-2">Apellido</label>
                    <input
                        type="text"
                        value={apellido}
                        onChange={(e) => setApellido(e.target.value)}
                        required
                        className="input-field"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-2">Correo Electrónico</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                        className="input-field"
                    />
                </div>
                <div className="mb-4">
                    <label className="block text-sm font-medium mb-2">Contraseña</label>
                    <input
                        type="password"
                        value={contrasena}
                        onChange={(e) => setContrasena(e.target.value)}
                        required
                        className="input-field"
                    />
                </div>
                {error && <p className="error-message">{error}</p>} {/* Aplica la clase de error */}
                {success && <p className="success-message">{success}</p>} {/* Aplica la clase de éxito */}
                <button
                    type="submit"
                    className="submit-button" // Aplica la clase del botón
                >
                    Registrarse
                </button>
            </form>
        </Modal>
    );
}

export default Registro;
