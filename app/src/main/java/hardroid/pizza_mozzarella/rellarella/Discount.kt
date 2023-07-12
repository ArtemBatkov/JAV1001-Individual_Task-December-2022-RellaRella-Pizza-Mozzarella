package hardroid.pizza_mozzarella.rellarella

import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.VideoView
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

    private lateinit var videoPromotion: VideoView

    private lateinit var progressBar: ProgressBar

    private lateinit var mediaPlayer: MediaPlayer

    private var player: MediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        setContentView(R.layout.discount_promotion_layout)

        //DiscountPhoto = findViewById(R.id.discount_photo)
        PlayButton = findViewById(R.id.play)
        PlayButton.visibility = View.VISIBLE
        TimerTv = findViewById(R.id.timer)
        GoBack = findViewById(R.id.goback)

        videoPromotion = findViewById(R.id.videoPromotion)
        progressBar = findViewById(R.id.progressBar)



//        val photo = intent.getStringExtra("PHOTO")
//
//        if (photo?.isNotBlank() == true) {
//            Glide.with(DiscountPhoto.context)
//                .load(photo)
//                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .fitCenter()
//                .placeholder(R.drawable.sale_icon)
//                .error(R.drawable.sale_icon)
//                .into(DiscountPhoto)
//        }else{
//          DiscountPhoto.setImageResource(R.drawable.sale_icon)
//        }
//
//        val url = resources.getString(R.string.link_to_theme)
//        player = MediaPlayer()
//
//        val audioAttributes = AudioAttributes.Builder()
//            .setUsage(AudioAttributes.USAGE_MEDIA)
//            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
//            .build()
//        player.setAudioAttributes(audioAttributes)
//
//        player.setDataSource(url)
//        player.prepareAsync()


        PlayButton.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                //before
//                PlayButton.visibility = View.GONE
//                player.start()
//                UpdateTimer()

                //after
                if(videoPromotion != null){
                    PlayButton.visibility = View.GONE
                    videoPromotion.start()
                }
            }
        })

//        player.setOnCompletionListener(object:MediaPlayer.OnCompletionListener{
//            override fun onCompletion(p0: MediaPlayer?) {
//                CanExit = true
//                GoBack.visibility = View.VISIBLE
//                SaveData()
//            }
//
//        })

        GoBack.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
               gotoMainActivity()
            }
        })

        var videoURL = "https://raw.githubusercontent.com/ArtemBatkov/RellaRella-PizzaMozzarella/master/Gyro%20Theme.mp4"
        videoPromotion.setVideoURI(Uri.parse(videoURL))

        videoPromotion.setOnPreparedListener { mediaPlayer ->
            this.mediaPlayer = mediaPlayer
            setupProgressBar()
        }

        videoPromotion.setOnCompletionListener {
            GoBack.visibility = View.VISIBLE
                SaveData()
        }


    }

    private lateinit var ProgressBarHandler: Handler
    private lateinit var updateProgressBar: Runnable

    private fun setupProgressBar() {
        val duration = videoPromotion.duration
        progressBar.max = duration

        updateProgressBar = object : Runnable {
            override fun run() {
                val currentPosition = videoPromotion.currentPosition
                progressBar.progress = currentPosition
                ProgressBarHandler.postDelayed(this, 1000)
            }
        }

        ProgressBarHandler = Handler()
        ProgressBarHandler.postDelayed(updateProgressBar, 1000)
    }



    private var handler: Handler = Handler()
    private var runnable: Runnable? = null
    private fun UpdateTimer(){
        var currentDuration: Long = player.currentPosition.toLong()
        var remainTime = player.duration - currentDuration
        if (remainTime<0) remainTime = 0
        val formater = SimpleDateFormat("mm:ss")
        TimerTv.text = "Remain time: ${formater.format(remainTime).toString()}"
        runnable = object : Runnable{
            override fun run() {
                 if(player.isPlaying()){
                     UpdateTimer()
                 }
            }
        }
        if(runnable != null)
            handler.postDelayed(runnable!!,1000)
    }


    fun openYouTubeApp(view: View) {
        val youtubeUrl = "https://youtu.be/mH5uZKfd5yY"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))


        val packageManager = packageManager
        val activities = packageManager.queryIntentActivities(intent, 0)
        val isYouTubeAppInstalled = activities.isNotEmpty()

        if (isYouTubeAppInstalled) {
            intent.setPackage("com.google.android.youtube")
        }

        startActivity(intent)
    }


    private var CanExit = false

    override fun onBackPressed() {
        //if(CanExit){
        player.stop()
        runnable = null
        gotoMainActivity()
        //}
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