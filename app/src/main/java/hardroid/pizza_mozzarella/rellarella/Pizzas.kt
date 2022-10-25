package hardroid.pizza_mozzarella.rellarella

// Pizzas class just save data
//"data" clause that says this class is a constructor of the data
data class Pizzas(
    val id:  Long,
    val photo: String,
    val name: String,
    val description: String,
    val price: Double
)