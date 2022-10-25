package hardroid.pizza_mozzarella.rellarella


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import hardroid.pizza_mozzarella.rellarella.databinding.ItemPizzaBinding


//Adapter creates ViewHolder objects as needed and sets the data for those views
//The process of associating view to their data is called binding
class PizzasAdapter : RecyclerView.Adapter<PizzasAdapter.UsersViewHolder>() {

    //ViewHolder is a wrapper around a View that contains the layout for an individual item in the list
    class UsersViewHolder(val binding: ItemPizzaBinding):RecyclerView.ViewHolder(binding.root)

    var pizzas: List<Pizzas> = emptyList()
        set(newValue){ //notify recycler view
            field = newValue
            notifyDataSetChanged()
        }






    //How much elements?
    override fun getItemCount(): Int {
        return pizzas.size
    }

    //Create new element of the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemPizzaBinding = ItemPizzaBinding.inflate(inflater,parent,false)
        return  UsersViewHolder(binding)
     }

    //Update List
    //Also associate view with data
    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val pizza: Pizzas = pizzas[position]
        with(holder.binding){
            pizzaDescription.text = pizza.description
            pizzaTitle.text = pizza.name
            var price_rnd = pizza.price
            pizzaPrice.text = "$${if (price_rnd%1.0==0.0) pizza.price.toInt().toString() else pizza.price.toString()}"
            /*The main problem of onBindView is that
            WE HAVE TO UPlOAD DATA IN ALL IF/ELSE BRANCHES
            because we can have bags:

            -Each phone has an individual screen
            -So if one can show 10 view, a larger phone is able to show 12
            -But RecyclerView cached 10 views, so this(or these) element(s) is(are) used twice
            -We have to update in both branches
             */
            if(pizza.photo.isNotBlank()){
                    Glide.with(pizzaPhoto.context)
                        .load(pizza.photo)
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                        .circleCrop()
                        .placeholder(R.drawable.ic_default_pizza)
                        .error(R.drawable.ic_default_pizza)
                        .into(pizzaPhoto)
            }else{
                 pizzaPhoto.setImageResource(R.drawable.ic_default_pizza)
            }

        }
    }


}