package hardroid.pizza_mozzarella.rellarella

import android.app.Application
import hardroid.pizza_mozzarella.rellarella.model.PizzaService

class App : Application(){
    val pizzaService = PizzaService()
}