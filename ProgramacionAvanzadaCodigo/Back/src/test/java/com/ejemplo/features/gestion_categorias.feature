Feature: Gestión Categorías

  Scenario: Crear categoría
    Given El administrador se autentica con usuario "2fernando2cagliero@gmail.com" y contraseña "fer12345"
    And El administrador ingresa nombre "Categoria Prueba" y descripción "Categoría para test de prueba."
    When el administrador intenta crear la categoría
    Then la categoría se crea correctamente

  Scenario: No se deben crear categorías duplicadas con el mismo nombre
    Given El administrador se autentica con usuario "2fernando2cagliero@gmail.com" y contraseña "fer12345"
    And El administrador ingresa nombre "Categoria Prueba 2" y descripción "Categoría para test de prueba."
    And el administrador crea la categoría
    When el administrador intenta crear otra categoría con el mismo nombre
    Then el sistema debe rechazar la creación, indicando que la categoría ya existe
