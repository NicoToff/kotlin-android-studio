fun main() {
    val hut = Hut() // Args are optional @see Hut.kt
    println(hut)
    hut.addResident(Resident("Alice", 24))
    println(hut)

    var (inhabitants, capacity, buildingMaterial) = hut // Destructuring is possible if componentN function are defined.
    println("A hut is made with ${buildingMaterial}. This one has ${inhabitants.size} residents and its capacity is ${capacity}.")

    val nico = Resident("Nico", 33) // Data classes automatically have componentN functions defined.
    hut.addResident(nico)
    val (n, a) = nico // The name of the new variables doesn't have to match those in the class
    println("$n is $a! He's our new resident.")

    val bob = Resident("Bob", 31)

    val roundHut = RoundHut(arrayListOf(nico, bob), 5.0, BuildingMaterial.CONCRETE, 10)

    with(roundHut) {
        println("The floor area of the round hut is ${"%.2f".format(floorArea())} m².")
        println("The building material is $buildingMaterial")
        println("The capacity is $capacity")
        println("The residents of the hut are ${residents.joinToString { it.name }}")
    }
}