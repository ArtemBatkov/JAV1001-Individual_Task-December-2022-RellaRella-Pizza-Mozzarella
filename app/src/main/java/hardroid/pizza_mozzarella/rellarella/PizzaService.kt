package hardroid.pizza_mozzarella.rellarella

import com.github.javafaker.Faker
import java.util.*

/*New type declaration of variables
Similarly in C++ typedef keyword
PizzasListeners is a function which takes List of Pizzas and returns nothing
 */
typealias PizzasListeners = (Pizzas: List<Pizzas>)->Unit

//This class is used to manipulate data

class PizzaService {
    private var PizzasList: MutableList<Pizzas> = mutableListOf<Pizzas>() //List of pizzas
    private val listeners: MutableSet<PizzasListeners> = mutableSetOf<PizzasListeners>()
    //a variable that saves all listener of pizza


    init{
        PizzasList.add(Pizzas(1, IMAGES[0],"PIZZA MOZZARELLA","Pizza Mozzarella is the classic of original pizza. Including the best song of it",55.0))
        PizzasList.add(Pizzas(2, IMAGES[1],"JOYSKE DIAMOND","The calmest pizza. It includes groceries from Morio town.",35.0))
        PizzasList.add(Pizzas(3, IMAGES[2],"STAR JOSTAR","Pizza with ora ora. The best choice for strong man.",60.2))
        PizzasList.add(Pizzas(4, IMAGES[3],"JOHNATHAN's HONOR","Only for gentlemen.",100.0))
        PizzasList.add(Pizzas(5, IMAGES[4],"FATHER AND HIS SON","For DIO and Giorno funs.",44.5))


        // WE CAN PUT DATA BY FAKE OR BY MYSELF. (faker has bad options for pizza)
        /*
        //Data Generation
        val faker: Faker = Faker.instance()
        IMAGES.shuffle()
        Pizzas = (1..10).map{
            Pizzas(
                id = it.toLong(),
                name = faker.name().name(),
                description = faker.food().dish(),
                photo = IMAGES[it % IMAGES.size],
                price = faker.number().randomDouble(1,2,50)
            )
        }.toMutableList()
        */
    }

    //get list
    fun getPizza():List<Pizzas>{
        return PizzasList
    }

    //delete pizza from list
    fun deletePizza(pizza: Pizzas){
        val indexToDelete: Int = PizzasList.indexOfFirst { it.id == pizza.id }
        if(indexToDelete!=-1){
            PizzasList.removeAt(indexToDelete)
            notifyChanges()
        }
    }

    //move pizza to up or down
    fun movePizza(pizza:Pizzas, moveBy: Int){
        val oldIndex:Int = PizzasList.indexOfFirst { it.id  == pizza.id}
        if(oldIndex == -1) return
        val newIndex : Int = oldIndex + moveBy
        if(newIndex < 0 || newIndex >= PizzasList.size) return
        Collections.swap(PizzasList,oldIndex,newIndex)
        notifyChanges()
    }

    fun addListener(listener: PizzasListeners){
        listeners.add(listener)
        listener.invoke(PizzasList)
    }

    fun removeListener(listener: PizzasListeners){
        listeners.remove(listener)
    }


    private fun notifyChanges(){
        listeners.forEach { it.invoke(PizzasList) }
    }

    //instead of static
    companion object{

        private val IMAGES = mutableListOf(
            "https://lh3.googleusercontent.com/pw/AL9nZEUzXvAWnS4djr16cxX3zovVFa0-GiQt9IzzsIvHS4A4lTGCPCGKCxJCA7Zx-XL7eKVZhuv3rGt8KWoYE8dBABWvuLlscdMQ7D-BR3dDcu1sGDpNbhKUxpEdXMqwEuUBen9yK7Cg7ZpTZjBCaldU0Kdk=w728-h410-no?authuser=0",
            "https://lh3.googleusercontent.com/pw/AL9nZEXnRcYLdyC3Al2YfJpXqmqutp3iZo2ojSDU8nLaXcbbPsYPkn23-hjGeNE28HAQO0EuKk0OTuU8xRrMjrfqyYeVDGPZioE2IxfHzVHTfzfFcREmeY1ULS-5K3gsHECBQTgwzuxdnEKDwe4xSQZ5ASRC=w960-h540-no?authuser=0",
            "https://lh3.googleusercontent.com/pw/AL9nZEWk-Txleato4O-tlGyJLTkiEX2Qx0-KCvpPbIXRrc_afzr2350zVJDkAI01LIuunwCB77uPDN0-P_D6NL635ewj9wPhGyUAJaCwI6vVu1tb5zxolQod8iNm1Ae00lRmvx5SMQVUhv82-DdGvhNFuATZ=w960-h600-no?authuser=0",
            "https://lh3.googleusercontent.com/pw/AL9nZEVRSycJA3MHWT4Mk_UniIJAJBJonGUfTwb20hDqEZFPTwa22UWJ-9fgnkMn8Ct_zwz9mzhQ0yC7zrm7u2ZpkRilFhDZ8YlsC4T4kXYg_weZuuz3VJjOs4CB291oeFnWK2Z4mHr4r3SeMH2vKe54WEb2=w960-h540-no?authuser=0",
            "https://lh3.googleusercontent.com/pw/AL9nZEXK-vQUr4rAI75aN-kbfuBzlJtl8g1XAKhhq3_XnxD_dwpaaB1w0Kj9SuxbrWAwzq1HvFmjUrniSvJXkEuB_IUr4OT6u5KcdtplnJw7gx_QMHDBLjKvAXETenQfivBekaHKzhFeREuCNQXBMCW42MDh=w960-h540-no?authuser=0"
        )

//        private val IMAGES = mutableListOf(
//        "https://drive.google.com/drive/folders/1DioLOs7TeEjUXx2Hc1Ouihz-ykJzAJDs?usp=sharing"
//        )
    }



}