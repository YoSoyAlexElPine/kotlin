package Modelo

/**
 * Objeto singleton utilizado para almacenar diferentes listas de objetos Card.
 *
 * @property cards Lista mutable para almacenar objetos Card genéricos.
 * @property camiones Lista mutable para almacenar objetos Card representando camiones.
 * @property empleados Lista mutable para almacenar objetos Card representando empleados.
 * @property adminClaves Lista mutable para almacenar claves de administrador.
 *
 * @author Autor Alex Pineño Sanchez
 */
object Almacen {
    var cards: MutableList<Card> = mutableListOf()
    var camiones: MutableList<Card> = mutableListOf()
    var empleados: MutableList<Card> = mutableListOf()
    var adminClaves: MutableList<String> = mutableListOf()
    var viajes: MutableList<Card> = mutableListOf()
    var recordatorios: MutableList<Card> = mutableListOf()
}
