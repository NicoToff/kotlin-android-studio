abstract class Dwelling(private var residents: Int) {
    abstract val buildingMaterial: String
    abstract val capacity: Int

    fun hasRoom(): Boolean {
        return capacity > residents
    }

    fun addResident(nbr : Int = 1) {
        if(hasRoom()) residents += nbr
        if(residents > capacity) residents = capacity
    }

    override fun toString(): String {
        return "$residents / ${capacity}${if(!hasRoom()) " (FULL)" else ""}"
    }

    operator fun component1(): Int {
        return (residents)
    }

    operator fun component2(): Int {
        return (capacity)
    }

    operator fun component3(): String {
        return (buildingMaterial)
    }


}