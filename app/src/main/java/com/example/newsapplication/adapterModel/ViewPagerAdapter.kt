package com.example.newsapplication.adapterModel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapplication.uiModel.DataFragment
import com.example.newsapplication.dataModel.Source

class ViewPagerAdapter(
    fragmentActivity:FragmentActivity,
    private val sources: MutableList<Source>,
    private val category: String
    ):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
         return sources.size
    }

    override fun createFragment(position: Int): Fragment {
        return DataFragment.newInstance(sources[position].id,category)

    }
}