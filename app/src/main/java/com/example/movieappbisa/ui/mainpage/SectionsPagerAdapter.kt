package com.example.movieappbisa.ui.mainpage

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.movieappbisa.R
import com.example.movieappbisa.ui.home.HomeFragment
import com.example.movieappbisa.ui.tvshow.TvshowFragment

class SectionsPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(R.string.home, R.string.fav)
    }

    override fun getItem(position: Int): Fragment =
        when (position) {
            0 -> HomeFragment()
            1 -> TvshowFragment()
            else -> Fragment()
        }

    override fun getPageTitle(position: Int): CharSequence = mContext.resources.getString(
        TAB_TITLES[position])

    override fun getCount(): Int = 2

}