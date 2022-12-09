fun main() {
    val hut = Hut() // Defaults to 0, like Java

    println(hut)
    hut.addResident(1)
    println(hut)

    val (residents, capacity, buildingMaterial) = hut // Destructuring is possible if componentN function are defined.
    println("A hut is made with ${buildingMaterial}.")
    println("This one has $residents residents and its capacity is ${capacity}.")

    data class Resident(val name: String, var age: Int) // Data classes automatically have componentN functions
    val nico = Resident("Nico", 33)
    val (n, a) = nico // The name of the new variables doesn't have to match those in the class
    println("$n is $a")
}