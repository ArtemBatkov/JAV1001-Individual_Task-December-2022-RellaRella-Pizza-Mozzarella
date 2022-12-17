package hardroid.pizza_mozzarella.rellarella.model_cart

import hardroid.pizza_mozzarella.rellarella.model.Ingredient

data class Order(
    var order_photo: String,
    var order_old_price: Double,
    var order_new_price: Double,
    var order_ingredients: List<Ingredient>,
    var order_quantity: Int
)