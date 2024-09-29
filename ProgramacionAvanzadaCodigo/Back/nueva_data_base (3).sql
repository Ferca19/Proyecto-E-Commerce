-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 29-09-2024 a las 21:13:06
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
  `nombre` varchar(255) DEFAULT NULL,
  `eliminado` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `categoria`
--

INSERT INTO `categoria` (`id`, `descripcion`, `nombre`, `eliminado`) VALUES
(1, 'Categoria para Calzados', 'Calzados', 0),
(2, 'Categoria para Pantalones', 'Pantalones', 0),
(3, 'Categoria para Gorras', 'Gorras', 1),
(4, 'Categoria para Calzados', 'Tecnologia', 0),
(5, 'Categoria para Calzados', 'Electronica', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_pedido`
--

CREATE TABLE `detalle_pedido` (
  `id` int NOT NULL,
  `cantidad` int DEFAULT NULL,
  `eliminado` int NOT NULL,
  `subtotal` double NOT NULL,
  `pedido_id` int NOT NULL,
  `producto_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `detalle_pedido`
--

INSERT INTO `detalle_pedido` (`id`, `cantidad`, `eliminado`, `subtotal`, `pedido_id`, `producto_id`) VALUES
(1, 2, 0, 100000, 2, 1),
(2, 1, 0, 40000, 2, 2),
(3, 3, 0, 150000, 3, 1),
(4, 4, 0, 160000, 3, 2),
(5, 1, 0, 50000, 4, 1),
(6, 1, 0, 40000, 4, 2),
(7, 1, 0, 50000, 5, 1),
(8, 1, 0, 40000, 5, 2),
(9, 1, 0, 50000, 6, 1),
(10, 1, 0, 40000, 6, 2),
(11, 1, 0, 50000, 7, 1),
(12, 1, 0, 40000, 7, 2),
(13, 6, 0, 300000, 8, 1),
(14, 3, 0, 120000, 8, 2),
(15, 6, 0, 300000, 9, 1),
(16, 3, 0, 120000, 9, 2),
(17, 6, 0, 300000, 10, 1),
(18, 3, 0, 120000, 10, 2),
(19, 6, 0, 300000, 11, 1),
(20, 3, 0, 120000, 11, 2),
(27, 6, 0, 300000, 15, 1),
(28, 3, 0, 120000, 15, 2),
(29, 4, 0, 200000, 16, 1),
(30, 2, 0, 80000, 16, 2),
(31, 4, 0, 200000, 17, 1),
(32, 2, 0, 80000, 17, 2),
(33, 4, 0, 200000, 18, 1),
(34, 2, 0, 80000, 18, 2),
(35, 4, 0, 200000, 19, 1),
(36, 2, 0, 80000, 19, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado`
--

CREATE TABLE `estado` (
  `id` int NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `eliminado` int NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `estado`
--

INSERT INTO `estado` (`id`, `descripcion`, `eliminado`, `nombre`) VALUES
(1, 'Estado para pedidos Pendientes', 0, 'Pendiente'),
(2, 'Estado para pedidos Completados', 0, 'Completado');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `marca`
--

CREATE TABLE `marca` (
  `id` int NOT NULL,
  `denominacion` varchar(255) DEFAULT NULL,
  `observaciones` varchar(255) DEFAULT NULL,
  `eliminado` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `marca`
--

INSERT INTO `marca` (`id`, `denominacion`, `observaciones`, `eliminado`) VALUES
(1, 'Nike', 'Marca de vestimenta principalmente deportiva', 0),
(2, 'Adidas', 'Marca de vestimenta principalmente deportiva', 0),
(3, 'Topper', 'Marca de vestimenta principalmente deportiva', 0),
(4, 'Logitech', 'Marca', 0),
(5, 'Intel', 'Marca', 0),
(6, 'Intel', 'Marca', 0),
(7, 'Puma', 'Marca puma', 0),
(8, 'Asus', 'marca asus', 0),
(9, 'Samsung', 'marca Samsung', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedido`
--

CREATE TABLE `pedido` (
  `id` int NOT NULL,
  `eliminado` int NOT NULL,
  `fechayhora` datetime(6) DEFAULT NULL,
  `importe_total` double NOT NULL,
  `estado_id` int NOT NULL,
  `usuario_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `pedido`
--

INSERT INTO `pedido` (`id`, `eliminado`, `fechayhora`, `importe_total`, `estado_id`, `usuario_id`) VALUES
(2, 0, '2024-09-26 19:23:18.765000', 140000, 1, 1),
(3, 0, '2024-09-26 19:32:54.117000', 310000, 1, 1),
(4, 0, '2024-09-26 19:33:24.689000', 90000, 1, 1),
(5, 0, '2024-09-26 19:43:01.517000', 90000, 1, 1),
(6, 0, '2024-09-26 19:50:17.977000', 90000, 1, 1),
(7, 0, '2024-09-26 20:01:18.671000', 90000, 1, 1),
(8, 0, '2024-09-27 10:27:33.542000', 420000, 1, 1),
(9, 0, '2024-09-27 10:39:00.215000', 420000, 1, 1),
(10, 0, '2024-09-27 10:47:58.867000', 420000, 1, 1),
(11, 0, '2024-09-27 10:55:05.379000', 420000, 1, 1),
(15, 0, '2024-09-27 11:38:41.159000', 420000, 1, 1),
(16, 1, '2024-09-27 11:54:41.082000', 280000, 1, 1),
(17, 0, '2024-09-27 11:56:04.688000', 280000, 1, 1),
(18, 0, '2024-09-27 14:37:44.901000', 280000, 1, 3),
(19, 0, '2024-09-29 14:49:02.130000', 280000, 1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` int NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `eliminado` int NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double NOT NULL,
  `stock` int NOT NULL,
  `categoria_id` int NOT NULL,
  `marca_id` int NOT NULL,
  `subcategoria_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `descripcion`, `eliminado`, `nombre`, `precio`, `stock`, `categoria_id`, `marca_id`, `subcategoria_id`) VALUES
(1, 'zapatillas deportivas', 0, 'ZapatillasAir max', 50000, 16, 1, 1, 1),
(2, 'zapatillas deportivas', 0, 'Zapatillas airforce', 40000, 15, 1, 1, 1),
(3, 'zapatillas deportivas', 0, 'Zapatillas running 1', 40000, 18, 1, 1, 1);

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
(1, 'Subcategoria para calzados, especificamente zapatillas', 0, 'Zapatillas'),
(2, 'Subcategoria para remeras', 0, 'Remeras');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id` int NOT NULL,
  `apellido` varchar(255) DEFAULT NULL,
  `contrasena` varchar(255) DEFAULT NULL,
  `eliminado` int NOT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `apellido`, `contrasena`, `eliminado`, `mail`, `nombre`) VALUES
(1, 'Pérez', 'contraseña123', 0, 'juan.perez@example.com', 'Juan'),
(2, NULL, '$2a$10$uA.pqHcvQJ0mZYmlZW4muuJ0QyJ2WEDklbTUxVM2LpDaMyD6j5ZU2', 0, 'loloblanc@gmail.com', 'Lolo'),
(3, 'Cagliero', '$2a$10$aPAwoIzEgQwnBDg0A06Iy.KCQPYhczAtZNzpPbomymG6utAsP3BkS', 0, '2fernando2cagliero@gmail.com', 'Fer'),
(4, NULL, '$2a$10$AbnjIzpdPqrpQQIiynDl1eMMHPKOnmkNbzkyUibMC8CYRKLn7eJa.', 0, '1fernando1cagliero@gmail.com', NULL),
(5, NULL, '$2a$10$Qp1diIGJUA0Bb5hPr3/Dd.PY9aehJKIPKK.vgHov2.Oe7wPXvf1nC', 0, 'usuariodeprueba@gmail.com', NULL),
(6, 'ledo', '$2a$10$bfu457tIv4Gxe/dTc27XbuHg7cJCu5ate3HdLncNgSSsNdCOpmyOO', 0, 'martinledo@gmail.com', 'martin'),
(7, 'borgo', '$2a$10$A0zt04IjtlMNkEMb9Yd9AuEWAcRjML8nmEqdyQrRm7iyJb0mVQmH2', 0, 'kevinborgo@gmail.com', 'kevin');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKgqvba9e7dildyw45u0usdj1k2` (`pedido_id`),
  ADD KEY `FK2yc3nts8mdyqf6dw6ndosk67a` (`producto_id`);

--
-- Indices de la tabla `estado`
--
ALTER TABLE `estado`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `marca`
--
ALTER TABLE `marca`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlpuc2kd4q97wd68te94hcw8sl` (`estado_id`),
  ADD KEY `FK6uxomgomm93vg965o8brugt00` (`usuario_id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKodqr7965ok9rwquj1utiamt0m` (`categoria_id`),
  ADD KEY `FK868tnrt85f21kgcvt9bftgr8r` (`marca_id`),
  ADD KEY `FKa571qppf005vbgabojxpwgjwe` (`subcategoria_id`);

--
-- Indices de la tabla `subcategoria`
--
ALTER TABLE `subcategoria`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;

--
-- AUTO_INCREMENT de la tabla `estado`
--
ALTER TABLE `estado`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `marca`
--
ALTER TABLE `marca`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `subcategoria`
--
ALTER TABLE `subcategoria`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  ADD CONSTRAINT `FK2yc3nts8mdyqf6dw6ndosk67a` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`),
  ADD CONSTRAINT `FKgqvba9e7dildyw45u0usdj1k2` FOREIGN KEY (`pedido_id`) REFERENCES `pedido` (`id`);

--
-- Filtros para la tabla `pedido`
--
ALTER TABLE `pedido`
  ADD CONSTRAINT `FK6uxomgomm93vg965o8brugt00` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`),
  ADD CONSTRAINT `FKlpuc2kd4q97wd68te94hcw8sl` FOREIGN KEY (`estado_id`) REFERENCES `estado` (`id`);

--
-- Filtros para la tabla `producto`
--
ALTER TABLE `producto`
  ADD CONSTRAINT `FK868tnrt85f21kgcvt9bftgr8r` FOREIGN KEY (`marca_id`) REFERENCES `marca` (`id`),
  ADD CONSTRAINT `FKa571qppf005vbgabojxpwgjwe` FOREIGN KEY (`subcategoria_id`) REFERENCES `subcategoria` (`id`),
  ADD CONSTRAINT `FKodqr7965ok9rwquj1utiamt0m` FOREIGN KEY (`categoria_id`) REFERENCES `categoria` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
