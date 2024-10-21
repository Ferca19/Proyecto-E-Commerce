package com.ejemplo.tests;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import programacion.ejemplo.DTO.CategoriaDTO;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpMethod;

import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional  // Revertir cambios al final de cada prueba
@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // Permitir uso de @BeforeAll no estático
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)  // Esto fuerza el reinicio del contexto de Spring
public class CategoriaDefinicionPasos {

    private final TestRestTemplate restTemplate; // Inyectar TestRestTemplate

    private ResponseEntity<CategoriaDTO> response; // Cambiado a CategoriaDTO
    private String token;

    private String nombreCategoria;
    private String descripcionCategoria;

    // Constructor que acepta TestRestTemplate
    public CategoriaDefinicionPasos(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @BeforeAll
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
    @Rollback(true)
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
        response = restTemplate.postForEntity("http://localhost:8080/categorias/admin", requestEntity, CategoriaDTO.class);

    }

    @Then("la categoría se crea correctamente")
    public void la_categoria_se_crea_correctamente() {
        // Verificar que la respuesta es 201 Created
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Realizar la solicitud para eliminar la categoría creada
        eliminarCategoriaCreada();
    }

    @Given("el administrador crea la categoría")
    @Rollback(true)
    public void el_administrador_crea_la_categoria() {
        // Crear un objeto JSON o DTO con el nombre y descripción
        CategoriaDTO nuevaCategoria = new CategoriaDTO();
        nuevaCategoria.setNombre(nombreCategoria); // Usa el nombre guardado
        nuevaCategoria.setDescripcion(descripcionCategoria); // Usa la descripción guardada

        // Configurar el encabezado con el token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);  // Incluir el token

        // Crear el HttpEntity con el DTO y los encabezados
        HttpEntity<CategoriaDTO> requestEntity = new HttpEntity<>(nuevaCategoria, headers);

        // Hacer la solicitud POST a la API de creación de categorías
        response = restTemplate.postForEntity("http://localhost:8080/categorias/admin", requestEntity, CategoriaDTO.class);
    }


    @When("el administrador intenta crear otra categoría con el mismo nombre")
    public void el_administrador_intenta_crear_otra_categoria_con_el_mismo_nombre() {
        // Intentar crear una categoría con el mismo nombre
        CategoriaDTO categoriaDuplicada = new CategoriaDTO();
        categoriaDuplicada.setNombre(nombreCategoria);
        categoriaDuplicada.setDescripcion(descripcionCategoria);

        // Configurar el encabezado con el token
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);  // Incluir el token

        // Crear el HttpEntity con el DTO y los encabezados
        HttpEntity<CategoriaDTO> requestEntity = new HttpEntity<>(categoriaDuplicada, headers);

        // Hacer la solicitud POST a la API de creación de categorías
        response = restTemplate.postForEntity("http://localhost:8080/categorias/admin", requestEntity, CategoriaDTO.class);
    }

    @Then("el sistema debe rechazar la creación, indicando que la categoría ya existe")
    public void el_sistema_debe_rechazar_la_creacion_indicando_que_la_categoria_ya_existe() {
        // Verificar que el servidor responde con un 400 o 409
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        // Realizar la solicitud para eliminar la categoría creada
        eliminarCategoriaCreada();
    }

    private void eliminarCategoriaCreada() {
        // Obtener el ID de la categoría creada
        CategoriaDTO categoriaCreada = response.getBody(); // Asegúrate de que este no sea nulo
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