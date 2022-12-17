package hardroid.pizza_mozzarella.rellarella

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

class Discount  : AppCompatActivity()  {
    private lateinit var PlayButton: ImageView
    private lateinit var DiscountPhoto:ImageView
    private lateinit var TimerTv: TextView
    private lateinit var GoBack: Button

    private var player: MediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.discount_promotion_layout)

        DiscountPhoto = findViewById(R.id.discount_photo)
        PlayButton = findViewById(R.id.play)
        PlayButton.visibility = View.VISIBLE
        TimerTv = findViewById(R.id.timer)
        GoBack = findViewById(R.id.goback)

        val photo = intent.getStringExtra("PHOTO")

        if (photo?.isNotBlank() == true) {
            Glide.with(DiscountPhoto.context)
                .load(photo)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .fitCenter()
                .placeholder(R.drawable.sale_icon)
                .error(R.drawable.sale_icon)
                .into(DiscountPhoto)
        }else{
          DiscountPhoto.setImageResource(R.drawable.sale_icon)
        }

        player = MediaPlayer.create(this, R.raw.pizza)

        PlayButton.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                PlayButton.visibility = View.GONE
                player.start()
                UpdateTimer()
            }
        })

        player.setOnCompletionListener(object:MediaPlayer.OnCompletionListener{
            override fun onCompletion(p0: MediaPlayer?) {
                CanExit = true
                GoBack.visibility = View.VISIBLE
                SaveData()
            }

        })

        GoBack.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
               gotoMainActivity()
            }
        })



    }
    private var handler: Handler = Handler()

    private fun UpdateTimer(){
        var currentDuration: Long = player.currentPosition.toLong()
        var remainTime = player.duration - currentDuration
        if (remainTime<0) remainTime = 0
        val formater = SimpleDateFormat("mm:ss")
        TimerTv.text = "Remain time: ${formater.format(remainTime).toString()}"
        var runnable = object : Runnable{
            override fun run() {
                 if(player.isPlaying()){
                     UpdateTimer()
                 }
            }
        }
        handler.postDelayed(runnable,1000)
    }



    private var CanExit = false

    override fun onBackPressed() {
        if(CanExit){
            gotoMainActivity()
        }
        super.onBackPressed()
    }

    private fun gotoMainActivity(){
        try {
            var intent = Intent(this@Discount,MainActivity::class.java)
            startActivity(intent)
            finish()
        }catch (_:Exception){}
    }

    private fun SaveData(){
        val Pref = prefs.getPref()
        val keySP = prefs.keySpecialPromotion
        if (Pref != null) {
            val editor = Pref.edit()
            editor.apply{
                putBoolean(keySP,true)
            }.apply()
        }
    }
}