Feature: Creación de Pedidos

  Scenario: Crear pedido con un usuario ID válido y detalles no vacíos
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 2        |
    When se crea un pedido con el usuario y los detalles del pedido
    Then el pedido debe ser creado con estado inicial
    And el importe total del pedido debe estar calculado