Feature: Gestión Categorías

  Scenario: No se deben crear categorías duplicadas con el mismo nombre
    Given una categoría con nombre "Categoria Prueba 2" ya existe
    When el administrador intenta crear otra categoría con el mismo nombre "Categoria Prueba 2"
    Then el sistema debe rechazar la creación, indicando que la categoría ya existe

