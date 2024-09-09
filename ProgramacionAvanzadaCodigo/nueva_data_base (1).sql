-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 09-09-2024 a las 15:52:05
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
  `estado` int NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `descripcion`, `estado`, `nombre`) VALUES
(1, 'Categoria para Calzados', 0, 'Calzados'),
(2, 'Categoria para Pantalones', 0, 'Pantalones'),
(3, 'Categoria para Gorras', 0, 'Gorras');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE `marca` (
  `id` int NOT NULL,
  `denominacion` varchar(255) DEFAULT NULL,
  `estado` int NOT NULL,
  `observaciones` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `marca`
--

INSERT INTO `marca` (`id`, `denominacion`, `estado`, `observaciones`) VALUES
(1, 'Nike', 0, 'Marca de vestimenta principalmente deportiva');

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
  `marca_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `descripcion`, `nombre`, `precio`, `stock`, `categoria_id`, `marca_id`) VALUES
(1, 'Zapatillas modelo air max', 'Zapatillas Air max', 699.99, 20, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto_variante`
--

CREATE TABLE `producto_variante` (
  `id` int NOT NULL,
  `nombre_variante` varchar(255) DEFAULT NULL,
  `valor_variante` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `producto_variante`
--

INSERT INTO `producto_variante` (`id`, `nombre_variante`, `valor_variante`) VALUES
(1, 'Talle', '42'),
(2, 'Talle', '41'),
(3, 'Color', 'Rosa-Blanco');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto_variante_producto`
--

CREATE TABLE `producto_variante_producto` (
  `producto_variante_id` int NOT NULL,
  `producto_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `producto_variante_producto`
--

INSERT INTO `producto_variante_producto` (`producto_variante_id`, `producto_id`) VALUES
(2, 1),
(3, 1);

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
  ADD KEY `FK868tnrt85f21kgcvt9bftgr8r` (`marca_id`);

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
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `marca`
--
ALTER TABLE `marca`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `producto_variante`
--
ALTER TABLE `producto_variante`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `FK868tnrt85f21kgcvt9bftgr8r` FOREIGN KEY (`marca_id`) REFERENCES `marca` (`id`),
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
