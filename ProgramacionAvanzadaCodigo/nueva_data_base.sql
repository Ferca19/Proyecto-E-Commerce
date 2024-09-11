-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 11-09-2024 a las 15:12:27
-- Versión del servidor: 8.0.39
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `nueva_data_base`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categoria`
--

CREATE TABLE `categoria` (
  `id` int NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `eliminado` int NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `descripcion`, `eliminado`, `nombre`) VALUES
(1, 'Categoria para Ropa', 0, 'Ropa'),
(2, 'Categoria para Electronica', 0, 'Electronica'),
(3, 'Categoria para Alimentos y Bebidas', 0, 'Alimentos y Bebidas'),
(4, 'Categoria para Muebles', 0, 'Muebles');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE `marca` (
  `id` int NOT NULL,
  `denominacion` varchar(255) DEFAULT NULL,
  `eliminado` int NOT NULL,
  `observaciones` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `marca`
--

INSERT INTO `marca` (`id`, `denominacion`, `eliminado`, `observaciones`) VALUES
(1, 'Nike', 0, 'Marca de ropa principalmente deportiva'),
(2, 'Samsung', 0, 'Marca de electronica'),
(3, 'La Serenísima', 0, 'Marca de lacteos'),
(4, 'Xiaomi', 0, 'Marca de tecnología'),
(5, 'Apple', 0, 'Marca de apple');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` int NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double NOT NULL,
  `stock` int NOT NULL,
  `categoria_id` int NOT NULL,
  `marca_id` int NOT NULL,
  `subcategoria_id` int NOT NULL,
  `eliminado` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `descripcion`, `nombre`, `precio`, `stock`, `categoria_id`, `marca_id`, `subcategoria_id`, `eliminado`) VALUES
(1, 'Televisor LED 4K con tecnología HDR', 'Televisor LED 55 pulgadas', 499.99, 10, 2, 2, 2, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto_variante`
--

CREATE TABLE `producto_variante` (
  `id` int NOT NULL,
  `nombre_variante` varchar(255) DEFAULT NULL,
  `valor_variante` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto_variante_producto`
--

CREATE TABLE `producto_variante_producto` (
  `producto_variante_id` int NOT NULL,
  `producto_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `subcategoria`
--

CREATE TABLE `subcategoria` (
  `id` int NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `eliminado` int NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `subcategoria`
--

INSERT INTO `subcategoria` (`id`, `descripcion`, `eliminado`, `nombre`) VALUES
(1, 'Subcategoria para Camisetas', 0, 'Camisetas'),
(2, 'Subcategoria para smartphones', 0, 'Smartphones'),
(3, 'Subcategoria para lacteos', 0, 'Lacteos'),
(4, 'Subcategoria para Notebooks', 0, 'Notebooks');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKodqr7965ok9rwquj1utiamt0m` (`categoria_id`),
  ADD KEY `FK868tnrt85f21kgcvt9bftgr8r` (`marca_id`),
  ADD KEY `FKa571qppf005vbgabojxpwgjwe` (`subcategoria_id`);

--
-- Indices de la tabla `producto_variante`
--
ALTER TABLE `producto_variante`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `producto_variante_producto`
--
ALTER TABLE `producto_variante_producto`
  ADD KEY `FK5k7p82vu3puibbqsk7gk7t9k6` (`producto_id`),
  ADD KEY `FK1dwypxbg1jvp3lrn6jy0p20yb` (`producto_variante_id`);

--
-- Indices de la tabla `subcategoria`
--
ALTER TABLE `subcategoria`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `marca`
--
ALTER TABLE `marca`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `producto_variante`
--
ALTER TABLE `producto_variante`
  MODIFY `id` int NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `subcategoria`
--
ALTER TABLE `subcategoria`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `FK868tnrt85f21kgcvt9bftgr8r` FOREIGN KEY (`marca_id`) REFERENCES `marca` (`id`),
  ADD CONSTRAINT `FKa571qppf005vbgabojxpwgjwe` FOREIGN KEY (`subcategoria_id`) REFERENCES `subcategoria` (`id`),
  ADD CONSTRAINT `FKodqr7965ok9rwquj1utiamt0m` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`);

--
-- Filtros para la tabla `producto_variante_producto`
--
ALTER TABLE `producto_variante_producto`
  ADD CONSTRAINT `FK1dwypxbg1jvp3lrn6jy0p20yb` FOREIGN KEY (`producto_variante_id`) REFERENCES `producto_variante` (`id`),
  ADD CONSTRAINT `FK5k7p82vu3puibbqsk7gk7t9k6` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
