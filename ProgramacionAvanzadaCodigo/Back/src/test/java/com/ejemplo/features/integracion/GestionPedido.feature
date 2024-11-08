Feature: Gestion Pedido

  Scenario: Crear un pedido con un usuario válido y detalles de pedido
    Given un usuario con ID 1 y nombre "Juan Perez" existe en el sistema
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 2        |
    When se crea un pedido con el usuario y los detalles del pedido
    Then el pedido debe ser creado con el usuario asignado
    And el importe total del pedido debe estar calculado correctamente
