package hardroid.pizza_mozzarella.rellarella

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import hardroid.pizza_mozzarella.rellarella.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: PizzasAdapter


    private  val pizzaService: PizzaService
        get() = (applicationContext as App).pizzaService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//         val binding: ViewDataBinding? = DataBindingUtil.setContentView(this,R.layout.activity_main);


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = PizzasAdapter()

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        pizzaService.addListener(pizasListener)
    }

    private val pizasListener: PizzasListeners = {
        adapter.pizzas = it
    }



}