package hardroid.pizza_mozzarella.rellarella


import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import hardroid.pizza_mozzarella.rellarella.databinding.ItemPizzaBinding
import hardroid.pizza_mozzarella.rellarella.model.PizzaModel


//Adapter creates ViewHolder objects as needed and sets the data for those views
//The process of associating view to their data is called binding
class PizzasAdapter(private val context: Context, private val PizzaList: List<PizzaModel>) : RecyclerView.Adapter<PizzasAdapter.PizzaViewHolder>(), Parcelable {









    //ViewHolder is a wrapper around a View that contains the layout for an individual item in the list

    class PizzaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       lateinit var PizzaPhoto: ImageView
       lateinit var PizzaTitle: TextView;
       lateinit var PizzaDescription: TextView;
       lateinit var PizzaPrice: TextView;
       lateinit var PizzaButton: Button;
       init {
            super.itemView
            PizzaPhoto = itemView.findViewById(R.id.pizza_photo)
            PizzaTitle = itemView.findViewById(R.id.pizza_title)
            PizzaDescription = itemView.findViewById(R.id.pizza_description)
            PizzaPrice = itemView.findViewById(R.id.pizza_price)
            PizzaButton = itemView.findViewById(R.id.pizza_button)

        }
    }

//    var pizzas: List<PizzaModel> = emptyList()
//        set(newValue){ //notify recycler view
//            field = newValue
//            notifyDataSetChanged()
//        }


    constructor(parcel: Parcel) : this(
        TODO("context"),
        TODO("PizzaList")
    ) {
    }

    //How much elements?
    override fun getItemCount(): Int {
        return this.PizzaList.size
    }

    //Create new element of the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
//        val binding: ItemPizzaBinding = ItemPizzaBinding.inflate(inflater,parent,false)
        val view: View = inflater.inflate(R.layout.item_pizza, parent, false)
        return  PizzaViewHolder(view)
     }


    //Update List
    //Also associate view with data
    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {
        val CurrentPizza = PizzaList[position]
        if (CurrentPizza.photo.isNotBlank()) {
            Glide.with(holder.PizzaPhoto.context)
                .load(CurrentPizza.photo)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .circleCrop()
                .placeholder(R.drawable.ic_default_pizza)
                .error(R.drawable.ic_default_pizza)
                .into(holder.PizzaPhoto)
        }
        else {
            holder.PizzaPhoto.setImageResource(R.drawable.ic_default_pizza)
        }
        holder.PizzaTitle.text = CurrentPizza.name
        holder.PizzaDescription.text = CurrentPizza.description
        holder.PizzaPrice.text = "$${if (CurrentPizza.price%1.0==0.0) CurrentPizza.price.toInt().toString() else CurrentPizza.price.toString()}"
    }






//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<PizzasAdapter> {
//        override fun createFromParcel(parcel: Parcel): PizzasAdapter {
//            return PizzasAdapter(parcel)
//        }
//
//        override fun newArray(size: Int): Array<PizzasAdapter?> {
//            return arrayOfNulls(size)
//        }
//    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PizzasAdapter> {
        override fun createFromParcel(parcel: Parcel): PizzasAdapter {
            return PizzasAdapter(parcel)
        }

        override fun newArray(size: Int): Array<PizzasAdapter?> {
            return arrayOfNulls(size)
        }
    }


}