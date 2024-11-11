Feature: Creación de Pedidos

  Scenario: Crear pedido con un usuario ID válido y detalles no vacíos
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 2        |
    When se intenta crear un pedido con el usuario y la lista de detalles
    Then el pedido debe ser creado con su estado inicial
    And el importe total del pedido debe estar calculado

  Scenario: Crear un pedido con usuario nulo
    Given un usuario nulo existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 5        |
    When se intenta crear un pedido con un usuario nulo y los detalles del pedido
    Then se debe lanzar una excepción indicando "Usuario no encontrado"

  Scenario: Crear pedido con un usuario ID válido y lista de detalles vacía
    Given un usuario con ID 3 existe
    And una lista de detalles de pedido vacía
    When se intenta crear un pedido con el usuario y la lista de detalles vacía
    Then se debe lanzar una excepción indicando "El pedido debe contener al menos un detalle de producto."

  Scenario: Crear pedido con un usuario válido y detalle de producto con cantidad cero
    Given un usuario con ID 3 existe
    And los detalles del pedido con cantidad de producto 0 o negativa son:
      | productoId | cantidad |
      | 1          | 0        |
      | 1          | -1       |
    When se intenta crear un pedido con el usuario y el detalle de pedido con cantidad 0
    Then se debe lanzar una excepción indicando "La cantidad del producto no puede ser 0 o negativa"

  Scenario: Crear pedido con un pedido con un producto no disponible
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 99999999   | 2        |
    When se intenta crear un pedido con el usuario y la lista de detalles pero con un producto no disponible
    Then se debe lanzar una excepción indicando "Producto no encontrado"

