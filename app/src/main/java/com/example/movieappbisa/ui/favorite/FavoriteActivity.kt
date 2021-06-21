package com.example.movieappbisa.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.movieappbisa.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityFavoriteBinding=ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(activityFavoriteBinding.root)

        val sectionsPagerAdapter = SectionPagerFavAdapter(this, supportFragmentManager)

        activityFavoriteBinding.viewPager.adapter = sectionsPagerAdapter
        activityFavoriteBinding.tabs.setupWithViewPager(activityFavoriteBinding.viewPager)

        supportActionBar?.title="My Favorite"
        supportActionBar?.elevation = 0f
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}