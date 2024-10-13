package com.example.animemangatoon.Activity

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.animemangatoon.R
import com.example.animemangatoon.modelclass.Webtoon
import com.example.animemangatoon.DatabaseUtils.WebtoonDatabase
import com.google.android.material.snackbar.Snackbar

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailScreen : AppCompatActivity() {

    private lateinit var webtoonImage: ImageView
    private lateinit var webtoonDescription: TextView
    private lateinit var addToFavoritesButton: ImageView
    private lateinit var ratingBar: RatingBar
    private lateinit var averageRatingText: TextView
    private lateinit var back: ImageView
    private lateinit var title: TextView

    private var currentRating: Float = 0f
    private var ratingCount: Int = 0
    lateinit   var webtoonImageUrl:String

    private var isFavorite: Boolean = false
    private var webtoonId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_screen)
        setCustomWindowBackground()

        webtoonImage = findViewById(R.id.webtoonImage)
        webtoonDescription = findViewById(R.id.webtoonDescription)
        addToFavoritesButton = findViewById(R.id.btnAddToFavorites)
        ratingBar = findViewById(R.id.ratingBar)
        averageRatingText = findViewById(R.id.averageRatingText)
        back = findViewById(R.id.imgBackk)
        title = findViewById(R.id.txttitle)
        back.visibility= View.VISIBLE

        checkFavoriteStatus()

        val webtoonTitle = intent.getStringExtra("WEBTOON_TITLE")
        title.setText(webtoonTitle)
         webtoonImageUrl = intent.getStringExtra("WEBTOON_IMAGE_URL").toString()
        webtoonId = intent.getIntExtra("WEBTOON_ID", 0)

        Glide.with(this)
            .load(webtoonImageUrl)
            .into(webtoonImage)


        when (webtoonId) {
            1 ->    webtoonDescription.text = getString(R.string.SoloLeveling)
            2 ->    webtoonDescription.text = getString(R.string.Towerofgod)
            3 ->    webtoonDescription.text = getString(R.string.Noblesse)
            4 ->    webtoonDescription.text = getString(R.string.Hardcorelevelingwarrior)
            5 ->    webtoonDescription.text = getString(R.string.SoloLeveling)
            6 ->    webtoonDescription.text = getString(R.string.SoloLeveling)

            else -> println("Unknown number")
        }



        addToFavoritesButton.setOnClickListener {
            toggleFavorite(webtoonTitle)
        }
        back.setOnClickListener{
            onBackPressed()
        }
        // Handle rating changes
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            updateAverageRating(rating)
        }
    }
    private fun checkFavoriteStatus() {
        CoroutineScope(Dispatchers.IO).launch {
            val db = WebtoonDatabase.getDatabase(this@DetailScreen)
            val existingWebtoon = db.webtoonDao().getById(webtoonId)

            runOnUiThread {
                if (existingWebtoon != null) {
                    isFavorite = true
                    addToFavoritesButton.setImageResource(R.drawable.selectedheart)
                } else {
                    isFavorite = false
                    addToFavoritesButton.setImageResource(R.drawable.unselectedheart)
                }
            }
        }
    }
    private fun toggleFavorite(webtoonTitle: String?) {
        if (webtoonTitle != null) {
            CoroutineScope(Dispatchers.IO).launch {
                val db = WebtoonDatabase.getDatabase(this@DetailScreen)
                if (isFavorite) {
                    db.webtoonDao().deleteById(webtoonId)
                    runOnUiThread {
                        isFavorite = false
                        addToFavoritesButton.setImageResource(R.drawable.unselectedheart)
                        Toast.makeText(this@DetailScreen, "$webtoonTitle removed from favorites", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val webtoon = Webtoon(webtoonId, webtoonTitle, webtoonDescription.text.toString(), webtoonImageUrl)
                    db.webtoonDao().insert(webtoon)
                    runOnUiThread {
                        isFavorite = true
                        addToFavoritesButton.setImageResource(R.drawable.selectedheart)
                        Toast.makeText(this@DetailScreen, "$webtoonTitle added to favorites", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun updateAverageRating(newRating: Float) {
        currentRating = newRating

        averageRatingText.text = "Rating: %.2f".format(currentRating)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
    private fun setCustomWindowBackground() {
        val window: Window = window
        val background: Drawable = resources.getDrawable(R.drawable.backgroundhome, theme)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.fab)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.fab)
        window.setBackgroundDrawable(background)
    }
}
