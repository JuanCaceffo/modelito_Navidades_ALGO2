import java.time.LocalDate
import kotlin.Exception

object PAPANOEL{
    val regalosEnStock = mutableListOf<Regalos>()
    val registroRegalados = mutableListOf<Regalos>()
    val acciones = mutableSetOf<Acciones>()
    val MAIL = "papauel@polo.com"

    fun encontrarRegaloPara(persona: Personas) : Regalos = regalosEnStock.find { persona.aceptarRegalo(it) } ?: Voucher(2000.0, "Papapp")
    fun entregarRegalo(persona: Personas){
        val REGALO_PARA_PERSONA = encontrarRegaloPara(persona)
        persona.recibirRegalo(REGALO_PARA_PERSONA)
        borrarRegalo(REGALO_PARA_PERSONA)
        registrarRegaloEntregado(persona,REGALO_PARA_PERSONA)
    }
    fun registrarRegaloEntregado(persona: Personas,regalo: Regalos){
        validarRegaloEntregadoAntesDeRegistrar(regalo)
        registroRegalados.add(regalo)
        acciones.forEach { it.ejecutar(persona,regalo) }
    }
    fun borrarRegalo(regalo: Regalos){
        regalosEnStock.remove(regalo)
    }
    fun agregarAccion(accion:Acciones){
        acciones.add(accion)
    }
    fun quitarAccion(accion:Acciones){
        validarDesactivarAccion(accion)
        acciones.remove(accion)
    }
    //validaciones
    fun validarDesactivarAccion(accion: Acciones){
        if (acciones.contains(accion)){
            throw Exception("Para desactivar la accion debe habrse activado con anterioridad")
        }
    }
    fun validarRegaloEntregadoAntesDeRegistrar(regalo:Regalos){
        if(regalosEnStock.contains(regalo)){
            throw Exception("El regalo que queire registrar como entrregado no a sido entregado")
        }
    }
}

interface Acciones {
    fun ejecutar(persona:Personas,regalo: Regalos)
}
class MandarMail(val mailSender:MailSender):Acciones{
    override fun ejecutar(persona:Personas,regalo: Regalos) {
        val MAIL_PERSONA = Mail(
            from = PAPANOEL.MAIL,
            to = persona.datos.mail,
            content = """
                Tenemos el regalo perfecto para vos!.
                una vez que tengas el regalo en tus manos sacale una foto y subilo a tus redes
                att: papauel
            """.trimIndent(),
            subject = "Regalo navideño para ${persona.datos.mail}"
        )
        mailSender.sendEmail(MAIL_PERSONA)
    }

}
class InformarEnvio():Acciones{
    override fun ejecutar(persona: Personas,regalo: Regalos) {
        val datosInforme = DatosEnvio(
            direccion = persona.datos.direccion,
            nombre = persona.datos.nombre,
            DNI = persona.datos.DNI,
            codigoRegalo = regalo.CODIGO
        )
        elRenoLoco.recibirInforme(datosInforme)
    }
}
class CambiarCriterioAInteresado():Acciones{
    val UMBRAL_CAMBIO = 10000
    override fun ejecutar(persona: Personas, regalo: Regalos) {
        if (regalo.precio > UMBRAL_CAMBIO){
            persona.cambiarPreferencia(Interesadas(5000.0))
        }
    }
}

data class DatosPersonales(
    val nombre:String,
    val direccion:String,
    val DNI:String,
    val mail:String
)
class Personas(val datos: DatosPersonales,var preferencia : Preferencias){
    private val regalos = mutableListOf<Regalos>()

    fun aceptarRegalo(regalo: Regalos) = preferencia.aceptaRegalo(regalo)

    fun recibirRegalo(regalo: Regalos) {
        regalos.add(regalo)
    }
    fun cambiarPreferencia(nuevaPreferencia: Preferencias){
        preferencia = nuevaPreferencia
    }
}

interface Preferencias {
    fun aceptaRegalo(regalo: Regalos) : Boolean
}

class Conformistas():Preferencias{
    override fun aceptaRegalo(regalo: Regalos) = true
}
class Interesadas(var umbralDinero: Double = 8000.0):Preferencias{
    override fun aceptaRegalo(regalo: Regalos) = regalo.precio > umbralDinero
    fun setUmbralDinero(umbral:Double){
        umbralDinero = umbral
    }

}
class Exigentes():Preferencias{
    override fun aceptaRegalo(regalo: Regalos) = regalo.esValioso()

}
class Marqueras(var marcaDeInteres:String):Preferencias{
    fun setMarcaDeInteres(marca:String){
        marcaDeInteres = marca
    }
    override fun aceptaRegalo(regalo: Regalos) = regalo.marca == marcaDeInteres
}
class Combinetas():Preferencias{
    val preferencias = mutableSetOf<Preferencias>()
    override fun aceptaRegalo(regalo: Regalos) = preferencias.any { it.aceptaRegalo(regalo) }

}

abstract class Regalos(var precio:Double, val marca: String,) {
    val CODIGO = Regalos::class.toString()
    val UMBRAL = 5000
    open fun esValioso() = (precio > UMBRAL) && condiconEspecifica()
    abstract fun condiconEspecifica():Boolean
}
class Ropa(precio: Double, marca: String):Regalos(precio, marca){
    var marcasValiosas = mutableListOf<String>("Jordache", "Lee", "Charro", "Motor Oil")

    override fun condiconEspecifica() = marcasValiosas.contains(marca)
}
class Juguetes(precio: Double, marca: String, val fechaLanzamineto : LocalDate):Regalos(precio, marca){
    val ANIO_JUGUETE_VALIOSO = 2000

    override fun condiconEspecifica() = fechaLanzamineto.year < ANIO_JUGUETE_VALIOSO
}
class Perfumes(precio: Double, marca: String, val esImportado:Boolean) : Regalos(precio, marca){
    override fun condiconEspecifica() = esImportado

}
class Experiencias(precio: Double, marca: String, val fechaDeEntrega:LocalDate):Regalos(precio, marca){
    val DIA_VALIOSO_ENTREGA = 5
    override fun condiconEspecifica() = fechaDeEntrega.dayOfWeek.value == DIA_VALIOSO_ENTREGA
}
class Voucher(precio: Double, marca: String) : Regalos(precio, marca){
    override fun esValioso() = false
    override fun condiconEspecifica()  = false
}
//codigo externo a lo principal

interface MailSender {
    fun sendEmail(mail:Mail)
}

data class Mail(
    val from:String,
    val to:String,
    val subject:String,
    val content:String
)
data class DatosEnvio(
    val direccion: String,
    val nombre: String,
    val DNI: String,
    val codigoRegalo: String
)
object elRenoLoco{
    val informeEnvios = mutableListOf<DatosEnvio>()

    fun recibirInforme(datosEnvio: DatosEnvio){
        informeEnvios.add(datosEnvio)
    }
}
