package hardroid.pizza_mozzarella.rellarella.recycler_views_adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import hardroid.pizza_mozzarella.rellarella.PizzasAdapter
import hardroid.pizza_mozzarella.rellarella.R
import hardroid.pizza_mozzarella.rellarella.model.SpecialPromotionModel

class SpecialPromotionAdapter (private val context: Context, private val PromotionsList: List<SpecialPromotionModel>) : RecyclerView.Adapter<SpecialPromotionAdapter.SpecialPromotionViewHolder>() {

    class SpecialPromotionViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
        lateinit var PromotionPhoto: ImageView
        init {
            super.itemView
            PromotionPhoto = itemView.findViewById(R.id.special_promotion_photo)
        }
    }

    override fun getItemCount(): Int {
        return  PromotionsList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecialPromotionViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item_special_promotion, parent, false)
        return SpecialPromotionAdapter.SpecialPromotionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecialPromotionViewHolder, position: Int) {
        val CurrentPromotion = PromotionsList[position]
        if (CurrentPromotion.photo.isNotBlank()) {
            Glide.with(holder.PromotionPhoto.context)
                .load(CurrentPromotion.photo)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .fitCenter()
                .placeholder(R.drawable.sale_icon)
                .error(R.drawable.sale_icon)
                .into(holder.PromotionPhoto)
        }else{
            holder.PromotionPhoto.setImageResource(R.drawable.sale_icon)
        }
    }


}