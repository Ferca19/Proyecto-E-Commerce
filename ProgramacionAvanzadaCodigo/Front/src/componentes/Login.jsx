import  { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function Login() {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    // Manejar el envío del formulario
    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            // Cambiar los nombres de los campos enviados al backend
            const response = await axios.post('http://localhost:8080/auth/login', {
                mail: email,  // Cambiado de email a mail
                contrasena: password  // Cambiado de password a contrasena
            });

            const token = response.data.token;
            localStorage.setItem('token', token);

            navigate('/categorias');

        } catch (error) {
            console.error("Error en el login", error);
            setError("Credenciales incorrectas");
        }
    };

    return (
        <div className="login-container">
            <h2>Login</h2>
            <form onSubmit={handleLogin}>
                <div>
                    <label>Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Contraseña:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                {error && <p>{error}</p>}
                <button type="submit">Login</button>
            </form>
        </div>
    );
}

export default Login;
