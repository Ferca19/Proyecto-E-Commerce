import axios from "axios";
import { useEffect, useState } from "react";

function Marcas() {
    const url = "http://localhost:8080/marcas";
    const [marcas, setMarcas] = useState([]);
    const [denominacion, setDenominacion] = useState("");
    const [observaciones, setObservaciones] = useState("");
    const [error, setError] = useState(null);

    useEffect(() => {
        getMarcas();
    }, []);

    const getMarcas = async () => {
        try {
            const response = await axios.get(url);
            setMarcas(response.data);
        } catch (error) {
            console.error("Error al obtener marcas", error);
            setError("Error al cargar marcas");
        }
    };

    const eliminar = async (id) => {
        try {
            await axios.delete(`${url}/${id}`);
            setMarcas(marcas.filter((marca) => marca.id !== id));
        } catch (error) {
            console.error("Error al eliminar la marca", error);
        }
    };

    const agregarMarca = async (e) => {
        e.preventDefault();
        try {
            const nuevaMarca = { denominacion, observaciones };
            const response = await axios.post(url, nuevaMarca);
            setMarcas([...marcas, response.data]);
            setDenominacion("");
            setObservaciones("");
        } catch (error) {
            console.error("Error al agregar la marca", error);
            setError("Error al agregar la marca");
        }
    };

    if (error) return <div>{error}</div>;

    return (
        <div className="container">

            {/* Formulario para agregar nueva marca */}
            <div className="card p-4 mb-4 shadow">
                <div className="text-center my-4">
                    <h2>Agregar Nueva Marca</h2>
                </div>
                <form onSubmit={agregarMarca}>
                    <div className="mb-3">
                    <label htmlFor="denominacion" className="form-label">Denominación</label>
                        <input
                            type="text"
                            id="denominacion"
                            className="form-control"
                            value={denominacion}
                            onChange={(e) => setDenominacion(e.target.value)}
                            required
                        />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="observaciones" className="form-label">Observaciones</label>
                        <input
                            type="text"
                            id="observaciones"
                            className="form-control"
                            value={observaciones}
                            onChange={(e) => setObservaciones(e.target.value)}
                        />
                    </div>
                    <button type="submit" className="btn btn-success mb-3">Guardar</button>
                </form>
            </div>

            <div className="text-center my-4">
                <h2>Listado de Marcas</h2>
            </div>

            {/* Botón para actualizar la tabla */}
            <div className="mb-4 text-center">
                <button
                    onClick={getMarcas}
                    className="btn btn-primary btn-lg"
                >
                    Actualizar Tabla
                </button>
            </div>

            {/* Tabla de marcas */}
            <div className="table-responsive">
                <table className="table table-striped table-hover shadow">
                    <thead className="table-dark">
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">Denominación</th>
                        <th scope="col">Observaciones</th>
                        <th scope="col">Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    {marcas.map((marca, indice) => (
                        <tr key={indice}>
                            <th scope="row">{marca.id}</th>
                            <td>{marca.denominacion}</td>
                            <td>{marca.observaciones}</td>
                            <td className="text-center">
                                <button
                                    onClick={() => eliminar(marca.id)}
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

export default Marcas;
