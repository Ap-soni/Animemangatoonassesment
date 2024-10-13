package com.example.animemangatoon.Activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animemangatoon.Adapter.FavouritesAdapter
import com.example.animemangatoon.R
import com.example.animemangatoon.Adapter.WebtoonAdapter
import com.example.animemangatoon.DatabaseUtils.WebtoonDatabase
import com.example.animemangatoon.modelclass.Webtoon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesScreen : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var favouritesAdapter: FavouritesAdapter
    private lateinit var noFavoritesText: View
    private lateinit var back: ImageView
    private lateinit var title: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites_screen)
        setCustomWindowBackground()

        back = findViewById(R.id.imgBackk)
        title = findViewById(R.id.txttitle)
        title.setText(getString(R.string.Favourites))
        back.visibility= View.VISIBLE

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        noFavoritesText = findViewById(R.id.noFavoritesText)

        favouritesAdapter = FavouritesAdapter(listOf()) { webtoon ->
            handleWebtoonDeletion(webtoon)
        }

        recyclerView.adapter = favouritesAdapter

        back.setOnClickListener {
            onBackPressed()
        }

        loadAllWebtoons()
    }

    private fun loadAllWebtoons() {
        val db = WebtoonDatabase.getDatabase(this)
        CoroutineScope(Dispatchers.IO).launch {
            val allWebtoons = db.webtoonDao().getFavorites()

            Log.d("FavouritesScreen", "Fetched webtoons: ${allWebtoons.size}")

            runOnUiThread {
                if (allWebtoons.isEmpty()) {
                    noFavoritesText.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    noFavoritesText.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE

                    favouritesAdapter = FavouritesAdapter(allWebtoons) { webtoon ->
                        handleWebtoonDeletion(webtoon)
                    }
                    recyclerView.adapter = favouritesAdapter
                }
            }
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
    private fun handleWebtoonDeletion(webtoon: Webtoon) {
        val db = WebtoonDatabase.getDatabase(this)
        CoroutineScope(Dispatchers.IO).launch {
            db.webtoonDao().deleteById(webtoon.id)

            runOnUiThread {
                Toast.makeText(this@FavouritesScreen, "${webtoon.title} removed from favorites", Toast.LENGTH_SHORT).show()
                loadAllWebtoons()
            }
        }
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
