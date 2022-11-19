package hardroid.pizza_mozzarella.rellarella

import android.app.Application
import hardroid.pizza_mozzarella.rellarella.model.PizzaService

class App : Application(){
    val pizzaService = PizzaService()



     object AppLastPositionRecyclers{
        private var LastPositionRV:Int = 0

        fun setLastPosition(position:Int){
            LastPositionRV = position
        }

        fun getLastPosition(): Int {
            return LastPositionRV
        }
    }



}