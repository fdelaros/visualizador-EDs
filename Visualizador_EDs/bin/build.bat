@echo off
REM ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
REM Universidad de los Andes (Bogotá - Colombia)
REM Departamento de Ingeniería de Sistemas y Computación
REM
REM Proyecto Visualizador Estructuras de Datos
REM Autor: Diana Marcela Gutierrez - Abril-2020
REM ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
REM/


REM ---------------------------------------------------------
REM Asegura la creación de los directorios classes y lib
REM ---------------------------------------------------------

cd ..
mkdir classes
mkdir lib

REM ---------------------------------------------------------
REM Compila las clases del directotio source
REM ---------------------------------------------------------

cd src
javac -cp "../jar_files/jgrapht-core-1.1.0.jar;../jar_files/jgrapht-ext-1.1.0.jar;../jar_files/jgrapht-io-1.1.0.jar;../jar_files/jgraphx-2.0.0.1.jar;../jar_files/estructura.jar" -d ../classes/ app/*.java

REM ---------------------------------------------------------
REM Crea el archivo jar a partir de los archivos compilados
REM ---------------------------------------------------------

cd ../classes
jar cf ../lib/Visualizador-Estructuras.jar app/*

cd ../bin

echo "Archivo estructura.jar integrado al visualizador exitosamente"
echo "Ahora ejecute el comando run"
pause