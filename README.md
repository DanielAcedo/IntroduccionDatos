# Ejercicio de Introducción para la asignatura Acceso a Datos #
### Por Daniel Acedo Calderón ###

# Descripción de los ejercicios #

## Ejercicio 1 ##

La función base de esta aplicación es convertir una cantidad introducida en euros a dolares, o viceversa. Se puede elegir con radio buttons en que dirección irá la conversión.
He modificado y expandido el ejercicio incorporándole una llamada HTTP a la api http://fixer.io/, concretamente a http://api.fixer.io/latest que proporciona ratios de conversión para una gran cantidad de divisas. Los resultados se envían en formato JSON, los cuales he formateado y tratado para almacenarlos en la aplicación y hacer uso de ellos para las conversiones.

En el caso de no haber conexión se usará un ratio fijo marcado en el código.

![Ej1.JPG](https://bitbucket.org/repo/9rMrdL/images/196902422-Ej1.JPG) ![Ej1NoConexion.JPG](https://bitbucket.org/repo/9rMrdL/images/2518624938-Ej1NoConexion.JPG)

## Ejercicio 2 ##

Se trata de un conversor de centímetros a pulgadas y viceversa. Le he añadido un cambio de modo con marcos de diferente color para elegir si quieres introducir los centímetros y se conviertan a pulgadas, o al contrario.

![Ej2.JPG](https://bitbucket.org/repo/9rMrdL/images/2982653860-Ej2.JPG) ![Ej2Inverso.JPG](https://bitbucket.org/repo/9rMrdL/images/483431182-Ej2Inverso.JPG)

## Ejercicio 3 ##

Funciona como visor web. Debes introducir una URL en el campo y al pulsar el botón se abrirá una ventana del navegador con la dirección escrita. Para ser una URL correcta debe empezar por "http://". Aunque en este caso si no lo incluyes, el programa lo hará por ti mediante expresiones regulares.

![Ej3.JPG](https://bitbucket.org/repo/9rMrdL/images/875764949-Ej3.JPG)

## Ejercicio 4 ##

Esta aplicación tiene algo más de complejidad. Su función es ser un contador de descansos para el café. Introduces el tiempo que va a durar el descanso y la aplicación te avisará cuando se acabe el tiempo. Además cuenta con un contador de los cafés que llevas en ese momento. He añadido que se reproduzca un sonido cada vez que el temporizador llega a cero. Cuando llegas al límite de 10 cafés aparece una ventana avisándote y no puedes empezar mas temporizadores hasta que pulses el botón de reiniciar que aparecerá sólo entonces.

También he añadido un selector más preciso para poder añadir o restar segundos y minutos. Otra de las cosas que he añadido es la opción de poder pausar el temporizador en cualquier momento. Una vez hecho esto puedes reanudarlo por donde lo dejaste o cancelar el temporizador y volver a la pantalla de selección (también puedes cancelar mientras el temporizador está corriendo).

![Ej4Corriendo.JPG](https://bitbucket.org/repo/9rMrdL/images/2405191199-Ej4Corriendo.JPG) ![Ej4Pausado.JPG](https://bitbucket.org/repo/9rMrdL/images/130107962-Ej4Pausado.JPG)
![Ej4Seleccion.JPG](https://bitbucket.org/repo/9rMrdL/images/4134163582-Ej4Seleccion.JPG) ![Ej4Terminado.JPG](https://bitbucket.org/repo/9rMrdL/images/2995933241-Ej4Terminado.JPG)

## Ejercicio 5 ##