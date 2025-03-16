package com.example.newsapplication.adapterModel

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapplication.uiModel.DataFragment
import com.example.newsapplication.dataModel.Source
import com.example.newsapplication.viewModel.NewsViewModel

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val sources: MutableList<Source>
):FragmentStateAdapter(fragmentActivity) {
    private   val TAG = "ViewPagerAdapter"
    override fun getItemCount(): Int {
         return sources.size
    }

    override fun createFragment(position: Int): Fragment {
        Log.d(TAG, "createFragment: ${sources[position].id}")
        return DataFragment.newInstance(sources[position].id)
    }
}