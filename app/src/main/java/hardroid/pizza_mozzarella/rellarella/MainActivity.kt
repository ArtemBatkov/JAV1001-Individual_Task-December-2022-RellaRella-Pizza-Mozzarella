package hardroid.pizza_mozzarella.rellarella

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import hardroid.pizza_mozzarella.rellarella.model.PizzaModel
import hardroid.pizza_mozzarella.rellarella.model.PizzaService
import hardroid.pizza_mozzarella.rellarella.model.SpecialPromotionService
import hardroid.pizza_mozzarella.rellarella.recycler_views_adapters.SpecialPromotionAdapter

class MainActivity : AppCompatActivity() {

    private var PizzaModels: List<PizzaModel> = listOf()
    val PizzaService: PizzaService = PizzaService()

    val SpecialPromotionService = SpecialPromotionService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize
        setUpPizzaModels()



        var RecyclerView:RecyclerView =  findViewById(R.id.recycler_view)

        var PizzaAdapter = PizzasAdapter(this,PizzaModels)

        RecyclerView.adapter = PizzaAdapter

        RecyclerView.layoutManager = LinearLayoutManager(this)


        //promotions
//        var PromotionRecycler: RecyclerView = findViewById(R.id.special_promotion_recycler_view)
//        var SpecialPromotionAdapter = SpecialPromotionAdapter(this,SpecialPromotionService.getPromotionsList())
//        PromotionRecycler.adapter = SpecialPromotionAdapter
//        PromotionRecycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

    }

    private fun setUpPizzaModels(){
        PizzaModels = PizzaService.getPizza()
    }


}