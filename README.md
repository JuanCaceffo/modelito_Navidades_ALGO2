# <a name="_ntq02oajeche"></a>**Enunciado: Navidad![](Aspose.Words.e8cdad28-af80-4084-8e0e-6c282e260ff4.001.png)**
Ya se acerca Navidad (o algo así) y queremos que todas las personas estén contentas con su regalo. Para eso un importante personaje cuyo nombre prefiere permanecer en el anonimato nos pidió que modelemos un sistema que le permita definir los presentes para cada caso.
## <a name="_kmk3vfppfa1q"></a>**Punto 1: Qué te haría feliz**
Cada persona define qué tipos de regalo le gustan:

- **conformistas:** cualquier regalo le cae bien.
- **interesadas:** quieren que el regalo cueste más de una cierta cantidad de plata.
- **exigentes:** quieren que el regalo sea valioso (ver la definición en el punto 2).
- **marqueras**: quieren que el regalo sea de una marca determinada, que se pueda configurar (ej: "Nike Feraldy" o "Kodak")
- **combinetas:** combinan un conjunto de los anteriores criterios. Le cae bien un regalo si alguno de los criterios lo acepta.

Revisar el párrafo “qué se pide” ya que no es obligatorio implementar todos los criterios.
## <a name="_xkohfspgpddc"></a>**Punto 2: Regalos**
Además de la información anteriormente mencionada, los regalos valiosos son aquellos cuyo precio es mayor a $ 5.000, donde además hay que considerar

- la ropa si es de la marca “Jordache”, “Lee”, “Charro” o “Motor Oil”
- los juguetes si la fecha en la que se lanzaron al mercado es anterior al año 2000
- los perfumes de origen extranjero
- las experiencias en caso de que sea para un día viernes. Por ejemplo: un desayuno de Pani para el 08/07/2022. En ese caso se considera que la marca es el nombre de la empresa.

Nos interesa poder cambiar la condición para todos los regalos sin tener que modificar en muchos lugares (ej: que además del precio mayor a 5.000, la marca tenga más de 5 letras).
## <a name="_dwvrb73eb8rq"></a>**Punto 3: El proceso**
Para cada persona

- debe buscar el primer regalo adecuado para la persona en base a la lista de regalos que tiene en stock. Recordemos que cada persona define sus preferencias, por lo que ésta es una decisión fácil de implementar.
- en caso de que ningún regalo resulte adecuado, se debe generar como regalo un voucher de $ 2.000 disponibles en la aplicación Papapp. En ese caso la marca sería “Papapp” y no lo consideramos valioso.
- también se debe registrar el regalo entregado a la persona. Cada vez que esto pasa, queremos que se pueda configurar
    - que mande un mail a la persona
    - informar a la empresa de fletes “El reno loco” el regalo para su posterior envío, con la siguiente información: dirección del cliente, nombre de la persona a entregar, DNI, código del regalo (cada regalo tiene su identificación para el sistema de fletes)
    - si algún regalo supera los $ 10.000 se pide que modifique el criterio de la persona a interesada por un monto de $ 5.000 (esto lo necesita nuestro personaje para dar un mensaje de que no todo se puede en la vida)

Nuevas acciones pueden agregarse a futuro, queremos que se puedan agregar o quitar fácilmente.

## <a name="_d28xfkuxb6zk"></a>**Qué se pide**
- Debe resolver los 3 puntos, marcando explícitamente los métodos donde comienzan cada uno de ellos. Para el punto 1, se pide que implemente los criterios marqueras, combinetas y uno de los 3 restantes.
- Realice un diagrama de clases de la solución general.
- Explique brevemente qué ideas de diseño surgieron, qué alternativas surgieron y por qué se decidió por la solución que presentó.

# Diagrama de flujo:

![](modeloParcialNavidadALGO2.png)

# Explicaicon de diseño:

## Patrones encontrados:

- estrategy (class Persona)
- Composite (interface Preferenicas)
- template Method (abstract class Regalos)
- Observer (Acciones)

# Timepo completo de desarrollo

![](timeParcial.png)
