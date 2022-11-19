package hardroid.pizza_mozzarella.rellarella.recycler_views_adapters

import android.app.ActivityOptions
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import hardroid.pizza_mozzarella.rellarella.MainActivity
import hardroid.pizza_mozzarella.rellarella.R
import hardroid.pizza_mozzarella.rellarella.model.Ingredient
import hardroid.pizza_mozzarella.rellarella.model.IngredientsAdapter
import hardroid.pizza_mozzarella.rellarella.model.PizzaService

class PizzaSelected : AppCompatActivity() {
    private lateinit var PizzaProfilePhoto: ImageView
    private lateinit var BackButton: Button
    private lateinit var TitlePizza: TextView
    private lateinit var DescriptionPizza: TextView
    private lateinit var IngredientsPizza:TextView
    private lateinit var PizzaPrice:TextView
    private lateinit var AddToCart: Button
    private lateinit var IngredientsButton:ImageView

    private lateinit var IngredientsDialog: Dialog
    private lateinit var IngredientRecyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_pizza_selected)

        PizzaProfilePhoto = findViewById(R.id.pizza_photo_selected)
        BackButton = findViewById(R.id.button_back)
        TitlePizza = findViewById(R.id.title_pizza)
        DescriptionPizza = findViewById(R.id.description_pizza)
        IngredientsPizza = findViewById(R.id.ingredients_description)
        PizzaPrice = findViewById(R.id.final_price)
        AddToCart = findViewById(R.id.add_to_cart)
        IngredientsButton = findViewById(R.id.icon_filter)


        TitlePizza.text = intent.getStringExtra("PIZZA_TITLE").toString()
        DescriptionPizza.text = intent.getStringExtra("PIZZA_DESCRIPTION").toString()

        var Price = intent.getDoubleExtra("PIZZA_PRICE",50.0)
        PizzaPrice.text = "$${if(Price%1.0==0.0)Price.toInt() else Price}"


        val bundle = intent.getBundleExtra("data")
        val IngredientsList = bundle?.getParcelableArray("PIZZA_INGREDIENTS")?.map { it as Ingredient }?.toTypedArray()

        var IngredientString: String = ""
        if(IngredientsList != null){
            for(i in 0 until IngredientsList.size){
                IngredientString += "${IngredientsList.get(i).IngredientName} ${ if (i!=IngredientsList.size-1) "\n" else 0 }"
            }
        }
        IngredientsPizza.text = IngredientString
        val position = intent.getIntExtra("LAST_POSITION",0)
        PizzaService()

        var current_pizza_photo = intent.getStringExtra("PIZZA_PHOTO")
        Glide.with(PizzaProfilePhoto)
            .load(current_pizza_photo)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .centerInside()
            .placeholder(R.drawable.ic_default_pizza)
            .error(R.drawable.ic_default_pizza)
            .into(PizzaProfilePhoto)

        BackButton.setOnClickListener{
            onBackPressed()
        }

        //dialog ingredients changes -- start
        //Dialog when a user wants to change ingredients
        IngredientsDialog  = Dialog(this,R.style.Theme_DialogAnimation);//Create new dialog window
        IngredientsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//hide tittle
        IngredientsDialog.setContentView(R.layout.ingredients_choice);//path to dialog content
        IngredientsDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.WHITE));//Transperent
        //transparent background of the dialog window
        IngredientsDialog.setCancelable(true);//window CAN BE be closed by back button
        IngredientsDialog.window!!.setGravity(Gravity.BOTTOM)
        IngredientsDialog.window!!.attributes.windowAnimations = R.style.Theme_DialogAnimation
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        var width = displayMetrics.widthPixels
        var height = displayMetrics.heightPixels
        IngredientsDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,(height.toDouble()*0.7).toInt())

        IngredientRecyclerView = IngredientsDialog.findViewById(R.id.ingredients_list)
        if(IngredientsList!=null){
            var IngredientAdapter = IngredientsAdapter(this, IngredientsList.toList())
            IngredientRecyclerView.adapter = IngredientAdapter
            IngredientRecyclerView.layoutManager = LinearLayoutManager(IngredientsDialog.context)
        }



        //dialog ingredients changes -- end

        val LayoutChangeIngredients: RelativeLayout = findViewById(R.id.change_ingredients_button)
        LayoutChangeIngredients.setOnClickListener(object: View.OnClickListener{
            override fun onClick(view: View?) {
                IngredientsDialog.show()
            }
        })

    }
    override fun onBackPressed() {
        super.onBackPressed()
        gotoMainActivity()
    }

    private fun gotoMainActivity() {
       try{
           val intent = Intent(this@PizzaSelected,MainActivity::class.java)
           val bundle:Bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()

           startActivity(intent,bundle)
           finish()
       }catch (_:Exception){}
    }


}