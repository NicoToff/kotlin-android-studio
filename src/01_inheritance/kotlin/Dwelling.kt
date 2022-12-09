abstract class Dwelling(val residents: ArrayList<Resident>) {
    abstract val buildingMaterial: BuildingMaterial
    abstract val capacity: Int

    fun hasRoom(): Boolean {
        return capacity > residents.size
    }

    fun addResident(resident: Resident) {
        if (hasRoom()) residents.add(resident)
    }

    abstract fun floorArea(): Double


    override fun toString(): String {
        return "${residents.size} / ${capacity}${if (!hasRoom()) " (FULL)" else ""}"
    }

    operator fun component1(): ArrayList<Resident> {
        return (residents)
    }

    operator fun component2(): Int {
        return (capacity)
    }

    operator fun component3(): BuildingMaterial {
        return (buildingMaterial)
    }


}