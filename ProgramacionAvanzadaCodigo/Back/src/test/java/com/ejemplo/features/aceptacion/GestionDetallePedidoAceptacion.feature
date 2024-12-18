Feature: Gestion Detalle Pedido

  Scenario: Creación de Pedido con Productos Válidos
    Given un usuario con ID 3 existe #3
    And los detalles del pedido son: #3
      | productoId | cantidad |
      | 1          | 2        |
    When se crea un pedido con el usuario y los detalles del pedido #3
    Then el pedido debe ser creado con los detalles asignados #3
    And la respuesta debe contener los detalles del pedido #2

  Scenario: Calcular el importe total del pedido correctamente
    Given un usuario con ID 3 existe #3
    And los detalles del pedido son: #3
      | productoId | cantidad |
      | 1          | 6        |
    When se crea un pedido con el usuario y los detalles del pedido #3
    Then el importe total del pedido debe estar calculado correctamente, siendo 300000.0 #2

  Scenario: Manejo de Producto No Encontrado
    Given un usuario con ID 3 existe #3
    And los detalles del pedido son: #3
      | productoId | cantidad |
      | 999        | 2        |
    When se intenta crear un pedido con el usuario y la lista de detalles pero con un producto no existente #2
    Then se debe lanzar una excepción indicando "Producto no encontrado" #2

  Scenario: Manejo de Stock Insuficiente
    Given un usuario con ID 3 existe #3
    And los detalles del pedido son: #3
      | productoId | cantidad |
      | 11         | 1000     |
    When se intenta crear un pedido con el usuario y los detalles del pedido #2
    Then se debe lanzar una excepción indicando "Stock insuficiente para el producto con ID: 11" #2

  Scenario: Detalle de Pedido con Cantidad Cero
    Given un usuario con ID 3 existe #3
    And los detalles del pedido son: #3
      | productoId | cantidad |
      | 1          | 0        |
    When se intenta crear un pedido con el usuario y los detalles del pedido #2
    Then se debe lanzar una excepción indicando "La cantidad del producto no puede ser 0 o negativa" #2