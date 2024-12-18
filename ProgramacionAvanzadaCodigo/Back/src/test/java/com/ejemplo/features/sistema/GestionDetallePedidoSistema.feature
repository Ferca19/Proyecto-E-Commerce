Feature: Flujo Completo de Creación de Pedido

  Scenario: Validar el flujo completo de creación de pedido
    Given un usuario con ID 3 existe #4
    And los detalles del pedido son: #4
      | productoId | cantidad |
      | 1          | 2        |
      | 2          | 3        |
    When se crea un pedido con el usuario y los detalles del pedido #4
    Then el pedido debe ser creado con los detalles asignados #4
    And todos los detalles del pedido deben estar asociados al mismo pedido #2
    And el detalle de cada pedido debe ser guardado en el repositorio correctamente #2
    And el importe total del pedido debe estar calculado correctamente, siendo 220000.0 #3

  Scenario: Validar que los cambios en la base de datos se reflejan correctamente
    Given un usuario con ID 3 existe #4
    And los detalles del pedido son: #4
      | productoId | cantidad |
      | 1          | 2        |
      | 2          | 3        |
    When se crea un pedido con el usuario y los detalles del pedido #4
    Then el pedido debe estar correctamente almacenado en la base de datos
    And los detalles del pedido deben ser consultables desde la base de datos

  Scenario: Validar la integración del servicio con el repositorio
    Given un usuario con ID 3 existe #4
    And los detalles del pedido son: #4
      | productoId | cantidad |
      | 1          | 2        |
      | 2          | 3        |
    When se crea un pedido con el usuario y los detalles del pedido #4
    Then el pedido debe estar correctamente almacenado en el repositorio
    And los detalles del pedido deben estar correctamente asociados al pedido en el repositorio

  Scenario: Validar el rendimiento al crear múltiples pedidos simultáneamente
    Given un usuario con ID 3 existe #4
    And los detalles del pedido son: #4
      | productoId | cantidad |
      | 1          | 2        |
      | 2          | 3        |
    When se crean múltiples pedidos simultáneamente #2
    Then todos los pedidos deben ser creados exitosamente #2
    And el tiempo de ejecución debe estar dentro de los límites aceptables #2


  Scenario: Confirmar la integridad de los datos almacenados después de múltiples operaciones
    Given un usuario con ID 3 existe #4
    And los detalles del pedido son: #4
      | productoId | cantidad |
      | 1          | 2        |
      | 2          | 3        |
    When se crea un pedido con el usuario y los detalles del pedido #4
    Then los datos almacenados en la base de datos deben coincidir con los esperados
