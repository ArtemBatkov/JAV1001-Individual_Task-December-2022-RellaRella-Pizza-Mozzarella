package hardroid.pizza_mozzarella.rellarella

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hardroid.pizza_mozzarella.rellarella.model_cart.Order
import hardroid.pizza_mozzarella.rellarella.model_cart.OrderActionListener
import hardroid.pizza_mozzarella.rellarella.model_cart.OrderAdapter
import hardroid.pizza_mozzarella.rellarella.model_cart.OrderList
import java.util.*


class Cart : AppCompatActivity()  {

    private lateinit var OrderRecyclerView: RecyclerView
    private lateinit var CartOrderAdapter: OrderAdapter
    private lateinit var OverallPrice: TextView
    private lateinit var MakeOrder: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cart_layout)

        OrderRecyclerView = findViewById(R.id.order_recyclerview)
        OverallPrice = findViewById(R.id.overall_price)
        MakeOrder = findViewById(R.id.confirm_order)

        CartOrderAdapter = OrderAdapter(object : OrderActionListener{
            override fun onOrderDeleted(order: Order) {
                val position = orders.getOrderPosition(order)
                if(position != -1){
                    orders.deleteOrder(order)
                    CartOrderAdapter.notifyItemRemoved(position)
                    CalculateOverallPrice()
                }
                else{
                    CartOrderAdapter.notifyDataSetChanged()
                }

            }

            override fun onOrderPlus(order: Order) {
                val position = orders.getOrderPosition(order)
                if(position != -1){
                    val currentValue = orders.getQuantity(order)
                    if(currentValue != -1){
                        orders.setQuantity(order,currentValue+1)
                        CartOrderAdapter.notifyItemChanged(position)
                        CalculateOverallPrice()
                    }
                }
                else{
                    CartOrderAdapter.notifyDataSetChanged()
                }
            }

            override fun onOrderMinus(order: Order) {
                val position = orders.getOrderPosition(order)
                if(position != -1){
                    val currentValue = orders.getQuantity(order)
                    if(currentValue != -1){
                        val new_quantity = if(currentValue-1<0){
                            1
                        } else {
                            currentValue-1
                        }
                        orders.setQuantity(order,new_quantity)
                        CartOrderAdapter.notifyItemChanged(position)
                        CalculateOverallPrice()
                    }
                    else{
                        CartOrderAdapter.notifyDataSetChanged()
                    }
                }
            }

        })

        OrderRecyclerView.adapter = CartOrderAdapter
        OrderRecyclerView.layoutManager = LinearLayoutManager(this)
        CalculateOverallPrice()
//        LoadData()
        MakeOrder.setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
               if(orders.getOrderList().count()>0){
                   val builder = AlertDialog.Builder(this@Cart)
                   builder.setCancelable(true)
                   builder.setMessage("You order for ${OverallPrice.text} was created!")
                   builder.create().show()
                   orders.clearList()
               }
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        gotoMainActivity()
    }

    fun gotoMainActivity(){
        try{
            var intent = Intent(this@Cart, MainActivity::class.java)
            startActivity(intent)
            finish()
        }catch (_:Exception){}
    }

    fun CalculateOverallPrice(){
        val list = orders.getOrderList()
        var sum = 0.0
        for (i in 0 until list.count()){
            sum += list[i].order_new_price * list[i].order_quantity
        }
        OverallPrice.setText("${ConvertPrice(sum)}")
    }

    fun ConvertPrice(price:Double):String{
        return "$ ${if(price%1.0==0.0) price.toInt() else price}"
    }

    private fun SaveOrders(){
        val Pref = prefs.getPref()
        if (Pref != null) {
            val keyOrders = prefs.keyOrders
            prefs.put(orders.getOrderList(),keyOrders)
        }
    }

    override fun onPause() {
        super.onPause()
//        SaveOrders()
    }

    private fun LoadData() {
        val Pref = prefs.getPref()
        val keySP = prefs.keySpecialPromotion
        if (Pref != null) {
            val keyOrder = prefs.keyOrders
            val listOrders: List<Order>? = prefs.get<List<Order>>(keyOrder)

            var builder = GsonBuilder()
            val gson = builder.create()
            Log.i("GSON", gson.toJson(listOrders?.take(1)));
            var message = "{\"order_ingredients\":[{\"IngredientID\":1.0,\"IngredientName\":\"Italian sausages\",\"IngredientPrice\":1.2},{\"IngredientID\":5.0,\"IngredientName\":\"Bacon\",\"IngredientPrice\":1.0},{\"IngredientID\":14.0,\"IngredientName\":\"Mozzarella rella rella\",\"IngredientPrice\":2.5},{\"IngredientID\":11.0,\"IngredientName\":\"3 cheese blend\",\"IngredientPrice\":1.5}],\"order_new_price\":55.0,\"order_old_price\":55.0,\"order_photo\":\"https://lh3.googleusercontent.com/pw/AL9nZEUzXvAWnS4djr16cxX3zovVFa0-GiQt9IzzsIvHS4A4lTGCPCGKCxJCA7Zx-XL7eKVZhuv3rGt8KWoYE8dBABWvuLlscdMQ7D-BR3dDcu1sGDpNbhKUxpEdXMqwEuUBen9yK7Cg7ZpTZjBCaldU0Kdk\\u003dw728-h410-no?authuser\\u003d0\",\"order_quantity\":1.0}]"

//            val some = stringToArray(message,OrderList).get(0).getName()

            if (listOrders != null) {

            }
            var a = 3
            print(a)
        }
    }

//    fun <T> stringToArray(s: String?, clazz: Class<Array<T>>?): MutableList<Array<T>> {
//        val arr = Gson().fromJson(s, clazz)
//        return Arrays.asList(arr) //or return Arrays.asList(new Gson().fromJson(s, clazz)); for a one-liner
//    }

}