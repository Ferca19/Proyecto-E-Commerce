Feature: Creación de Detalles de Pedidos

  Scenario: Crear detalle de pedido con producto válido y stock suficiente
    Given un pedido válido existe
    And los detalles son:
      | productoId | cantidad |
      | 1          | 5        |
    When se intenta crear los detalles del pedido
    Then los detalles deben ser creados correctamente
    And el importe total debe ser 250000.0

  Scenario: Producto Válido con Stock Insuficiente
    Given un pedido válido existe
    And los detalles son:
      | productoId | cantidad |
      | 11         | 5        |
    Given se espera que se lance una excepción
    When se intenta crear los detalles del pedido
    Then se debe lanzar una excepción IllegalArgumentException con el mensaje "Stock insuficiente para el producto con ID: 11"

  Scenario: Producto No Encontrado
    Given un pedido válido existe
    And los detalles son:
      | productoId | cantidad |
      | 12         | 5        |
    Given se espera que se lance una excepción
    When se intenta crear los detalles del pedido
    Then se debe lanzar una excepción IllegalArgumentException con el mensaje "Producto no encontrado"

  Scenario: Detalle de Pedido con Cantidad Cero
    Given un pedido válido existe
    And los detalles son:
      | productoId | cantidad |
      | 1         | 0        |
    Given se espera que se lance una excepción
    When se intenta crear los detalles del pedido
    Then se debe lanzar una excepción IllegalArgumentException con el mensaje "La cantidad del producto no puede ser 0 o negativa"

  Scenario: Varios Productos Válidos
    Given un pedido válido existe
    And los detalles son:
      | productoId | cantidad |
      | 1          | 1        |
      | 2          | 1        |
      | 3          | 1        |
      | 5          | 1        |
    When se intenta crear los detalles del pedido
    Then los detalles deben ser creados correctamente
    And el importe total debe ser 160000.0