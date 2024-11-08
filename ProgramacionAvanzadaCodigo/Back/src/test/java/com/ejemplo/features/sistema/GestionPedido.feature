Feature: Flujo Completo de Creación de Pedido

  Scenario: Crear un pedido con usuario y detalles de pedido
    Given un usuario con ID 1 y nombre "Juan Pérez" existe en el sistema
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 2        |
      | 2          | 1        |
    When se crea un pedido con el usuario y los detalles del pedido
    Then el pedido debe ser creado con el usuario asignado
    And el pedido debe tener el estado "Pendiente"
    And el importe total del pedido debe estar calculado correctamente
    And el pedido debe estar almacenado en el repositorio
