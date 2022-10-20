package hardroid.pizza_mozzarella.rellarella

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try{
            var intent = Intent(this,MainScreen::class.java)
            startActivity(intent)
            finish()
        }catch (exception: Exception){

        }
    }
}