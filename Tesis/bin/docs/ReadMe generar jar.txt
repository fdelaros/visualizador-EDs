Generación de jar para la estructura de datos

Prerrequisitos:
- El estudiante debe completar la implementación de una clase StandardMethods que extienda de la interfaz
  IStandardMethods.
- La jerarquía de paquetes para el proyecto del estudiante debe ser la siguiente:
	-> modelo
	   -> complementos :incluye la clase Edge y la interfaz IStandardMethods (dadas al estudiante)
	      además de la implementación de esta interfaz como se mencionó anteriormente.
	   -> estructuras: incluye la implementación de la estructura del estudiante.

Generación del jar:
- Desde eclipse click derecho sobre el proyecto que contiene la estructura y seleccionar la opción 
  "Exportar".
- En el wizard seleccionar bajo el directorio Java la opción "JAR File".
- Nombrar el jar como "estructura.jar"