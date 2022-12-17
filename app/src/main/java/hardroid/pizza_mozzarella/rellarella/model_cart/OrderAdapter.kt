package hardroid.pizza_mozzarella.rellarella.model_cart

import android.graphics.Paint
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import hardroid.pizza_mozzarella.rellarella.R
import hardroid.pizza_mozzarella.rellarella.databinding.IngredientItemBinding
import hardroid.pizza_mozzarella.rellarella.databinding.OrderItemBinding
import hardroid.pizza_mozzarella.rellarella.model.IngredientsAdapter
import hardroid.pizza_mozzarella.rellarella.orders
import hardroid.pizza_mozzarella.rellarella.prefs

interface OrderActionListener{
    fun onOrderDeleted(order: Order)
    fun onOrderPlus(order: Order)
    fun onOrderMinus(order: Order)
}

class OrderAdapter(
    private val orderListener: OrderActionListener
) : RecyclerView.Adapter<OrderAdapter.OrdersViewHolder>(), View.OnClickListener {

    private var PositionGlobal = 0;
    private lateinit var GlobalBinding: OrderItemBinding;
    class OrdersViewHolder(val binding: OrderItemBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: OrderItemBinding = OrderItemBinding.inflate(inflater, parent, false)
        //listeners
        binding.root.setOnClickListener(this)
        binding.orderDelete.setOnClickListener(this)
        binding.orderPlus.setOnClickListener(this)
        binding.orderMinus.setOnClickListener(this)
        return OrdersViewHolder(binding)
    }



    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val CurrentOrder = orders.getOrderList().get(position)

        with(holder.binding) {
            holder.itemView.tag = CurrentOrder
            orderDelete.tag = CurrentOrder
            orderPlus.tag = CurrentOrder
            orderMinus.tag = CurrentOrder

            val Pref = prefs.getPref()
            val keySP = prefs.keySpecialPromotion
            var HasDiscount = false
            if (Pref != null) {
                HasDiscount = Pref.getBoolean(keySP, false)
            }
            if(HasDiscount){
                orders.setNewPrice(CurrentOrder, CurrentOrder.order_old_price*0.5)
            } else{
                orders.setNewPrice(CurrentOrder, CurrentOrder.order_old_price)
            }

            orderIngredients.text = CurrentOrder.order_ingredients.joinToString{it.IngredientName}
            orderQuantity.text = CurrentOrder.order_quantity.toString()
            priceOrder.text = if (CurrentOrder.order_new_price < CurrentOrder.order_old_price){
//                priceOrder.paintFlags = priceOrder.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                priceOrder.text = ConvertPrice(CurrentOrder.order_old_price)
                val message = "${ConvertPrice(CurrentOrder.order_old_price)}\n${ConvertPrice(CurrentOrder.order_new_price)}";
                val spannableString:SpannableString = SpannableString(message)
                val position = message.indexOf("\n")
                spannableString.setSpan(StrikethroughSpan(),0,position, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString
            }else{
                priceOrder.paintFlags = 0
                ConvertPrice(CurrentOrder.order_old_price)
            }


            if (CurrentOrder.order_photo.isNotBlank()) {
                Glide.with(orderPhoto)
                    .load(CurrentOrder.order_photo)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .centerInside()
                    .placeholder(R.drawable.ic_default_pizza)
                    .error(R.drawable.ic_default_pizza)
                    .into(orderPhoto)
            }
            else {
                orderPhoto.setImageResource(R.drawable.ic_default_pizza)
            }

            orderMinus.isEnabled = CurrentOrder.order_quantity != 1
        }
    }

    override fun getItemCount(): Int {
       return orders.getOrderList().count()
    }

    override fun onClick(v: View?) {
        val order = v?.tag as Order
        when(v.id) {
            R.id.order_delete -> {
                orderListener.onOrderDeleted(order)
            }
            R.id.order_minus -> {
                orderListener.onOrderMinus(order)
            }
            R.id.order_plus ->{
                orderListener.onOrderPlus(order)
            }
    }
}

    private fun ConvertPrice(price:Double):String{
        return "$${if(price%1.0==0.0) price.toInt() else price}"
    }



}