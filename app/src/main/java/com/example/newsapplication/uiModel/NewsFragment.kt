package com.example.newsapplication.uiModel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapplication.R
import com.example.newsapplication.viewModel.NewsViewModel
import com.example.newsapplication.adapterModel.ViewPagerAdapter
import com.example.newsapplication.databinding.FragmentNewsBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class NewsFragment : Fragment() {
    private  val TAG = "NewsFragment"
    private val viewModel:NewsViewModel by  viewModels()
    private val newsViewModel:NewsViewModel by  activityViewModels()
    private lateinit var binding:FragmentNewsBinding
    lateinit var tabLayout: TabLayout
    lateinit var viewPager2: ViewPager2

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNewsBinding.inflate(inflater,container,false)

        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         binding.progressBar.show()
        tabLayout=binding.tabLayout
        viewPager2=binding.viewPager
        val category=arguments?.getString("category","")
        Log.d( TAG,"category: $category ")

        setTabs(category)

         saveLastSelectedTab()

        handelVisibility()

    }

    private fun handelVisibility() {
        val activity=requireActivity()
        val searchView=activity.findViewById<androidx.appcompat.widget.SearchView>(R.id.search_view)
        val search=activity.findViewById <ImageView>(R.id.search)
        val themeMode=activity.findViewById <ImageView>(R.id.theme_mode)
        val navigation=activity.findViewById <com.google.android.material.navigation.NavigationView>(R.id.nav_view)
        themeMode.visibility=View.GONE
        search.visibility=View.VISIBLE
        searchView.visibility=View.GONE
        navigation.visibility=View.GONE
        search.setOnClickListener {
            search.visibility=View.GONE
            searchView.visibility=View.VISIBLE
            searchView.isIconified=false
        }
        searchView.setOnCloseListener {
            searchView.visibility=View.GONE
            search.visibility=View.VISIBLE
            false
        }
    }



    private fun setTabs(category: String?) {
        viewModel.setCategory(category!!)
        viewModel.category.observe(viewLifecycleOwner, Observer {
            viewModel.getSources()
        })
        viewModel.sources.observe(viewLifecycleOwner,Observer { sources ->
            val adapter = ViewPagerAdapter(requireActivity(), sources)
            viewPager2.adapter = adapter
            // viewPager2.adapter?.notifyDataSetChanged()
            TabLayoutMediator(tabLayout, viewPager2) { tab, pos ->
                tab.text = sources[pos].name
            }.attach()
            binding.progressBar.hide()
        })

    }
    private fun saveLastSelectedTab() {
        viewPager2.registerOnPageChangeCallback(object :ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.lastSelectedTabPosition=position
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
       // newsViewModel.selectedPositionStates.clear()
        (activity as MainActivity).supportActionBar?.title=getString(R.string.app_name)
            val activity=requireActivity()
            val searchView=activity.findViewById<androidx.appcompat.widget.SearchView>(R.id.search_view)
            val search=activity.findViewById <ImageView>(R.id.search)
            val themeMode=activity.findViewById <ImageView>(R.id.theme_mode)
            val navigation=activity.findViewById <com.google.android.material.navigation.NavigationView>(R.id.nav_view)
            search.visibility=View.GONE
            searchView.visibility=View.GONE
            navigation.visibility=View.VISIBLE
            themeMode.visibility=View.VISIBLE

    }





}