package hardroid.pizza_mozzarella.rellarella.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hardroid.pizza_mozzarella.rellarella.R
import hardroid.pizza_mozzarella.rellarella.recycler_views_adapters.RecyclerViewInterface

class IngredientsAdapter(
    private val context: Context,
    private var IngredientsList: List<Ingredient> ) : RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {

    fun UpdateListElements(newListItems: List<Ingredient>){
        this.IngredientsList = newListItems
    }

    inner class IngredientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var Text:TextView
        init {
            super.itemView
            Text = itemView.findViewById(R.id.ingredient)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.ingredient_item, parent, false)
        return  IngredientsViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val CurrentItem = IngredientsList[position]
        holder.Text.text = "name: ${CurrentItem.IngredientName}\ncost: $${CurrentItem.IngredientPrice}"
    }

    override fun getItemCount(): Int {
       return  IngredientsList.size
    }
}