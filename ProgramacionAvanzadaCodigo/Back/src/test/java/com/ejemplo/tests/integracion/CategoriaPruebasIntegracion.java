package com.ejemplo.tests.integracion;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import programacion.ejemplo.DTO.CategoriaDTO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpMethod;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CategoriaPruebasIntegracion {

    private final TestRestTemplate restTemplate; // Inyectar TestRestTemplate

    private ResponseEntity<CategoriaDTO> successResponse;
    private ResponseEntity<String> errorResponse;

    private String token;
    private String nombreCategoria;
    private String descripcionCategoria;

    // Constructor que acepta TestRestTemplate
    public CategoriaPruebasIntegracion(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Given("El administrador se autentica con usuario {string} y contraseña {string}")
    public void el_administrador_se_autentica_con_usuario_y_contraseña(String mail, String contrasena) {
        // Crear un objeto con las credenciales del administrador
        Map<String, String> credenciales = new HashMap<>();
        credenciales.put("mail", mail);
        credenciales.put("contrasena", contrasena);

        // Hacer una solicitud POST al endpoint de autenticación
        ResponseEntity<Map> authResponse = restTemplate.postForEntity("http://localhost:8080/auth/login", credenciales, Map.class);

        // Verificar que la autenticación fue exitosa (200 OK)
        assertEquals(HttpStatus.OK, authResponse.getStatusCode());

        // Obtener el token de la respuesta
        Map<String, String> body = authResponse.getBody();
        token = body.get("token"); // Asumimos que el token está en el campo "token" del cuerpo
    }

    @Given("El administrador ingresa nombre {string} y descripción {string}")
    public void el_administrador_ingresa_nombre_y_descripcion(String nombre, String descripcion) {
        // Guardar los datos de la categoría
        nombreCategoria = nombre;
        descripcionCategoria = descripcion;
    }

    @When("el administrador intenta crear la categoría")
    public void el_administrador_intenta_crear_la_categoria() {
        // Crear un objeto JSON o DTO con el nombre y descripción
        CategoriaDTO nuevaCategoria = new CategoriaDTO();
        nuevaCategoria.setNombre(nombreCategoria);
        nuevaCategoria.setDescripcion(descripcionCategoria);

        // Configurar el encabezado con el token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);  // Incluir el token

        // Crear el HttpEntity con el DTO y los encabezados
        HttpEntity<CategoriaDTO> requestEntity = new HttpEntity<>(nuevaCategoria, headers);

        // Hacer la solicitud POST a la API de creación de categorías
        successResponse = restTemplate.postForEntity("http://localhost:8080/categorias/admin", requestEntity, CategoriaDTO.class);

    }

    @Then("la categoría se crea correctamente")
    public void la_categoria_se_crea_correctamente() {
        // Verificar que la respuesta es 201 Created
        assertEquals(HttpStatus.CREATED, successResponse.getStatusCode());

        // Realizar la solicitud para eliminar la categoría creada
        eliminarCategoriaCreada();
    }


    private void eliminarCategoriaCreada() {
        // Obtener el ID de la categoría creada
        CategoriaDTO categoriaCreada = successResponse.getBody(); // Asegúrate de que este no sea nulo
        Integer categoriaId = categoriaCreada.getId(); // Obtén el ID

        // Configurar los encabezados para la solicitud DELETE
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);  // Incluir el token

        // Crear el HttpEntity con los encabezados (no se necesita cuerpo para DELETE)
        HttpEntity<Void> deleteRequestEntity = new HttpEntity<>(headers);

        // Hacer la solicitud DELETE para eliminar la categoría creada
        ResponseEntity<Void> deleteResponse = restTemplate.exchange(
                "http://localhost:8080/categorias/admin/fisicamente/" + categoriaId,
                HttpMethod.DELETE,
                deleteRequestEntity,
                Void.class
        );

        // Verificar que la eliminación fue exitosa
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
    }
}