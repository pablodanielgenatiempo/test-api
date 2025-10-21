#!/bin/bash

# Script para ejecutar la aplicación en modo debug
echo "🚀 Iniciando aplicación en modo DEBUG..."
echo "📍 Puerto de debugging: 5005"
echo "🌐 Puerto de la aplicación: 8080"
echo "🔗 Conecta tu IDE a: localhost:5005"
echo ""

# Limpiar cualquier configuración de debug previa
unset JAVA_OPTS
unset MAVEN_OPTS

# Ejecutar con debugging habilitado usando sintaxis moderna
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005" -Dspring-boot.run.profiles=dev
