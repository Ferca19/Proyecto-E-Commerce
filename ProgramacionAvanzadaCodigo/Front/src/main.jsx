import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App.jsx';
import './app.css'
import './index.css'; // Importar tus estilos globales

ReactDOM.createRoot(document.getElementById('root')).render(
    <React.StrictMode>
        <App />
    </React.StrictMode>,
);
