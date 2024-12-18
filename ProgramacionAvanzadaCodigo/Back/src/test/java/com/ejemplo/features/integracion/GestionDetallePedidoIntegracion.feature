Feature: Gestion Detalle Pedido

  Scenario: Integración con ProductoService para un producto válido
    Given un usuario con ID 3 existe #2
    And los detalles del pedido son: #2
      | productoId | cantidad |
      | 1          | 2        |
    When se crea un pedido con el usuario y los detalles del pedido #2
    Then el detalle se crea con el producto correspondiente al ID proporcionada: 1
    And el pedido debe ser creado correctamente
    And el importe total del pedido debe estar calculado correctamente, siendo 100000.0 #2

  Scenario: Integración con ProductoService para un producto no existente
    Given un usuario con ID 3 existe #2
    And los detalles del pedido son: #2
      | productoId | cantidad |
      | 999        | 2        |
    When se intenta crear un pedido con el usuario y la lista de detalles pero con un producto no existente
    Then se debe lanzar una excepción indicando "Producto no encontrado"
    And el pedido no debe estar guardado en la base de datos #2

  Scenario: Integración con Ajustar Inventario
    Given un usuario con ID 3 existe #2
    And los detalles del pedido son: #2
      | productoId | cantidad |
      | 13         | 2        |
    When se crea un pedido con el usuario y los detalles del pedido #2
    Then se debe ajustar correctamente el stock de cada producto
    And el pedido debe ser creado con los detalles asignados #2
    And el importe total del pedido debe estar calculado correctamente, siendo 90000.0 #2

  Scenario: Guardado del detalle de pedido en el repositorio
    Given un usuario con ID 3 existe #2
    And los detalles del pedido son: #2
      | productoId | cantidad |
      | 1          | 2        |
    When se crea un pedido con el usuario y los detalles del pedido #2
    Then el detalle de cada pedido debe ser guardado en el repositorio correctamente
    And el pedido debe ser creado con los detalles asignados #2

  Scenario: Guardado de múltiples detalles de pedido en una transacción
    Given un usuario con ID 3 existe #2
    And los detalles del pedido son: #2
      | productoId | cantidad |
      | 1          | 2        |
      | 2          | 3        |
    When se crea un pedido con el usuario y los detalles del pedido #2
    Then el detalle de cada pedido debe ser guardado en el repositorio correctamente
    And todos los detalles del pedido deben estar asociados al mismo pedido