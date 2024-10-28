Feature: Validación de precio del producto

  Scenario Outline: Verificar límite de precio del producto
    Given un producto con precio "<precio>"
    When el administrador intenta guardar el producto
    Then el sistema debe responder con "<resultado>"

    Examples:
      | precio   | resultado                                                  |
      | 0.0      | El precio del producto no puede ser 0 o negativo           |
      | 1.0      | Producto guardado con éxito                                |
      | 9999.0   | Producto guardado con éxito                                |
      | 10000.0  | Producto guardado con éxito                                |
      | -1.0     | El precio del producto no puede ser 0 o negativo           |