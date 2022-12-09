class Hut(residents: ArrayList<Resident> = arrayListOf(), val width: Double = 3.0) : Dwelling(residents) {
    override val buildingMaterial = BuildingMaterial.WOOD
    override val capacity = 3
    override fun floorArea(): Double {
        return width * width
    }

    override fun toString(): String {
        return "Hut has ${super.toString()} residents living within ${this.floorArea()} m²."
    }
}