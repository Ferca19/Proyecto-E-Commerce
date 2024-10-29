import axios from "axios";
import { useEffect, useState } from "react";

function Categorias() {
    const url = "http://localhost:8080/categorias";
    const [categorias, setCategorias] = useState([]);
    const [nombre, setNombre] = useState("");
    const [descripcion, setCategoria] = useState("");
    const [error, setError] = useState(null);

    useEffect(() => {
        getCategorias();
    }, []);

    const getCategorias = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get(url+"/public", {
                headers: {
                    'Authorization': `Bearer ${token}`  // Enviar el token en las cabeceras
                }
            });
            setCategorias(response.data);
        } catch (error) {
            console.error("Error al obtener categorias", error);
            setError("Error al cargar categorias");
        }
    };

    const eliminar = async (id) => {
        try {
            const token = localStorage.getItem('token');
            await axios.delete(`${url}/admin/${id}`, {
                headers: {
                    'Authorization': `Bearer ${token}`  // Enviar el token en las cabeceras
                }
            });
            setCategorias(categorias.filter((categoria) => categoria.id !== id));
        } catch (error) {
            console.error("Error al eliminar la categoria", error);
        }
    };

    const agregarCategoria = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            const nuevaCategoria = { nombre, descripcion };
            const response = await axios.post(url+"/admin", nuevaCategoria, {
                headers: {
                    'Authorization': `Bearer ${token}`  // Enviar el token en las cabeceras
                }
            });
            setCategorias([...categorias, response.data]);
            setNombre("");
            setCategoria("");
        } catch (error) {
            console.error("Error al agregar la categoria", error);
            setError("Error al agregar la categoria");
        }
    };

    if (error) return <div>{error}</div>;

    return (
        <div className="container">

            {/* Formulario para agregar nueva categoria */}
            <div className="card p-4 mb-4 shadow">
                <div className="text-center my-4">
                    <h2>Agregar Nueva Categoria</h2>
                </div>
                <form onSubmit={agregarCategoria}>
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
                            onChange={(e) => setCategoria(e.target.value)}
                        />
                    </div>
                    <button type="submit" className="btn btn-success mb-3">Guardar</button>
                </form>
            </div>

            <div className="text-center my-4">
                <h2>Listado de Categorias</h2>
            </div>

            {/* Botón para actualizar la tabla */}
            <div className="mb-4 text-center">
                <button
                    onClick={getCategorias}
                    className="btn btn-primary btn-lg"
                >
                    Actualizar Tabla
                </button>
            </div>

            {/* Tabla de categorias */}
            <div className="table-responsive">
                <table className="table table-striped table-hover shadow">
                    <thead className="table-dark">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Denominación</th>
                        <th scope="col">Categoria</th>
                        <th scope="col">Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    {categorias.map((categoria, indice) => (
                        <tr key={indice}>
                            <th scope="row">{categoria.id}</th>
                            <td>{categoria.nombre}</td>
                            <td>{categoria.descripcion}</td>
                            <td className="text-center">
                                <button
                                    onClick={() => eliminar(categoria.id)}
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

export default Categorias;
