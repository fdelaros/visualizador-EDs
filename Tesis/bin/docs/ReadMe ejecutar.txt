Visualizador de estructuras

Prerrequisitos: 
-  Se debe haber generado el archivo estructura.jar (ver ReadMe generar jar).

Ejecución del visualizador:
-  Agregar el jar generado al directorio "jar_files".
-  En el directorio "bin", ejecutar el archivo "clean", debería ver en consola el siguiente mensaje: "Listo
   para actualizar el visualizador".
-  Ejecutar el archivo "build", debería ver en consola el siguiente mensaje: "Archivo estructura.jar 
   integrado al visualizador exitosamente".
-  Ejecutar el archivo "run", debería ver en consola el siguiente mensaje: "Visualizador iniciado".
-  Al cerrar el visualizador, debería ver en consola el siguiente mensaje: "Ejecución finalizada del 
   visualizador".

Interacción con el visualizador y entradas esperadas:
-  Buscar nodos: para un sólo nodo, escribir la etiqueta que lo identifica en la visualización. Para varios
   nodos, escribir las etiquetas separadas por "," del siguiente modo: nodo1,nodo2,nodo3
-  Buscar vecinos: escribir la etiqueta que identifica al nodo a localizar y consultar.
-  Agregar nodo: escribir la etiqueta del nodo a agregar.
-  Eliminar nodo: escribir la etiqueta del nodo a eliminar.
-  Buscar arcos: escribir la etiqueta del nodo inicial seguido de ">" y después la etiqueta del nodo destino,
   del siguiente modo: nodo1>nodo2
-  Agregar arcos: para agregar un arco, escribir la etiqueta del nodo inicial seguido de ">" y después la 
   etiqueta del nodo destino. Para agregar varios nodos, seguir la anterior notación separándolos por una ","
   del siguiente modo: nodo1>nodo2,nodo4>nodo8
-  Mostrar arcos
-  Mostrar nodos
-  Mostrar camino: se muestran los nodos inicial y final en azul, los intermedios en amarillo, y en caso de
   encontrar inconsistencias en la secuencia, se mostrarán en rojo aquellos nodos que no permiten continuar
   el camino.