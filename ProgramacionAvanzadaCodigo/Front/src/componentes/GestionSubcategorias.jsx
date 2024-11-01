import axios from "axios";
import { useEffect, useState } from "react";

function GestionSubcategorias() {
    const url = "http://localhost:8080/subcategorias";
    const [subcategorias, setSubcategorias] = useState([]);
    const [nombre, setNombre] = useState("");
    const [descripcion, setDescripcion] = useState("");
    const [error, setError] = useState(null);

    useEffect(() => {
        getSubcategoria();
    }, []);

    const getSubcategoria = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get(url + "/public", {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setSubcategorias(response.data);
        } catch (error) {
            console.error("Error al obtener subcategorias", error);
            setError("Error al cargar subcategorias");
        }
    };

    const eliminar = async (id) => {
        const confirmar = window.confirm("¿Estás seguro de que deseas eliminar esta subcategoría?");
        if (!confirmar) return;

        try {
            const token = localStorage.getItem('token');
            await axios.delete(`${url}/admin/${id}`, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setSubcategorias(subcategorias.filter((subcategoria) => subcategoria.id !== id));
        } catch (error) {
            console.error("Error al eliminar la subcategoria", error);
        }
    };

    const agregarSubcategoria = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            const nuevaSubcategoria = { nombre, descripcion };
            const response = await axios.post(url + "/admin", nuevaSubcategoria, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            setSubcategorias([...subcategorias, response.data]);
            setNombre("");
            setDescripcion("");
        } catch (error) {
            console.error("Error al agregar la subcategoria", error);
            setError("Error al agregar la subcategoria");
        }
    };

    if (error) return <div>{error}</div>;

    return (
        <div className="container">
            {/* Formulario para agregar nueva subcategoria */}
            <div className="card p-4 mb-4 shadow">
                <div className="text-center my-4">
                    <h2>Agregar Nueva Subcategoría</h2>
                </div>
                <form onSubmit={agregarSubcategoria}>
                    <div className="mb-3">
                        <label htmlFor="nombre" className="form-label">Nombre</label>
                        <input
                            type="text"
                            id="nombre"
                            className="form-control"
                            value={nombre}
                            onChange={(e) => setNombre(e.target.value)}
                            required
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="descripcion" className="form-label">Descripción</label>
                        <input
                            type="text"
                            id="descripcion"
                            className="form-control"
                            value={descripcion}
                            onChange={(e) => setDescripcion(e.target.value)}
                        />
                    </div>
                    <button type="submit" className="btn btn-success mb-3">Guardar</button>
                </form>
            </div>

            <div className="text-center my-4">
                <h2>Listado de Subcategorías</h2>
            </div>

            {/* Botón para actualizar la tabla */}
            <div className="mb-4 text-center">
                <button
                    onClick={getSubcategoria}
                    className="btn btn-primary btn-lg"
                >
                    Actualizar Tabla
                </button>
            </div>

            {/* Tabla de subcategorias */}
            <div className="table-responsive">
                <table className="table table-striped table-hover shadow">
                    <thead className="table-dark">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Nombre</th>
                        <th scope="col">Descripción</th>
                        <th scope="col">Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    {subcategorias.map((subcategoria, indice) => (
                        <tr key={indice}>
                            <th scope="row">{subcategoria.id}</th>
                            <td>{subcategoria.nombre}</td>
                            <td>{subcategoria.descripcion}</td>
                            <td className="text-center">
                                <button
                                    onClick={() => eliminar(subcategoria.id)}
                                    className="btn btn-danger btn-sm"
                                >
                                    Eliminar
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default GestionSubcategorias;
