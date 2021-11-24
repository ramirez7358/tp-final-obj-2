# TPFinalObj2

Correciones:

La logica de la detecciones de desplazamiento esta bien?

Ahora se puede suscribir al Parking system para monitoriar los estacionamientos. 
¿Esta bien pasarle a cada area el alert manager?

Cuando finalizo todos los estacionamientos, hay que descontar el credito del que creo el estacionamiento.

Hice un metodo en la clase AppSEM que recibe un string y lo muestro por System.out.printl en vez tener los sysout
esparcidos por ahi.

Los singleton en vez de accederlos directamente ahora los guardo en un atributo de la clase.
¿Esta bien o esta mal usar singleton para estos casos?

Verificar la implementacion de exist parking con stream en Parking Area

StartParkingResponse y EndParkingResponse son solo objetos de respuesta, por eso no tienen comportamiento.
¿Esto esta mal?

Elimine la clase Car y Cellphone y ahora toda esa info esta en la AppSEM.
Time util se creo por que no sabia como mockear los metodos staticos de LocalTime y LocalDateTime

Cambie la implementacion de inForce en Parking pero sigue siendo parecida.

## Casos de uso

### Registrar un parking manual (compra puntual)

1. Registrar por medio de un punto de venta el estacionamiento 
recibiendo la patente y la cantidad de horas.
2. Desde el punto de venta registrar el parking en el sistema central.
3. Comprobar que exista el parking

### Registrar parking por app sin credito (error)

1. Con un usuario sin credito iniciar parking
2. Comprobar que no se creo el parking

## Registrar parking por app con credito

1. Cargar credito en un punto de venta
2. Indicar el inicio de estacionamiento
3. Verificar que se creo el parking
4. Finalizar estacionamiento
5. Verificar que se desconto credito correspondiente.

### Consultar saldo desde la APP

1. Consultar saldo
2. Cargar saldo
3. Volver a consultar saldo

### Inspeccion de zona

1. Desde la app de inspeccion consultar si una patente
tiene un estacionamiento vigente.
2. La patente tiene un estacionamiento vigente por lo tanto
no es necesario dar de alta una infraccion.

### Inspeccion de zona - alta de infraccion

1. Desde la app de inspeccion consultar si una patente
tiene un estacionamiento vigente.
2. El sistema devuelve que no tiene estacionamiento vigente.
3. Dar de alta una infraccion.


