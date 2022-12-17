package hardroid.pizza_mozzarella.rellarella.model_cart

class OrderList() {
    private var OrderList: MutableList<Order> = mutableListOf()

    fun setOrderList(newOrderList: List<Order>){
        OrderList = newOrderList.toMutableList()
    }

    fun clearList(){
        OrderList.clear()
    }

    fun getOrderList():List<Order>{
        return OrderList.toList()
    }

    fun addNewOrder(order: Order){
        OrderList.add(order)
    }

    fun deleteOrder(order: Order){
        val indexToDelete: Int = getIndexOfOrder(order)
        if (indexToDelete != -1){
            OrderList.removeAt(indexToDelete)
        }
    }

    fun setNewPrice(order: Order, new_price: Double){
        val indexToUpdate: Int = getIndexOfOrder(order)
        if(indexToUpdate != -1){
            OrderList.get(indexToUpdate).order_new_price = new_price
        }
    }

    fun setQuantity(order: Order, quantity: Int){
        val indexQuantity: Int = getIndexOfOrder(order)
        if(indexQuantity != -1){
            OrderList.get(indexQuantity).order_quantity = quantity
        }
    }

    fun getQuantity(order: Order):Int{
        val index = getIndexOfOrder(order)
        if(index != -1){
            return OrderList.get(index).order_quantity
        }else{
            return  -1
        }
    }

    fun getOrderPosition(order: Order): Int{
         return getIndexOfOrder(order)
    }

    private fun getIndexOfOrder(order:Order): Int{
        val index: Int = OrderList.indexOfFirst {
            it.order_photo == order.order_photo &&
                    it.order_old_price == order.order_old_price &&
                    it.order_quantity == order.order_quantity &&
                    it.order_new_price == order.order_new_price &&
                    it.order_ingredients == order.order_ingredients }
        return  index
    }
}