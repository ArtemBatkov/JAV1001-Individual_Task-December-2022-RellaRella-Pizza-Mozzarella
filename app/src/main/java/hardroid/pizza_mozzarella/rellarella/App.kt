package hardroid.pizza_mozzarella.rellarella

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity

import hardroid.pizza_mozzarella.rellarella.model.PizzaService
import hardroid.pizza_mozzarella.rellarella.model_cart.Order
import hardroid.pizza_mozzarella.rellarella.model_cart.OrderList

val prefs: SharPref by lazy{
    App.prefs!!
}

val orders: OrderList by lazy {
    App.orders!!
}






class App : Application(){
    val pizzaService = PizzaService()

    var count =0

     object AppLastPositionRecyclers{
        private var LastPositionRV:Int = 0

        fun setLastPosition(position:Int){
            LastPositionRV = position
        }

        fun getLastPosition(): Int {
            return LastPositionRV
        }
    }

    companion object{
        private var count: Int = 0
        var prefs: SharPref? = null
        var orders: OrderList? = null

        fun setCount(new: Int){
            count = new
        }

        fun getCount() = count

    }



    override fun onCreate() {
        prefs = SharPref(applicationContext)
        orders = OrderList()
        count = 0
        super.onCreate()
    }


}