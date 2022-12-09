import java.lang.Math.PI

class RoundHut(
    residents: ArrayList<Resident> = arrayListOf(),
    private val radius: Double = 2.0,
    override val buildingMaterial: BuildingMaterial = BuildingMaterial.STRAW,
    override val capacity: Int = 4
) : Dwelling(residents) {
    override fun floorArea(): Double {
        return PI * radius * radius
    }
}