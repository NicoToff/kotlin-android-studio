class Hut : Dwelling(0) {
    override val buildingMaterial = "Wood"
    override val capacity = 3

    override fun toString(): String {
        return "Hut has ${super.toString()} residents"
    }
}