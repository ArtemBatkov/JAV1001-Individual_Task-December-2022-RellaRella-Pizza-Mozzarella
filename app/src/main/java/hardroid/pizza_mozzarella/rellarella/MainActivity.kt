package hardroid.pizza_mozzarella.rellarella

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import hardroid.pizza_mozzarella.rellarella.model.PizzaModel
import hardroid.pizza_mozzarella.rellarella.model.PizzaService
import hardroid.pizza_mozzarella.rellarella.model.SpecialPromotionService
import hardroid.pizza_mozzarella.rellarella.recycler_views_adapters.PizzaSelected
import hardroid.pizza_mozzarella.rellarella.recycler_views_adapters.RecyclerViewInterface
import hardroid.pizza_mozzarella.rellarella.recycler_views_adapters.SpecialPromotionAdapter
import kotlinx.android.parcel.Parcelize


class MainActivity : AppCompatActivity() , RecyclerViewInterface  {



    private var PizzaModels: List<PizzaModel> = listOf()
    val PizzaService: PizzaService = PizzaService()

    val SpecialPromotionService = SpecialPromotionService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialize
        setUpPizzaModels()

        var RecyclerView:RecyclerView =  findViewById(R.id.recycler_view)

        var PizzaAdapter = PizzasAdapter(this,PizzaModels,this)

        RecyclerView.adapter = PizzaAdapter

        RecyclerView.layoutManager = LinearLayoutManager(this)


        //check is last position suits to recyclerview position
        val last_position_pizza : Int = if (App.AppLastPositionRecyclers.getLastPosition()!=androidx.recyclerview.widget.RecyclerView.NO_POSITION){
             App.AppLastPositionRecyclers.getLastPosition()
        }else{
             0
        }
        RecyclerView.scrollToPosition(last_position_pizza)

    }

    private fun setUpPizzaModels(){
        PizzaModels = PizzaService.getPizza()
    }

    override fun OnItemClick(position: Int) {
        App.AppLastPositionRecyclers.setLastPosition(position)
        try{
            val intent = Intent(this@MainActivity, PizzaSelected::class.java)
            intent.putExtra("PIZZA_PHOTO",PizzaModels.get(position).photo)
            intent.putExtra("PIZZA_TITLE",PizzaModels.get(position).name)
            intent.putExtra("PIZZA_DESCRIPTION",PizzaModels.get(position).description)
            intent.putExtra("PIZZA_PRICE",PizzaModels.get(position).price)

            var bundle_dunble: Bundle = Bundle();
            bundle_dunble.putParcelableArray("PIZZA_INGREDIENTS", PizzaModels.get(position).original_ingredients.toTypedArray())

            intent.putExtra("data",bundle_dunble)
            val bundle:Bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            startActivity(intent,bundle)
            finish()
        }
        catch (_:Exception){}
    }


}