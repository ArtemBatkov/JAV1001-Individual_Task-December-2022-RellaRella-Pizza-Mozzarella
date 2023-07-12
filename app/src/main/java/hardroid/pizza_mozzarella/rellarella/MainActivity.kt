package hardroid.pizza_mozzarella.rellarella

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.joanzapata.iconify.widget.IconButton
import hardroid.pizza_mozzarella.rellarella.App.Companion.getCount
import hardroid.pizza_mozzarella.rellarella.recycler_views_adapters.PizzaSelected
import hardroid.pizza_mozzarella.rellarella.recycler_views_adapters.RecyclerViewInterface
import  hardroid.pizza_mozzarella.rellarella.Cart
import hardroid.pizza_mozzarella.rellarella.model.*
import hardroid.pizza_mozzarella.rellarella.model_cart.Order
import hardroid.pizza_mozzarella.rellarella.model_cart.OrderList


interface PromotionClick{
    fun onClick(promotion: SpecialPromotionModel, position: Int)


}



class MainActivity : AppCompatActivity() , RecyclerViewInterface, PromotionClick {

    lateinit var service: ConnectivityManager
//    lateinit var CartButton: ImageView
//    lateinit var OrderQuantity: TextView

    private var PizzaModels: List<PizzaModel> = listOf()
    val PizzaService: PizzaService = PizzaService()

    val SpecialPromotionService = SpecialPromotionService()

    //lateinit var BackImage: MaterialCardView

    private lateinit var BottomNavigationMenu: BottomNavigationView

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        var itemMessages =  menu?.findItem(R.id.cart)
        var layout = itemMessages?.actionView //as? RelativeLayout
        var textview = layout?.findViewById<TextView>(R.id.cart_quantity)
        textview?.text = orders.getOrderList().count().toString()

        var icon = layout?.findViewById<IconButton>(R.id.cart_icon)
        icon?.setText(orders.getOrderList().count().toString())
        icon?.text = ""

        icon?.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                gotoOrders()
            }
        })
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.cart -> {
                gotoOrders()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //BackImage = findViewById(R.id.backimage)

//        CartButton = findViewById(R.id.add_to_cart)
//        OrderQuantity = findViewById(R.id.cart_quantity)

//        OrderQuantity.text = orders.getOrderList().count().toString()

        BottomNavigationMenu = findViewById(R.id.bottom_menu)
        //try to update bottom menu
        val cartItem: MenuItem = BottomNavigationMenu.menu.findItem(R.id.cart)
        cartItem.title = orders.getOrderList().count().toString()


        BottomNavigationMenu.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.cart -> {
                    gotoOrders()
                    true
                }
                R.id.account -> {
                    gotoAccount()
                    true
                }
                R.id.map -> {
                    gotoMap()
                    true
                }
                else -> false
            }
        }



        service = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (!IsOnline()) {
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(false)
            builder.setMessage("Seems you don't have network access!\nConnect your device to the internet and restart the app!")
            builder.create().show()
        }

//        CartButton.setOnClickListener(object: View.OnClickListener{
//            override fun onClick(p0: View?) {
//                gotoOrders()
//            }
//
//        })

        //initialize
        setUpPizzaModels()

        var RecyclerView: RecyclerView = findViewById(R.id.recycler_view)

        var PizzaAdapter = PizzasAdapter(this, PizzaModels, this, this)

        RecyclerView.adapter = PizzaAdapter

        RecyclerView.layoutManager = LinearLayoutManager(this)


        //check is last position suits to recyclerview position
        val last_position_pizza: Int =
            if (App.AppLastPositionRecyclers.getLastPosition() != androidx.recyclerview.widget.RecyclerView.NO_POSITION) {
                App.AppLastPositionRecyclers.getLastPosition()
            } else {
                0
            }
        RecyclerView.scrollToPosition(last_position_pizza)

        LoadData()



        if(!PromotionAlreadySeen && orders.getOrderList().count() == 0){
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(true)
            builder.setMessage("Would you like to listen the \"Cheese song by Gyro Zeppeli?\" \nYou will have a 50% discount for the first order!")
            builder.setPositiveButton("Listen") {
                    dialog, id ->  gotoCheeseSong()
            }

            builder.create().show()
            App().count = 1
        }

    }



    private fun gotoOrders(){
        try {
            var intent = Intent(this, Cart::class.java)
            startActivity(intent)
            finish()
        }
        catch (ex:Exception){
            print(ex.message)
        }
    }

    private fun gotoAccount(){
        try {
            var intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }
        catch (ex:Exception){
            print(ex.message)
        }
    }

    private fun gotoMap(){
        try {
            var intent = Intent(this, MapPage::class.java)
            startActivity(intent)
            finish()
        }
        catch (ex:Exception){
            print(ex.message)
        }
    }


    private fun setUpPizzaModels() {
        PizzaModels = PizzaService.getPizza()
    }

    override fun OnItemClick(position: Int) {
        App.AppLastPositionRecyclers.setLastPosition(position)
        try {
            val intent = Intent(this@MainActivity, PizzaSelected::class.java)
            intent.putExtra("PIZZA_PHOTO", PizzaModels.get(position).photo)
            intent.putExtra("PIZZA_TITLE", PizzaModels.get(position).name)
            intent.putExtra("PIZZA_DESCRIPTION", PizzaModels.get(position).description)
            intent.putExtra("PIZZA_PRICE", PizzaModels.get(position).price)

            var bundle_dunble: Bundle = Bundle();
            bundle_dunble.putParcelableArray(
                "PIZZA_INGREDIENTS",
                PizzaModels.get(position).original_ingredients.toTypedArray()
            )

            intent.putExtra("data", bundle_dunble)
            val bundle: Bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            startActivity(intent, bundle)
            finish()
        } catch (_: Exception) {
        }
    }

    override fun onClick(promotion: SpecialPromotionModel, position: Int) {
        if (position == 0 && !PromotionAlreadySeen && IsOnline()) {
            try {
                val intent = android.content.Intent(
                    this,
                    hardroid.pizza_mozzarella.rellarella.Discount::class.java
                )

                intent.putExtra("PHOTO", promotion.photo)
                startActivity(intent)
                finish()
            } catch (ex: Exception) {
                ex.message
            }
        } else if (!IsOnline()) {
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(true)
            builder.setMessage("You are not online!")
            builder.create().show()
        } else if (PromotionAlreadySeen) {
            val builder = AlertDialog.Builder(this)
            builder.setCancelable(true)
            builder.setMessage("You've already seen the promotion! You have 50% discount!")
            builder.create().show()
        }
    }




    private var PromotionAlreadySeen = false

    private fun LoadData() {
        val Pref = prefs.getPref()
        val keySP = prefs.keySpecialPromotion
        if (Pref != null) {
            PromotionAlreadySeen = Pref.getBoolean(keySP, false)
        }
    }


    private fun IsOnline(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = service.getNetworkCapabilities(service.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                    else ->
                        return false

                }
            }
        } else {
            val activeNetworkInfo = service.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

    fun gotoCheeseSong(){
        try {
            var intent = Intent(this@MainActivity,Discount::class.java)
            intent.putExtra("PHOTO","https://lh3.googleusercontent.com/pw/AL9nZEWL4tFdEm-0LHLUiA9L1u2ZbvIf_YkcWzWnhvSpu7e9qdSADyXTBNRp7j8kim9-nOOSWgPF1jbpfhpDeZNMMpbKWmSeaDcJBPgRoCPGS_-RwX0fguQeLo-knZbaj2yO53N0E6xu6r9dLpw5dz2_eEhW=w760-h727-no?authuser=0")
            startActivity(intent)
            finish()
        }catch (_:Exception){}
    }
}
