Feature: Gestion Pedido

  Scenario: Crear un pedido con un usuario válido y detalles de pedido
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 2        |
    When se crea un pedido con el usuario y los detalles del pedido
    Then el pedido debe ser creado con el usuario asignado


  Scenario: Cálculo correcto de detalles del pedido
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 2        |
      | 2          | 3        |
    When se crea un pedido con el usuario y los detalles del pedido
    Then el pedido debe ser creado con los detalles asignados
    And el importe total del pedido debe estar calculado correctamente, siendo 220000.0


  Scenario: Guardado correcto del pedido en la base de datos
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 1        |
      | 2          | 2        |
    When se crea un pedido con el usuario y los detalles del pedido
    Then el pedido debe ser guardado en la base de datos
    And los datos del pedido guardado deben ser correctos, siendo el importe total 130000.0


  Scenario: Recuperar un pedido después de crearlo
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 3        |
    When se crea un pedido con el usuario y los detalles del pedido
    Then el pedido debe ser recuperado correctamente desde el repositorio


  Scenario: No guardar pedido si ocurre un error en la creación de detalles
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | -1         | 2        |
    When se intenta crear un pedido con el usuario y la lista de detalles inválidos
    Then se debe lanzar una excepción
    And el pedido no debe estar guardado en la base de datos