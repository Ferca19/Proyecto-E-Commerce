-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 06-11-2024 a las 04:16:14
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
-- Estructura de tabla para la tabla `ajuste_inventario`
--

CREATE TABLE `ajuste_inventario` (
  `id` int NOT NULL,
  `eliminado` int NOT NULL,
  `fecha` datetime(6) DEFAULT NULL,
  `razon_ajuste` varchar(255) DEFAULT NULL,
  `tipo_ajuste` int NOT NULL,
  `usuario_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `ajuste_inventario`
--

INSERT INTO `ajuste_inventario` (`id`, `eliminado`, `fecha`, `razon_ajuste`, `tipo_ajuste`, `usuario_id`) VALUES
(9, 0, '2024-10-30 07:15:30.000000', 'Corrección de inventario', 1, 3),
(10, 0, '2024-10-30 07:15:30.000000', 'Corrección de inventario', 1, 3),
(11, 0, '2024-10-30 07:15:30.000000', 'Corrección de inventario', 2, 3),
(12, 0, '2024-10-30 07:15:30.000000', 'Corrección de inventario', 2, 3),
(13, 0, '2024-10-30 07:15:30.000000', 'Corrección de inventario', 2, 3),
(14, 0, '2024-10-30 07:15:30.000000', 'Corrección de inventario', 2, 3),
(15, 0, '2024-10-30 07:15:30.000000', 'Corrección de inventario', 2, 3),
(16, 0, '2024-10-30 07:15:30.000000', 'Corrección de inventario', 2, 3),
(17, 0, '2024-10-30 07:15:30.000000', 'Corrección de inventario', 2, 3),
(18, 0, '2024-10-30 07:15:30.000000', 'Corrección de inventario', 2, 3),
(19, 0, NULL, 'Corrección de inventario', 2, 3),
(20, 0, '2024-10-30 14:53:00.433000', 'Corrección de inventario', 2, 3),
(22, 0, '2024-10-30 14:56:29.995000', 'Corrección de inventario', 2, 3),
(23, 0, '2024-10-30 15:43:02.622000', 'Corrección de inventario', 2, 3),
(24, 0, '2024-10-30 15:43:16.056000', 'Corrección de inventario', 1, 3);

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
(5, 'Categoria para Calzados', 'Electronica', 0),
(6, 'Categoria para Televisores', 'Televisores', 1),
(7, 'Categoria para Televisores', 'Pedilo ya', 1),
(51, 'nuevaaaaaaa', 'Nueva categoria', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalle_ajuste_inventario`
--

CREATE TABLE `detalle_ajuste_inventario` (
  `id` int NOT NULL,
  `cantidad_ajustada` int NOT NULL,
  `eliminado` int NOT NULL,
  `ajuste_inventario_id` int NOT NULL,
  `producto_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `detalle_ajuste_inventario`
--

INSERT INTO `detalle_ajuste_inventario` (`id`, `cantidad_ajustada`, `eliminado`, `ajuste_inventario_id`, `producto_id`) VALUES
(1, 10, 0, 9, 1),
(2, 10, 0, 10, 2),
(3, 10, 0, 11, 1),
(4, 10, 0, 12, 1),
(5, 10, 0, 13, 1),
(6, 10, 0, 14, 1),
(7, 10, 0, 15, 1),
(8, 10, 0, 16, 1),
(9, 10, 0, 17, 2),
(10, 10, 0, 18, 2),
(11, 1, 0, 19, 2),
(12, 1, 0, 20, 2),
(13, 10, 0, 22, 4),
(14, 10, 0, 23, 1),
(15, 10, 0, 24, 1);

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
(36, 2, 0, 80000, 19, 3),
(37, 4, 0, 200000, 20, 1),
(38, 2, 0, 80000, 20, 3),
(39, 2, 0, 100000, 21, 1),
(40, 1, 0, 30000, 21, 6),
(41, 3, 0, 90000, 21, 5),
(42, 1, 0, 30000, 22, 4),
(43, 1, 0, 50000, 22, 1),
(44, 1, 0, 40000, 22, 2),
(45, 1, 0, 40000, 22, 3),
(46, 1, 0, 30000, 22, 5),
(47, 1, 0, 30000, 22, 6),
(48, 1, 0, 35000, 22, 7),
(49, 1, 0, 35000, 22, 8),
(59, 3, 0, 105000, 32, 7),
(60, 3, 0, 105000, 33, 7),
(61, 3, 0, 105000, 34, 7),
(62, 1, 0, 35000, 35, 7),
(63, 1, 0, 35000, 36, 7),
(64, 1, 0, 30000, 37, 5),
(65, 1, 0, 12500, 38, 9),
(66, 2, 0, 60000, 39, 6),
(67, 2, 0, 100000, 40, 1),
(68, 1, 0, 50000, 41, 1),
(69, 1, 0, 50000, 43, 1),
(70, 1, 0, 50000, 44, 1),
(71, 3, 0, 90000, 45, 6),
(72, 3, 0, 90000, 46, 6),
(73, 1, 0, 50000, 47, 1),
(74, 4, 0, 200000, 52, 1),
(75, 3, 0, 120000, 52, 2),
(76, 2, 0, 25000, 52, 9);

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
(7, 'Puma', 'Marca puma', 0),
(8, 'Asus', 'marca asus', 0),
(9, 'Samsung', 'marca Samsung', 0),
(10, 'Hp', 'marcaaaaaaaa', 1),
(13, 'Intel', 'nueva descripcion', 0),
(16, 'Rexona', 'Marca', 1);

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
(19, 0, '2024-09-29 14:49:02.130000', 280000, 1, 3),
(20, 0, '2024-09-29 17:34:44.622000', 280000, 1, 7),
(21, 0, '2024-10-03 11:46:07.322000', 220000, 1, 1),
(22, 0, '2024-10-04 13:34:02.036000', 290000, 1, 3),
(32, 0, '2024-10-29 15:04:59.267000', 105000, 1, 3),
(33, 0, '2024-10-29 15:05:13.046000', 105000, 1, 3),
(34, 0, '2024-10-29 15:05:54.639000', 105000, 1, 3),
(35, 0, '2024-10-29 15:06:21.655000', 35000, 1, 3),
(36, 0, '2024-10-29 15:07:28.478000', 35000, 1, 3),
(37, 0, '2024-10-29 18:44:39.982000', 30000, 1, 3),
(38, 0, '2024-10-29 18:59:58.380000', 12500, 1, 3),
(39, 0, '2024-10-30 10:23:05.109000', 60000, 1, 3),
(40, 0, '2024-10-30 10:31:14.119000', 100000, 1, 3),
(41, 0, '2024-10-30 15:49:08.903000', 50000, 1, 3),
(43, 0, '2024-10-30 19:20:43.534000', 50000, 1, 3),
(44, 0, '2024-10-30 19:44:44.714000', 50000, 1, 3),
(45, 0, '2024-10-30 20:00:56.358000', 90000, 1, 3),
(46, 0, '2024-10-30 20:35:24.531000', 90000, 1, 3),
(47, 0, '2024-10-30 20:38:32.165000', 50000, 1, 3),
(52, 0, '2024-10-30 21:27:00.559000', 345000, 1, 3);

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
  `subcategoria_id` int NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `tamano` varchar(255) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `descripcion`, `eliminado`, `nombre`, `precio`, `stock`, `categoria_id`, `marca_id`, `subcategoria_id`, `color`, `tamano`, `imagen`) VALUES
(1, 'zapatillas deportivas', 0, 'Zapatillas Nike SB Dunk Low Travis Scott', 50000, 9, 1, 1, 1, 'Marron', '42', 'zapatillas nike SB Dunk Travis Scott.jpg'),
(2, 'zapatillas deportivas', 0, 'Zapatillas Air Force 1', 40000, 23, 1, 1, 1, 'Blanco', '42', 'Nike Air Zoom Pegasus 35 Turbo.gif'),
(3, 'zapatillas deportivas', 0, 'Zapatillas Forum Buckle Low - Bad Bunny', 40000, 15, 1, 1, 1, 'Celeste', '42', 'zapatillas FORUM BUCKLE LOW - BAD BUNNY.jpg'),
(4, 'zapatillas deportivas', 1, 'Zapatillas Air Force 1 Travis Scott Cactus Jack', 30000, 19, 1, 1, 1, 'Beige', '42', 'zapatillas Air Force 1 Travis Scott Cactus Jack Grey.jpg'),
(5, 'zapatillas deportivas', 0, 'Zapatillas Adidas Forum Low Bad Bunny ', 30000, 5, 1, 1, 1, 'negro', '41', 'zapatillas Adidas Forum Low Bad Bunny.png'),
(6, 'zapatillas deportivas', 0, 'Zapatillas Nike LeBron NXXT Gen AMPD', 30000, 0, 1, 1, 1, 'Rojo', '42', 'zapatillas nike lebron.jpeg'),
(7, 'Zapatillas deportivas', 0, 'Zapatillas running 5', 35000, 18, 1, 1, 1, 'Negro', '42', 'nike1geekersblog.gif'),
(8, 'Zapatillas deportivas', 1, 'Zapatillas running 6', 35000, 29, 1, 1, 1, 'Blanco', '43', 'Zapatillas2.avif'),
(9, 'prueba', 0, 'Zapatillas de Prueba', 12500, 7, 1, 2, 1, 'Azul', '42', 'Zapatillas_Ultrabounce_Azul_IE0717_01_standard.avif'),
(10, 'Zapatillas Deportivas', 0, 'Zapatillas Adidas Duramo', 45000, 40, 1, 1, 1, 'Negro', '42', 'Zapatillas_Duramo_SL_Negro_IE4034_01_standard.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id` int NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `eliminado` int NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id`, `descripcion`, `eliminado`, `nombre`) VALUES
(1, 'Este es un rol para un Administrador', 0, 'Administrador'),
(2, 'Este es un rol para un Cliente', 0, 'Cliente');

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
(2, 'Subcategoria para remeras', 0, 'Remeras'),
(3, 'nuevaaaaaaa', 1, 'Ojotas');

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
  `nombre` varchar(255) DEFAULT NULL,
  `rol_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id`, `apellido`, `contrasena`, `eliminado`, `mail`, `nombre`, `rol_id`) VALUES
(1, 'Pérez', 'contraseña123', 0, 'juan.perez@example.com', 'Juan', 2),
(3, 'Cagliero', '$2a$10$aPAwoIzEgQwnBDg0A06Iy.KCQPYhczAtZNzpPbomymG6utAsP3BkS', 0, '2fernando2cagliero@gmail.com', 'Fer', 1),
(6, 'ledo', '$2a$10$bfu457tIv4Gxe/dTc27XbuHg7cJCu5ate3HdLncNgSSsNdCOpmyOO', 0, 'martinledo@gmail.com', 'martin', 2),
(7, 'borgo', '$2a$10$A0zt04IjtlMNkEMb9Yd9AuEWAcRjML8nmEqdyQrRm7iyJb0mVQmH2', 0, 'kevinborgo@gmail.com', 'kevin', 2),
(8, 'Cagliero', '$2a$10$l62RZh9FQquHBz4VXtYUUesOMBi5/47V1b.41NtrH41.VADQoFhE.', 0, '1fernando1cagliero@gmail.com', 'Fernando', 2),
(13, 'prueba', '$2a$10$xk2dcHKSihrtvBMY8a8OJ.3ci7JfpWxizQGNKxPGKMKqljwEZOWda', 0, 'usuarioprueba@gmail.com', 'usuario', 2),
(27, 'blanc', '$2a$10$FlfI7Dy.GTMhTQ4qba9fd.vR6JUFWUf99VKP6soaqM1wxVhH09KXS', 0, 'loloblanc@gmail.com', 'lolo', 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `ajuste_inventario`
--
ALTER TABLE `ajuste_inventario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKltexsmjrhj6gmxrgorw21tx2a` (`usuario_id`);

--
-- Indices de la tabla `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `detalle_ajuste_inventario`
--
ALTER TABLE `detalle_ajuste_inventario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsrj4965y0mqb6bqpbe0y354hy` (`ajuste_inventario_id`),
  ADD KEY `FK9gk6q5lrfr29trjwp9odio00s` (`producto_id`);

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
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `subcategoria`
--
ALTER TABLE `subcategoria`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKshkwj12wg6vkm6iuwhvcfpct8` (`rol_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ajuste_inventario`
--
ALTER TABLE `ajuste_inventario`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT de la tabla `categoria`
--
ALTER TABLE `categoria`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT de la tabla `detalle_ajuste_inventario`
--
ALTER TABLE `detalle_ajuste_inventario`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT de la tabla `detalle_pedido`
--
ALTER TABLE `detalle_pedido`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=77;

--
-- AUTO_INCREMENT de la tabla `estado`
--
ALTER TABLE `estado`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `marca`
--
ALTER TABLE `marca`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `pedido`
--
ALTER TABLE `pedido`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=53;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `subcategoria`
--
ALTER TABLE `subcategoria`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ajuste_inventario`
--
ALTER TABLE `ajuste_inventario`
  ADD CONSTRAINT `FKltexsmjrhj6gmxrgorw21tx2a` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`);

--
-- Filtros para la tabla `detalle_ajuste_inventario`
--
ALTER TABLE `detalle_ajuste_inventario`
  ADD CONSTRAINT `FK9gk6q5lrfr29trjwp9odio00s` FOREIGN KEY (`producto_id`) REFERENCES `producto` (`id`),
  ADD CONSTRAINT `FKsrj4965y0mqb6bqpbe0y354hy` FOREIGN KEY (`ajuste_inventario_id`) REFERENCES `ajuste_inventario` (`id`);

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

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `FKshkwj12wg6vkm6iuwhvcfpct8` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
