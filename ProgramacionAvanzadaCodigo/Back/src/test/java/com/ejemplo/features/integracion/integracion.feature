Feature: Gestión Categorías

  Scenario: Crear categoría
    Given El administrador se autentica con usuario "2fernando2cagliero@gmail.com" y contraseña "fer12345"
    And El administrador ingresa nombre "Categoria Prueba" y descripción "Categoría para test de prueba."
    When el administrador intenta crear la categoría
    Then la categoría se crea correctamente
