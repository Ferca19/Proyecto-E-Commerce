Feature: Flujo Completo de Creación de Pedido

  Scenario: Crear un pedido con usuario y detalles de pedido
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 2        |
      | 2          | 1        |
    When se crea un pedido con el usuario y los detalles del pedido
    Then el pedido debe ser creado con el usuario asignado
    And el pedido debe tener el estado "Pendiente"
    And el pedido debe estar almacenado en el repositorio


  Scenario: Verificar que los cambios en la base de datos se reflejan correctamente después de la creación del pedido
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 2        |
    When se crea un pedido con el usuario y los detalles del pedido
    Then el pedido debe estar almacenado en la base de datos
    And el pedido almacenado debe ser consultable por su ID


  Scenario: Verificar el cálculo del importe total y los detalles del pedido después de su creación
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 2          | 2        |
      | 3          | 1        |
    When se crea un pedido con el usuario y los detalles del pedido
    Then el importe total del pedido debe estar calculado correctamente, siendo 120000.0
    And los detalles del pedido deben estar almacenados en la base de datos

  Scenario: Evaluar el rendimiento del sistema al manejar múltiples pedidos simultáneamente
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 2          | 2        |
      | 3          | 1        |
    When se crean múltiples pedidos simultáneamente
    Then todos los pedidos deben ser creados exitosamente
    And el tiempo de ejecución debe estar dentro de los límites aceptables