package com.example.animemangatoon.Activity

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animemangatoon.R
import com.example.animemangatoon.modelclass.Webtoon
import com.example.animemangatoon.Adapter.WebtoonAdapter
import com.example.animemangatoon.databinding.ActivityHomeScreenBinding

class HomeScreen : AppCompatActivity() {

    lateinit var binding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCustomWindowBackground()

        binding = ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val webtoons = listOf(
            Webtoon(1, "Solo Leveling",  getString(R.string.SoloLevelingsmall),"https://www.hindustantimes.com/ht-img/img/2024/05/14/550x309/Anime_SoloLevelingArise_SungJinwoo_1715330097206_1715661002246.jpg"),
            Webtoon(2, "Tower of God",  getString(R.string.Towerofgodsmall), "https://i.pinimg.com/564x/ab/38/cc/ab38cc698097aee2bdf75a3a5799403c.jpg"),
            Webtoon(3, "Noblesse",getString(R.string.Noblessesmall) , "https://images8.alphacoders.com/973/thumb-1920-973633.jpg"),
            Webtoon(4, "Hardcore Leveling Warrior",getString(R.string.Hardcorelevelingwarriorsmall) , "https://lootandlevel.com/wp-content/uploads/2024/07/DALL%C2%B7E-2024-07-26-16.57.08-A-climactic-battle-scene-from-Hardcore-Leveling-Warrior-Chapter-329-with-the-protagonist-Hardcore-Leveling-Warrior-engaged-in-intense-combat.-The--1024x585.webp"),
            Webtoon(5, "Second Life Ranker",getString(R.string.Secondliferankersmall) , "https://e0.pxfuel.com/wallpapers/735/181/desktop-wallpaper-second-life-ranker.jpg"),
            Webtoon(6, "The Advanced Player of the Tutorial Tower",getString(R.string.Theadvancedplayerofthetutoriatowersmall) , "https://substackcdn.com/image/fetch/w_1456,c_limit,f_webp,q_auto:good,fl_progressive:steep/https%3A%2F%2Fbucketeer-e05bbc84-baa3-437e-9518-adb32be77984.s3.amazonaws.com%2Fpublic%2Fimages%2F58bdb4e7-1b23-4d8d-ae77-d1d0dc5f0858_767x400.jpeg"),

        )

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val webtoonAdapter = WebtoonAdapter(webtoons) { webtoon ->
            val intent = Intent(this, DetailScreen::class.java).apply {
                putExtra("WEBTOON_ID", webtoon.id)
                putExtra("WEBTOON_TITLE", webtoon.title)
                putExtra("WEBTOON_DESCRIPTION", webtoon.description)
                putExtra("WEBTOON_IMAGE_URL", webtoon.imageUrl)
            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = webtoonAdapter
        binding.favourites.setOnClickListener {
            val intent = Intent(this, FavouritesScreen::class.java)
            startActivity(intent)
        }


    }


    private fun setCustomWindowBackground() {
        val window: Window = window
        val background: Drawable = resources.getDrawable(R.drawable.backgroundhome, theme)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, R.color.fab)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.transparent)
        window.setBackgroundDrawable(background)
    }
    private fun enableEdgeToEdge() {
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }
}
