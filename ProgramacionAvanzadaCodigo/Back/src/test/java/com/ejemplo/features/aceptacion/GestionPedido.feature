Feature: Creación de Pedidos

  Scenario: Creación de un pedido con éxito
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 2        |
    When se intenta crear un pedido con el usuario y la lista de detalles
    Then el pedido se crea exitosamente
    And la respuesta debe contener los detalles del pedido


  Scenario: Validación de Estado del Pedido
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 2        |
    When se intenta crear un pedido con el usuario y la lista de detalles
    Then el pedido se crea exitosamente
    And el estado del pedido debe ser "Pendiente"


  Scenario: Cálculo Correcto del Importe Total
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 2        |
    When se intenta crear un pedido con el usuario y la lista de detalles
    Then el pedido se crea exitosamente
    And el importe total del pedido debe estar calculado correctamente


  Scenario: Crear un pedido con usuario nulo
    Given un usuario nulo existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | 1          | 5        |
    When se intenta crear un pedido con un usuario nulo y los detalles del pedido
    Then se debe lanzar una excepción indicando "Usuario no encontrado"


  Scenario: Validación de Detalles de Pedido con Producto No Válido
    Given un usuario con ID 3 existe
    And los detalles del pedido son:
      | productoId | cantidad |
      | -1         | 2        |
    When se intenta crear un pedido con el usuario y los detalles no validos
    Then se debe lanzar una excepción indicando "Producto no encontrado"
