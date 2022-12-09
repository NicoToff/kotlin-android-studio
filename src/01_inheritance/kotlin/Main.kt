fun main() {
    val hut = Hut() // Args are optional @see Hut.kt
    println(hut)
    hut.addResident(Resident("Alice", 24))
    println(hut)

    var (residents, capacity, buildingMaterial) = hut // Destructuring is possible if componentN function are defined.
    println("A hut is made with ${buildingMaterial}.")
    println("This one has ${residents.size} residents and its capacity is ${capacity}.")

    val nico = Resident("Nico", 33) // Data classes automatically have componentN functions defined.
    hut.addResident(nico)
    val (n, a) = nico // The name of the new variables doesn't have to match those in the class
    println("$n is $a!")
}