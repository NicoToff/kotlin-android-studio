abstract class Dwelling(private var residents: Array<Resident>) {
    abstract val buildingMaterial: BuildingMaterial
    abstract val capacity: Int

    fun hasRoom(): Boolean {
        return capacity > residents.size
    }

    fun addResident(resident: Resident) {
        if (hasRoom()) residents += resident
    }

    abstract fun floorArea(): Double


    override fun toString(): String {
        return "${residents.size} / ${capacity}${if (!hasRoom()) " (FULL)" else ""}"
    }

    operator fun component1(): Array<Resident> {
        return (residents)
    }

    operator fun component2(): Int {
        return (capacity)
    }

    operator fun component3(): BuildingMaterial {
        return (buildingMaterial)
    }


}