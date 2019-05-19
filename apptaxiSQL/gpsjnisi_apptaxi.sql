-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 25-02-2019 a las 18:54:38
-- Versión del servidor: 5.5.62-cll
-- Versión de PHP: 7.2.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gpsjnisi_apptaxi`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `api_key`
--

CREATE TABLE `api_key` (
  `id` int(11) NOT NULL,
  `api_key` varchar(250) NOT NULL,
  `describe` varchar(20) NOT NULL,
  `tipo` enum('conductor','cliente') NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `api_key`
--

INSERT INTO `api_key` (`id`, `api_key`, `describe`, `tipo`) VALUES
(1, 'AIzaSyDWcWMkt18v2b-_ctjztPPUDNo0n93Y8Zw', 'NUEVO SERVICIO', 'conductor'),
(2, 'AIzaSyDWcWMkt18v2b-_ctjztPPUDNo0n93Y8Zw', 'SERVICIO ACEPTADO', 'conductor'),
(3, 'AIzaSyDWcWMkt18v2b-_ctjztPPUDNo0n93Y8Zw', 'TRANSPORTANDO', 'conductor'),
(4, 'AIzaSyDWcWMkt18v2b-_ctjztPPUDNo0n93Y8Zw', 'FINALIZAR', 'conductor'),
(5, 'AIzaSyDWcWMkt18v2b-_ctjztPPUDNo0n93Y8Zw', 'TARIFARIO', 'conductor');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `asistencia`
--

CREATE TABLE `asistencia` (
  `id` int(11) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `fecha` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `barrios`
--

CREATE TABLE `barrios` (
  `id_barrio` int(11) NOT NULL,
  `id_distrito` int(11) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `barrios`
--

INSERT INTO `barrios` (`id_barrio`, `id_distrito`, `tipo`, `nombre`, `estado`) VALUES
(2, 2, 'Barrio', 'Aranjuez', 1),
(7, 2, 'Urb.', 'Miraflores', 1),
(3, 2, 'Urb.', 'Las Quintanas', 1),
(4, 2, 'Barrio', 'La Intendencia', 1),
(5, 1, 'Urb.', 'FÃ¡tima', 1),
(6, 1, 'Urb.', 'California', 1),
(8, 2, 'Barrio', 'Centro Historico', 1),
(9, 2, 'Urb.', 'El Molino', 1),
(10, 2, 'Urb.', 'Los Jardines', 1),
(11, 1, 'Urb.', 'El Golf', 1),
(12, 2, 'Urb.', 'Pay Pay', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `barrios_referencia`
--

CREATE TABLE `barrios_referencia` (
  `id_referencia` int(11) NOT NULL,
  `id_barrio` int(11) NOT NULL,
  `lat` varchar(25) NOT NULL,
  `lng` varchar(25) NOT NULL,
  `descripcion` text NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `barrios_referencia`
--

INSERT INTO `barrios_referencia` (`id_referencia`, `id_barrio`, `lat`, `lng`, `descripcion`, `estado`) VALUES
(1, 7, '-8.098214', '-79.022094', 'Todo urbanizacion miraflores', 1),
(4, 2, '-8.103206', '-79.013897', 'Por la altura de iquitos,pasamayo', 1),
(5, 8, '-8.115293265824544', '-79.02787685394287', 'Entre Grau y Bolognesi', 1),
(6, 8, '-8.112000619067551', '-79.03133153915405', 'Entre Bolognesi y San Martin', 1),
(7, 8, '-8.107667029971706', '-79.02753353118896', 'Entre Junin y San Martin', 1),
(8, 8, '-8.109451454667578', '-79.02422904968262', 'Entre Colon y Bolivar', 1),
(9, 8, '-8.112000619067551', '-79.02798414230347', 'Por la plaza de armas', 1),
(10, 9, '-8.100529252035114', '-79.02391791343689', 'Toda la urvbanizacion el molino', 1),
(11, 12, '-8.094697848441387', '-79.01686906814575', 'Toda la urbanizacion pay pay', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `chat_conductor_usuario`
--

CREATE TABLE `chat_conductor_usuario` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `mensaje` text NOT NULL,
  `fecha` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `destinos`
--

CREATE TABLE `destinos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `destinos`
--

INSERT INTO `destinos` (`id`, `nombre`, `estado`) VALUES
(1, 'California', 1),
(2, 'San Andres', 1),
(3, 'Miraflores', 1),
(4, 'Los Jardines', 1),
(5, 'Salaverry', 1),
(6, 'Huanchaco', 1),
(7, 'Moche', 1),
(8, 'Laredo', 1),
(9, 'Conache', 1),
(10, 'Poroto', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `DEVICEINFO`
--

CREATE TABLE `DEVICEINFO` (
  `id` int(11) NOT NULL,
  `TOKENID` varchar(250) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `DEVICEINFO`
--

INSERT INTO `DEVICEINFO` (`id`, `TOKENID`) VALUES
(147, 'egzscpQcVNA:APA91bE9C3Vwr0BlwWKfT0XfensqMObrIG1g2kvCmMGPCclzodxhef9O3nEoOo58UyIJqE2OAqaX2H3ytGxt9X4P9gOpNiNXh1slc9mS9HiZciQOQYMOYqnsMD26qojQhCd_AbkvyDDS');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `direcciones`
--

CREATE TABLE `direcciones` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `pais` varchar(50) DEFAULT NULL,
  `depa` varchar(100) DEFAULT NULL,
  `prov` varchar(100) DEFAULT NULL,
  `distr` varchar(100) DEFAULT NULL,
  `dire` varchar(100) NOT NULL,
  `numero` varchar(50) NOT NULL,
  `urba` varchar(100) NOT NULL,
  `referencia` varchar(100) NOT NULL,
  `oculto` int(11) NOT NULL DEFAULT '0',
  `fecha_registro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `latitud` varchar(20) NOT NULL,
  `longitud` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `direcciones`
--

INSERT INTO `direcciones` (`id`, `id_usuario`, `pais`, `depa`, `prov`, `distr`, `dire`, `numero`, `urba`, `referencia`, `oculto`, `fecha_registro`, `latitud`, `longitud`) VALUES
(1606, 25, NULL, NULL, NULL, NULL, 'VXJiYW5pemFjacOzbiBDYXBsaW5hIA==', 'C4', '', 'cG9yIGVsIGVzY3VhZHLDs24=', 0, '2019-01-13 22:12:27', '-18.00159263735733', '-70.23222483694553'),
(1607, 31, NULL, NULL, NULL, NULL, 'QXZlbmlkYSBKb3JnZSBCYXNhZHJlIEdyb2htYW5uIE9lc3Rl', '3', '', '', 0, '2019-01-28 02:47:59', '-18.008537988901995', '-70.25752041488887');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `distritos`
--

CREATE TABLE `distritos` (
  `id` int(11) NOT NULL,
  `provincia` varchar(50) NOT NULL,
  `distrito` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `distritos`
--

INSERT INTO `distritos` (`id`, `provincia`, `distrito`) VALUES
(1, 'Trujillo', 'Victor Larco Herrera'),
(2, 'Trujillo', 'Trujillo'),
(3, 'Trujillo', 'Huanchaco'),
(4, 'Trujillo', 'Laredo'),
(5, 'Trujillo', 'La Esperanza'),
(6, 'Trujillo', 'El Milagro'),
(7, 'Trujillo', 'Florencia de Mora'),
(8, 'Trujillo', 'Moche'),
(9, 'Trujillo', 'Salaverry');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleado`
--

CREATE TABLE `empleado` (
  `id` int(11) NOT NULL,
  `version` int(11) NOT NULL,
  `id_empresa` int(11) NOT NULL,
  `apepat` varchar(50) NOT NULL,
  `apemat` varchar(50) NOT NULL,
  `nombre1` varchar(50) NOT NULL,
  `nombre2` varchar(50) NOT NULL,
  `dni` varchar(15) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `tipo` enum('Classic','Premium','Station') NOT NULL,
  `marca` varchar(50) NOT NULL,
  `color` varchar(50) NOT NULL,
  `placa` varchar(15) NOT NULL,
  `telefono` varchar(30) NOT NULL,
  `user` varchar(50) DEFAULT NULL,
  `pass` varchar(100) DEFAULT NULL,
  `foto` varchar(50) NOT NULL,
  `estado` int(11) NOT NULL,
  `nro_vehiculo` varchar(11) NOT NULL,
  `pedido` int(11) DEFAULT NULL,
  `ubica_lat` varchar(25) DEFAULT NULL,
  `ubica_lon` varchar(25) DEFAULT NULL,
  `bearing` varchar(25) NOT NULL DEFAULT '0',
  `turno` char(1) DEFAULT NULL,
  `ukey` varchar(150) DEFAULT NULL,
  `conectado` varchar(11) DEFAULT '0',
  `fecha_login` datetime DEFAULT NULL,
  `tokenId` varchar(250) DEFAULT NULL,
  `factura` int(11) NOT NULL DEFAULT '0',
  `promedio` decimal(10,2) NOT NULL DEFAULT '5.00'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `empleado`
--

INSERT INTO `empleado` (`id`, `version`, `id_empresa`, `apepat`, `apemat`, `nombre1`, `nombre2`, `dni`, `direccion`, `tipo`, `marca`, `color`, `placa`, `telefono`, `user`, `pass`, `foto`, `estado`, `nro_vehiculo`, `pedido`, `ubica_lat`, `ubica_lon`, `bearing`, `turno`, `ukey`, `conectado`, `fecha_login`, `tokenId`, `factura`, `promedio`) VALUES
(1981, 2, 1, 'Huatangare', 'Quispe', 'Sandi', 'Yoneli', '45678912', 'av America Sur 1285', 'Station', 'Toyota', 'negro metalico', 't1p280', '942563171', '45678912', 'MTIzNDU2', '45678912.jpg', 1, '1', NULL, '-8.12637097103033', '-79.02683508522021', '0.0', NULL, NULL, '1', NULL, 'dhxFe9fZ8Y0:APA91bEXAebJc6_-fkxU6SaCrXGfYJ0RGJ5FowJgnID5Cs0ZM8b2fkGhGHaExw8lsAa-Pxda0Bqen_DTYIpgF7uvKiC0LXGWAc1gfywmqBglaq1Z5BPuVkUcuZpkQQYVuFs38ILd-bJk', 0, '5.00'),
(1982, 0, 1, 'GERONIMO', 'SANES', 'ALAN', 'PIERRE', '47155738', '12346A AS', 'Station', 'TOYOTA', 'NREGRO', 'T2P111', '929258017', '47155738', NULL, '47155738.jpg', 1, '125', NULL, NULL, NULL, '0', NULL, NULL, '0', NULL, NULL, 0, '5.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `ruc` varchar(15) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `telefono` varchar(50) NOT NULL,
  `gerente` varchar(150) NOT NULL,
  `contacto` varchar(150) NOT NULL,
  `user` varchar(50) NOT NULL,
  `pass` varchar(150) NOT NULL,
  `estado` int(11) NOT NULL,
  `logo` varchar(100) NOT NULL,
  `provincia` varchar(20) NOT NULL,
  `notificar` int(11) NOT NULL,
  `credito` int(11) NOT NULL,
  `icono` varchar(50) NOT NULL,
  `version` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`id`, `nombre`, `ruc`, `direccion`, `telefono`, `gerente`, `contacto`, `user`, `pass`, `estado`, `logo`, `provincia`, `notificar`, `credito`, `icono`, `version`) VALUES
(1, 'App Taxi', '123145678912', '', '', '', '', 'admin', 'YWJj', 1, '', 'Trujillo', 1, 1, 'logo_fast_taxi_alitas.png', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa_credito`
--

CREATE TABLE `empresa_credito` (
  `id` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `contacto` varchar(100) NOT NULL,
  `ruc` varchar(20) NOT NULL,
  `direccion` varchar(50) NOT NULL,
  `user` varchar(25) NOT NULL,
  `pass` varchar(100) NOT NULL,
  `telefono` varchar(50) NOT NULL,
  `estado` int(11) NOT NULL,
  `id_empresa` int(11) NOT NULL,
  `porc` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `empresa_credito`
--

INSERT INTO `empresa_credito` (`id`, `nombre`, `contacto`, `ruc`, `direccion`, `user`, `pass`, `telefono`, `estado`, `id_empresa`, `porc`) VALUES
(9, 'SMARTINGPRO', 'Luis Huaman', '10441739562', 'Av. Fatima 130 ', '-', 'LQ==', '977123151', 1, 1, 20),
(10, 'NEWTAKCI SRL', 'Deysi Villanueva', '20481612196', 'Av. Miraflores 535', '-', 'LQ==', '310303', 1, 1, 8),
(11, 'SCANIA DEL PERU S.A.', 'Brenda Alzamora', '20101363008', 'Av Ramiro. Priale KM. 7.5 Wuachipa', '-', 'LQ==', '949142870', 1, 1, 8);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fs_users`
--

CREATE TABLE `fs_users` (
  `id` int(11) NOT NULL,
  `name` varchar(150) NOT NULL,
  `phone` varchar(25) NOT NULL,
  `email` varchar(150) NOT NULL,
  `sex` varchar(20) NOT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `bearing` float DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `fs_users`
--

INSERT INTO `fs_users` (`id`, `name`, `phone`, `email`, `sex`, `lat`, `lng`, `bearing`) VALUES
(1, 'Luis', '+51977123151', 'jluis.huaman@gmail.com', 'male', -8.141272, -79.034507, 0),
(2, 'Christ', '992486516', 'christ_1690@hotmail.com', 'female', -8.12806, -79.041213, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `gcm_devices`
--

CREATE TABLE `gcm_devices` (
  `id` int(11) NOT NULL,
  `gcm_regid` text,
  `name` varchar(50) NOT NULL,
  `email` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `keys_mapa`
--

CREATE TABLE `keys_mapa` (
  `id` int(11) NOT NULL,
  `keys` text NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `keys_mapa`
--

INSERT INTO `keys_mapa` (`id`, `keys`, `estado`) VALUES
(1, 'AIzaSyBNAHJGZZE4hzvH5dHfHq3TOKe2HYP5Fks', 1),
(2, 'AIzaSyBNAHJGZZE4hzvH5dHfHq3TOKe2HYP5Fks', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `llamada_clientes`
--

CREATE TABLE `llamada_clientes` (
  `id` int(11) NOT NULL,
  `codigo` varchar(10) NOT NULL,
  `nombres` varchar(150) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `urba` varchar(50) NOT NULL,
  `referencia` varchar(150) NOT NULL,
  `id_distrito` int(11) NOT NULL,
  `id_empresa` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `llamada_direccion`
--

CREATE TABLE `llamada_direccion` (
  `id` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `direccion` varchar(150) NOT NULL,
  `urba` varchar(100) NOT NULL,
  `referencia` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `llamada_pedidos`
--

CREATE TABLE `llamada_pedidos` (
  `id` int(11) NOT NULL,
  `distrito` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  `cod` varchar(10) NOT NULL,
  `nombres` varchar(250) NOT NULL,
  `direccion` varchar(250) NOT NULL,
  `urba` varchar(250) NOT NULL,
  `referencia` varchar(250) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `id_empresa` int(11) NOT NULL,
  `tipo` enum('Camioneta','Auto','Cualquiera','Vip') NOT NULL,
  `quita` int(11) NOT NULL,
  `califica` int(11) NOT NULL,
  `estado` int(11) NOT NULL,
  `observa` varchar(200) NOT NULL,
  `motivo_anula` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `modulo_base`
--

CREATE TABLE `modulo_base` (
  `id` int(11) NOT NULL,
  `tipo` varchar(10) NOT NULL,
  `precio_base` decimal(10,2) NOT NULL,
  `precio_base_corp` decimal(10,2) NOT NULL,
  `km_minimo` int(11) NOT NULL,
  `km_minimo_corp` decimal(10,2) NOT NULL,
  `dist_minimo` int(11) NOT NULL,
  `prec_km` decimal(10,2) NOT NULL,
  `prec_km_ld` decimal(10,2) NOT NULL,
  `prec_km_corp` decimal(10,2) NOT NULL,
  `prec_km_corp_ld` decimal(10,2) NOT NULL,
  `km_dist_larga` decimal(10,2) NOT NULL,
  `estado` int(11) NOT NULL,
  `add_urbano` decimal(10,2) NOT NULL,
  `add_aeropuerto` decimal(10,2) NOT NULL,
  `add_turistico` decimal(10,2) NOT NULL,
  `hora_noche_inicio` time NOT NULL,
  `hora_noche_fin` time NOT NULL,
  `dia_promo` int(11) NOT NULL,
  `monto_promo` decimal(10,2) NOT NULL,
  `porc_agregar` decimal(10,2) NOT NULL,
  `tGcm` int(11) NOT NULL,
  `factura` decimal(10,2) NOT NULL,
  `version` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `modulo_base`
--

INSERT INTO `modulo_base` (`id`, `tipo`, `precio_base`, `precio_base_corp`, `km_minimo`, `km_minimo_corp`, `dist_minimo`, `prec_km`, `prec_km_ld`, `prec_km_corp`, `prec_km_corp_ld`, `km_dist_larga`, `estado`, `add_urbano`, `add_aeropuerto`, `add_turistico`, `hora_noche_inicio`, `hora_noche_fin`, `dia_promo`, `monto_promo`, `porc_agregar`, `tGcm`, `factura`, `version`) VALUES
(1, 'app', '4.00', '6.00', 2, '1.50', 7, '2.40', '2.20', '2.40', '2.00', '15.00', 1, '1.50', '3.00', '3.00', '21:00:00', '06:00:00', 10, '5.00', '1.25', -300, '3.00', 37),
(2, 'call', '4.00', '0.00', 2, '1.50', 7, '2.40', '2.20', '0.00', '0.00', '15.00', 0, '1.50', '3.00', '3.00', '21:00:00', '06:00:00', 10, '0.00', '1.25', -300, '3.00', 0),
(3, 'corp', '6.00', '0.00', 2, '1.50', 7, '2.40', '2.40', '0.00', '0.00', '15.00', 0, '1.50', '3.00', '3.00', '21:00:00', '06:00:00', 10, '0.00', '1.25', -300, '3.00', 0),
(4, 'amb', '4.00', '0.00', 2, '1.50', 7, '2.40', '2.20', '0.00', '0.00', '15.00', 0, '1.50', '3.00', '3.00', '21:00:00', '06:00:00', 10, '0.00', '1.25', -300, '3.00', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `notificacion`
--

CREATE TABLE `notificacion` (
  `id` int(11) NOT NULL,
  `id_empleado` int(11) DEFAULT NULL,
  `id_pedido` int(11) DEFAULT NULL,
  `asunto` varchar(50) DEFAULT NULL,
  `mensaje` text,
  `estado` int(11) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `resultado` text,
  `tipo` int(11) DEFAULT NULL,
  `id_empresa` int(11) DEFAULT NULL,
  `visto` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `observaciones`
--

CREATE TABLE `observaciones` (
  `id` int(11) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `observa` text NOT NULL,
  `fecha` datetime NOT NULL,
  `estado` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `paradas`
--

CREATE TABLE `paradas` (
  `id` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `lat` varchar(25) NOT NULL,
  `lng` varchar(25) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos`
--

CREATE TABLE `pedidos` (
  `id` int(11) NOT NULL,
  `id_empresa` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_direccion` int(11) DEFAULT NULL,
  `direccion` text NOT NULL,
  `nro` varchar(250) CHARACTER SET latin1 NOT NULL,
  `urba` varchar(250) CHARACTER SET latin1 NOT NULL,
  `refe` varchar(250) CHARACTER SET latin1 NOT NULL,
  `tipo` enum('Camioneta','Auto','Cualquiera','Vip') CHARACTER SET latin1 NOT NULL,
  `fecha` datetime NOT NULL,
  `fecha_lanza` datetime DEFAULT NULL,
  `fecha_ubicacion` datetime DEFAULT NULL,
  `fecha_inicio` datetime DEFAULT NULL,
  `fecha_fin` datetime DEFAULT NULL,
  `id_empleado` int(11) NOT NULL,
  `estado` int(11) NOT NULL DEFAULT '0',
  `alertar` int(11) NOT NULL DEFAULT '0',
  `base` int(11) NOT NULL,
  `observa` text CHARACTER SET latin1,
  `califica` int(11) NOT NULL DEFAULT '0',
  `confirmar` int(11) NOT NULL,
  `destino` varchar(150) CHARACTER SET latin1 NOT NULL,
  `refe_d` varchar(150) CHARACTER SET latin1 NOT NULL,
  `precio` decimal(10,2) DEFAULT '0.00',
  `prec_sug` decimal(10,2) NOT NULL DEFAULT '0.00',
  `descuento` decimal(10,2) NOT NULL DEFAULT '0.00',
  `id_vale` int(11) NOT NULL DEFAULT '0',
  `conductor` int(11) DEFAULT '0',
  `quita` int(11) NOT NULL DEFAULT '0',
  `coordenadas` varchar(200) CHARACTER SET latin1 NOT NULL,
  `motivo_anula` varchar(100) CHARACTER SET latin1 NOT NULL DEFAULT '0',
  `coor_destino` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `distancia` int(11) NOT NULL,
  `vibrar` int(11) NOT NULL DEFAULT '0',
  `factura` int(11) NOT NULL DEFAULT '0',
  `corporativo` int(11) NOT NULL DEFAULT '0',
  `m_base` int(11) NOT NULL DEFAULT '0',
  `asignado` int(11) NOT NULL DEFAULT '0',
  `pasajero` varchar(50) DEFAULT NULL,
  `area` varchar(50) DEFAULT NULL,
  `motivo_precio` varchar(50) DEFAULT NULL,
  `llamada` int(11) NOT NULL DEFAULT '0',
  `prueba` int(11) NOT NULL DEFAULT '0',
  `fecha_ampliada` datetime DEFAULT NULL,
  `alert_aceptado` int(11) NOT NULL DEFAULT '0',
  `latOrigen` varchar(50) DEFAULT '0',
  `lngOrigen` varchar(50) DEFAULT '0',
  `latDestino` varchar(50) DEFAULT '0',
  `lngDestino` varchar(50) DEFAULT '0',
  `direDestino` varchar(250) DEFAULT NULL,
  `tipo_servicio` int(11) NOT NULL DEFAULT '0',
  `silenciar_anu` int(11) NOT NULL,
  `actualizar_dire` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `pedidos`
--

INSERT INTO `pedidos` (`id`, `id_empresa`, `id_usuario`, `id_direccion`, `direccion`, `nro`, `urba`, `refe`, `tipo`, `fecha`, `fecha_lanza`, `fecha_ubicacion`, `fecha_inicio`, `fecha_fin`, `id_empleado`, `estado`, `alertar`, `base`, `observa`, `califica`, `confirmar`, `destino`, `refe_d`, `precio`, `prec_sug`, `descuento`, `id_vale`, `conductor`, `quita`, `coordenadas`, `motivo_anula`, `coor_destino`, `distancia`, `vibrar`, `factura`, `corporativo`, `m_base`, `asignado`, `pasajero`, `area`, `motivo_precio`, `llamada`, `prueba`, `fecha_ampliada`, `alert_aceptado`, `latOrigen`, `lngOrigen`, `latDestino`, `lngDestino`, `direDestino`, `tipo_servicio`, `silenciar_anu`, `actualizar_dire`) VALUES
(3, 0, 1, NULL, 'SnIuIEdyYXU=', 'MzIw', '', 'U2FyYW5kYWphcw==', 'Cualquiera', '2017-10-20 12:57:57', '2017-10-20 12:57:57', NULL, NULL, '2017-10-20 12:58:32', 1956, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 1, NULL, 0, '-8.1160094', '-79.02679539999997', '0', '0', NULL, 1, 0, 0),
(4, 0, 1, NULL, 'SnIuIEdyYXU=', 'MzIw', '', 'U2FyYW5kYWphcw==', 'Cualquiera', '2017-10-20 15:17:47', '2017-10-20 15:17:47', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 1, NULL, 0, '-8.1160094', '-79.02679539999997', '0', '0', NULL, 1, 1, 0),
(5, 0, 1, NULL, 'SnIuIEdyYXU=', 'MzIw', '', 'U2FyYW5kYWphcw==', 'Cualquiera', '2017-10-20 15:29:14', '2017-10-20 15:29:14', NULL, NULL, NULL, 1956, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 1, NULL, 0, '-8.1160094', '-79.02679539999997', '0', '0', NULL, 1, 0, 0),
(6, 0, 1, NULL, 'SnIuIEdyYXU=', 'MzIw', '', 'U2FyYW5kYWphcw==', 'Cualquiera', '2017-10-20 15:43:59', '2017-10-20 15:43:59', NULL, NULL, '2017-10-20 15:45:23', 1956, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 1, NULL, 0, '-8.1160094', '-79.02679539999997', '0', '0', NULL, 1, 0, 0),
(7, 0, 1, NULL, 'SnIuIEdyYXU=', 'MzIw', '', 'U2FyYW5kYWphcw==', 'Cualquiera', '2017-10-20 15:47:21', '2017-10-20 15:47:21', NULL, NULL, NULL, 1956, 5, 0, 0, NULL, 0, 0, '', '', '5.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 1, NULL, 0, '-8.1160094', '-79.02679539999997', '0', '0', NULL, 1, 0, 0),
(8, 0, 1, NULL, 'SnIuIEdyYXU=', 'MzIw', '', 'U2FyYW5kYWphcw==', 'Cualquiera', '2017-10-20 15:49:51', '2017-10-20 15:49:51', NULL, NULL, NULL, 1956, 3, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 1, NULL, 0, '-8.1160094', '-79.02679539999997', '0', '0', NULL, 1, 0, 0),
(9, 0, 1, NULL, 'SnIuIEdyYXU=', 'MzIw', '', 'U2FyYW5kYWphcw==', 'Cualquiera', '2017-10-20 15:53:06', '2017-10-20 15:53:06', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 1, NULL, 0, '-8.1160094', '-79.02679539999997', '0', '0', NULL, 1, 1, 0),
(10, 0, 1, NULL, 'SnIuIEdyYXU=', 'MzIw', '', 'U2FyYW5kYWphcw==', 'Cualquiera', '2017-10-20 15:55:11', '2017-10-20 15:55:11', NULL, NULL, '2017-10-20 15:55:41', 1956, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 1, NULL, 0, '-8.1160094', '-79.02679539999997', '0', '0', NULL, 1, 0, 0),
(11, 0, 1, NULL, 'SnIuIEdyYXU=', 'MzIw', '', 'U2FyYW5kYWphcw==', 'Cualquiera', '2017-10-20 17:48:13', '2017-10-20 17:48:13', NULL, NULL, '2017-10-20 17:48:42', 1956, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 1, NULL, 0, '-8.1160094', '-79.02679539999997', '0', '0', NULL, 1, 0, 0),
(12, 0, 1, 5252, 'QXYuIEVzcGHDsWE=', 'MjI1MA==', '', 'Qk9PTEVWQVJE', 'Cualquiera', '2017-10-20 17:51:23', '2017-10-20 17:51:23', NULL, NULL, '2017-10-20 17:51:55', 1956, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 1, NULL, 0, '-8.1142764', '-79.02440769999998', '0', '0', NULL, 1, 0, 0),
(13, 0, 2824, NULL, 'Smlyw7NuIE1pZ3VlbCBHcmF1', 'Mzg4', '', 'ZXNxdWluYQ==', 'Cualquiera', '2017-10-21 12:57:49', '2017-10-21 12:57:49', NULL, NULL, NULL, 1956, 5, 0, 0, NULL, 0, 0, '', '', '5.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.11544528454949', '-79.02633491903543', '0', '0', NULL, 0, 0, 0),
(14, 0, 2824, NULL, 'Smlyw7NuIE1pZ3VlbCBHcmF1', 'Mzc0', '', 'Z2dnZw==', 'Cualquiera', '2017-10-21 13:01:08', '2017-10-21 13:01:08', NULL, NULL, NULL, 1956, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.115541208999177', '-79.02638956904411', '0', '0', NULL, 0, 1, 0),
(15, 0, 2824, NULL, 'Smlyw7NuIE1pZ3VlbCBHcmF1', 'Mzcz', '', '', 'Cualquiera', '2017-10-21 13:03:33', '2017-10-21 13:03:33', NULL, NULL, '2017-10-21 13:04:10', 1956, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.115454910187355', '-79.02647003531456', '0', '0', NULL, 0, 0, 0),
(16, 0, 2824, NULL, 'Smlyw7NuIE1pZ3VlbCBHcmF1', 'MzU1', '', '', 'Cualquiera', '2017-10-21 13:05:45', '2017-10-21 13:05:45', NULL, NULL, '2017-10-21 13:07:13', 1956, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.115694555303703', '-79.02674797922373', '0', '0', NULL, 0, 0, 0),
(17, 0, 1, 5253, 'SnIuIEdyYXU=', 'NDY2', '', 'ZWRpZmljaW8gYXp1bA==', 'Cualquiera', '2017-10-23 15:45:15', '2017-10-23 15:45:15', NULL, NULL, NULL, 1956, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 1, NULL, 0, '-8.1145827', '-79.0256321', '0', '0', NULL, 1, 0, 0),
(18, 0, 1, NULL, 'SnIuIEdyYXU=', 'NDY2', '', 'ZWRpZmljaW8gYXp1bA==', 'Cualquiera', '2017-10-23 15:48:30', '2017-10-23 15:48:30', NULL, NULL, '2017-10-23 15:48:53', 1956, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 1, NULL, 0, '-8.1145827', '-79.0256321', '0', '0', NULL, 1, 0, 0),
(19, 0, 1, 5254, 'QXYuIEVzcGHDsWE=', 'MjIzNQ==', '', 'cmljYXJkb3M=', 'Cualquiera', '2017-10-23 15:55:57', '2017-10-23 15:55:57', NULL, NULL, '2017-10-23 15:56:35', 1956, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 1, NULL, 0, '-8.114321214247358', '-79.02425614047547', '0', '0', NULL, 1, 0, 0),
(20, 0, 1, NULL, 'Smlyw7NuIE1pZ3VlbCBHcmF1', 'Mzc0', '', '', 'Cualquiera', '2017-10-23 16:13:20', '2017-10-23 16:13:20', NULL, NULL, NULL, 1956, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.115533906792741', '-79.02638923376799', '0', '0', NULL, 0, 1, 0),
(21, 0, 1, NULL, 'Smlyw7NuIERpZWdvIERlIEFsbWFncm8=', 'ODUx', '', '', 'Cualquiera', '2017-10-23 16:19:18', '2017-10-23 16:19:18', NULL, NULL, '2017-10-23 16:23:22', 1956, 5, 0, 0, 'b2s=', 5, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.115519302379465', '-79.025916159153', '0', '0', NULL, 0, 0, 0),
(22, 0, 2, NULL, 'QXZlbmlkYSBMYSBQZXJsYQ==', 'MzA3', '', '', 'Cualquiera', '2018-12-27 14:11:51', '2018-12-27 14:11:51', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.127448275724081', '-79.02762070298195', '0', '0', NULL, 0, 1, 0),
(23, 0, 2, NULL, 'QXZlbmlkYSBMYSBQZXJsYQ==', 'MzA3', '', '', 'Cualquiera', '2018-12-27 14:11:57', '2018-12-27 14:11:57', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.127448275724081', '-79.02762070298195', '0', '0', NULL, 0, 1, 0),
(24, 0, 2, NULL, 'QXZlbmlkYSBMYSBQZXJsYQ==', 'MzA3', '', '', 'Cualquiera', '2018-12-27 14:12:06', '2018-12-27 14:12:06', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.127448275724081', '-79.02762070298195', '0', '0', NULL, 0, 1, 0),
(25, 0, 2, NULL, 'QXZlbmlkYSBMYSBQZXJsYQ==', 'MzA3', '', '', 'Cualquiera', '2018-12-27 14:12:08', '2018-12-27 14:12:08', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.127448275724081', '-79.02762070298195', '0', '0', NULL, 0, 1, 0),
(26, 0, 2, NULL, 'QXZlbmlkYSBMYSBQZXJsYQ==', 'MzA3', '', '', 'Cualquiera', '2018-12-27 14:12:09', '2018-12-27 14:12:09', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.127448275724081', '-79.02762070298195', '0', '0', NULL, 0, 1, 0),
(27, 0, 2, NULL, 'QXZlbmlkYSBMYSBQZXJsYQ==', 'MzA3', '', '', 'Cualquiera', '2018-12-27 14:13:14', '2018-12-27 14:13:14', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.127448275724081', '-79.02762070298195', '0', '0', NULL, 0, 1, 0),
(28, 0, 2, NULL, 'QXZlbmlkYSBMYSBQZXJsYQ==', 'MzA3', '', '', 'Cualquiera', '2018-12-27 14:18:11', '2018-12-27 14:18:11', NULL, NULL, NULL, 1956, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.127448275724081', '-79.02762070298195', '0', '0', NULL, 0, 1, 0),
(29, 0, 15, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjU5NQ==', '', '', 'Cualquiera', '2018-12-28 12:02:29', '2018-12-28 12:02:29', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.125031641725707', '-79.02525901794434', '0', '0', NULL, 0, 0, 0),
(30, 0, 15, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjU5NQ==', '', '', 'Cualquiera', '2018-12-28 12:02:50', '2018-12-28 12:02:50', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.125031641725707', '-79.02525901794434', '0', '0', NULL, 0, 0, 0),
(31, 0, 15, NULL, 'Q2FsbGUgNA==', 'MTA=', '', '', 'Cualquiera', '2018-12-28 12:03:22', '2018-12-28 12:03:22', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.124515852279913', '-79.02583468705416', '0', '0', NULL, 0, 0, 0),
(32, 0, 15, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjU3Mw==', '', '', 'Cualquiera', '2018-12-28 12:04:34', '2018-12-28 12:04:34', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12444183610814', '-79.02506187558174', '0', '0', NULL, 0, 0, 0),
(33, 0, 16, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjY0MA==', '', '', 'Cualquiera', '2018-12-28 12:13:33', '2018-12-28 12:13:33', NULL, NULL, NULL, 1956, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12535459063584', '-79.02645595371723', '0', '0', NULL, 0, 0, 0),
(34, 0, 23, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-04 16:27:38', '2019-01-04 16:27:38', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00960135412166', '-70.25388233363628', '0', '0', NULL, 0, 1, 0),
(35, 0, 23, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-04 16:30:21', '2019-01-04 16:30:21', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00960135412166', '-70.25388233363628', '0', '0', NULL, 0, 1, 0),
(36, 0, 23, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-04 16:30:42', '2019-01-04 16:30:42', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00960135412166', '-70.25388233363628', '0', '0', NULL, 0, 1, 0),
(37, 0, 23, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-04 16:32:03', '2019-01-04 16:32:03', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00961665888159', '-70.25388568639755', '0', '0', NULL, 0, 1, 0),
(38, 0, 23, NULL, 'QXZlbmlkYSBDb3JvbmVsIE1lbmRvemE=', 'OTc0', '', '', '', '2019-01-04 16:33:20', '2019-01-04 16:33:20', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.0096212', '-70.2538905', '0', '0', NULL, 0, 1, 0),
(39, 0, 23, NULL, 'QXZlbmlkYSBDb3JvbmVsIE1lbmRvemE=', 'OTc0', '', '', 'Cualquiera', '2019-01-04 16:34:16', '2019-01-04 16:34:16', NULL, NULL, NULL, 1975, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.0096212', '-70.2538905', '0', '0', NULL, 0, 0, 0),
(40, 0, 23, NULL, 'UHJlc2JpdGVybyBBbmRpYQ==', 'Mw==', '', '', '', '2019-01-04 16:38:46', '2019-01-04 16:38:46', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009854839036617', '-70.25338277220726', '0', '0', NULL, 0, 1, 0),
(41, 0, 23, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-04 18:21:47', '2019-01-04 18:21:47', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 2, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009624311261053', '-70.25388333946466', '0', '0', NULL, 0, 1, 0),
(42, 0, 23, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-04 18:25:12', '2019-01-04 18:25:12', NULL, NULL, NULL, 1960, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009624311261053', '-70.25388333946466', '0', '0', NULL, 0, 0, 0),
(43, 0, 23, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-04 18:25:48', '2019-01-04 18:25:48', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009616977730737', '-70.25388199836016', '0', '0', NULL, 0, 0, 0),
(44, 0, 23, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-04 18:26:39', '2019-01-04 18:26:39', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 2, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009614426937507', '-70.25388099253178', '0', '0', NULL, 0, 0, 0),
(45, 0, 23, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-04 18:29:12', '2019-01-04 18:29:12', NULL, NULL, NULL, 1967, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00957010689936', '-70.2538089081645', '0', '0', NULL, 0, 1, 0),
(46, 0, 23, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-04 18:37:54', '2019-01-04 18:37:54', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00960804995429', '-70.25387864559889', '0', '0', NULL, 0, 0, 0),
(47, 0, 23, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-04 18:39:44', '2019-01-04 18:39:44', NULL, NULL, '2019-01-04 19:12:10', 1967, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-18.00960804995429', '-70.25387864559889', '0', '0', NULL, 0, 0, 0),
(48, 0, 16, NULL, 'Q3ViYQ==', 'Mjg=', '', '', '', '2019-01-08 14:20:48', '2019-01-08 14:20:48', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12209654531953', '-79.03143312782049', '0', '0', NULL, 0, 1, 0),
(49, 0, 16, NULL, 'Q3ViYQ==', 'Mjg=', '', '', '', '2019-01-08 14:27:05', '2019-01-08 14:27:05', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12209654531953', '-79.03143312782049', '0', '0', NULL, 0, 1, 0),
(50, 0, 16, NULL, 'Q3ViYQ==', 'Mjg=', '', '', '', '2019-01-08 14:27:43', '2019-01-08 14:27:43', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12209654531953', '-79.03143312782049', '0', '0', NULL, 0, 1, 0),
(51, 0, 16, NULL, 'Q3ViYQ==', 'Mjg=', '', '', '', '2019-01-08 14:27:46', '2019-01-08 14:27:46', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12209654531953', '-79.03143312782049', '0', '0', NULL, 0, 1, 0),
(52, 0, 16, NULL, 'Q3ViYQ==', 'Mjg=', '', '', 'Cualquiera', '2019-01-08 14:34:18', '2019-01-08 14:34:18', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.122106170797984', '-79.03142776340246', '0', '0', NULL, 0, 0, 0),
(53, 0, 16, NULL, 'Q3ViYQ==', 'Mjg=', '', '', '', '2019-01-08 14:34:27', '2019-01-08 14:34:27', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.122106170797984', '-79.03142776340246', '0', '0', NULL, 0, 1, 0),
(54, 0, 16, NULL, 'Q3ViYQ==', 'Mjg=', '', '', '', '2019-01-08 14:38:01', '2019-01-08 14:38:01', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.122106170797984', '-79.03142776340246', '0', '0', NULL, 0, 1, 0),
(55, 0, 16, NULL, 'Q3ViYQ==', 'Mjg=', '', '', '', '2019-01-08 14:38:04', '2019-01-08 14:38:04', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.122106170797984', '-79.03142776340246', '0', '0', NULL, 0, 1, 0),
(56, 0, 16, NULL, 'Q3ViYQ==', 'Mjg=', '', '', '', '2019-01-08 14:38:09', '2019-01-08 14:38:09', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.122106170797984', '-79.03142776340246', '0', '0', NULL, 0, 0, 0),
(57, 0, 16, NULL, 'Q3ViYQ==', 'Mjg=', '', '', '', '2019-01-08 14:40:42', '2019-01-08 14:40:42', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.122097541058698', '-79.03143782168627', '0', '0', NULL, 0, 1, 0),
(58, 0, 16, NULL, 'Q3ViYQ==', 'Mjg=', '', '', '', '2019-01-08 14:43:01', '2019-01-08 14:43:01', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.122097541058698', '-79.03143782168627', '0', '0', NULL, 0, 0, 0),
(59, 0, 5, NULL, 'QXZlbmlkYSBDb3JvbmVsIE1lbmRvemE=', 'OTc0', '', '', '', '2019-01-08 16:40:13', '2019-01-08 16:40:13', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.006567796926944', '-70.24627424776556', '0', '0', NULL, 0, 0, 0),
(60, 0, 5, NULL, 'R2VuZXJhbCBWYXJlbGE=', 'NDcx', '', '', '', '2019-01-08 16:41:39', '2019-01-08 16:41:39', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00747780579691', '-70.24629302322865', '0', '0', NULL, 0, 0, 0),
(61, 0, 5, NULL, 'QXZlbmlkYSBDb3JvbmVsIE1lbmRvemE=', 'OTc0', '', '', '', '2019-01-08 16:41:53', '2019-01-08 16:41:53', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.006567796926944', '-70.24627424776556', '0', '0', NULL, 0, 0, 0),
(62, 0, 5, NULL, 'QXZlbmlkYSBDb3JvbmVsIE1lbmRvemE=', 'OTc0', '', '', '', '2019-01-08 16:47:36', '2019-01-08 16:47:36', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.006567796926944', '-70.24627424776556', '0', '0', NULL, 0, 0, 0),
(63, 0, 5, NULL, 'R2VuZXJhbCBWYXJlbGE=', 'MzUz', '', '', '', '2019-01-08 16:49:03', '2019-01-08 16:49:03', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.007761584756825', '-70.24644691497087', '0', '0', NULL, 0, 0, 0),
(64, 0, 5, NULL, 'QXZlbmlkYSBDb3JvbmVsIE1lbmRvemE=', 'OTc0', '', '', 'Cualquiera', '2019-01-08 16:49:58', '2019-01-08 16:49:58', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.006567796926944', '-70.24627424776556', '0', '0', NULL, 0, 0, 0),
(65, 0, 5, NULL, 'QXZlbmlkYSBDb3JvbmVsIE1lbmRvemE=', 'OTc0', '', '', '', '2019-01-08 16:54:22', '2019-01-08 16:54:22', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.006567796926944', '-70.24627424776556', '0', '0', NULL, 0, 0, 0),
(66, 0, 5, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-08 16:55:44', '2019-01-08 16:55:44', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00964376105736', '-70.25387696921825', '0', '0', NULL, 0, 0, 0),
(67, 0, 5, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-08 16:58:53', '2019-01-08 16:58:53', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009660341209898', '-70.25387294590473', '0', '0', NULL, 0, 0, 0),
(68, 0, 5, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-08 16:59:38', '2019-01-08 16:59:38', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009662254304313', '-70.25388535112143', '0', '0', NULL, 0, 0, 0),
(69, 0, 5, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-08 17:00:44', '2019-01-08 17:00:44', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009664486247786', '-70.25386825203896', '0', '0', NULL, 0, 0, 0),
(70, 0, 5, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-08 17:02:22', '2019-01-08 17:02:22', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009660978908034', '-70.25388769805431', '0', '0', NULL, 0, 0, 0),
(71, 0, 5, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-08 17:05:26', '2019-01-08 17:05:26', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00966289200245', '-70.2538863569498', '0', '0', NULL, 0, 0, 0),
(72, 0, 17, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'Njc0', '', '', 'Cualquiera', '2019-01-08 17:08:57', '2019-01-08 17:08:57', NULL, NULL, NULL, 1958, 1, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009768749861106', '-70.25373246520758', '0', '0', NULL, 0, 0, 0),
(73, 0, 5, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-08 17:09:11', '2019-01-08 17:09:11', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00966289200245', '-70.25386691093445', '0', '0', NULL, 0, 0, 0),
(74, 0, 5, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-08 17:12:08', '2019-01-08 17:12:08', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00966289200245', '-70.25388535112143', '0', '0', NULL, 0, 0, 0),
(75, 0, 17, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', 'Y29jYSBjb2xhIA==', '', '2019-01-08 17:13:37', '2019-01-08 17:13:37', NULL, NULL, NULL, 1970, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00965555847373', '-70.253876298666', '0', '0', NULL, 0, 0, 0),
(76, 0, 24, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-08 17:26:07', '2019-01-08 17:26:07', NULL, NULL, '2019-01-08 17:26:50', 1958, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-18.009666080493098', '-70.25388836860657', '0', '0', NULL, 0, 0, 0),
(77, 0, 24, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-08 17:27:08', '2019-01-08 17:27:08', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00965428307741', '-70.25385484099388', '0', '0', NULL, 0, 0, 0),
(78, 0, 24, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-08 17:28:15', '2019-01-08 17:28:15', NULL, NULL, '2019-01-08 17:30:37', 1958, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-18.00966002236082', '-70.25388333946466', '0', '0', NULL, 0, 0, 0),
(79, 0, 24, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-08 17:35:22', '2019-01-08 17:35:22', NULL, NULL, NULL, 1977, 3, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009606774557625', '-70.25390312075615', '0', '0', NULL, 0, 0, 0),
(80, 0, 24, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-08 17:37:39', '2019-01-08 17:37:39', NULL, NULL, NULL, 1959, 1, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009596890233112', '-70.25388233363628', '0', '0', NULL, 0, 0, 0),
(81, 0, 24, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-08 17:40:09', '2019-01-08 17:40:09', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00966639934215', '-70.25385919958353', '0', '0', NULL, 0, 0, 0),
(82, 0, 24, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-08 17:41:02', '2019-01-08 17:41:02', NULL, NULL, NULL, 1970, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009651413435638', '-70.25386288762094', '0', '0', NULL, 0, 0, 0),
(83, 0, 24, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', 'Cualquiera', '2019-01-08 17:41:37', '2019-01-08 17:41:37', NULL, NULL, NULL, 1959, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009657471568215', '-70.25387663394213', '0', '0', NULL, 0, 0, 0),
(84, 0, 24, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-08 17:56:23', '2019-01-08 17:56:23', NULL, NULL, NULL, 1967, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009645355302865', '-70.25388367474079', '0', '0', NULL, 0, 0, 0),
(85, 0, 27, NULL, 'OCBEZSBTZW50aWVtYnJl', 'MjEyNQ==', '', '', 'Cualquiera', '2019-01-13 07:21:16', '2019-01-13 07:21:16', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009253808173955', '-70.22915370762348', '0', '0', NULL, 0, 0, 0),
(86, 0, 25, NULL, 'TCBGbG9yZXMgTWFydG9yZWxs', 'NjAxMw==', '', 'dXJiYW5pemFjacOzbiBDYXBsaW5hIEM0', 'Cualquiera', '2019-01-13 17:12:27', '2019-01-13 17:12:27', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00159263735733', '-70.23222483694553', '0', '0', NULL, 0, 0, 0),
(87, 0, 25, NULL, 'VXJiYW5pemFjacOzbiBDYXBsaW5hIA==', 'QzQ=', '', 'cG9yIGVsIGVzY3VhZHLDs24=', 'Cualquiera', '2019-01-13 17:14:10', '2019-01-13 17:14:10', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00159263735733', '-70.23222483694553', '0', '0', NULL, 0, 0, 0),
(88, 0, 23, NULL, 'QXZlbmlkYSBBdWd1c3RvIEIgTGVndWlh', 'Nzcw', '', '', 'Cualquiera', '2019-01-17 16:07:31', '2019-01-17 16:07:31', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.008117742647606', '-70.24957872927189', '0', '0', NULL, 0, 1, 0),
(89, 0, 26, NULL, 'RW5yaXF1ZSBRdWlqYW5v', 'NTk1', '', 'YWJham8gZGUgbGEgZG9ub2ZyaW8=', '', '2019-01-18 23:09:31', '2019-01-18 23:09:31', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.007600564167106', '-70.2567432448268', '0', '0', NULL, 0, 0, 0),
(90, 0, 23, NULL, 'QXZlbmlkYSBDb3JvbmVsIE1lbmRvemE=', 'OTc2', '', '', 'Cualquiera', '2019-01-21 16:47:36', '2019-01-21 16:47:36', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.0065626952522', '-70.24627424776556', '0', '0', NULL, 0, 0, 0),
(91, 0, 29, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-22 11:57:02', '2019-01-22 11:57:02', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009663210851517', '-70.25387495756148', '0', '0', NULL, 0, 0, 0),
(92, 0, 29, NULL, 'Q2FycmV0ZXJhIFBhbmFtZXJpY2FuYSBTdXI=', 'NDk1', '', '', 'Cualquiera', '2019-01-22 15:03:23', '2019-01-22 15:03:23', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.04709434502364', '-70.2764031663537', '0', '0', NULL, 0, 0, 0),
(93, 0, 29, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-23 11:46:02', '2019-01-23 11:46:02', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009668631285574', '-70.25396682322025', '0', '0', NULL, 0, 0, 0),
(94, 0, 29, NULL, 'UGFzYWplIDggZGUgRGljaWVtYnJl', 'MTMy', '', '', '', '2019-01-24 10:14:53', '2019-01-24 10:14:53', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.031518320932758', '-70.24587091058493', '0', '0', NULL, 0, 0, 0),
(95, 0, 24, NULL, 'SW5kdXN0cmlhbA==', 'MTg0', '', '', '', '2019-01-26 18:17:28', '2019-01-26 18:17:28', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.002165953295222', '-70.25552820414305', '0', '0', NULL, 0, 0, 0),
(96, 0, 24, NULL, 'SW5kdXN0cmlhbA==', 'MTg0', '', '', 'Cualquiera', '2019-01-26 18:18:14', '2019-01-26 18:18:14', NULL, NULL, NULL, 1972, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.002165953295222', '-70.25552820414305', '0', '0', NULL, 0, 0, 0),
(97, 0, 24, NULL, 'R3VzdGF2byBQaW50bw==', 'MzU0', '', '', '', '2019-01-26 19:33:11', '2019-01-26 19:33:11', NULL, NULL, NULL, 1972, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00476657771168', '-70.24252619594337', '0', '0', NULL, 0, 0, 0),
(98, 0, 24, NULL, 'R2VuZXJhbCBWYXJlbGE=', 'NDk1', '', '', '', '2019-01-26 19:41:37', '2019-01-26 19:41:37', NULL, NULL, NULL, 1972, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.003459893093023', '-70.2433754503727', '0', '0', NULL, 0, 0, 0),
(99, 0, 24, NULL, 'RnJhbmNpc2NvIFBhdWxhIFZpZ2ls', 'MTEyOA==', '', '', '', '2019-01-26 21:13:01', '2019-01-26 21:13:01', NULL, NULL, NULL, 1976, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.002593547561368', '-70.24314243346453', '0', '0', NULL, 0, 0, 0),
(100, 0, 2421, NULL, 'QW1idWxhdG9yaW8=', 'U04=', '', 'LQ==', 'Cualquiera', '2019-01-27 08:18:35', '2019-01-27 08:18:35', NULL, NULL, '2019-01-27 08:19:46', 1976, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-18.001811', '-70.2477798', '0', '0', NULL, 3, 0, 0),
(101, 0, 31, NULL, 'QXZlbmlkYSBKb3JnZSBCYXNhZHJlIEdyb2htYW5uIE9lc3Rl', 'Mw==', '', '', 'Cualquiera', '2019-01-27 21:47:59', '2019-01-27 21:47:59', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.008537988901995', '-70.25752041488887', '0', '0', NULL, 0, 0, 0),
(102, 0, 24, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-01-28 16:39:26', '2019-01-28 16:39:26', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009612832691726', '-70.25387495756148', '0', '0', NULL, 0, 0, 0),
(103, 0, 32, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'MjUw', '', 'cHJvbXV2aQ==', '', '2019-01-30 19:46:00', '2019-01-30 19:46:00', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.012637091006884', '-70.25157395750284', '0', '0', NULL, 0, 0, 0),
(104, 0, 16, NULL, '', '', '', '', 'Cualquiera', '2019-02-08 10:52:48', '2019-02-08 10:52:48', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '0.0', '0.0', '0', '0', NULL, 0, 1, 0),
(105, 0, 23, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-02-09 11:37:51', '2019-02-09 11:37:51', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009627180903273', '-70.25388769805431', '0', '0', NULL, 0, 0, 0),
(106, 0, 23, NULL, 'QXZlbmlkYSBIaXDDs2xpdG8gVW5hbnVl', 'NjM2', '', '', '', '2019-02-09 16:25:24', '2019-02-09 16:25:24', NULL, NULL, NULL, 1977, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.009646311850155', '-70.25387529283762', '0', '0', NULL, 0, 0, 0),
(107, 0, 29, NULL, 'UGVyw7o=', 'MTA=', '', '', '', '2019-02-11 20:32:46', '2019-02-11 20:32:46', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.031097173074855', '-70.26529613882305', '0', '0', NULL, 0, 0, 0),
(108, 0, 29, NULL, 'TW9kZXN0byBNb2xpbmE=', 'ODM1', '', '', 'Cualquiera', '2019-02-12 23:31:13', '2019-02-12 23:31:13', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.003253271505173', '-70.24733506143093', '0', '0', NULL, 0, 0, 0),
(109, 0, 35, NULL, 'UHJvbG9uZ2FjaW9uIEJsb25kZWxs', 'MzU1', '', '', 'Cualquiera', '2019-02-13 08:45:09', '2019-02-13 08:45:09', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.016701663435736', '-70.25649178773166', '0', '0', NULL, 0, 0, 0),
(110, 0, 39, NULL, 'Q2FsbGUgSnVzdG8gQXJpYXMgQXJhZ3Vleg==', 'NjM=', '', '', '', '2019-02-14 14:28:06', '2019-02-14 14:28:06', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-17.986484229818984', '-70.22870242595673', '0', '0', NULL, 0, 0, 0),
(111, 0, 29, NULL, 'QXZlbmlkYSBDb3JvbmVsIE1lbmRvemE=', 'OTc2', '', '', '', '2019-02-19 15:49:57', '2019-02-19 15:49:57', NULL, NULL, NULL, 1970, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-18.00961665888159', '-70.2538776397705', '0', '0', NULL, 0, 0, 0),
(112, 0, 42, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MzQ5NQ==', '', '', 'Cualquiera', '2019-02-21 17:21:30', '2019-02-21 17:21:30', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126574027445304', '-79.03411131352186', '0', '0', NULL, 0, 1, 0),
(113, 0, 42, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MzQ5NQ==', '', '', 'Cualquiera', '2019-02-21 17:22:15', '2019-02-21 17:22:15', NULL, NULL, '2019-02-21 17:24:36', 1981, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.126574027445304', '-79.03411131352186', '0', '0', NULL, 0, 0, 0),
(114, 0, 42, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MzQ5NQ==', '', '', 'Cualquiera', '2019-02-21 17:24:47', '2019-02-21 17:24:47', NULL, NULL, '2019-02-21 17:26:04', 1981, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.126574027445304', '-79.03411131352186', '0', '0', NULL, 0, 0, 0),
(115, 0, 42, NULL, 'U2FudG8gVG9yaWJpbyBkZSBNb2dyb3Zlam8=', 'NTE4', '', '', 'Cualquiera', '2019-02-21 17:26:36', '2019-02-21 17:26:36', NULL, NULL, '2019-02-21 17:27:06', 1981, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.1190123973769', '-79.03624333441257', '0', '0', NULL, 0, 0, 0),
(116, 0, 42, NULL, 'TGEgTGliZXJ0YWQgMTA0', 'NDcx', '', '', 'Cualquiera', '2019-02-21 17:43:12', '2019-02-21 17:43:12', NULL, NULL, NULL, 1981, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.111587376717651', '-79.02820978313684', '0', '0', NULL, 0, 0, 0),
(117, 0, 42, NULL, 'QW1hem9uYXM=', 'Mzg5', '', '', 'Cualquiera', '2019-02-21 17:43:45', '2019-02-21 17:43:45', NULL, NULL, NULL, 1981, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.104564847212224', '-79.02299724519253', '0', '0', NULL, 0, 0, 0),
(118, 0, 42, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-21 17:44:15', '2019-02-21 17:44:15', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126303189323272', '-79.02678687125444', '0', '0', NULL, 0, 0, 0),
(119, 0, 42, NULL, 'U2FudG8gVG9yaWJpbyBkZSBNb2dyb3Zlam8=', 'NTE4', '', '', 'Cualquiera', '2019-02-21 17:44:21', '2019-02-21 17:44:21', NULL, NULL, '2019-02-21 17:47:39', 1981, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.1190123973769', '-79.03624333441257', '0', '0', NULL, 0, 0, 0),
(120, 0, 42, NULL, 'Sm9zw6kgR2FsdmV6', 'ODY1', '', '', 'Cualquiera', '2019-02-21 17:48:04', '2019-02-21 17:48:04', NULL, NULL, NULL, 1981, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.115288618963037', '-79.01611033827066', '0', '0', NULL, 0, 0, 0),
(121, 0, 42, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjEwMQ==', '', '', 'Cualquiera', '2019-02-21 17:50:56', '2019-02-21 17:50:56', NULL, NULL, '2019-02-21 17:54:35', 1981, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.12601675125755', '-79.02689281851052', '0', '0', NULL, 0, 0, 0),
(122, 0, 42, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'MzAw', '', '', 'Cualquiera', '2019-02-21 17:54:46', '2019-02-21 17:54:46', NULL, NULL, '2019-02-21 17:55:23', 1981, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.125866727994381', '-79.02514468878508', '0', '0', NULL, 0, 0, 0),
(123, 0, 42, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjYwMQ==', '', '', 'Cualquiera', '2019-02-21 18:01:25', '2019-02-21 18:01:25', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12521950309662', '-79.0254306793213', '0', '0', NULL, 0, 0, 0),
(124, 0, 42, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjYwMQ==', '', '', '', '2019-02-21 18:02:44', '2019-02-21 18:02:44', NULL, NULL, '2019-02-21 18:03:10', 1981, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.12521950309662', '-79.0254306793213', '0', '0', NULL, 0, 0, 0),
(125, 0, 41, NULL, 'Q3ViYQ==', 'Mjg=', '', '', 'Cualquiera', '2019-02-22 12:36:33', '2019-02-22 12:36:33', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.122125421754205', '-79.03141636401415', '0', '0', NULL, 0, 0, 0),
(126, 0, 41, NULL, 'Q3ViYQ==', 'Mjg=', '', '', 'Cualquiera', '2019-02-22 12:37:01', '2019-02-22 12:37:01', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12212343027601', '-79.03142340481281', '0', '0', NULL, 0, 0, 0),
(127, 0, 41, NULL, 'Q3ViYQ==', 'Mjg=', '', '', 'Cualquiera', '2019-02-22 12:37:53', '2019-02-22 12:37:53', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.122132391927769', '-79.0314032882452', '0', '0', NULL, 0, 0, 0),
(128, 0, 41, NULL, 'Q3ViYQ==', 'Mjg=', '', '', 'Cualquiera', '2019-02-22 12:43:04', '2019-02-22 12:43:04', NULL, NULL, '2019-02-22 12:43:27', 1981, 5, 0, 0, NULL, 0, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.122108162276257', '-79.03145056217909', '0', '0', NULL, 0, 0, 0),
(129, 0, 41, NULL, 'Q3ViYQ==', 'Mjg=', '', '', 'Cualquiera', '2019-02-22 12:46:17', '2019-02-22 12:46:17', NULL, NULL, '2019-02-22 12:48:56', 1981, 5, 0, 0, 'YnVlbiBzZXJ2aWNpbw==', 5, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.122098204884798', '-79.0314569324255', '0', '0', NULL, 0, 0, 0),
(130, 0, 41, NULL, 'Q3ViYQ==', 'Mjg=', '', '', 'Cualquiera', '2019-02-22 13:11:55', '2019-02-22 13:11:55', NULL, NULL, '2019-02-22 13:12:12', 1981, 5, 0, 0, 'aG9s', 5, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.122097872971755', '-79.0314569324255', '0', '0', NULL, 0, 0, 0),
(131, 0, 41, NULL, 'QnJhc2ls', 'MzAw', '', '', 'Cualquiera', '2019-02-22 13:12:34', '2019-02-22 13:12:34', NULL, NULL, NULL, 1981, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.118052164388672', '-79.02829628437757', '0', '0', NULL, 0, 0, 0),
(132, 0, 41, NULL, 'Q3ViYQ==', 'Mjg=', '', '', 'Cualquiera', '2019-02-22 13:23:06', '2019-02-22 13:23:06', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.122092230449805', '-79.03146866708995', '0', '0', NULL, 0, 0, 0),
(133, 0, 41, NULL, 'Q3ViYQ==', 'Mjg=', '', '', 'Cualquiera', '2019-02-22 13:28:04', '2019-02-22 13:28:04', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.122092230449805', '-79.03146866708995', '0', '0', NULL, 0, 0, 0),
(134, 0, 41, NULL, 'SXNhYmVsIGRlIEJvYmFkaWxsYQ==', 'NTEz', '', '', 'Cualquiera', '2019-02-22 13:29:57', '2019-02-22 13:29:57', NULL, NULL, NULL, 1981, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12163286251533', '-79.02957536280155', '0', '0', NULL, 0, 0, 0),
(135, 0, 41, NULL, 'TWFydGluIEx1dGhlciBLaW5n', 'MjUy', '', '', 'Cualquiera', '2019-02-25 15:21:43', '2019-02-25 15:21:43', NULL, NULL, NULL, 1981, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.127639123139812', '-79.02816317975521', '0', '0', NULL, 0, 1, 0),
(136, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 15:22:38', '2019-02-25 15:22:38', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126312150881697', '-79.02679190039635', '0', '0', NULL, 0, 1, 0),
(137, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'MjI0', '', '', 'Cualquiera', '2019-02-25 15:23:08', '2019-02-25 15:23:08', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.125330029268454', '-79.02463305741549', '0', '0', NULL, 0, 0, 0),
(138, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 15:23:24', '2019-02-25 15:23:24', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126321444349491', '-79.02678150683641', '0', '0', NULL, 0, 1, 0),
(139, 0, 41, NULL, 'RmVybmFuZG8gZGUgTW9udGVzaW5vcw==', 'MTMz', '', '', 'Cualquiera', '2019-02-25 15:23:41', '2019-02-25 15:23:41', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.124109924856134', '-79.02380593121052', '0', '0', NULL, 0, 1, 0),
(140, 0, 41, NULL, 'U2FudG8gVG9yaWJpbyBkZSBNb2dyb3Zlam8=', 'NTE4', '', '', 'Cualquiera', '2019-02-25 15:24:13', '2019-02-25 15:24:13', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.1190123973769', '-79.03624333441257', '0', '0', NULL, 0, 1, 0),
(141, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'MTY2', '', '', 'Cualquiera', '2019-02-25 15:24:30', '2019-02-25 15:24:30', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.124772419442309', '-79.02382336556911', '0', '0', NULL, 0, 0, 0),
(142, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 15:27:11', '2019-02-25 15:27:11', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126315469977358', '-79.02678787708282', '0', '0', NULL, 0, 0, 0),
(143, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 15:28:09', '2019-02-25 15:28:09', NULL, NULL, '2019-02-25 15:28:43', 1981, 5, 0, 0, 'Ym4=', 5, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.126313810429535', '-79.0267875418067', '0', '0', NULL, 0, 0, 0),
(144, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 15:29:18', '2019-02-25 15:29:18', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126317461434741', '-79.0267737954855', '0', '0', NULL, 0, 0, 0),
(145, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'Mjg4', '', '', 'Cualquiera', '2019-02-25 15:29:39', '2019-02-25 15:29:39', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.125736951188298', '-79.02495123445988', '0', '0', NULL, 0, 0, 0),
(146, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'MTY2', '', '', 'Cualquiera', '2019-02-25 15:30:09', '2019-02-25 15:30:09', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.124556013515376', '-79.0238394588232', '0', '0', NULL, 0, 0, 0);
INSERT INTO `pedidos` (`id`, `id_empresa`, `id_usuario`, `id_direccion`, `direccion`, `nro`, `urba`, `refe`, `tipo`, `fecha`, `fecha_lanza`, `fecha_ubicacion`, `fecha_inicio`, `fecha_fin`, `id_empleado`, `estado`, `alertar`, `base`, `observa`, `califica`, `confirmar`, `destino`, `refe_d`, `precio`, `prec_sug`, `descuento`, `id_vale`, `conductor`, `quita`, `coordenadas`, `motivo_anula`, `coor_destino`, `distancia`, `vibrar`, `factura`, `corporativo`, `m_base`, `asignado`, `pasajero`, `area`, `motivo_precio`, `llamada`, `prueba`, `fecha_ampliada`, `alert_aceptado`, `latOrigen`, `lngOrigen`, `latDestino`, `lngDestino`, `direDestino`, `tipo_servicio`, `silenciar_anu`, `actualizar_dire`) VALUES
(147, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 15:30:58', '2019-02-25 15:30:58', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126312150881697', '-79.02679190039635', '0', '0', NULL, 0, 0, 0),
(148, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'MzA4', '', '', 'Cualquiera', '2019-02-25 15:31:20', '2019-02-25 15:31:20', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.125754874330253', '-79.02544409036636', '0', '0', NULL, 0, 0, 0),
(149, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 15:32:05', '2019-02-25 15:32:05', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126312150881697', '-79.02679190039635', '0', '0', NULL, 0, 0, 0),
(150, 0, 41, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjU3Mw==', '', '', 'Cualquiera', '2019-02-25 15:34:23', '2019-02-25 15:34:23', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12480229141692', '-79.024748057127', '0', '0', NULL, 0, 0, 0),
(151, 0, 41, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjYwNg==', '', '', 'Cualquiera', '2019-02-25 15:35:23', '2019-02-25 15:35:23', NULL, NULL, NULL, 1981, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.125156772012586', '-79.02573376893997', '0', '0', NULL, 0, 0, 0),
(152, 0, 41, NULL, 'VGl0dSBDdXNpIEh1YWxscGE=', 'MjMx', '', 'anprc2ttcw==', 'Cualquiera', '2019-02-25 15:36:25', '2019-02-25 15:36:25', NULL, NULL, NULL, 1981, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.123619027611236', '-79.02154617011547', '0', '0', NULL, 0, 0, 0),
(153, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', 'QXYgRXNwYcOxYSBhbGZyZW50ZSBkZSBsYSBmYXJtYWNpYQ==', 'Cualquiera', '2019-02-25 15:37:32', '2019-02-25 15:37:32', NULL, NULL, '2019-02-25 15:38:03', 1981, 5, 0, 0, 'c2kgbHV5IGJ1ZWIgc2VydmljaW8=', 5, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.126329742088409', '-79.02678620070219', '0', '0', NULL, 0, 0, 0),
(154, 0, 41, NULL, 'QXZlbmlkYSBNb2NoZQ==', 'OTg2', '', '', 'Cualquiera', '2019-02-25 15:38:55', '2019-02-25 15:38:55', NULL, NULL, NULL, 1981, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.123992428207135', '-79.02352966368198', '0', '0', NULL, 0, 1, 0),
(155, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', '', '2019-02-25 15:39:29', '2019-02-25 15:39:29', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126310823243418', '-79.02677915990353', '0', '0', NULL, 0, 1, 0),
(156, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'MzA4', '', '', '', '2019-02-25 15:39:39', '2019-02-25 15:39:39', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.125792712071748', '-79.02532540261745', '0', '0', NULL, 0, 1, 0),
(157, 0, 2, 5255, 'VHJ1amlsbG8=', 'MjQ=', '', 'MTIx', 'Cualquiera', '2019-02-25 15:42:01', '2019-02-25 15:42:01', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.1090524', '-79.0215336', '0', '0', NULL, 1, 0, 0),
(158, 0, 3, 5256, 'VHJ1amlsbG8=', 'MTI=', '', 'YXNkYXNk', 'Cualquiera', '2019-02-25 15:44:24', '2019-02-25 15:44:24', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.124177220443105', '-79.02634011855469', '0', '0', NULL, 1, 0, 0),
(159, 0, 41, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjU4MQ==', '', '', '', '2019-02-25 15:54:20', '2019-02-25 15:54:20', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12517901001704', '-79.02494721114635', '0', '0', NULL, 0, 1, 0),
(160, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'MTM5', '', '', 'Cualquiera', '2019-02-25 15:55:59', '2019-02-25 15:55:59', NULL, NULL, NULL, 1981, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.124869337396289', '-79.02337208390234', '0', '0', NULL, 0, 1, 0),
(161, 0, 4, 5257, 'QXYgQW3DqXJpY2EgU3Vy', 'Mjg1Nw==', '', 'YXNk', 'Cualquiera', '2019-02-25 16:02:11', '2019-02-25 16:02:11', NULL, NULL, NULL, 1981, 1, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.1264982', '-79.02768909999998', '0', '0', NULL, 1, 0, 0),
(162, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 16:02:52', '2019-02-25 16:02:52', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126311487062559', '-79.02677446603775', '0', '0', NULL, 0, 0, 0),
(163, 0, 41, NULL, 'QXZlbmlkYSBNb2NoZQ==', 'OTkw', '', '', 'Cualquiera', '2019-02-25 16:03:04', '2019-02-25 16:03:04', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12325857123982', '-79.02398094534874', '0', '0', NULL, 0, 0, 0),
(164, 0, 41, NULL, 'TGEgTWFyaW5h', 'MTEz', '', '', 'Cualquiera', '2019-02-25 16:03:17', '2019-02-25 16:03:17', NULL, NULL, NULL, 1981, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.124190247404288', '-79.02320209890604', '0', '0', NULL, 0, 0, 0),
(165, 0, 41, NULL, 'RmVybmFuZG8gZGUgTW9udGVzaW5vcw==', 'MTMz', '', '', 'Cualquiera', '2019-02-25 16:04:12', '2019-02-25 16:04:12', NULL, NULL, NULL, 1981, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.124085363412568', '-79.02382101863623', '0', '0', NULL, 0, 0, 0),
(166, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 16:04:57', '2019-02-25 16:04:57', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126308831785998', '-79.02677915990353', '0', '0', NULL, 0, 0, 0),
(167, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 16:05:01', '2019-02-25 16:05:01', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126308831785998', '-79.02677915990353', '0', '0', NULL, 0, 0, 0),
(168, 0, 41, NULL, 'RmVybmFuZG8gZGUgTW9udGVzaW5vcw==', 'MTMz', '', '', 'Cualquiera', '2019-02-25 16:05:07', '2019-02-25 16:05:07', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.124213813107515', '-79.02370266616344', '0', '0', NULL, 0, 0, 0),
(169, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'MTQ0', '', '', 'Cualquiera', '2019-02-25 16:05:16', '2019-02-25 16:05:16', NULL, NULL, NULL, 1981, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.124367819922718', '-79.0234461799264', '0', '0', NULL, 0, 0, 0),
(170, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 16:28:45', '2019-02-25 16:28:45', NULL, NULL, NULL, 1981, 1, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126322108168605', '-79.02678117156029', '0', '0', NULL, 0, 0, 0),
(171, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 16:28:47', '2019-02-25 16:28:47', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126322108168605', '-79.02678117156029', '0', '0', NULL, 0, 0, 0),
(172, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 16:29:37', '2019-02-25 16:29:37', NULL, NULL, '2019-02-25 16:31:31', 1981, 5, 0, 0, 'YnVlbm8=', 5, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.12631978480168', '-79.02678284794092', '0', '0', NULL, 0, 0, 0),
(173, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 16:41:23', '2019-02-25 16:41:23', NULL, NULL, '2019-02-25 16:41:48', 1981, 5, 0, 0, 'Y2Y=', 1, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.12631779334431', '-79.0267788246274', '0', '0', NULL, 0, 0, 0),
(174, 0, 41, NULL, 'Sm9yZ2UgV2FzaGluZ3Rvbg==', 'MTQx', '', '', 'Cualquiera', '2019-02-25 16:42:02', '2019-02-25 16:42:02', NULL, NULL, '2019-02-25 16:43:13', 1981, 5, 0, 0, 'ampr', 5, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.125097028113908', '-79.02445435523987', '0', '0', NULL, 0, 0, 0),
(175, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'Mjg0', '', '', 'Cualquiera', '2019-02-25 16:43:36', '2019-02-25 16:43:36', NULL, NULL, NULL, 1981, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12436450081095', '-79.02305994182825', '0', '0', NULL, 0, 0, 0),
(176, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 16:55:15', '2019-02-25 16:55:15', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126312150881697', '-79.02679190039635', '0', '0', NULL, 0, 0, 0),
(177, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 16:56:54', '2019-02-25 16:56:54', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126320780530376', '-79.02678217738865', '0', '0', NULL, 0, 0, 0),
(178, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 16:57:02', '2019-02-25 16:57:02', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126312150881697', '-79.02679190039635', '0', '0', NULL, 0, 0, 0),
(179, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 16:57:09', '2019-02-25 16:57:09', NULL, NULL, '2019-02-25 16:57:40', 1981, 5, 0, 0, 'ZmM=', 5, 0, '', '', '4.00', '4.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, '--', 0, 0, NULL, 0, '-8.126287589572923', '-79.0267426148057', '0', '0', NULL, 0, 0, 0),
(180, 0, 41, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjQ5MQ==', '', '', 'Cualquiera', '2019-02-25 16:57:26', '2019-02-25 16:57:26', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12472362854566', '-79.02448352426289', '0', '0', NULL, 0, 0, 0),
(181, 0, 41, NULL, 'QXZlbmlkYSBNb2NoZQ==', 'OTg2', '', '', 'Cualquiera', '2019-02-25 16:57:57', '2019-02-25 16:57:57', NULL, NULL, NULL, 1981, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.123760753922765', '-79.02312096208335', '0', '0', NULL, 0, 1, 0),
(182, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 17:10:19', '2019-02-25 17:10:19', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126312150881697', '-79.02679190039635', '0', '0', NULL, 0, 0, 0),
(183, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 17:11:15', '2019-02-25 17:11:15', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126312150881697', '-79.02679190039635', '0', '0', NULL, 0, 1, 0),
(184, 0, 41, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjcwMQ==', '', '', 'Cualquiera', '2019-02-25 17:11:26', '2019-02-25 17:11:26', NULL, NULL, NULL, 1981, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12591618257184', '-79.026302061975', '0', '0', NULL, 0, 0, 0),
(185, 0, 5, 5258, 'U3RhLiBDcnV6', 'MjM=', '', 'YU9ERUU=', 'Cualquiera', '2019-02-25 18:08:14', '2019-02-25 18:08:14', NULL, NULL, NULL, 0, 0, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 1, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.117065499999999', '-79.01662679999998', '0', '0', NULL, 1, 0, 0),
(186, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 18:11:56', '2019-02-25 18:11:56', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126304516961563', '-79.02679190039635', '0', '0', NULL, 0, 0, 0),
(187, 0, 41, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjYyMQ==', '', '', '', '2019-02-25 18:12:26', '2019-02-25 18:12:26', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.125412011123993', '-79.02560368180275', '0', '0', NULL, 0, 0, 0),
(188, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 18:12:36', '2019-02-25 18:12:36', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12629323203588', '-79.0267912298441', '0', '0', NULL, 0, 0, 0),
(189, 0, 41, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjYzMg==', '', '', '', '2019-02-25 18:14:34', '2019-02-25 18:14:34', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.12465890591851', '-79.02450498193501', '0', '0', NULL, 0, 0, 0),
(190, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 18:14:42', '2019-02-25 18:14:42', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126312150881697', '-79.02679190039635', '0', '0', NULL, 0, 0, 0),
(191, 0, 41, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjU5NQ==', '', '', '', '2019-02-25 18:14:52', '2019-02-25 18:14:52', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.125018697211058', '-79.0252536535263', '0', '0', NULL, 0, 0, 0),
(192, 0, 41, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjYyMQ==', '', '', '', '2019-02-25 18:15:01', '2019-02-25 18:15:01', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.125368530870716', '-79.02555104345083', '0', '0', NULL, 0, 0, 0),
(193, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', 'Cualquiera', '2019-02-25 18:19:33', '2019-02-25 18:19:33', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126323767716404', '-79.02677580714226', '0', '0', NULL, 0, 0, 0),
(194, 0, 41, NULL, 'QXZlbmlkYSBBbcOpcmljYSBTdXI=', 'MjYzOQ==', '', '', '', '2019-02-25 18:19:47', '2019-02-25 18:19:47', NULL, NULL, NULL, 0, 7, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.125567013209956', '-79.02594733983278', '0', '0', NULL, 0, 0, 0),
(195, 0, 41, NULL, 'Sm9obiBGLiBLZW5uZWR5', 'NDI4', '', '', '', '2019-02-25 18:24:25', '2019-02-25 18:24:25', NULL, NULL, NULL, 0, 9, 0, 0, NULL, 0, 0, '', '', '0.00', '0.00', '0.00', 0, 0, 0, '', '0', NULL, 0, 0, 0, 0, 0, 0, NULL, NULL, NULL, 0, 0, NULL, 0, '-8.126321444349491', '-79.02676340192556', '0', '0', NULL, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos_aceptados`
--

CREATE TABLE `pedidos_aceptados` (
  `id` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `fecha` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos_creditos`
--

CREATE TABLE `pedidos_creditos` (
  `id` int(11) NOT NULL,
  `id_empresa` int(11) NOT NULL,
  `id_emp_credito` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  `nombres` varchar(250) NOT NULL,
  `direccion` varchar(250) NOT NULL,
  `urba` varchar(250) NOT NULL,
  `referencia` varchar(250) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `estado` int(11) NOT NULL,
  `destino` varchar(150) NOT NULL,
  `lat_o` varchar(20) NOT NULL,
  `long_o` varchar(20) NOT NULL,
  `lat_d` varchar(20) NOT NULL,
  `long_d` varchar(20) NOT NULL,
  `tipo_servicio` enum('Servicio','Por tiempo') NOT NULL,
  `costo_hora` decimal(10,2) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `externo` int(11) NOT NULL,
  `base` int(11) NOT NULL,
  `confirmar` int(11) NOT NULL,
  `recibo` varchar(20) NOT NULL,
  `pagado_conductor` int(11) NOT NULL,
  `fecha_pago_co` datetime NOT NULL,
  `cobrado_empresa` int(11) NOT NULL,
  `fecha_cobrado_em` datetime NOT NULL,
  `observa` varchar(200) NOT NULL,
  `motivo_anula` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos_dejados_libres`
--

CREATE TABLE `pedidos_dejados_libres` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `fecha` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos_historial`
--

CREATE TABLE `pedidos_historial` (
  `id` int(11) NOT NULL,
  `id_empresa` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_direccion` int(11) DEFAULT NULL,
  `direccion` text NOT NULL,
  `nro` varchar(250) CHARACTER SET latin1 NOT NULL,
  `urba` varchar(250) CHARACTER SET latin1 NOT NULL,
  `refe` varchar(250) CHARACTER SET latin1 NOT NULL,
  `tipo` enum('Camioneta','Auto','Cualquiera','Vip') CHARACTER SET latin1 NOT NULL,
  `fecha` datetime NOT NULL,
  `fecha_lanza` datetime DEFAULT NULL,
  `fecha_ubicacion` datetime DEFAULT NULL,
  `fecha_inicio` datetime DEFAULT NULL,
  `fecha_fin` datetime DEFAULT NULL,
  `id_empleado` int(11) NOT NULL,
  `estado` int(11) NOT NULL DEFAULT '0',
  `alertar` int(11) NOT NULL DEFAULT '0',
  `base` int(11) NOT NULL,
  `observa` text CHARACTER SET latin1,
  `califica` int(11) NOT NULL DEFAULT '0',
  `confirmar` int(11) NOT NULL,
  `destino` varchar(150) CHARACTER SET latin1 NOT NULL,
  `refe_d` varchar(150) CHARACTER SET latin1 NOT NULL,
  `precio` decimal(10,2) DEFAULT '0.00',
  `prec_sug` decimal(10,2) NOT NULL DEFAULT '0.00',
  `descuento` decimal(10,2) NOT NULL DEFAULT '0.00',
  `id_vale` int(11) NOT NULL DEFAULT '0',
  `conductor` int(11) DEFAULT '0',
  `quita` int(11) NOT NULL DEFAULT '0',
  `coordenadas` varchar(200) CHARACTER SET latin1 NOT NULL,
  `motivo_anula` varchar(100) CHARACTER SET latin1 NOT NULL DEFAULT '0',
  `coor_destino` varchar(200) CHARACTER SET latin1 DEFAULT NULL,
  `distancia` int(11) NOT NULL,
  `vibrar` int(11) NOT NULL DEFAULT '0',
  `factura` int(11) NOT NULL DEFAULT '0',
  `corporativo` int(11) NOT NULL DEFAULT '0',
  `m_base` int(11) NOT NULL DEFAULT '0',
  `asignado` int(11) NOT NULL DEFAULT '0',
  `pasajero` varchar(50) DEFAULT NULL,
  `area` varchar(50) DEFAULT NULL,
  `motivo_precio` varchar(50) DEFAULT NULL,
  `llamada` int(11) NOT NULL DEFAULT '0',
  `prueba` int(11) NOT NULL DEFAULT '0',
  `fecha_ampliada` datetime DEFAULT NULL,
  `alert_aceptado` int(11) NOT NULL DEFAULT '0',
  `latOrigen` varchar(50) DEFAULT '0',
  `lngOrigen` varchar(50) DEFAULT '0',
  `latDestino` varchar(50) DEFAULT '0',
  `lngDestino` varchar(50) DEFAULT '0',
  `direDestino` varchar(250) DEFAULT NULL,
  `tipo_servicio` int(11) NOT NULL DEFAULT '0',
  `silenciar_anu` int(11) NOT NULL,
  `actualizar_dire` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos_recorrido`
--

CREATE TABLE `pedidos_recorrido` (
  `id` int(11) NOT NULL,
  `id_pedido` varchar(25) NOT NULL,
  `lat` varchar(25) NOT NULL,
  `lng` varchar(25) NOT NULL,
  `bearing` varchar(25) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pedidos_recorrido`
--

INSERT INTO `pedidos_recorrido` (`id`, `id_pedido`, `lat`, `lng`, `bearing`) VALUES
(2365249, '3', '-8.1160094', '-79.02679539999997', '0'),
(2365250, '3', '-8.11490101497506', '-79.0253034900095', '166.4674'),
(2365251, '3', '-8.11490101497506', '-79.0253034900095', '166.4674'),
(2365252, '6', '-8.1160094', '-79.02679539999997', '0'),
(2365253, '6', '-8.11485306838867', '-79.02583930649341', '0.0'),
(2365254, '6', '-8.114911130427602', '-79.02592926511417', '0.0'),
(2365255, '6', '-8.114954278984497', '-79.02604727184188', '0.0'),
(2365256, '6', '-8.114986145513019', '-79.02609704679747', '0.0'),
(2365257, '6', '-8.11498570048801', '-79.02611835366027', '0.0'),
(2365258, '6', '-8.114975841193615', '-79.0262453923366', '0.0'),
(2365259, '6', '-8.11499072601068', '-79.02630592277941', '0.0'),
(2365260, '6', '-8.11506738585834', '-79.02643106630114', '0.0'),
(2365261, '6', '-8.11508882097457', '-79.02644319392033', '0.0'),
(2365262, '6', '-8.115084859707089', '-79.02639755133639', '0.0'),
(2365263, '8', '-8.1160094', '-79.02679539999997', '0'),
(2365264, '10', '-8.1160094', '-79.02679539999997', '0'),
(2365265, '10', '-8.114809428635844', '-79.0253569758454', '43.842995'),
(2365266, '10', '-8.114809428635832', '-79.02535697584541', '43.842995'),
(2365267, '10', '-8.114809428635832', '-79.02535697584541', '43.842995'),
(2365268, '11', '-8.1160094', '-79.02679539999997', '0'),
(2365269, '11', '-8.114992893823873', '-79.02534869833771', '161.03964'),
(2365270, '11', '-8.114898105321828', '-79.02548575079115', '0.0'),
(2365271, '11', '-8.114828279370265', '-79.02541570451264', '0.0'),
(2365272, '12', '-8.1142764', '-79.02440769999998', '0'),
(2365273, '12', '-8.115188972867537', '-79.02517664625265', '171.21677'),
(2365274, '12', '-8.115188972867537', '-79.02517664625265', '171.21677'),
(2365275, '15', '-8.115454910187355', '-79.02647003531456', '0'),
(2365276, '15', '-8.114839688141954', '-79.02541648403036', '0.0'),
(2365277, '15', '-8.11484050913095', '-79.02540829735439', '0.0'),
(2365278, '16', '-8.115694555303703', '-79.02674797922373', '0'),
(2365279, '16', '-8.114831910318387', '-79.02545692989808', '0.0'),
(2365280, '16', '-8.114829663372372', '-79.02545918552204', '0.0'),
(2365281, '16', '-8.11482471792407', '-79.02546353327193', '0.0'),
(2365282, '18', '-8.1145827', '-79.0256321', '0'),
(2365283, '18', '-8.114772820294862', '-79.02536679177048', '0.0'),
(2365284, '18', '-8.114772820294862', '-79.02536679177048', '0.0'),
(2365285, '19', '-8.114321214247358', '-79.02425614047547', '0'),
(2365286, '19', '-8.11480177365832', '-79.02558272597604', '0.0'),
(2365287, '19', '-8.114802344471395', '-79.02557125018207', '0.0'),
(2365288, '21', '-8.114807869019408', '-79.0254848550777', '304.59024'),
(2365289, '21', '-8.114820254104947', '-79.02547264794097', '304.59024'),
(2365290, '21', '-8.114843091093547', '-79.02546543526368', '304.59024'),
(2365291, '21', '-8.114859442384699', '-79.0254742153528', '304.59024'),
(2365292, '21', '-8.114867632783968', '-79.02547684825646', '304.59024'),
(2365293, '21', '-8.114878108015303', '-79.02549775037748', '304.59024'),
(2365294, '21', '-8.114875959970531', '-79.02551608374411', '304.59024'),
(2365295, '21', '-8.114875269938096', '-79.02553413735241', '304.59024'),
(2365296, '21', '-8.11487756701055', '-79.02554880574178', '304.59024'),
(2365297, '21', '-8.114877516292836', '-79.02555468933312', '304.59024'),
(2365298, '21', '-8.114876744714856', '-79.0255546903921', '304.59024'),
(2365299, '21', '-8.114875376304171', '-79.02555168810461', '304.59024'),
(2365300, '21', '-8.114870240175573', '-79.02554917428796', '304.59024'),
(2365301, '21', '-8.114867981326896', '-79.02554760981322', '304.59024'),
(2365302, '21', '-8.114871429830059', '-79.02555401636849', '304.59024'),
(2365303, '21', '-8.114872912816761', '-79.02555792921609', '304.59024'),
(2365304, '21', '-8.114874793849227', '-79.02556297639761', '304.59024'),
(2365305, '21', '-8.114876432318713', '-79.02556790660084', '304.59024'),
(2365306, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(2365307, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(2365308, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(2365309, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(2365310, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(2365311, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(2365312, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(2365313, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(2365314, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(2365315, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(2365316, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(2365317, '47', '-18.00954220801857', '-70.25382153318738', '143.59052'),
(2365318, '47', '-18.00955609583445', '-70.25378620075395', '123.86846'),
(2365319, '47', '-18.009571961693478', '-70.2537604260393', '93.23929'),
(2365320, '47', '-18.009572596571275', '-70.25375829613071', '93.23929'),
(2365321, '47', '-18.0095735577201', '-70.25375760571218', '93.23929'),
(2365322, '47', '-18.009574200050483', '-70.25375768283808', '93.23929'),
(2365323, '47', '-18.009574540937813', '-70.25375733342389', '93.23929'),
(2365324, '47', '-18.00957477193508', '-70.25375692765105', '93.23929'),
(2365325, '47', '-18.00957788601978', '-70.25376300649988', '93.23929'),
(2365326, '47', '-18.009578385644534', '-70.25376572205425', '93.23929'),
(2365327, '47', '-18.009575637733633', '-70.25376942922406', '93.23929'),
(2365328, '47', '-18.009578243467256', '-70.25377559357182', '93.23929'),
(2365329, '47', '-18.00957561699935', '-70.2537755040837', '93.23929'),
(2365330, '47', '-18.009698', '-70.2538596', '223.0'),
(2365331, '47', '-18.009740658046464', '-70.25386379419672', '48.333126'),
(2365332, '47', '-18.009721628056383', '-70.25385520011847', '0.0'),
(2365333, '47', '-18.009758044162314', '-70.253946993161', '218.9456'),
(2365334, '47', '-18.009747018613297', '-70.25399221631025', '214.85144'),
(2365335, '47', '-18.00974488894901', '-70.25399649389338', '141.98009'),
(2365336, '47', '-18.009753976395377', '-70.25395421803547', '104.686264'),
(2365337, '47', '-18.0097390737203', '-70.25395349554738', '77.200005'),
(2365338, '47', '-18.009696028190117', '-70.25387738316707', '62.61966'),
(2365339, '47', '-18.009613833423717', '-70.25382552275293', '64.15913'),
(2365340, '47', '-18.00959157858351', '-70.25379734849353', '24.184778'),
(2365341, '47', '-18.009593672740905', '-70.25377865189316', '89.5324'),
(2365342, '47', '-18.00959998952389', '-70.25371751303342', '81.44592'),
(2365343, '47', '-18.009554968213806', '-70.25369203127151', '97.673325'),
(2365344, '47', '-18.00956572941225', '-70.25367136150108', '0.0'),
(2365345, '47', '-18.009577698708863', '-70.2536418408449', '0.0'),
(2365346, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365347, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365348, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365349, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365350, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365351, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365352, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365353, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365354, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365355, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365356, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365357, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365358, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365359, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365360, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365361, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365362, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365363, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365364, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365365, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365366, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365367, '47', '-18.00957636255958', '-70.25363613329124', '0.0'),
(2365368, '47', '-18.009849645641395', '-70.25410752437556', '238.48659'),
(2365369, '47', '-18.00983537981999', '-70.25404926686834', '95.482025'),
(2365370, '47', '-18.009920380121873', '-70.25426877675497', '244.2407'),
(2365371, '47', '-18.00988404838618', '-70.25422417062478', '67.731026'),
(2365372, '47', '-18.00963691869408', '-70.25377151204177', '0.0'),
(2365373, '47', '-18.00963691869408', '-70.25377151204177', '0.0'),
(2365374, '47', '-18.00963691869408', '-70.25377151204177', '0.0'),
(2365375, '47', '-18.00963691869408', '-70.25377151204177', '0.0'),
(2365376, '47', '-18.00963691869408', '-70.25377151204177', '0.0'),
(2365377, '47', '-18.009656092235723', '-70.25376548040016', '0.0'),
(2365378, '47', '-18.009657527853733', '-70.2537724324758', '0.0'),
(2365379, '47', '-18.009672494834476', '-70.25377386561966', '207.89323'),
(2365380, '47', '-18.00968751126767', '-70.2538074541132', '239.98969'),
(2365381, '76', '-18.0096588', '-70.2538858', '0.0'),
(2365382, '76', '-18.0096588', '-70.2538858', '0.0'),
(2365383, '76', '-18.0096588', '-70.2538858', '0.0'),
(2365384, '76', '-18.0096588', '-70.2538858', '0.0'),
(2365385, '76', '-18.0096588', '-70.2538858', '0.0'),
(2365386, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365387, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365388, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365389, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365390, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365391, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365392, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365393, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365394, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365395, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365396, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365397, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365398, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365399, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365400, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365401, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365402, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365403, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365404, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365405, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365406, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365407, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365408, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365409, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365410, '78', '-18.0096588', '-70.2538858', '0.0'),
(2365411, '100', '-18.001811', '-70.2477798', '0.0'),
(2365412, '100', '-18.001811', '-70.2477798', '0.0'),
(2365413, '100', '-18.001811', '-70.2477798', '0.0'),
(2365414, '100', '-18.001811', '-70.2477798', '0.0'),
(2365415, '100', '-18.001811', '-70.2477798', '0.0'),
(2365416, '100', '-18.001811', '-70.2477798', '0.0'),
(2365417, '100', '-18.001811', '-70.2477798', '0.0'),
(2365418, '100', '-18.001811', '-70.2477798', '0.0'),
(2365419, '100', '-18.001811', '-70.2477798', '0.0'),
(2365420, '100', '-18.001811', '-70.2477798', '0.0'),
(2365421, '100', '-18.001811', '-70.2477798', '0.0'),
(2365422, '100', '-18.001811', '-70.2477798', '0.0'),
(2365423, '100', '-18.001811', '-70.2477798', '0.0'),
(2365424, '100', '-18.001811', '-70.2477798', '0.0'),
(2365425, '100', '-18.001811', '-70.2477798', '0.0'),
(2365426, '100', '-18.001811', '-70.2477798', '0.0'),
(2365427, '113', '-8.126333333333333', '-79.02652333333334', '290.05'),
(2365428, '113', '-8.126231666666666', '-79.02641166666668', '33.45'),
(2365429, '113', '-8.126209999999999', '-79.02637333333334', '60.69'),
(2365430, '114', '-8.126341666666667', '-79.02659333333332', '93.84'),
(2365431, '114', '-8.126415', '-79.02671000000001', '267.25'),
(2365432, '114', '-8.126456666666666', '-79.02680166666667', '231.47'),
(2365433, '114', '-8.126441666666667', '-79.02679333333334', '212.55'),
(2365434, '114', '-8.126444999999999', '-79.02673333333334', '15.67'),
(2365435, '114', '-8.126406666666666', '-79.02672833333332', '293.76'),
(2365436, '114', '-8.126383333333333', '-79.02668166666668', '74.56'),
(2365437, '114', '-8.126371666666667', '-79.026675', '74.56'),
(2365438, '114', '-8.126344999999999', '-79.02666666666667', '74.56'),
(2365439, '114', '-8.126321666666666', '-79.02663', '74.56'),
(2365440, '114', '-8.126326666666667', '-79.02666166666667', '74.56'),
(2365441, '115', '-8.1190123973769', '-79.03624333441257', '0'),
(2365442, '115', '-8.126316666666666', '-79.02696333333333', '30.24'),
(2365443, '115', '-8.126303333333334', '-79.02699', '30.24'),
(2365444, '119', '-8.126323333333334', '-79.02668', '315.67'),
(2365445, '119', '-8.126368333333334', '-79.02664166666666', '315.67'),
(2365446, '119', '-8.126351666666666', '-79.02666500000001', '315.67'),
(2365447, '119', '-8.126280000000001', '-79.02666833333333', '315.67'),
(2365448, '121', '-8.126326666666667', '-79.02671833333333', '212.62'),
(2365449, '121', '-8.126335000000001', '-79.02672666666668', '35.45'),
(2365450, '121', '-8.126366666666666', '-79.02676833333334', '35.45'),
(2365451, '122', '-8.126401666666668', '-79.02668833333334', '35.45'),
(2365452, '122', '-8.126443333333333', '-79.02666166666667', '35.45'),
(2365453, '122', '-8.126425000000001', '-79.02671833333333', '35.45'),
(2365454, '124', '-8.126306666666666', '-79.02672833333332', '272.75'),
(2365455, '124', '-8.126333333333333', '-79.02672166666666', '272.75'),
(2365456, '128', '-8.1220982', '-79.0314572', '0.0'),
(2365457, '128', '-8.1220982', '-79.0314572', '0.0'),
(2365458, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365459, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365460, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365461, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365462, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365463, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365464, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365465, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365466, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365467, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365468, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365469, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365470, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365471, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365472, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365473, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365474, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365475, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365476, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365477, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365478, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365479, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365480, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365481, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365482, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365483, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365484, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365485, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365486, '129', '-8.1220982', '-79.0314572', '0.0'),
(2365487, '130', '-8.1220982', '-79.0314572', '0.0'),
(2365488, '130', '-8.1220982', '-79.0314572', '0.0'),
(2365489, '143', '-8.1263121', '-79.0267919', '0.0'),
(2365490, '143', '-8.1263121', '-79.0267919', '0.0'),
(2365491, '143', '-8.1263121', '-79.0267919', '0.0'),
(2365492, '143', '-8.1263121', '-79.0267919', '0.0'),
(2365493, '143', '-8.1263121', '-79.0267919', '0.0'),
(2365494, '143', '-8.1263121', '-79.0267919', '0.0'),
(2365495, '143', '-8.1263121', '-79.0267919', '0.0'),
(2365496, '153', '-8.12645360827446', '-79.02665351517498', '221.1'),
(2365497, '153', '-8.12645360827446', '-79.02665351517498', '221.1'),
(2365498, '153', '-8.126424942165613', '-79.02664630673826', '219.8'),
(2365499, '153', '-8.126395395956933', '-79.02666893787682', '0.0'),
(2365500, '153', '-8.12633533962071', '-79.0266818460077', '219.4'),
(2365501, '172', '-8.126309858635068', '-79.02674311771989', '0.0'),
(2365502, '172', '-8.126327712088823', '-79.02674127370119', '20.2'),
(2365503, '172', '-8.126352815888822', '-79.0267235878855', '57.4'),
(2365504, '172', '-8.12637398019433', '-79.02671931311488', '162.0'),
(2365505, '172', '-8.126376075670123', '-79.02672492899', '0.0'),
(2365506, '172', '-8.126376075670123', '-79.02672492899', '0.0'),
(2365507, '172', '-8.126376075670123', '-79.02672492899', '0.0'),
(2365508, '172', '-8.126390911638737', '-79.02672735974193', '190.2'),
(2365509, '172', '-8.12641039956361', '-79.02672928757966', '189.0'),
(2365510, '172', '-8.126450129784644', '-79.02671478688717', '165.5'),
(2365511, '172', '-8.12644430436194', '-79.02670556679368', '0.0'),
(2365512, '172', '-8.12644430436194', '-79.02670556679368', '0.0'),
(2365513, '172', '-8.126358557492495', '-79.02671839110553', '136.4'),
(2365514, '172', '-8.126360150054097', '-79.02671386487782', '0.0'),
(2365515, '172', '-8.126360150054097', '-79.02671386487782', '0.0'),
(2365516, '173', '-8.126443250127707', '-79.02658478398718', '0.0'),
(2365517, '173', '-8.12641935404117', '-79.02661354685694', '357.41644'),
(2365518, '174', '-8.126356058162466', '-79.02670692206226', '308.51617'),
(2365519, '174', '-8.126356058162466', '-79.02670692206226', '308.51617'),
(2365520, '174', '-8.126356058162466', '-79.02670692206226', '308.51617'),
(2365521, '179', '-8.126347189978773', '-79.02669349878481', '282.39618'),
(2365522, '179', '-8.126347189978773', '-79.02669349878481', '282.39618'),
(2365523, '179', '-8.126347189978773', '-79.02669349878481', '282.39618'),
(2365524, '179', '-8.126347189978773', '-79.02669349878481', '282.39618');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pedidos_recorrido_aceptado`
--

CREATE TABLE `pedidos_recorrido_aceptado` (
  `id` int(11) NOT NULL,
  `id_pedido` varchar(25) NOT NULL,
  `lat` varchar(25) NOT NULL,
  `lng` varchar(25) NOT NULL,
  `bearing` varchar(25) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pedidos_recorrido_aceptado`
--

INSERT INTO `pedidos_recorrido_aceptado` (`id`, `id_pedido`, `lat`, `lng`, `bearing`) VALUES
(1310776, '3', '-8.11490101497506', '-79.0253034900095', '166.4674'),
(1310777, '3', '-8.11490101497506', '-79.0253034900095', '166.4674'),
(1310778, '3', '-8.11490101497506', '-79.0253034900095', '166.4674'),
(1310779, '3', '-8.11490101497506', '-79.0253034900095', '166.4674'),
(1310780, '6', '-8.114782561778453', '-79.02531725936528', '0.0'),
(1310781, '6', '-8.114788114218694', '-79.0253355102708', '0.0'),
(1310782, '6', '-8.114774950229286', '-79.02544616187576', '0.0'),
(1310783, '6', '-8.114774950229286', '-79.02544616187576', '0.0'),
(1310784, '6', '-8.114757242284325', '-79.02562641164204', '0.0'),
(1310785, '6', '-8.114750040935762', '-79.02565755104371', '0.0'),
(1310786, '6', '-8.114837520194651', '-79.02581323912186', '0.0'),
(1310787, '10', '-8.114811052943914', '-79.02535615241864', '43.842995'),
(1310788, '10', '-8.114811052943914', '-79.02535615241864', '43.842995'),
(1310789, '10', '-8.114809428636969', '-79.0253569758448', '43.842995'),
(1310790, '11', '-8.114823014060159', '-79.0254876769001', '278.01324'),
(1310791, '11', '-8.114924285424259', '-79.02542955802974', '270.9258'),
(1310792, '11', '-8.115010369154147', '-79.02535064148222', '176.24341'),
(1310793, '12', '-8.115188972867537', '-79.02517664625265', '171.21677'),
(1310794, '12', '-8.115188972867537', '-79.02517664625265', '171.21677'),
(1310795, '12', '-8.115188972867537', '-79.02517664625265', '171.21677'),
(1310796, '12', '-8.115188972867537', '-79.02517664625265', '171.21677'),
(1310797, '14', '-8.114798210893937', '-79.02545262136883', '0.0'),
(1310798, '14', '-8.114813081463323', '-79.02544126909333', '0.0'),
(1310799, '14', '-8.114813554856534', '-79.02544026002194', '0.0'),
(1310800, '14', '-8.11481646145171', '-79.02543812670562', '0.0'),
(1310801, '14', '-8.11482093699448', '-79.02543341249621', '0.0'),
(1310802, '14', '-8.114810446111312', '-79.02544286690775', '0.0'),
(1310803, '14', '-8.114801837749098', '-79.02545225105555', '0.0'),
(1310804, '14', '-8.114801209036413', '-79.0254556839777', '0.0'),
(1310805, '14', '-8.114809224041334', '-79.02545262839563', '0.0'),
(1310806, '14', '-8.114823639146419', '-79.02544795746768', '0.0'),
(1310807, '14', '-8.11482185344636', '-79.02545309238978', '0.0'),
(1310808, '14', '-8.114820803404248', '-79.02545487625848', '0.0'),
(1310809, '14', '-8.114818618889515', '-79.0254627105388', '0.0'),
(1310810, '14', '-8.11482660173079', '-79.02546280177357', '0.0'),
(1310811, '14', '-8.114821174089798', '-79.02547309749006', '0.0'),
(1310812, '14', '-8.114818167906451', '-79.02547629964202', '0.0'),
(1310813, '14', '-8.114819712187384', '-79.02547459894843', '0.0'),
(1310814, '15', '-8.114829906990416', '-79.02535966158094', '0.0'),
(1310815, '15', '-8.114837316830803', '-79.02538184272414', '0.0'),
(1310816, '15', '-8.114838376475445', '-79.02538529049097', '0.0'),
(1310817, '15', '-8.114840511036142', '-79.02539290812541', '0.0'),
(1310818, '15', '-8.114840340287476', '-79.02540826327542', '0.0'),
(1310819, '16', '-8.11480325152476', '-79.02542294792237', '0.0'),
(1310820, '16', '-8.114798090560456', '-79.0254182817062', '0.0'),
(1310821, '16', '-8.1147945743224', '-79.02540577208379', '0.0'),
(1310822, '16', '-8.114793445187379', '-79.02540160155438', '0.0'),
(1310823, '16', '-8.114794019758456', '-79.02539488723475', '0.0'),
(1310824, '16', '-8.114797566602729', '-79.02538290723585', '0.0'),
(1310825, '16', '-8.114801560716474', '-79.02538489358486', '0.0'),
(1310826, '16', '-8.114804576543708', '-79.02539830150224', '0.0'),
(1310827, '16', '-8.114808258878918', '-79.02541391809254', '0.0'),
(1310828, '16', '-8.11481208265293', '-79.02542302063219', '0.0'),
(1310829, '16', '-8.114816891652703', '-79.02542967351198', '0.0'),
(1310830, '16', '-8.114824322916448', '-79.0254385390552', '0.0'),
(1310831, '16', '-8.114827212439184', '-79.0254445394882', '0.0'),
(1310832, '16', '-8.114827964692376', '-79.02544614298806', '0.0'),
(1310833, '16', '-8.114833408567115', '-79.02545489450183', '0.0'),
(1310834, '18', '-8.114772820294862', '-79.02536679177048', '0.0'),
(1310835, '18', '-8.114772820294862', '-79.02536679177048', '0.0'),
(1310836, '18', '-8.114772820294862', '-79.02536679177048', '0.0'),
(1310837, '19', '-8.114796934784332', '-79.02553613492603', '0.0'),
(1310838, '19', '-8.114795640310565', '-79.0255393918836', '0.0'),
(1310839, '19', '-8.114794841372653', '-79.02557429875573', '0.0'),
(1310840, '19', '-8.11479885378773', '-79.02558627073034', '0.0'),
(1310841, '19', '-8.114801454579363', '-79.02558651696317', '0.0'),
(1310842, '20', '-8.1147735647201', '-79.0254093186978', '75.22571'),
(1310843, '20', '-8.1147537307871', '-79.02539265120221', '75.22571'),
(1310844, '20', '-8.1147537307871', '-79.02539265120221', '75.22571'),
(1310845, '20', '-8.1147537307871', '-79.02539265120221', '75.22571'),
(1310846, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310847, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310848, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310849, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310850, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310851, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310852, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310853, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310854, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310855, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310856, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310857, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310858, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310859, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310860, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310861, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310862, '21', '-8.114886838447287', '-79.02545355554626', '75.22571'),
(1310863, '21', '-8.114877387806185', '-79.02545751531132', '75.22571'),
(1310864, '21', '-8.114863622571704', '-79.02545230191454', '224.10132'),
(1310865, '21', '-8.114829148048143', '-79.02544028853792', '224.10132'),
(1310866, '21', '-8.114800501871366', '-79.02543802965435', '224.10132'),
(1310867, '21', '-8.114790662228748', '-79.02544132793041', '224.10132'),
(1310868, '21', '-8.114781623574574', '-79.02544540509265', '224.10132'),
(1310869, '21', '-8.114775384255816', '-79.02545441893359', '304.59024'),
(1310870, '21', '-8.114774100701037', '-79.02545326340679', '304.59024'),
(1310871, '21', '-8.114773124851657', '-79.02545563282773', '304.59024'),
(1310872, '21', '-8.114770668489038', '-79.02546192125897', '304.59024'),
(1310873, '21', '-8.114765354971283', '-79.02546261905094', '304.59024'),
(1310874, '21', '-8.114771704854702', '-79.02547086428223', '304.59024'),
(1310875, '21', '-8.114787445802207', '-79.02548571567742', '304.59024'),
(1310876, '21', '-8.11480391356581', '-79.02548600865823', '304.59024'),
(1310877, '28', '-8.1263334', '-79.0267414', '0.0'),
(1310878, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310879, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310880, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310881, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310882, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310883, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310884, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310885, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310886, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310887, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310888, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310889, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310890, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310891, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310892, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310893, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310894, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310895, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310896, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310897, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310898, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310899, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310900, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310901, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310902, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310903, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310904, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310905, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310906, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310907, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310908, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310909, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310910, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310911, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310912, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310913, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310914, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310915, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310916, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310917, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310918, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310919, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310920, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310921, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310922, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310923, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310924, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310925, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310926, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310927, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310928, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310929, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310930, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310931, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310932, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310933, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310934, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310935, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310936, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310937, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310938, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310939, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310940, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310941, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310942, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310943, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310944, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310945, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310946, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310947, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310948, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310949, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310950, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310951, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310952, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310953, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310954, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310955, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310956, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310957, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310958, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310959, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310960, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310961, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310962, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310963, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310964, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310965, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310966, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310967, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310968, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310969, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310970, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310971, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310972, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310973, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310974, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310975, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310976, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310977, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310978, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310979, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310980, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310981, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310982, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310983, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310984, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310985, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310986, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310987, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310988, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310989, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310990, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310991, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310992, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310993, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310994, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310995, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310996, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310997, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310998, '33', '-8.1245253', '-79.0261878', '0.0'),
(1310999, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311000, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311001, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311002, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311003, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311004, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311005, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311006, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311007, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311008, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311009, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311010, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311011, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311012, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311013, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311014, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311015, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311016, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311017, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311018, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311019, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311020, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311021, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311022, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311023, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311024, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311025, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311026, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311027, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311028, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311029, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311030, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311031, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311032, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311033, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311034, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311035, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311036, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311037, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311038, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311039, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311040, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311041, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311042, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311043, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311044, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311045, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311046, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311047, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311048, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311049, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311050, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311051, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311052, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311053, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311054, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311055, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311056, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311057, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311058, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311059, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311060, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311061, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311062, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311063, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311064, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311065, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311066, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311067, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311068, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311069, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311070, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311071, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311072, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311073, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311074, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311075, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311076, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311077, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311078, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311079, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311080, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311081, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311082, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311083, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311084, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311085, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311086, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311087, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311088, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311089, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311090, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311091, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311092, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311093, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311094, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311095, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311096, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311097, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311098, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311099, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311100, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311101, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311102, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311103, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311104, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311105, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311106, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311107, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311108, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311109, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311110, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311111, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311112, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311113, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311114, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311115, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311116, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311117, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311118, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311119, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311120, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311121, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311122, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311123, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311124, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311125, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311126, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311127, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311128, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311129, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311130, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311131, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311132, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311133, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311134, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311135, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311136, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311137, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311138, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311139, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311140, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311141, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311142, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311143, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311144, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311145, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311146, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311147, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311148, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311149, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311150, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311151, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311152, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311153, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311154, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311155, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311156, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311157, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311158, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311159, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311160, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311161, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311162, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311163, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311164, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311165, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311166, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311167, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311168, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311169, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311170, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311171, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311172, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311173, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311174, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311175, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311176, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311177, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311178, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311179, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311180, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311181, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311182, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311183, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311184, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311185, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311186, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311187, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311188, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311189, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311190, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311191, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311192, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311193, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311194, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311195, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311196, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311197, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311198, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311199, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311200, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311201, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311202, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311203, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311204, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311205, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311206, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311207, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311208, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311209, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311210, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311211, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311212, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311213, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311214, '33', '-8.1245253', '-79.0261878', '0.0'),
(1311215, '39', '-18.010692870192514', '-70.24775899216188', '62.654987'),
(1311216, '39', '-18.010692870192514', '-70.24775899216188', '62.654987'),
(1311217, '39', '-18.010692870192514', '-70.24775899216188', '62.654987'),
(1311218, '39', '-18.04990713190538', '-70.27697840321335', '0.0'),
(1311219, '39', '-18.04988072422518', '-70.27696816327352', '0.0'),
(1311220, '39', '-18.04986752127574', '-70.27699792533505', '0.0'),
(1311221, '39', '-18.049857467418835', '-70.27703848453736', '0.0'),
(1311222, '39', '-18.049857467418835', '-70.27703848453736', '0.0'),
(1311223, '39', '-18.049857467418835', '-70.27703848453736', '0.0'),
(1311224, '39', '-18.04978434145352', '-70.27703554266242', '16.96179'),
(1311225, '42', '-18.049461459740996', '-70.27747663669288', '0.0'),
(1311226, '47', '-17.991985106101666', '-70.24383010036728', '221.01428'),
(1311227, '47', '-17.992153286025054', '-70.24398838768889', '221.5679'),
(1311228, '47', '-17.99228252066731', '-70.24411134939277', '222.22787'),
(1311229, '47', '-17.99243517744934', '-70.24424654209423', '220.09525'),
(1311230, '47', '-17.99264877565257', '-70.24441877997911', '218.11386'),
(1311231, '47', '-17.99286581281011', '-70.24459753893139', '216.43929'),
(1311232, '47', '-17.993078150117018', '-70.24475210112142', '212.07526'),
(1311233, '47', '-17.993293633907125', '-70.24486741473419', '198.9037'),
(1311234, '47', '-17.993530618168382', '-70.24493412699012', '193.75879'),
(1311235, '47', '-17.993758689876476', '-70.24497297954957', '185.03531'),
(1311236, '47', '-17.99398774062796', '-70.24498090977562', '181.26874'),
(1311237, '47', '-17.994082450115', '-70.24498231130917', '182.64337'),
(1311238, '47', '-17.994082450115', '-70.24498231130917', '182.64337'),
(1311239, '47', '-17.994082450115', '-70.24498231130917', '182.64337'),
(1311240, '47', '-17.994082450115', '-70.24498231130917', '182.64337'),
(1311241, '47', '-17.994206518530074', '-70.24498754394835', '202.94475'),
(1311242, '47', '-17.99427030978342', '-70.24516763711576', '270.47195'),
(1311243, '47', '-17.994246268178724', '-70.24549709155542', '271.53815'),
(1311244, '47', '-17.994228019014553', '-70.2458867356604', '271.50684'),
(1311245, '47', '-17.994222056425624', '-70.24631168716853', '269.73242'),
(1311246, '47', '-17.99422777178733', '-70.2467601817207', '270.06024'),
(1311247, '47', '-17.994228743882935', '-70.24721785065633', '270.28433'),
(1311248, '47', '-17.994227244207504', '-70.24764369939231', '267.98248'),
(1311249, '47', '-17.99423880233027', '-70.24805821333103', '270.72437'),
(1311250, '47', '-17.99424153164012', '-70.24841372936055', '269.94955'),
(1311251, '47', '-17.994240842937167', '-70.24880603980534', '270.01978'),
(1311252, '47', '-17.99424083125612', '-70.2492203813787', '269.66003'),
(1311253, '47', '-17.99423865122616', '-70.24963668718385', '268.83124'),
(1311254, '47', '-17.99423950895247', '-70.25006776050581', '270.36697'),
(1311255, '47', '-17.994237399879392', '-70.25049357440463', '269.09683'),
(1311256, '47', '-17.994255130797622', '-70.25079778545607', '266.64072'),
(1311257, '47', '-17.994259975916354', '-70.25087766333759', '266.48172'),
(1311258, '47', '-17.994259975916354', '-70.25087766333759', '266.48172'),
(1311259, '47', '-17.994264771315983', '-70.25093015063186', '264.86035'),
(1311260, '47', '-17.99428382393078', '-70.2511187143928', '264.05322'),
(1311261, '47', '-17.994336991994853', '-70.25135182948083', '257.2276'),
(1311262, '47', '-17.994372533097298', '-70.25158002061166', '265.48734'),
(1311263, '47', '-17.99441283355933', '-70.25187666963133', '258.75922'),
(1311264, '47', '-17.994502488653087', '-70.25221854944914', '251.88599'),
(1311265, '47', '-17.994634481277057', '-70.2525858598714', '248.16766'),
(1311266, '47', '-17.99480040635416', '-70.25290651032793', '239.33278'),
(1311267, '47', '-17.99503629004073', '-70.25332541326736', '237.56964'),
(1311268, '47', '-17.995312771602837', '-70.25375007993667', '235.79625'),
(1311269, '47', '-17.995529733287206', '-70.25405797181092', '233.20116'),
(1311270, '47', '-17.995753744401277', '-70.2543884436231', '234.04245'),
(1311271, '47', '-17.996054580306364', '-70.25481211024463', '232.71687'),
(1311272, '47', '-17.996321496099995', '-70.25517555139986', '232.6639'),
(1311273, '47', '-17.996507528842802', '-70.25543659150118', '233.33167'),
(1311274, '47', '-17.9966249648592', '-70.25560637025086', '232.78651'),
(1311275, '47', '-17.996863091303155', '-70.25592752482356', '232.02748'),
(1311276, '47', '-17.99720020292397', '-70.25635323970181', '233.50185'),
(1311277, '47', '-17.99751347115853', '-70.25678969392433', '233.62364'),
(1311278, '47', '-17.997754414852576', '-70.25712333510633', '233.87633'),
(1311279, '47', '-17.997780708069747', '-70.25716307330562', '233.4754'),
(1311280, '47', '-17.997780708069747', '-70.25716307330562', '233.4754'),
(1311281, '47', '-17.997780708069747', '-70.25716307330562', '233.4754'),
(1311282, '47', '-17.997780708069747', '-70.25716307330562', '233.4754'),
(1311283, '47', '-17.997780708069747', '-70.25716307330562', '233.4754'),
(1311284, '47', '-17.997780708069747', '-70.25716307330562', '233.4754'),
(1311285, '47', '-17.997780708069747', '-70.25716307330562', '233.4754'),
(1311286, '47', '-17.997780708069747', '-70.25716307330562', '233.4754'),
(1311287, '47', '-17.997780708069747', '-70.25716307330562', '233.4754'),
(1311288, '47', '-17.997867001228215', '-70.25724959202262', '216.54578'),
(1311289, '47', '-17.998090013283637', '-70.25728215013163', '148.73268'),
(1311290, '47', '-17.99834705857795', '-70.25702404155246', '138.04326'),
(1311291, '47', '-17.998676560563293', '-70.2567474243425', '144.09927'),
(1311292, '47', '-17.998813921018517', '-70.25664606820067', '143.6553'),
(1311293, '47', '-17.999145273042878', '-70.25639604395379', '151.6477'),
(1311294, '47', '-17.999648090248158', '-70.25622168346236', '168.45891'),
(1311295, '47', '-18.00016361353642', '-70.2560878414853', '164.26001'),
(1311296, '47', '-18.000630599329327', '-70.25587524172802', '152.99403'),
(1311297, '47', '-18.0010025011046', '-70.25567353383182', '155.91548'),
(1311298, '47', '-18.001224502067096', '-70.25575748543291', '220.34435'),
(1311299, '47', '-18.001504482465432', '-70.25601996165747', '221.72758'),
(1311300, '47', '-18.001779537738614', '-70.25627044230835', '221.7176'),
(1311301, '47', '-18.001968278733216', '-70.25643051080117', '221.62338'),
(1311302, '47', '-18.002253619092212', '-70.25669368313694', '220.17152'),
(1311303, '47', '-18.002507045487945', '-70.25683947600092', '156.77718'),
(1311304, '47', '-18.002776870442034', '-70.2565961693184', '131.59163'),
(1311305, '47', '-18.002917503938928', '-70.25644729330281', '140.5462'),
(1311306, '47', '-18.002917503938928', '-70.25644729330281', '140.5462'),
(1311307, '47', '-18.002917503938928', '-70.25644729330281', '140.5462'),
(1311308, '47', '-18.002917503938928', '-70.25644729330281', '140.5462'),
(1311309, '47', '-18.00294486146996', '-70.25642222453439', '139.15854'),
(1311310, '47', '-18.00301570392385', '-70.25636381011074', '156.86763'),
(1311311, '47', '-18.00301570392385', '-70.25636381011074', '156.86763'),
(1311312, '47', '-18.00306645802739', '-70.25637537585776', '211.18243'),
(1311313, '47', '-18.00322255034831', '-70.25657891349937', '229.95468'),
(1311314, '47', '-18.003307372882226', '-70.25663207296375', '171.44751'),
(1311315, '47', '-18.003360494467337', '-70.25656063985483', '101.340935'),
(1311316, '47', '-18.00348555302317', '-70.25625918103252', '120.222984'),
(1311317, '47', '-18.003701972859183', '-70.25589859998425', '125.22869'),
(1311318, '47', '-18.003976546256204', '-70.2555068939108', '127.09175'),
(1311319, '47', '-18.004332078490492', '-70.25511359053507', '137.66136'),
(1311320, '47', '-18.004748917813902', '-70.25473479086828', '137.87068'),
(1311321, '47', '-18.005166016089007', '-70.25431895845486', '137.50319'),
(1311322, '47', '-18.00562897327822', '-70.25389418654333', '139.47174'),
(1311323, '47', '-18.006097508300783', '-70.2534890151289', '139.80052'),
(1311324, '47', '-18.00634417086232', '-70.25333297854118', '197.28459'),
(1311325, '47', '-18.00654658565283', '-70.25358626200814', '233.33961'),
(1311326, '47', '-18.006734145639594', '-70.253892921052', '236.88501'),
(1311327, '47', '-18.006913906548256', '-70.25412723170687', '229.09631'),
(1311328, '47', '-18.00715609206458', '-70.25446906482267', '234.27284'),
(1311329, '47', '-18.007447420222118', '-70.2548618439411', '233.40852'),
(1311330, '47', '-18.00762243223346', '-70.255099658263', '233.34048'),
(1311331, '47', '-18.007700724974217', '-70.2552014644232', '230.0244'),
(1311332, '47', '-18.00778920560935', '-70.25523416066245', '160.31506'),
(1311333, '47', '-18.008047395620583', '-70.25505548256238', '144.20702'),
(1311334, '47', '-18.00841345475383', '-70.25477702711294', '145.9203'),
(1311335, '47', '-18.00875624506217', '-70.2545000095285', '145.03352'),
(1311336, '47', '-18.009006082266026', '-70.2543139108565', '142.58154'),
(1311337, '47', '-18.00917893509086', '-70.25417940206877', '134.28911'),
(1311338, '47', '-18.009373766812786', '-70.2539613471257', '141.56473'),
(1311339, '47', '-18.009516891157322', '-70.25384262585031', '147.05354'),
(1311340, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(1311341, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(1311342, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(1311343, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(1311344, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(1311345, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(1311346, '47', '-18.009555867482355', '-70.25381441537927', '143.59052'),
(1311347, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311348, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311349, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311350, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311351, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311352, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311353, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311354, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311355, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311356, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311357, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311358, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311359, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311360, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311361, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311362, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311363, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311364, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311365, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311366, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311367, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311368, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311369, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311370, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311371, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311372, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311373, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311374, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311375, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311376, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311377, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311378, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311379, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311380, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311381, '72', '-18.00971717', '-70.2538508', '0.0'),
(1311382, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311383, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311384, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311385, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311386, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311387, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311388, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311389, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311390, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311391, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311392, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311393, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311394, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311395, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311396, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311397, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311398, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311399, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311400, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311401, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311402, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311403, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311404, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311405, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311406, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311407, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311408, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311409, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311410, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311411, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311412, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311413, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311414, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311415, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311416, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311417, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311418, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311419, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311420, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311421, '75', '-18.0096603', '-70.2538545', '0.0'),
(1311422, '76', '-18.0096588', '-70.2538858', '0.0'),
(1311423, '76', '-18.0096588', '-70.2538858', '0.0'),
(1311424, '76', '-18.0096588', '-70.2538858', '0.0'),
(1311425, '76', '-18.0096588', '-70.2538858', '0.0'),
(1311426, '78', '-18.0096588', '-70.2538858', '0.0'),
(1311427, '78', '-18.0096588', '-70.2538858', '0.0'),
(1311428, '78', '-18.0096588', '-70.2538858', '0.0'),
(1311429, '79', '-18.0096511', '-70.2538754', '0.0'),
(1311430, '82', '-18.0098074393391', '-70.25396647757692', '11.405862'),
(1311431, '82', '-18.0097140469231', '-70.2539663681761', '11.442668'),
(1311432, '82', '-18.0098074393391', '-70.25396647757692', '18.838163'),
(1311433, '82', '-18.009738675912743', '-70.25394612846029', '13.048862'),
(1311434, '83', '-18.009756666666664', '-70.25372999999999', '145.11'),
(1311435, '83', '-18.009755', '-70.25372833333333', '70.7'),
(1311436, '83', '-18.009751666666666', '-70.25373333333333', '170.04'),
(1311437, '83', '-18.00975', '-70.25373333333333', '253.29'),
(1311438, '83', '-18.00975', '-70.25373333333333', '253.29'),
(1311439, '83', '-18.00975', '-70.25373333333333', '253.29'),
(1311440, '83', '-18.00975', '-70.25373333333333', '253.29'),
(1311441, '83', '-18.009745000000002', '-70.25379', '69.6'),
(1311442, '83', '-18.009731666666667', '-70.25376', '327.01'),
(1311443, '83', '-18.009733333333333', '-70.25377', '311.34'),
(1311444, '83', '-18.009738333333335', '-70.25378166666665', '297.67'),
(1311445, '83', '-18.009738333333335', '-70.25377666666667', '246.85'),
(1311446, '83', '-18.009733333333333', '-70.25376666666666', '121.36'),
(1311447, '83', '-18.009815', '-70.25367333333334', '147.46'),
(1311448, '83', '-18.01001', '-70.25354166666666', '146.84'),
(1311449, '83', '-18.010243333333335', '-70.253385', '146.03'),
(1311450, '83', '-18.0105', '-70.25320333333335', '143.9'),
(1311451, '83', '-18.010749999999998', '-70.25299666666666', '144.53'),
(1311452, '83', '-18.011016666666666', '-70.252815', '144.37'),
(1311453, '83', '-18.01124666666667', '-70.25262833333333', '144.52'),
(1311454, '83', '-18.011464999999998', '-70.25246833333334', '147.35'),
(1311455, '83', '-18.01162', '-70.25235666666667', '132.04'),
(1311456, '83', '-18.011635000000002', '-70.25234999999999', '222.02'),
(1311457, '83', '-18.011635000000002', '-70.25234999999999', '222.02'),
(1311458, '83', '-18.011635000000002', '-70.25234999999999', '222.02'),
(1311459, '83', '-18.011635000000002', '-70.25234999999999', '222.02'),
(1311460, '83', '-18.011686666666666', '-70.25230333333334', '141.01'),
(1311461, '83', '-18.011845', '-70.25217833333333', '145.33'),
(1311462, '83', '-18.012025', '-70.25205833333332', '152.21'),
(1311463, '83', '-18.012196666666668', '-70.25191166666667', '138.07'),
(1311464, '83', '-18.012361666666667', '-70.25179333333334', '148.47'),
(1311465, '83', '-18.012506666666663', '-70.25171166666668', '146.01'),
(1311466, '83', '-18.01251166666667', '-70.25169666666667', '170.86'),
(1311467, '83', '-18.01251166666667', '-70.25169666666667', '110.89'),
(1311468, '83', '-18.01251166666667', '-70.25169666666667', '304.27'),
(1311469, '83', '-18.01251166666667', '-70.25169666666667', '304.27'),
(1311470, '83', '-18.012531666666668', '-70.25168000000001', '149.33'),
(1311471, '83', '-18.01255666666667', '-70.25166166666666', '41.71'),
(1311472, '83', '-18.012561666666667', '-70.25166333333333', '65.12'),
(1311473, '83', '-18.01256', '-70.25166666666668', '283.54'),
(1311474, '83', '-18.01256', '-70.25166666666668', '283.54'),
(1311475, '83', '-18.012603333333335', '-70.25163166666667', '95.12'),
(1311476, '83', '-18.012603333333335', '-70.25162', '149.99'),
(1311477, '83', '-18.01267', '-70.25157833333333', '154.23'),
(1311478, '83', '-18.012731666666664', '-70.25153666666667', '145.65'),
(1311479, '83', '-18.012745', '-70.25153', '157.23'),
(1311480, '83', '-18.01274833333333', '-70.25152833333333', '45.39'),
(1311481, '83', '-18.01274833333333', '-70.25152833333333', '45.39'),
(1311482, '83', '-18.012783333333335', '-70.25150666666667', '150.76'),
(1311483, '83', '-18.012783333333335', '-70.25150333333333', '326.23'),
(1311484, '83', '-18.012785', '-70.25150166666667', '152.01'),
(1311485, '83', '-18.012785', '-70.25150166666667', '152.01'),
(1311486, '83', '-18.012800000000002', '-70.251495', '147.85'),
(1311487, '83', '-18.012801666666668', '-70.25149333333334', '148.54'),
(1311488, '83', '-18.012801666666668', '-70.25149333333334', '148.54'),
(1311489, '83', '-18.012801666666668', '-70.25149333333334', '148.54'),
(1311490, '83', '-18.012801666666668', '-70.25149333333334', '148.54'),
(1311491, '83', '-18.012801666666668', '-70.25149333333334', '148.54'),
(1311492, '83', '-18.012858333333334', '-70.25145', '148.08'),
(1311493, '83', '-18.012911666666664', '-70.25141666666667', '142.64'),
(1311494, '83', '-18.01300666666667', '-70.25134', '151.16'),
(1311495, '83', '-18.013143333333336', '-70.25123333333333', '142.51'),
(1311496, '83', '-18.013311666666667', '-70.25108333333333', '147.66'),
(1311497, '83', '-18.013465', '-70.25098333333334', '145.46'),
(1311498, '83', '-18.013555', '-70.250975', '176.8'),
(1311499, '83', '-18.013665', '-70.251065', '240.12'),
(1311500, '83', '-18.013823333333335', '-70.25131166666667', '238.09'),
(1311501, '83', '-18.01398', '-70.25158333333334', '238.12'),
(1311502, '83', '-18.014115', '-70.251825', '238.98'),
(1311503, '83', '-18.014239999999997', '-70.25204166666667', '238.44'),
(1311504, '83', '-18.014378333333333', '-70.25228', '241.16'),
(1311505, '83', '-18.014431666666667', '-70.25237666666666', '195.06'),
(1311506, '83', '-18.01443', '-70.25237666666666', '228.86'),
(1311507, '83', '-18.01443', '-70.25237666666666', '228.86'),
(1311508, '83', '-18.014433333333333', '-70.252385', '238.84'),
(1311509, '83', '-18.014466666666667', '-70.25244500000001', '233.65'),
(1311510, '83', '-18.014473333333335', '-70.25245833333334', '37.5'),
(1311511, '83', '-18.014476666666667', '-70.25246166666668', '228.5'),
(1311512, '83', '-18.014566666666667', '-70.25258833333334', '232.0'),
(1311513, '83', '-18.01473', '-70.25280833333333', '234.18'),
(1311514, '83', '-18.014915', '-70.25309166666666', '235.63'),
(1311515, '83', '-18.015066666666666', '-70.25335166666667', '252.2'),
(1311516, '83', '-18.01494166666667', '-70.25343500000001', '351.16'),
(1311517, '83', '-18.014831666666666', '-70.25344666666666', '331.9'),
(1311518, '83', '-18.01482', '-70.25345', '111.91'),
(1311519, '83', '-18.014821666666666', '-70.25345166666666', '330.35'),
(1311520, '83', '-18.014821666666666', '-70.25345166666666', '330.35'),
(1311521, '83', '-18.014821666666666', '-70.25345166666666', '330.35'),
(1311522, '83', '-18.014821666666666', '-70.25345166666666', '330.35'),
(1311523, '83', '-18.014821666666666', '-70.25345166666666', '330.35'),
(1311524, '83', '-18.014799999999997', '-70.25346166666667', '330.88'),
(1311525, '83', '-18.014648333333334', '-70.25353166666667', '338.94'),
(1311526, '83', '-18.014368333333334', '-70.25363833333334', '343.55'),
(1311527, '83', '-18.01419333333333', '-70.25347666666667', '61.0'),
(1311528, '83', '-18.01405833333333', '-70.253215', '57.12'),
(1311529, '83', '-18.013933333333334', '-70.25301333333333', '58.96'),
(1311530, '83', '-18.013921666666665', '-70.25298333333333', '8.31'),
(1311531, '83', '-18.013921666666665', '-70.25298333333333', '8.31'),
(1311532, '83', '-18.01387', '-70.25293166666667', '56.99'),
(1311533, '83', '-18.013851666666667', '-70.25289333333333', '90.99'),
(1311534, '83', '-18.01386', '-70.25288166666665', '218.18'),
(1311535, '83', '-18.01386', '-70.25287666666667', '278.02'),
(1311536, '83', '-18.01386', '-70.25287666666667', '278.02'),
(1311537, '83', '-18.01386', '-70.25287666666667', '278.02'),
(1311538, '83', '-18.01386', '-70.25287666666667', '278.02'),
(1311539, '83', '-18.013856666666666', '-70.25286166666666', '55.8'),
(1311540, '83', '-18.013775', '-70.25273999999999', '56.37'),
(1311541, '83', '-18.013693333333332', '-70.2526', '55.49'),
(1311542, '83', '-18.013638333333333', '-70.252505', '58.39'),
(1311543, '83', '-18.013615', '-70.25247666666668', '13.79'),
(1311544, '83', '-18.01361166666667', '-70.252475', '70.77'),
(1311545, '83', '-18.01361166666667', '-70.252475', '0.18'),
(1311546, '83', '-18.01361166666667', '-70.252475', '0.18'),
(1311547, '83', '-18.013591666666667', '-70.25244500000001', '69.4'),
(1311548, '83', '-18.013583333333333', '-70.25242333333334', '66.1'),
(1311549, '83', '-18.01358', '-70.25241', '69.99'),
(1311550, '83', '-18.013511666666666', '-70.25233833333333', '57.82'),
(1311551, '83', '-18.013495', '-70.25231666666667', '77.37'),
(1311552, '83', '-18.013436666666667', '-70.252255', '51.96'),
(1311553, '83', '-18.01335', '-70.25210333333332', '54.2'),
(1311554, '83', '-18.013271666666665', '-70.251955', '53.05'),
(1311555, '83', '-18.013183333333334', '-70.25180999999999', '55.23'),
(1311556, '83', '-18.01314', '-70.25174', '56.25'),
(1311557, '83', '-18.013138333333334', '-70.25173833333334', '354.4'),
(1311558, '83', '-18.013138333333334', '-70.25173666666666', '89.78'),
(1311559, '83', '-18.013128333333334', '-70.25172166666667', '51.09'),
(1311560, '83', '-18.013095', '-70.25167333333334', '50.17'),
(1311561, '83', '-18.013061666666665', '-70.251595', '57.52'),
(1311562, '83', '-18.013048333333334', '-70.25155333333333', '135.48'),
(1311563, '83', '-18.013046666666664', '-70.25154166666667', '306.32'),
(1311564, '83', '-18.013044999999998', '-70.25154166666667', '240.69'),
(1311565, '83', '-18.013044999999998', '-70.25154166666667', '240.69'),
(1311566, '83', '-18.013044999999998', '-70.25154166666667', '240.69'),
(1311567, '83', '-18.012978333333333', '-70.25143', '61.62'),
(1311568, '83', '-18.012905', '-70.251275', '56.09'),
(1311569, '83', '-18.012855000000002', '-70.25113833333333', '84.9'),
(1311570, '83', '-18.012823333333333', '-70.25109833333333', '63.12'),
(1311571, '83', '-18.01279', '-70.25108833333334', '43.73'),
(1311572, '83', '-18.01279', '-70.25108333333333', '109.89'),
(1311573, '83', '-18.01279', '-70.25108333333333', '109.89'),
(1311574, '83', '-18.01279', '-70.25108333333333', '109.89'),
(1311575, '83', '-18.012786666666667', '-70.251055', '79.41'),
(1311576, '83', '-18.012708333333332', '-70.25094666666668', '46.31'),
(1311577, '83', '-18.012583333333335', '-70.250805', '74.91'),
(1311578, '83', '-18.012573333333332', '-70.250805', '276.02'),
(1311579, '83', '-18.012573333333332', '-70.250805', '276.02'),
(1311580, '83', '-18.012573333333332', '-70.250805', '276.02'),
(1311581, '83', '-18.012573333333332', '-70.250805', '276.02'),
(1311582, '83', '-18.012573333333332', '-70.250805', '276.02'),
(1311583, '83', '-18.012573333333332', '-70.250805', '276.02'),
(1311584, '83', '-18.012573333333332', '-70.250805', '276.02'),
(1311585, '83', '-18.012526666666666', '-70.25074500000001', '66.81'),
(1311586, '83', '-18.01245833333333', '-70.25062', '57.4'),
(1311587, '83', '-18.012418333333336', '-70.25053833333332', '93.02'),
(1311588, '83', '-18.01239', '-70.25047166666667', '62.3'),
(1311589, '83', '-18.012308333333333', '-70.25033166666667', '57.11'),
(1311590, '83', '-18.012263333333333', '-70.25024499999999', '57.69'),
(1311591, '83', '-18.012128333333333', '-70.25004833333334', '61.29'),
(1311592, '83', '-18.01201', '-70.24985833333334', '55.08'),
(1311593, '83', '-18.01187', '-70.24961666666667', '56.41'),
(1311594, '83', '-18.011734999999998', '-70.2494', '62.94'),
(1311595, '83', '-18.011605', '-70.24919166666668', '58.06'),
(1311596, '83', '-18.01149', '-70.24898', '58.96'),
(1311597, '83', '-18.011405', '-70.24884166666668', '56.71'),
(1311598, '96', '-17.99656575487138', '-70.25650432718334', '0.0'),
(1311599, '96', '-17.99656575487138', '-70.25650432718334', '0.0'),
(1311600, '96', '-17.99656575487138', '-70.25650432718334', '0.0'),
(1311601, '96', '-17.99656575487138', '-70.25650432718334', '0.0'),
(1311602, '96', '-17.99656575487138', '-70.25650432718334', '0.0'),
(1311603, '96', '-17.99656575487138', '-70.25650432718334', '0.0'),
(1311604, '96', '-17.99656575487138', '-70.25650432718334', '0.0'),
(1311605, '96', '-17.99656575487138', '-70.25650432718334', '0.0'),
(1311606, '96', '-17.99656575487138', '-70.25650432718334', '0.0'),
(1311607, '97', '-17.997672142119455', '-70.25785273211665', '230.04294'),
(1311608, '97', '-18.021900134228265', '-70.24312432102109', '141.59111'),
(1311609, '97', '-18.022189827272808', '-70.24297002595664', '0.0'),
(1311610, '97', '-18.02231701596394', '-70.24287859855205', '135.75041'),
(1311611, '97', '-18.022340578102277', '-70.24287358163764', '141.18082'),
(1311612, '97', '-18.022511642162648', '-70.2427378514145', '138.68286'),
(1311613, '98', '-18.02275090708329', '-70.24247929559274', '140.1586');
INSERT INTO `pedidos_recorrido_aceptado` (`id`, `id_pedido`, `lat`, `lng`, `bearing`) VALUES
(1311614, '98', '-18.03613317378044', '-70.24674035546678', '182.42169'),
(1311615, '98', '-18.03659249464286', '-70.24684458814424', '0.0'),
(1311616, '99', '-18.0055889', '-70.2477153', '0.0'),
(1311617, '106', '-18.00938', '-70.253665', '339.93'),
(1311618, '106', '-18.010383333333333', '-70.25399166666666', '339.93'),
(1311619, '106', '-18.010748333333332', '-70.254195', '339.93'),
(1311620, '106', '-18.010328333333334', '-70.25435833333333', '339.93'),
(1311621, '106', '-18.010328333333334', '-70.25435833333333', '339.93'),
(1311622, '106', '-18.010328333333334', '-70.25435833333333', '339.93'),
(1311623, '106', '-18.010328333333334', '-70.25435833333333', '339.93'),
(1311624, '111', '-18.0097695', '-70.2447793', '0.0'),
(1311625, '111', '-18.0097695', '-70.2447793', '0.0'),
(1311626, '111', '-17.997823', '-70.247152', '0.0'),
(1311627, '111', '-17.997823', '-70.247152', '0.0'),
(1311628, '111', '-17.997823', '-70.247152', '0.0'),
(1311629, '111', '-17.997823', '-70.247152', '0.0'),
(1311630, '113', '-8.126245', '-79.02652166666667', '308.38'),
(1311631, '113', '-8.126273333333334', '-79.02647833333334', '308.38'),
(1311632, '113', '-8.126301666666667', '-79.02648500000001', '308.38'),
(1311633, '113', '-8.12632', '-79.02651166666666', '116.52'),
(1311634, '113', '-8.12634', '-79.02651166666666', '131.27'),
(1311635, '113', '-8.126371666666667', '-79.02655833333333', '131.27'),
(1311636, '113', '-8.126471666666667', '-79.02656', '131.27'),
(1311637, '113', '-8.126478333333333', '-79.02659833333334', '99.54'),
(1311638, '113', '-8.126465', '-79.02662166666667', '156.6'),
(1311639, '113', '-8.126353333333332', '-79.02657333333333', '156.6'),
(1311640, '113', '-8.126285', '-79.02647499999999', '156.6'),
(1311641, '113', '-8.12633', '-79.02647833333334', '193.31'),
(1311642, '113', '-8.126368333333334', '-79.02651166666666', '149.99'),
(1311643, '113', '-8.126363333333334', '-79.02659999999999', '236.36'),
(1311644, '113', '-8.126455', '-79.02670833333333', '236.36'),
(1311645, '113', '-8.126573333333333', '-79.02683', '236.36'),
(1311646, '113', '-8.126536666666668', '-79.026775', '236.36'),
(1311647, '113', '-8.126506666666668', '-79.02675333333333', '282.65'),
(1311648, '113', '-8.12642', '-79.02673166666666', '67.12'),
(1311649, '113', '-8.126366666666666', '-79.02662000000001', '67.12'),
(1311650, '113', '-8.126433333333333', '-79.02648333333333', '136.07'),
(1311651, '113', '-8.126416666666668', '-79.02654666666668', '264.71'),
(1311652, '113', '-8.126421666666666', '-79.02656666666667', '63.67'),
(1311653, '113', '-8.126373333333333', '-79.02650333333332', '63.67'),
(1311654, '113', '-8.126333333333333', '-79.02652333333334', '290.05'),
(1311655, '114', '-8.126235000000001', '-79.02647833333334', '9.83'),
(1311656, '114', '-8.126268333333334', '-79.02635166666667', '93.84'),
(1311657, '114', '-8.126280000000001', '-79.02646666666666', '93.84'),
(1311658, '114', '-8.126306666666666', '-79.02655', '93.84'),
(1311659, '114', '-8.12633', '-79.02658333333333', '93.84'),
(1311660, '115', '-8.126295', '-79.02683666666667', '30.24'),
(1311661, '115', '-8.126358333333334', '-79.026855', '30.24'),
(1311662, '115', '-8.126343333333333', '-79.02689833333334', '30.24'),
(1311663, '115', '-8.126335000000001', '-79.02695666666666', '30.24'),
(1311664, '117', '-8.126444999999999', '-79.02670333333334', '122.35'),
(1311665, '117', '-8.126448333333334', '-79.026675', '122.35'),
(1311666, '117', '-8.126513333333332', '-79.02667333333333', '122.35'),
(1311667, '117', '-8.126516666666667', '-79.02664499999999', '122.35'),
(1311668, '117', '-8.126513333333332', '-79.02664499999999', '168.91'),
(1311669, '117', '-8.126485', '-79.02662666666667', '168.91'),
(1311670, '119', '-8.126393333333334', '-79.02669166666666', '168.91'),
(1311671, '119', '-8.126380000000001', '-79.02670833333333', '168.91'),
(1311672, '119', '-8.126336666666667', '-79.02670833333333', '168.91'),
(1311673, '119', '-8.126323333333334', '-79.02669333333333', '168.91'),
(1311674, '119', '-8.12633', '-79.02669666666667', '168.91'),
(1311675, '119', '-8.126336666666667', '-79.026675', '168.91'),
(1311676, '119', '-8.126344999999999', '-79.02666333333333', '168.91'),
(1311677, '119', '-8.126316666666666', '-79.02670499999999', '168.91'),
(1311678, '119', '-8.126321666666666', '-79.02672666666668', '168.91'),
(1311679, '119', '-8.126266666666668', '-79.02674666666667', '168.91'),
(1311680, '119', '-8.126241666666665', '-79.02675500000001', '168.91'),
(1311681, '119', '-8.126263333333332', '-79.02673499999999', '168.91'),
(1311682, '119', '-8.126203333333333', '-79.026765', '168.91'),
(1311683, '119', '-8.126283333333333', '-79.02680000000001', '168.91'),
(1311684, '119', '-8.126299999999999', '-79.02678166666666', '168.91'),
(1311685, '119', '-8.126281666666667', '-79.02678833333333', '168.91'),
(1311686, '119', '-8.126253333333333', '-79.02683999999999', '168.91'),
(1311687, '119', '-8.126211666666668', '-79.02685', '168.91'),
(1311688, '119', '-8.126178333333334', '-79.02680000000001', '168.91'),
(1311689, '119', '-8.126143333333333', '-79.02677333333332', '168.91'),
(1311690, '119', '-8.126196666666667', '-79.02675500000001', '168.91'),
(1311691, '119', '-8.126299999999999', '-79.02671000000001', '168.91'),
(1311692, '119', '-8.126335000000001', '-79.02670499999999', '168.91'),
(1311693, '119', '-8.126366666666666', '-79.02671666666667', '168.91'),
(1311694, '119', '-8.126335000000001', '-79.02670333333334', '168.91'),
(1311695, '119', '-8.12633', '-79.02671166666667', '168.91'),
(1311696, '119', '-8.126391666666667', '-79.02670499999999', '168.91'),
(1311697, '119', '-8.126408333333334', '-79.02669999999999', '168.91'),
(1311698, '119', '-8.126368333333334', '-79.02670166666667', '168.91'),
(1311699, '119', '-8.126353333333332', '-79.02670333333334', '168.91'),
(1311700, '119', '-8.126353333333332', '-79.02670333333334', '168.91'),
(1311701, '119', '-8.126311666666668', '-79.02671833333333', '315.67'),
(1311702, '119', '-8.12632', '-79.02671166666667', '315.67'),
(1311703, '119', '-8.126335000000001', '-79.02670833333333', '315.67'),
(1311704, '119', '-8.126341666666667', '-79.02668666666666', '315.67'),
(1311705, '119', '-8.126338333333333', '-79.02668166666668', '315.67'),
(1311706, '120', '-8.126298333333333', '-79.02674', '20.03'),
(1311707, '120', '-8.126303333333334', '-79.02674999999999', '20.03'),
(1311708, '120', '-8.126341666666667', '-79.02669333333333', '20.03'),
(1311709, '120', '-8.126346666666667', '-79.026695', '20.03'),
(1311710, '120', '-8.126335000000001', '-79.02669999999999', '20.03'),
(1311711, '121', '-8.126341666666667', '-79.02666500000001', '166.33'),
(1311712, '121', '-8.126381666666667', '-79.02667333333333', '166.33'),
(1311713, '121', '-8.126416666666668', '-79.02665833333334', '166.33'),
(1311714, '121', '-8.126421666666666', '-79.02666333333333', '166.33'),
(1311715, '121', '-8.126471666666667', '-79.02663333333334', '166.33'),
(1311716, '121', '-8.126463333333334', '-79.02663166666666', '166.33'),
(1311717, '121', '-8.126463333333334', '-79.02663166666666', '166.33'),
(1311718, '121', '-8.126443333333333', '-79.02665833333334', '45.16'),
(1311719, '121', '-8.126416666666668', '-79.02665166666667', '45.16'),
(1311720, '121', '-8.126331666666665', '-79.02665', '45.16'),
(1311721, '121', '-8.126371666666667', '-79.02663666666668', '45.16'),
(1311722, '121', '-8.126366666666666', '-79.02664666666666', '153.87'),
(1311723, '121', '-8.126376666666665', '-79.026635', '168.75'),
(1311724, '121', '-8.126378333333333', '-79.02657666666667', '168.75'),
(1311725, '121', '-8.126399999999999', '-79.02659999999999', '293.56'),
(1311726, '121', '-8.126355', '-79.02662833333333', '324.42'),
(1311727, '121', '-8.126336666666667', '-79.02662166666667', '324.42'),
(1311728, '121', '-8.126355', '-79.02662166666667', '324.42'),
(1311729, '121', '-8.126338333333333', '-79.02663166666666', '324.42'),
(1311730, '121', '-8.126341666666667', '-79.02665', '324.42'),
(1311731, '121', '-8.126328333333333', '-79.02664666666666', '324.42'),
(1311732, '121', '-8.126321666666666', '-79.02665166666667', '324.42'),
(1311733, '121', '-8.126305', '-79.02665499999999', '324.42'),
(1311734, '121', '-8.126285', '-79.02664833333333', '324.42'),
(1311735, '121', '-8.126291666666667', '-79.02665', '324.42'),
(1311736, '121', '-8.126311666666668', '-79.02665', '324.42'),
(1311737, '121', '-8.126341666666667', '-79.02664499999999', '324.42'),
(1311738, '121', '-8.126351666666666', '-79.02666500000001', '324.42'),
(1311739, '121', '-8.126351666666666', '-79.02665666666667', '324.42'),
(1311740, '121', '-8.126378333333333', '-79.02668666666666', '212.62'),
(1311741, '121', '-8.12641', '-79.02665666666667', '212.62'),
(1311742, '121', '-8.126465', '-79.02662666666667', '212.62'),
(1311743, '121', '-8.126461666666668', '-79.02668666666666', '212.62'),
(1311744, '121', '-8.126444999999999', '-79.02668833333334', '212.62'),
(1311745, '121', '-8.126418333333334', '-79.02666833333333', '212.62'),
(1311746, '121', '-8.126421666666666', '-79.02666166666667', '212.62'),
(1311747, '121', '-8.126433333333333', '-79.02667166666667', '212.62'),
(1311748, '121', '-8.126443333333333', '-79.02666500000001', '212.62'),
(1311749, '121', '-8.126411666666666', '-79.02667666666666', '212.62'),
(1311750, '121', '-8.126383333333333', '-79.02667666666666', '212.62'),
(1311751, '121', '-8.126331666666665', '-79.026715', '212.62'),
(1311752, '122', '-8.126305', '-79.02679833333333', '35.45'),
(1311753, '122', '-8.126303333333334', '-79.02675166666667', '35.45'),
(1311754, '122', '-8.126299999999999', '-79.02676166666667', '35.45'),
(1311755, '122', '-8.126403333333334', '-79.02667666666666', '35.45'),
(1311756, '122', '-8.126399999999999', '-79.026685', '35.45'),
(1311757, '124', '-8.126275', '-79.026675', '315.08'),
(1311758, '124', '-8.126306666666666', '-79.026725', '148.9'),
(1311759, '124', '-8.126306666666666', '-79.02672833333332', '212.46'),
(1311760, '124', '-8.126306666666666', '-79.02672833333332', '204.89'),
(1311761, '128', '-8.1220982', '-79.0314572', '0.0'),
(1311762, '128', '-8.1220982', '-79.0314572', '0.0'),
(1311763, '128', '-8.1220982', '-79.0314572', '0.0'),
(1311764, '129', '-8.1220982', '-79.0314572', '0.0'),
(1311765, '129', '-8.1220982', '-79.0314572', '0.0'),
(1311766, '129', '-8.1220982', '-79.0314572', '0.0'),
(1311767, '130', '-8.1220982', '-79.0314572', '0.0'),
(1311768, '130', '-8.1220982', '-79.0314572', '0.0'),
(1311769, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311770, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311771, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311772, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311773, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311774, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311775, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311776, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311777, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311778, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311779, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311780, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311781, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311782, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311783, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311784, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311785, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311786, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311787, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311788, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311789, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311790, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311791, '131', '-8.1220982', '-79.0314572', '0.0'),
(1311792, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311793, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311794, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311795, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311796, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311797, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311798, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311799, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311800, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311801, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311802, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311803, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311804, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311805, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311806, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311807, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311808, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311809, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311810, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311811, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311812, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311813, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311814, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311815, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311816, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311817, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311818, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311819, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311820, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311821, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311822, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311823, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311824, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311825, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311826, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311827, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311828, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311829, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311830, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311831, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311832, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311833, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311834, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311835, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311836, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311837, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311838, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311839, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311840, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311841, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311842, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311843, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311844, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311845, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311846, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311847, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311848, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311849, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311850, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311851, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311852, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311853, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311854, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311855, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311856, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311857, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311858, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311859, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311860, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311861, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311862, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311863, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311864, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311865, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311866, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311867, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311868, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311869, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311870, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311871, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311872, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311873, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311874, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311875, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311876, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311877, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311878, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311879, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311880, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311881, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311882, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311883, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311884, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311885, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311886, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311887, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311888, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311889, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311890, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311891, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311892, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311893, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311894, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311895, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311896, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311897, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311898, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311899, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311900, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311901, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311902, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311903, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311904, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311905, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311906, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311907, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311908, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311909, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311910, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311911, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311912, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311913, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311914, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311915, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311916, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311917, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311918, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311919, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311920, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311921, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311922, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311923, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311924, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311925, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311926, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311927, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311928, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311929, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311930, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311931, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311932, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311933, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311934, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311935, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311936, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311937, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311938, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311939, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311940, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311941, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311942, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311943, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311944, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311945, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311946, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311947, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311948, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311949, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311950, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311951, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311952, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311953, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311954, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311955, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311956, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311957, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311958, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311959, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311960, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311961, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311962, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311963, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311964, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311965, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311966, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311967, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311968, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311969, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311970, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311971, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311972, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311973, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311974, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311975, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311976, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311977, '134', '-8.1220982', '-79.0314572', '0.0'),
(1311978, '143', '-8.1263121', '-79.0267919', '0.0'),
(1311979, '143', '-8.1263121', '-79.0267919', '0.0'),
(1311980, '143', '-8.1263121', '-79.0267919', '0.0'),
(1311981, '152', '-8.126400467008352', '-79.02677136473358', '216.7'),
(1311982, '152', '-8.126410692930222', '-79.02675300836563', '0.0'),
(1311983, '152', '-8.126405328512192', '-79.02676717378199', '233.8'),
(1311984, '152', '-8.126376788131893', '-79.02674839831889', '341.6'),
(1311985, '152', '-8.126396611332893', '-79.02672509662807', '192.0'),
(1311986, '152', '-8.126420206390321', '-79.0267267730087', '223.2'),
(1311987, '152', '-8.126429887488484', '-79.02671269141138', '160.4'),
(1311988, '152', '-8.126428504474461', '-79.02669919654727', '117.9'),
(1311989, '152', '-8.126449082046747', '-79.02665904723108', '132.1'),
(1311990, '153', '-8.126467857509851', '-79.02667614631355', '254.8'),
(1311991, '153', '-8.12645260244608', '-79.02667790651321', '0.0'),
(1311992, '153', '-8.12645704485476', '-79.02665711939335', '221.5'),
(1311993, '154', '-8.12639501877129', '-79.02675409801304', '175.5'),
(1311994, '154', '-8.126456919126213', '-79.02668670751154', '99.5'),
(1311995, '154', '-8.126406711526215', '-79.02670313604176', '33.4'),
(1311996, '154', '-8.126395521685481', '-79.02669475413859', '52.3'),
(1311997, '154', '-8.126373016275465', '-79.02669575996697', '2.5'),
(1311998, '154', '-8.126368867233396', '-79.02669408358634', '0.0'),
(1311999, '160', '-8.126321719028056', '-79.02670347131789', '0.0'),
(1312000, '160', '-8.126321719028056', '-79.02670347131789', '0.0'),
(1312001, '160', '-8.126321719028056', '-79.02670347131789', '0.0'),
(1312002, '160', '-8.126321719028056', '-79.02670347131789', '0.0'),
(1312003, '160', '-8.126321719028056', '-79.02670347131789', '0.0'),
(1312004, '161', '-8.126699617132545', '-79.02688988484442', '0.0'),
(1312005, '161', '-8.126331944949925', '-79.02675342746079', '335.6'),
(1312006, '161', '-8.12633684836328', '-79.02674202807248', '0.0'),
(1312007, '161', '-8.12633684836328', '-79.02674202807248', '0.0'),
(1312008, '161', '-8.126376327127218', '-79.02676591649652', '273.1'),
(1312009, '161', '-8.126372303813696', '-79.02674345299602', '265.7'),
(1312010, '161', '-8.126352145336568', '-79.02677228674293', '294.9'),
(1312011, '161', '-8.126366352662444', '-79.026746051386', '301.7'),
(1312012, '161', '-8.126339698210359', '-79.02673792093992', '27.4'),
(1312013, '161', '-8.126313965767622', '-79.02674881741405', '349.2'),
(1312014, '165', '-8.126335465349257', '-79.02683171443641', '143.8'),
(1312015, '165', '-8.126345607452095', '-79.02682945132256', '0.0'),
(1312016, '165', '-8.126322347670794', '-79.02685585431755', '46.9'),
(1312017, '165', '-8.126322347670794', '-79.02685585431755', '46.9'),
(1312018, '165', '-8.126322347670794', '-79.02685585431755', '46.9'),
(1312019, '165', '-8.126322347670794', '-79.02685585431755', '46.9'),
(1312020, '165', '-8.126218034885824', '-79.02652435004711', '54.4'),
(1312021, '165', '-8.126235720701516', '-79.02654949575663', '342.5'),
(1312022, '169', '-8.126229769550264', '-79.02667907997966', '0.0'),
(1312023, '169', '-8.126278761774302', '-79.02664965949953', '202.6'),
(1312024, '169', '-8.126273439265788', '-79.0266387630254', '0.0'),
(1312025, '169', '-8.126278133131564', '-79.02664136141539', '201.3'),
(1312026, '169', '-8.126283581368625', '-79.0266247652471', '201.2'),
(1312027, '169', '-8.12627058941871', '-79.02664630673826', '0.0'),
(1312028, '169', '-8.12627058941871', '-79.02664630673826', '0.0'),
(1312029, '169', '-8.126279641874135', '-79.02662241831422', '185.2'),
(1312030, '169', '-8.126260698772967', '-79.02669332921505', '237.2'),
(1312031, '169', '-8.126279474236071', '-79.02666944079101', '220.7'),
(1312032, '169', '-8.126279474236071', '-79.02666944079101', '220.7'),
(1312033, '169', '-8.126279474236071', '-79.02666944079101', '220.7'),
(1312034, '169', '-8.126279474236071', '-79.02666944079101', '220.7'),
(1312035, '169', '-8.12629456166178', '-79.02694755233824', '251.2'),
(1312036, '169', '-8.12629456166178', '-79.02694755233824', '251.2'),
(1312037, '169', '-8.12629456166178', '-79.02694755233824', '251.2'),
(1312038, '169', '-8.12629456166178', '-79.02694755233824', '251.2'),
(1312039, '169', '-8.126287395134568', '-79.02722742408514', '273.5'),
(1312040, '169', '-8.126343595795333', '-79.02710286900401', '268.9'),
(1312041, '169', '-8.12636103015393', '-79.02694788761437', '0.0'),
(1312042, '169', '-8.1263686157763', '-79.02690438553691', '0.0'),
(1312043, '169', '-8.126362538896501', '-79.02687412686646', '0.0'),
(1312044, '169', '-8.126356503926218', '-79.02685350738466', '0.0'),
(1312045, '169', '-8.126356503926218', '-79.02685350738466', '0.0'),
(1312046, '169', '-8.126364215277135', '-79.026801539585', '268.3'),
(1312047, '169', '-8.126356629654765', '-79.02677102945745', '117.6'),
(1312048, '169', '-8.126371968537569', '-79.02671528980136', '104.2'),
(1312049, '169', '-8.126385002397', '-79.02671964839101', '0.0'),
(1312050, '169', '-8.126454739831388', '-79.02660087682307', '0.0'),
(1312051, '170', '-8.126343553885818', '-79.02674932032824', '0.0'),
(1312052, '170', '-8.126343553885818', '-79.02674932032824', '0.0'),
(1312053, '170', '-8.126348038204014', '-79.02672551572323', '304.8'),
(1312054, '170', '-8.126352061517537', '-79.02671637944877', '0.0'),
(1312055, '170', '-8.126350594684482', '-79.0267410222441', '298.1'),
(1312056, '172', '-8.126366855576634', '-79.02670850045979', '264.2'),
(1312057, '172', '-8.126381146721542', '-79.02669852599502', '0.0'),
(1312058, '172', '-8.126363335177302', '-79.02669592760503', '2.1'),
(1312059, '172', '-8.126344811171293', '-79.02670472860336', '11.9'),
(1312060, '172', '-8.126307050697505', '-79.02673481963575', '321.9'),
(1312061, '172', '-8.126297369599342', '-79.02673993259668', '0.0'),
(1312062, '172', '-8.126308182254434', '-79.02674370445311', '25.2'),
(1312063, '173', '-8.1263121', '-79.0267919', '0.0'),
(1312064, '173', '-8.126604352857091', '-79.02643471893242', '0.0'),
(1312065, '173', '-8.126450634825197', '-79.02657300922463', '0.0'),
(1312066, '174', '-8.126297080971035', '-79.02674319776682', '0.0'),
(1312067, '174', '-8.126282569476215', '-79.0267693702951', '306.1439'),
(1312068, '174', '-8.126359297022848', '-79.02671976453288', '308.51617'),
(1312069, '174', '-8.126356058162466', '-79.02670692206226', '308.51617'),
(1312070, '174', '-8.126356058162466', '-79.02670692206226', '308.51617'),
(1312071, '174', '-8.126356058162466', '-79.02670692206226', '308.51617'),
(1312072, '174', '-8.126356058162466', '-79.02670692206226', '308.51617'),
(1312073, '174', '-8.126356058162466', '-79.02670692206226', '308.51617'),
(1312074, '174', '-8.126356058162466', '-79.02670692206226', '308.51617'),
(1312075, '174', '-8.126356058162466', '-79.02670692206226', '308.51617'),
(1312076, '174', '-8.126356058162466', '-79.02670692206226', '308.51617'),
(1312077, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312078, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312079, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312080, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312081, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312082, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312083, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312084, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312085, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312086, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312087, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312088, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312089, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312090, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312091, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312092, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312093, '175', '-8.126323190604447', '-79.02673620755583', '289.5859'),
(1312094, '179', '-8.126347189978773', '-79.02669349878481', '282.39618'),
(1312095, '179', '-8.126347189978773', '-79.02669349878481', '282.39618'),
(1312096, '179', '-8.126347189978773', '-79.02669349878481', '282.39618'),
(1312097, '181', '-8.126347189978773', '-79.02669349878481', '282.39618'),
(1312098, '181', '-8.126347189978773', '-79.02669349878481', '282.39618'),
(1312099, '181', '-8.126347189978773', '-79.02669349878481', '282.39618'),
(1312100, '181', '-8.126358400575487', '-79.02669218453497', '282.39618'),
(1312101, '181', '-8.126358400575487', '-79.02669218453497', '282.39618'),
(1312102, '181', '-8.126358400575487', '-79.02669218453497', '282.39618'),
(1312103, '181', '-8.126358400575487', '-79.02669218453497', '282.39618'),
(1312104, '181', '-8.126358400575487', '-79.02669218453497', '282.39618'),
(1312105, '181', '-8.126358400575487', '-79.02669218453497', '282.39618'),
(1312106, '181', '-8.126358400575487', '-79.02669218453497', '282.39618'),
(1312107, '181', '-8.126358400575487', '-79.02669218453497', '282.39618'),
(1312108, '181', '-8.126358400575487', '-79.02669218453497', '282.39618'),
(1312109, '181', '-8.126380276691622', '-79.02669045938994', '282.39618'),
(1312110, '181', '-8.126393301959489', '-79.02668751425509', '282.39618'),
(1312111, '181', '-8.126393301959489', '-79.02668751425509', '282.39618'),
(1312112, '181', '-8.126393301959489', '-79.02668751425509', '282.39618'),
(1312113, '181', '-8.126393301959489', '-79.02668751425509', '282.39618'),
(1312114, '181', '-8.126393301959489', '-79.02668751425509', '282.39618'),
(1312115, '181', '-8.126393301959489', '-79.02668751425509', '282.39618'),
(1312116, '181', '-8.126393301959489', '-79.02668751425509', '282.39618'),
(1312117, '181', '-8.126393301959489', '-79.02668751425509', '282.39618'),
(1312118, '181', '-8.12642583181922', '-79.02674217670474', '282.39618'),
(1312119, '181', '-8.12642583181922', '-79.02674217670474', '282.39618'),
(1312120, '181', '-8.12642583181922', '-79.02674217670474', '282.39618'),
(1312121, '181', '-8.126399369525366', '-79.02675354726395', '282.39618'),
(1312122, '181', '-8.12642583181922', '-79.02674217670474', '282.39618'),
(1312123, '181', '-8.126392973445787', '-79.02677686467987', '282.39618'),
(1312124, '181', '-8.126376922900663', '-79.02678503446178', '312.98648'),
(1312125, '181', '-8.126376922900663', '-79.02678503446178', '312.98648'),
(1312126, '181', '-8.126376922900663', '-79.02678503446178', '312.98648'),
(1312127, '181', '-8.126376922900663', '-79.02678503446178', '312.98648'),
(1312128, '181', '-8.126376922900663', '-79.02678503446178', '312.98648'),
(1312129, '181', '-8.126376922900663', '-79.02678503446178', '312.98648'),
(1312130, '181', '-8.126376922900663', '-79.02678503446178', '312.98648'),
(1312131, '181', '-8.126376922900663', '-79.02678503446178', '312.98648'),
(1312132, '181', '-8.126376922900663', '-79.02678503446178', '312.98648'),
(1312133, '181', '-8.126376922900663', '-79.02678503446178', '312.98648'),
(1312134, '181', '-8.126396925831564', '-79.02677913787586', '312.98648'),
(1312135, '181', '-8.126376922900663', '-79.02678503446178', '341.5834'),
(1312136, '181', '-8.126375068993024', '-79.02677937953041', '341.5834'),
(1312137, '181', '-8.126346066725016', '-79.0267264903217', '341.5834'),
(1312138, '181', '-8.126376922900663', '-79.02678503446178', '341.5834'),
(1312139, '181', '-8.126376922900663', '-79.02678503446178', '341.5834'),
(1312140, '181', '-8.126376922900663', '-79.02678503446178', '341.5834'),
(1312141, '181', '-8.126376922900663', '-79.02678503446178', '341.5834'),
(1312142, '181', '-8.126376922900663', '-79.02678503446178', '341.5834'),
(1312143, '181', '-8.126376922900663', '-79.02678503446178', '341.5834'),
(1312144, '181', '-8.126376922900663', '-79.02678503446178', '341.5834'),
(1312145, '181', '-8.126376922900663', '-79.02678503446178', '341.5834'),
(1312146, '181', '-8.126376922900663', '-79.02678503446178', '341.5834'),
(1312147, '181', '-8.126376922900663', '-79.02678503446178', '341.5834'),
(1312148, '181', '-8.126339041989924', '-79.02682076350716', '324.4772'),
(1312149, '181', '-8.126334426169835', '-79.02686337281611', '305.01532'),
(1312150, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312151, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312152, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312153, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312154, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312155, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312156, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312157, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312158, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312159, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312160, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312161, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312162, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312163, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312164, '181', '-8.126363665200584', '-79.02686314324853', '305.01532'),
(1312165, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312166, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312167, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312168, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312169, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312170, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312171, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312172, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312173, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312174, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312175, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312176, '181', '-8.126345468452547', '-79.02686242296788', '305.01532'),
(1312177, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312178, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312179, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312180, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312181, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312182, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312183, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312184, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312185, '181', '-8.126349379608584', '-79.0268803456363', '305.01532'),
(1312186, '181', '-8.126179671556057', '-79.02687401739497', '310.20142'),
(1312187, '181', '-8.126234523441028', '-79.02677523145336', '310.20142'),
(1312188, '181', '-8.126239744047648', '-79.02674452474315', '310.20142'),
(1312189, '181', '-8.12628800855757', '-79.02670625835746', '310.20142'),
(1312190, '184', '-8.126436123234033', '-79.02675003338263', '192.64949'),
(1312191, '184', '-8.126436123234033', '-79.02675003338263', '192.64949'),
(1312192, '184', '-8.126436123234033', '-79.02675003338263', '192.64949'),
(1312193, '184', '-8.126436123234033', '-79.02675003338263', '192.64949'),
(1312194, '184', '-8.12647405419663', '-79.02670125769407', '127.522644'),
(1312195, '184', '-8.126402801765163', '-79.0267200156626', '127.522644'),
(1312196, '184', '-8.126395784955905', '-79.026722818474', '127.522644'),
(1312197, '184', '-8.126395784955905', '-79.026722818474', '127.522644'),
(1312198, '184', '-8.126395784955905', '-79.026722818474', '127.522644');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promocion`
--

CREATE TABLE `promocion` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_invitado` int(11) NOT NULL,
  `importe` decimal(10,2) NOT NULL,
  `fecha` datetime NOT NULL,
  `motivo` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promocion_acumulado`
--

CREATE TABLE `promocion_acumulado` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `importe` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promocion_utilizado`
--

CREATE TABLE `promocion_utilizado` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_pedido` int(11) NOT NULL,
  `importe` decimal(10,2) NOT NULL,
  `fecha` datetime NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prueba`
--

CREATE TABLE `prueba` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `token` varchar(250) NOT NULL,
  `lat` varchar(25) NOT NULL,
  `lng` varchar(25) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `prueba`
--

INSERT INTO `prueba` (`id`, `id_usuario`, `token`, `lat`, `lng`) VALUES
(65, 1895, 'eQExDMqJMq0:APA91bHPr_XN2jbxG4pCLuYlChxrrm5f7MumaH9VFkhkalq835T3-VdHZ9RUBrCcLBax1SMSMHHheMFJwHjwChWEiqsawetwv2Nzim903-Qu0i3JnKVtoFZ2UVL9IzVCV8A6wpo0i701', '-8.112213', '-79.028183'),
(66, 1895, 'eQExDMqJMq0:APA91bHPr_XN2jbxG4pCLuYlChxrrm5f7MumaH9VFkhkalq835T3-VdHZ9RUBrCcLBax1SMSMHHheMFJwHjwChWEiqsawetwv2Nzim903-Qu0i3JnKVtoFZ2UVL9IzVCV8A6wpo0i701', '-8.112213', '-79.028183');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reporte_asistencia`
--

CREATE TABLE `reporte_asistencia` (
  `id` int(11) NOT NULL,
  `nro_unidad` varchar(5) NOT NULL,
  `id_empleado` int(11) NOT NULL,
  `fecha` date NOT NULL,
  `hora` time NOT NULL,
  `id_empresa` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tabla_codigo`
--

CREATE TABLE `tabla_codigo` (
  `id` int(11) NOT NULL,
  `letra` varchar(2) NOT NULL,
  `nro` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tabla_codigo`
--

INSERT INTO `tabla_codigo` (`id`, `letra`, `nro`) VALUES
(161, 'L', 1),
(162, 'C', 0),
(163, 'G', 0),
(164, 'N', 0),
(165, 'M', 0),
(166, 'P', 0),
(167, 'D', 0),
(168, 'O', 0),
(169, 'R', 0),
(170, 'J', 0),
(171, 'U', 0),
(172, 'Z', 0),
(173, 'S', 0),
(174, 'E', 0),
(175, 'V', 0),
(176, 'Q', 0),
(177, 'A', 9),
(178, 'B', 0),
(179, 'I', 0),
(180, 'W', 0),
(181, 'T', 0),
(182, 'Y', 0),
(183, 'F', 0),
(184, 'X', 0),
(185, 'H', 0),
(186, 'K', 0),
(188, 'Ñ', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarifario`
--

CREATE TABLE `tarifario` (
  `id` int(11) NOT NULL,
  `minimo` int(11) NOT NULL,
  `maximo` int(11) NOT NULL,
  `normal` decimal(10,2) NOT NULL,
  `corp` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tarifario`
--

INSERT INTO `tarifario` (`id`, `minimo`, `maximo`, `normal`, `corp`) VALUES
(1, 2000, 2500, '4.50', '6.00'),
(2, 2500, 3000, '5.00', '6.50'),
(3, 3000, 3500, '5.50', '7.00'),
(4, 3500, 4000, '6.00', '7.50'),
(5, 4000, 4500, '6.50', '8.00'),
(6, 4500, 5000, '7.00', '8.50'),
(7, 5000, 5500, '7.50', '9.00'),
(8, 5500, 6000, '8.00', '9.50'),
(9, 6000, 6500, '8.50', '10.00'),
(10, 6500, 7000, '9.00', '10.50'),
(11, 7000, 7500, '10.00', '12.00'),
(12, 7500, 8000, '11.00', '13.50'),
(13, 0, 2000, '4.00', '5.50');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarifa_aeropuerto`
--

CREATE TABLE `tarifa_aeropuerto` (
  `id` int(11) NOT NULL,
  `id_origen` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tarifa_aeropuerto`
--

INSERT INTO `tarifa_aeropuerto` (`id`, `id_origen`, `precio`) VALUES
(5, 2, '25.00'),
(6, 3, '25.00'),
(7, 4, '25.00'),
(8, 1, '25.00'),
(9, 5, '25.00'),
(10, 6, '28.00'),
(11, 8, '25.00'),
(12, 7, '25.00'),
(13, 9, '25.00'),
(14, 10, '25.00'),
(15, 11, '25.00'),
(16, 18, '27.00'),
(17, 22, '27.00'),
(18, 17, '27.00'),
(19, 20, '28.00'),
(20, 39, '25.00'),
(21, 15, '25.00'),
(22, 41, '25.00'),
(23, 50, '25.00'),
(24, 38, '23.00'),
(25, 21, '25.00'),
(26, 29, '25.00'),
(27, 24, '25.00'),
(28, 49, '25.00'),
(29, 46, '25.00'),
(30, 30, '25.00'),
(31, 14, '28.00'),
(32, 26, '25.00'),
(33, 47, '27.00'),
(34, 25, '25.00'),
(35, 34, '26.00'),
(36, 33, '26.00'),
(37, 31, '25.00'),
(38, 45, '23.00'),
(39, 36, '25.00'),
(40, 16, '26.00'),
(41, 32, '25.00'),
(42, 37, '23.00'),
(43, 28, '25.00'),
(44, 12, '26.00'),
(45, 23, '25.00'),
(46, 19, '27.00'),
(47, 42, '25.00'),
(48, 48, '25.00'),
(49, 43, '25.00'),
(50, 13, '25.00'),
(51, 35, '26.00'),
(52, 44, '23.00'),
(53, 27, '25.00'),
(54, 40, '25.00'),
(55, 51, '25.00'),
(56, 52, '25.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tarifa_turistico`
--

CREATE TABLE `tarifa_turistico` (
  `id` int(11) NOT NULL,
  `id_origen` int(11) NOT NULL,
  `id_destino` int(11) NOT NULL,
  `precio` decimal(10,2) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tarifa_turistico`
--

INSERT INTO `tarifa_turistico` (`id`, `id_origen`, `id_destino`, `precio`) VALUES
(1, 1, 1, '25.00'),
(2, 1, 2, '16.00'),
(3, 3, 1, '25.00'),
(4, 3, 2, '18.00'),
(5, 3, 3, '18.00'),
(6, 5, 1, '25.00'),
(7, 5, 2, '16.00'),
(8, 5, 3, '20.00'),
(9, 2, 1, '25.00'),
(10, 2, 2, '16.00'),
(11, 2, 3, '16.00'),
(12, 4, 1, '25.00'),
(13, 4, 2, '16.00'),
(14, 4, 3, '18.00'),
(15, 1, 3, '16.00'),
(16, 6, 1, '28.00'),
(17, 6, 2, '20.00'),
(18, 6, 3, '18.00'),
(19, 39, 1, '25.00'),
(20, 39, 2, '16.00'),
(21, 39, 3, '20.00'),
(22, 22, 1, '27.00'),
(23, 22, 2, '20.00'),
(24, 22, 3, '18.00'),
(25, 41, 1, '25.00'),
(26, 41, 2, '16.00'),
(27, 41, 3, '20.00'),
(28, 15, 1, '25.00'),
(29, 15, 2, '18.00'),
(30, 15, 3, '20.00'),
(31, 50, 1, '25.00'),
(32, 50, 2, '18.00'),
(33, 50, 3, '20.00'),
(34, 38, 1, '23.00'),
(35, 38, 2, '16.00'),
(36, 38, 3, '20.00'),
(37, 21, 1, '25.00'),
(38, 21, 2, '18.00'),
(39, 21, 3, '18.00'),
(40, 29, 1, '25.00'),
(41, 29, 2, '18.00'),
(42, 29, 3, '20.00'),
(43, 24, 1, '25.00'),
(44, 24, 2, '16.00'),
(45, 24, 3, '18.00'),
(46, 49, 1, '25.00'),
(47, 49, 2, '18.00'),
(48, 49, 3, '20.00'),
(49, 46, 1, '25.00'),
(50, 46, 2, '16.00'),
(51, 46, 3, '20.00'),
(52, 30, 1, '25.00'),
(53, 30, 2, '18.00'),
(54, 30, 3, '18.00'),
(55, 14, 1, '25.00'),
(56, 14, 2, '18.00'),
(57, 14, 3, '14.00'),
(58, 26, 1, '25.00'),
(59, 26, 2, '18.00'),
(60, 26, 3, '18.00'),
(61, 47, 1, '25.00'),
(62, 47, 2, '18.00'),
(63, 47, 3, '16.00'),
(64, 25, 1, '25.00'),
(65, 25, 2, '16.00'),
(66, 25, 3, '20.00'),
(67, 34, 1, '25.00'),
(68, 34, 2, '18.00'),
(69, 34, 3, '20.00'),
(70, 17, 1, '28.00'),
(71, 17, 2, '20.00'),
(72, 17, 3, '18.00'),
(73, 33, 1, '25.00'),
(74, 33, 2, '18.00'),
(75, 33, 3, '18.00'),
(76, 31, 1, '25.00'),
(77, 31, 2, '18.00'),
(78, 31, 3, '16.00'),
(79, 20, 1, '25.00'),
(80, 20, 2, '20.00'),
(81, 20, 3, '18.00'),
(82, 45, 1, '23.00'),
(83, 45, 2, '16.00'),
(84, 45, 3, '20.00'),
(85, 8, 1, '25.00'),
(86, 8, 2, '18.00'),
(87, 8, 3, '20.00'),
(88, 36, 1, '25.00'),
(89, 36, 2, '18.00'),
(90, 36, 3, '20.00'),
(91, 16, 1, '26.00'),
(92, 16, 2, '20.00'),
(93, 16, 3, '18.00'),
(94, 10, 1, '0.00'),
(95, 10, 2, '0.00'),
(96, 10, 3, '0.00'),
(97, 32, 1, '25.00'),
(98, 32, 2, '16.00'),
(99, 32, 3, '16.00'),
(100, 37, 1, '25.00'),
(101, 37, 2, '15.00'),
(102, 37, 3, '18.00'),
(103, 28, 1, '25.00'),
(104, 28, 2, '16.00'),
(105, 28, 3, '16.00'),
(106, 12, 1, '26.00'),
(107, 12, 2, '16.00'),
(108, 12, 3, '16.00'),
(109, 23, 1, '25.00'),
(110, 23, 2, '16.00'),
(111, 23, 3, '18.00'),
(112, 7, 1, '25.00'),
(113, 7, 2, '16.00'),
(114, 7, 3, '18.00'),
(115, 19, 1, '25.00'),
(116, 19, 2, '16.00'),
(117, 19, 3, '16.00'),
(118, 42, 1, '25.00'),
(119, 42, 2, '16.00'),
(120, 42, 3, '16.00'),
(121, 48, 1, '25.00'),
(122, 48, 2, '16.00'),
(123, 48, 3, '16.00'),
(124, 43, 1, '25.00'),
(125, 43, 2, '16.00'),
(126, 43, 3, '16.00'),
(127, 13, 1, '25.00'),
(128, 13, 2, '16.00'),
(129, 13, 3, '16.00'),
(130, 35, 1, '25.00'),
(131, 35, 2, '16.00'),
(132, 35, 3, '18.00'),
(133, 18, 1, '25.00'),
(134, 18, 2, '18.00'),
(135, 18, 3, '16.00'),
(136, 51, 1, '25.00'),
(137, 51, 2, '16.00'),
(138, 51, 3, '16.00'),
(139, 44, 1, '25.00'),
(140, 44, 2, '16.00'),
(141, 44, 3, '18.00'),
(142, 27, 1, '25.00'),
(143, 27, 2, '16.00'),
(144, 27, 3, '16.00'),
(145, 40, 1, '25.00'),
(146, 40, 2, '16.00'),
(147, 40, 3, '16.00'),
(148, 52, 1, '25.00'),
(149, 52, 2, '16.00'),
(150, 52, 3, '16.00'),
(151, 11, 1, '25.00'),
(152, 11, 2, '16.00'),
(153, 11, 3, '18.00'),
(154, 9, 1, '25.00'),
(155, 9, 2, '18.00'),
(156, 9, 3, '16.00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_empresa`
--

CREATE TABLE `user_empresa` (
  `id` int(11) NOT NULL,
  `id_empresa` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `user` varchar(20) NOT NULL,
  `pass` varchar(100) NOT NULL,
  `password` varchar(250) NOT NULL,
  `estado` int(11) NOT NULL,
  `d_empresa` int(11) NOT NULL,
  `d_empleado` int(11) NOT NULL,
  `d_reporte` int(11) NOT NULL,
  `d_paga_credito` int(11) NOT NULL,
  `d_mensaje_multi` int(11) NOT NULL,
  `d_cobra_credito` int(11) NOT NULL,
  `d_empresa_credito` int(11) NOT NULL,
  `d_clientes` int(11) NOT NULL,
  `d_clientes_llamada` int(11) NOT NULL,
  `d_chat` int(11) NOT NULL,
  `d_aeropuerto` int(11) NOT NULL DEFAULT '0',
  `d_turistico` int(11) NOT NULL DEFAULT '0',
  `d_facturacion` int(11) NOT NULL,
  `asistencia` int(11) NOT NULL,
  `resumen` int(11) NOT NULL,
  `control_personal` int(11) NOT NULL,
  `seguimiento` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `user_empresa`
--

INSERT INTO `user_empresa` (`id`, `id_empresa`, `nombre`, `user`, `pass`, `password`, `estado`, `d_empresa`, `d_empleado`, `d_reporte`, `d_paga_credito`, `d_mensaje_multi`, `d_cobra_credito`, `d_empresa_credito`, `d_clientes`, `d_clientes_llamada`, `d_chat`, `d_aeropuerto`, `d_turistico`, `d_facturacion`, `asistencia`, `resumen`, `control_personal`, `seguimiento`) VALUES
(1, 1, 'Base Operadora', 'operadora', 'YWJj', 'b349e67445488ae1fad84633400057e759a46fb3', 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(3, 1, 'Administrador', 'administrador', 'YWJj', 'b349e67445488ae1fad84633400057e759a46fb3', 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `user_prueba`
--

CREATE TABLE `user_prueba` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `prueba` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `user_prueba`
--

INSERT INTO `user_prueba` (`id`, `id_usuario`, `prueba`) VALUES
(1, 121, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `codigo` varchar(10) NOT NULL,
  `paso` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `pass` varchar(150) NOT NULL,
  `estado` int(11) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `pais` varchar(20) DEFAULT NULL,
  `ciudad` varchar(50) DEFAULT NULL,
  `ukey` varchar(150) NOT NULL,
  `telefono` varchar(20) NOT NULL,
  `fecha_login` datetime DEFAULT NULL,
  `sms` int(11) NOT NULL DEFAULT '0',
  `id_empresa` int(11) NOT NULL,
  `tokenId` varchar(250) DEFAULT NULL,
  `invitados` int(11) NOT NULL DEFAULT '0',
  `bolsa` decimal(10,2) NOT NULL DEFAULT '0.00',
  `id_corporativo` int(11) NOT NULL DEFAULT '0',
  `dni_usu_corp` varchar(15) DEFAULT NULL,
  `version` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `codigo`, `paso`, `nombre`, `email`, `pass`, `estado`, `fecha`, `pais`, `ciudad`, `ukey`, `telefono`, `fecha_login`, `sms`, `id_empresa`, `tokenId`, `invitados`, `bolsa`, `id_corporativo`, `dni_usu_corp`, `version`) VALUES
(42, 'wjsfagoove', 2, 'WmFtYnJpbmEgTsO6w7FleiBMb3Bleg==', 'gps-jnisi@hotmail.com', 'NDI=', 1, '2019-02-21 22:19:41', NULL, NULL, '', '991143120', NULL, 0, 0, 'd1SyV-z_pJI:APA91bF4oMavmFf6asJYlOSFQRYdbre8C6lANV3iwXSgdaJOEnHeSWw6FVx_99bgZmwdVQMW8pHgHY5pjAW00KP8EgvklrGoPwt2h99hGP8VERu4QwL1kztJ5eKL1f0P6M1EGIRCQOxw', 0, '0.00', 0, NULL, 2),
(41, 'drfit0axs7', 2, 'QWxhbmdlcg==', 'a@a.a', 'NDE=', 1, '2019-02-21 18:04:39', NULL, NULL, '', '929258017', NULL, 0, 0, 'e9ajVwNYujw:APA91bEyhBp5EDfRK1zsEDkYCYtR8zIgz9Ot7-ETeiDB4DC0_XET78vgt8Pd9Uw4_ggNgyp5CYpXV8fiYcc8wR-Yx9nnowZY2LSZWT6wyX1U9zUKPLQvn2u_mnF_Pwnd3zZ-0WmevDLh', 0, '0.00', 0, NULL, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios_corporativo`
--

CREATE TABLE `usuarios_corporativo` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `email` varchar(50) NOT NULL,
  `pass` varchar(150) NOT NULL,
  `dni` varchar(10) NOT NULL,
  `telefono` varchar(50) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_corporativo` int(11) NOT NULL,
  `estado` int(11) NOT NULL,
  `area` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios_corporativo`
--

INSERT INTO `usuarios_corporativo` (`id`, `nombre`, `email`, `pass`, `dni`, `telefono`, `fecha`, `id_corporativo`, `estado`, `area`) VALUES
(6, 'SkFJTUUgSFVBTUFO', 'JLUIS.HUAMAN@GMAIL.COM', 'YWJj', '44173956', '977123151', '2016-12-30 14:37:04', 9, 1, 'SISTEMAS');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios_corporativo_dire`
--

CREATE TABLE `usuarios_corporativo_dire` (
  `id` int(11) NOT NULL,
  `id_empresa` int(11) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `nro` varchar(50) NOT NULL,
  `refe` varchar(100) NOT NULL,
  `coordenadas` varchar(150) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios_llamada`
--

CREATE TABLE `usuarios_llamada` (
  `id` int(11) NOT NULL,
  `codigo` varchar(10) DEFAULT NULL,
  `nombre` varchar(50) NOT NULL,
  `telefono` varchar(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios_llamada`
--

INSERT INTO `usuarios_llamada` (`id`, `codigo`, `nombre`, `telefono`) VALUES
(1, 'L1', 'THVpcyBIdWFtYW4=', '977123151'),
(2, 'A1', 'YWxhbiBnZXJvbmltbw==', '929258017'),
(3, 'A7', 'YXNk', '929258016'),
(4, 'A8', 'YWxhbGE=', '929258018'),
(5, 'A9', 'QUxBTkY=', '929258166');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios_llamada_dire`
--

CREATE TABLE `usuarios_llamada_dire` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `direccion` text NOT NULL,
  `nro` varchar(50) NOT NULL,
  `refe` varchar(100) NOT NULL,
  `coordenadas` varchar(150) NOT NULL,
  `actualizar` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuarios_llamada_dire`
--

INSERT INTO `usuarios_llamada_dire` (`id`, `id_usuario`, `direccion`, `nro`, `refe`, `coordenadas`, `actualizar`) VALUES
(5253, 1, 'SnIuIEdyYXU=', 'NDY2', 'ZWRpZmljaW8gYXp1bA==', 'LTguMTE0NTgyNywtNzkuMDI1NjMyMQ==', 0),
(5254, 1, 'QXYuIEVzcGHDsWE=', 'MjIzNQ==', 'cmljYXJkb3M=', 'LTguMTE0MzIxMjE0MjQ3MzU4LC03OS4wMjQyNTYxNDA0NzU0Nw==', 0),
(5255, 2, 'VHJ1amlsbG8=', 'MjQ=', 'MTIx', 'LTguMTA5MDUyNCwtNzkuMDIxNTMzNg==', 0),
(5256, 3, 'VHJ1amlsbG8=', 'MTI=', 'YXNkYXNk', 'LTguMTI0MTc3MjIwNDQzMTA1LC03OS4wMjYzNDAxMTg1NTQ2OQ==', 0),
(5257, 4, 'QXYgQW3DqXJpY2EgU3Vy', 'Mjg1Nw==', 'YXNk', 'LTguMTI2NDk4MiwtNzkuMDI3Njg5MDk5OTk5OTg=', 0),
(5258, 5, 'U3RhLiBDcnV6', 'MjM=', 'YU9ERUU=', 'LTguMTE3MDY1NDk5OTk5OTk5LC03OS4wMTY2MjY3OTk5OTk5OA==', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios_mensajes`
--

CREATE TABLE `usuarios_mensajes` (
  `id` int(11) NOT NULL,
  `id_empresa` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `mensaje` text NOT NULL,
  `fecha` datetime NOT NULL,
  `visto` int(11) NOT NULL,
  `origen` int(11) NOT NULL,
  `visto_base` int(11) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vales`
--

CREATE TABLE `vales` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_invitado` int(11) NOT NULL,
  `importe` decimal(10,2) NOT NULL,
  `estado` int(11) NOT NULL,
  `fecha` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `motivo` varchar(20) DEFAULT NULL,
  `id_pedido` int(11) NOT NULL DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `zona_origen`
--

CREATE TABLE `zona_origen` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `lat` varchar(50) NOT NULL,
  `lng` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `zona_origen`
--

INSERT INTO `zona_origen` (`id`, `nombre`, `lat`, `lng`) VALUES
(1, 'Victor Larco - Urb. El Golf', '-8.140391', '-79.038697'),
(2, 'Victor Larco - Urb. California', '-8.130471', '-79.039748'),
(3, 'Trujillo - Urb. La Merced', '', ''),
(4, 'Trujillo - Urb. Miraflores', '', ''),
(5, 'Trujillo - Urb. Los Jardines', '', ''),
(6, 'Trujillo - Urb. El Bosque', '', ''),
(7, 'Trujillo - Urb. Primavera', '', ''),
(8, 'Trujillo - Urb. Las Quintanas', '', ''),
(9, 'VÃ­ctor Larco - Vista Alegre', '', ''),
(10, 'Victor Larco - Urb. La 5ta. de San Andres I Sector', '', ''),
(11, 'VÃ­ctor Larco - Urb. La 5ta. de San Andres II Sector', '', ''),
(12, 'Trujillo - Urb. Palermo', '', ''),
(13, 'Trujillo - Urb. Santa MarÃ­a', '', ''),
(14, 'Trujillo - Urb. La 5ta. de Santa MarÃ­a', '', ''),
(15, 'Trujillo - Urb. Aranjuez', '', ''),
(16, 'Trujillo - Urb. Los Granados', '', ''),
(17, 'Trujillo - Urb. La Noria', '', ''),
(18, 'Trujillo - Urb. Santo Dominguito', '', ''),
(19, 'Trujillo - Urb. Razuri', '', ''),
(20, 'Trujillo - Urb. La Rinconada', '', ''),
(21, 'Trujillo - Urb. Daniel Hoyle', '', ''),
(22, 'Trujillo - Urb. Alameda de Razuri', '', ''),
(23, 'Trujillo - Urb. Pay Pay', '', ''),
(24, 'Trujillo - Urb. El Molino', '', ''),
(25, 'Trujillo - Urb. La Intendencia', '', ''),
(26, 'Trujillo - Urb. La Arboleda', '', ''),
(27, 'Trujillo - Urb. Upao II', '', ''),
(28, 'Trujillo - Urb. Monserrate', '', ''),
(29, 'Trujillo - Urb. El Galeno', '', ''),
(30, 'Trujillo - Urb. IngenierÃ­a ', '', ''),
(31, 'Trujillo - Urb. La Perla', '', ''),
(32, 'Trujillo - Urb. Los Jazmines', '', ''),
(33, 'Trujillo - Urb. La Nueva Marqueza', '', ''),
(34, 'Trujillo - Urb. La Marqueza', '', ''),
(35, 'Trujillo - Urb. Santa Teresa de Ãvila', '', ''),
(36, 'Trujillo - Urb. Los Fresnos', '', ''),
(37, 'Trujillo - Urb. Mochica ', '', ''),
(38, 'Trujillo - Urb. Covicorti', '', ''),
(39, 'Trujillo - Centro CÃ­vico', '-8.110641', '-79.02734'),
(40, 'Trujillo - Urb. Vista Hermosa', '', ''),
(41, 'Trujillo - Urb. Alameda de San Andres', '', ''),
(42, 'Trujillo - Urb. San Andres', '', ''),
(43, 'Trujillo - Urb. San Nicolas', '', ''),
(44, 'Trujillo - Urb. Trupal', '', ''),
(45, 'Trujillo - Urb. Las Capullanas', '', ''),
(46, 'Trujillo - Urb. Huerta Grande', '', ''),
(47, 'Trujillo - Urb. La Encalada', '', ''),
(48, 'Trujillo - Urb. San Eloy', '', ''),
(49, 'Trujillo - Urb. El Recreo', '', ''),
(50, 'Trujillo - Urb. Chicago', '', ''),
(51, 'Trujillo - Urb. Torres Araujo', '', ''),
(52, 'VÃ­ctor Larco - Buenos Aires', '', '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `zona_turistica`
--

CREATE TABLE `zona_turistica` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `zona_turistica`
--

INSERT INTO `zona_turistica` (`id`, `nombre`) VALUES
(1, 'Huanchaco'),
(2, 'Chan Chan'),
(3, 'Huacas del Sol y la Luna');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `api_key`
--
ALTER TABLE `api_key`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `barrios`
--
ALTER TABLE `barrios`
  ADD PRIMARY KEY (`id_barrio`);

--
-- Indices de la tabla `barrios_referencia`
--
ALTER TABLE `barrios_referencia`
  ADD PRIMARY KEY (`id_referencia`);

--
-- Indices de la tabla `chat_conductor_usuario`
--
ALTER TABLE `chat_conductor_usuario`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `destinos`
--
ALTER TABLE `destinos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `DEVICEINFO`
--
ALTER TABLE `DEVICEINFO`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `direcciones`
--
ALTER TABLE `direcciones`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `distritos`
--
ALTER TABLE `distritos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `empleado`
--
ALTER TABLE `empleado`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id` (`id`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `empresa_credito`
--
ALTER TABLE `empresa_credito`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `fs_users`
--
ALTER TABLE `fs_users`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `gcm_devices`
--
ALTER TABLE `gcm_devices`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `keys_mapa`
--
ALTER TABLE `keys_mapa`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `llamada_clientes`
--
ALTER TABLE `llamada_clientes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `llamada_direccion`
--
ALTER TABLE `llamada_direccion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `llamada_pedidos`
--
ALTER TABLE `llamada_pedidos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `modulo_base`
--
ALTER TABLE `modulo_base`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `notificacion`
--
ALTER TABLE `notificacion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `observaciones`
--
ALTER TABLE `observaciones`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `paradas`
--
ALTER TABLE `paradas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pedidos_aceptados`
--
ALTER TABLE `pedidos_aceptados`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id_pedido` (`id_pedido`);

--
-- Indices de la tabla `pedidos_creditos`
--
ALTER TABLE `pedidos_creditos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pedidos_dejados_libres`
--
ALTER TABLE `pedidos_dejados_libres`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `pedidos_historial`
--
ALTER TABLE `pedidos_historial`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `id_2` (`id`);

--
-- Indices de la tabla `pedidos_recorrido`
--
ALTER TABLE `pedidos_recorrido`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `id_2` (`id`);

--
-- Indices de la tabla `pedidos_recorrido_aceptado`
--
ALTER TABLE `pedidos_recorrido_aceptado`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `id_2` (`id`);

--
-- Indices de la tabla `promocion`
--
ALTER TABLE `promocion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `promocion_acumulado`
--
ALTER TABLE `promocion_acumulado`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `promocion_utilizado`
--
ALTER TABLE `promocion_utilizado`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `prueba`
--
ALTER TABLE `prueba`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `reporte_asistencia`
--
ALTER TABLE `reporte_asistencia`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nro_unidad` (`nro_unidad`,`fecha`);

--
-- Indices de la tabla `tabla_codigo`
--
ALTER TABLE `tabla_codigo`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tarifario`
--
ALTER TABLE `tarifario`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tarifa_aeropuerto`
--
ALTER TABLE `tarifa_aeropuerto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `tarifa_turistico`
--
ALTER TABLE `tarifa_turistico`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `user_empresa`
--
ALTER TABLE `user_empresa`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `user_prueba`
--
ALTER TABLE `user_prueba`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios_corporativo`
--
ALTER TABLE `usuarios_corporativo`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios_corporativo_dire`
--
ALTER TABLE `usuarios_corporativo_dire`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios_llamada`
--
ALTER TABLE `usuarios_llamada`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `telefono` (`telefono`);

--
-- Indices de la tabla `usuarios_llamada_dire`
--
ALTER TABLE `usuarios_llamada_dire`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`),
  ADD KEY `id_2` (`id`),
  ADD KEY `id_3` (`id`);

--
-- Indices de la tabla `usuarios_mensajes`
--
ALTER TABLE `usuarios_mensajes`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `vales`
--
ALTER TABLE `vales`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `zona_origen`
--
ALTER TABLE `zona_origen`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `zona_turistica`
--
ALTER TABLE `zona_turistica`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `api_key`
--
ALTER TABLE `api_key`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `asistencia`
--
ALTER TABLE `asistencia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21659;

--
-- AUTO_INCREMENT de la tabla `barrios`
--
ALTER TABLE `barrios`
  MODIFY `id_barrio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `barrios_referencia`
--
ALTER TABLE `barrios_referencia`
  MODIFY `id_referencia` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `chat_conductor_usuario`
--
ALTER TABLE `chat_conductor_usuario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=523;

--
-- AUTO_INCREMENT de la tabla `destinos`
--
ALTER TABLE `destinos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `DEVICEINFO`
--
ALTER TABLE `DEVICEINFO`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=148;

--
-- AUTO_INCREMENT de la tabla `direcciones`
--
ALTER TABLE `direcciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1608;

--
-- AUTO_INCREMENT de la tabla `distritos`
--
ALTER TABLE `distritos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `empleado`
--
ALTER TABLE `empleado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1983;

--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `empresa_credito`
--
ALTER TABLE `empresa_credito`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `fs_users`
--
ALTER TABLE `fs_users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `gcm_devices`
--
ALTER TABLE `gcm_devices`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `keys_mapa`
--
ALTER TABLE `keys_mapa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `llamada_clientes`
--
ALTER TABLE `llamada_clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `llamada_direccion`
--
ALTER TABLE `llamada_direccion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT de la tabla `llamada_pedidos`
--
ALTER TABLE `llamada_pedidos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT de la tabla `modulo_base`
--
ALTER TABLE `modulo_base`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `notificacion`
--
ALTER TABLE `notificacion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=307;

--
-- AUTO_INCREMENT de la tabla `observaciones`
--
ALTER TABLE `observaciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `paradas`
--
ALTER TABLE `paradas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=196;

--
-- AUTO_INCREMENT de la tabla `pedidos_aceptados`
--
ALTER TABLE `pedidos_aceptados`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22590;

--
-- AUTO_INCREMENT de la tabla `pedidos_creditos`
--
ALTER TABLE `pedidos_creditos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `pedidos_dejados_libres`
--
ALTER TABLE `pedidos_dejados_libres`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `pedidos_recorrido`
--
ALTER TABLE `pedidos_recorrido`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2365525;

--
-- AUTO_INCREMENT de la tabla `pedidos_recorrido_aceptado`
--
ALTER TABLE `pedidos_recorrido_aceptado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1312199;

--
-- AUTO_INCREMENT de la tabla `promocion`
--
ALTER TABLE `promocion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=327;

--
-- AUTO_INCREMENT de la tabla `promocion_acumulado`
--
ALTER TABLE `promocion_acumulado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=191;

--
-- AUTO_INCREMENT de la tabla `promocion_utilizado`
--
ALTER TABLE `promocion_utilizado`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=204;

--
-- AUTO_INCREMENT de la tabla `prueba`
--
ALTER TABLE `prueba`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT de la tabla `reporte_asistencia`
--
ALTER TABLE `reporte_asistencia`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=459;

--
-- AUTO_INCREMENT de la tabla `tabla_codigo`
--
ALTER TABLE `tabla_codigo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=191;

--
-- AUTO_INCREMENT de la tabla `tarifario`
--
ALTER TABLE `tarifario`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de la tabla `tarifa_aeropuerto`
--
ALTER TABLE `tarifa_aeropuerto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT de la tabla `tarifa_turistico`
--
ALTER TABLE `tarifa_turistico`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=157;

--
-- AUTO_INCREMENT de la tabla `user_empresa`
--
ALTER TABLE `user_empresa`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `user_prueba`
--
ALTER TABLE `user_prueba`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT de la tabla `usuarios_corporativo`
--
ALTER TABLE `usuarios_corporativo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `usuarios_corporativo_dire`
--
ALTER TABLE `usuarios_corporativo_dire`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `usuarios_llamada`
--
ALTER TABLE `usuarios_llamada`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `usuarios_llamada_dire`
--
ALTER TABLE `usuarios_llamada_dire`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5259;

--
-- AUTO_INCREMENT de la tabla `usuarios_mensajes`
--
ALTER TABLE `usuarios_mensajes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=168;

--
-- AUTO_INCREMENT de la tabla `vales`
--
ALTER TABLE `vales`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=102;

--
-- AUTO_INCREMENT de la tabla `zona_origen`
--
ALTER TABLE `zona_origen`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT de la tabla `zona_turistica`
--
ALTER TABLE `zona_turistica`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
