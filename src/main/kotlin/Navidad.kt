import java.time.LocalDate

object PAPANOEL{
    val regalos = mutableListOf<Regalos>()
}
class Personas(){
    lateinit var preferencia : Preferencias

}

interface Preferencias {
    fun aceptaRegalo(regalo: Regalos) : Boolean
}

class Conformistas():Preferencias{
    override fun aceptaRegalo(regalo: Regalos) = true
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
//TODO: codear las otras preferencias

abstract class Regalos(var precio:Double, val marca: String) {
    val UMBRAL = 5000
    fun esValioso() = (precio > UMBRAL) && condiconEspecifica()
    abstract fun condiconEspecifica():Boolean
}
class Ropa(precio: Double, marca: String):Regalos(precio, marca){
    var marcasValiosas = mutableListOf<String>("Jordache", "Lee", "Charro", "Motor Oil")

    override fun condiconEspecifica() = marcasValiosas.contains(marca)
}
class Juguetes(precio: Double, marca: String,val fechaLanzamineto : LocalDate):Regalos(precio, marca){
    val ANIO_JUGUETE_VALIOSO = 2000

    override fun condiconEspecifica() = fechaLanzamineto.year < ANIO_JUGUETE_VALIOSO
}
class Perfumes(precio: Double, marca: String,val esImportado:Boolean) : Regalos(precio, marca){
    override fun condiconEspecifica() = esImportado

}
class Experiencias(precio: Double, marca: String,val fechaDeEntrega:LocalDate):Regalos(precio, marca){
    val DIA_VALIOSO_ENTREGA = 5
    override fun condiconEspecifica() = fechaDeEntrega.dayOfWeek.value == DIA_VALIOSO_ENTREGA
}
