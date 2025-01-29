package com.example.newsapplication.uiModel

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapplication.R
import com.example.newsapplication.apiModel.RetrofitInstance.Companion.apiInterface
import com.example.newsapplication.adapterModel.ViewPagerAdapter
import com.example.newsapplication.dataModel.Source
import com.example.newsapplication.dataModel.SourcesResponse
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewsFragment : Fragment() {
   //val binding= FragmentSportBinding.inflate(layoutInflater)
    lateinit var tabLayout: TabLayout
    lateinit var viewPager2: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

           val view= inflater.inflate(R.layout.fragment_news, container, false)
        tabLayout=view.findViewById(R.id.tab_layout)
        viewPager2=view.findViewById(R.id.viewPager)
        var sources:MutableList<Source> = mutableListOf()
        val category=arguments?.getString("category")
       Log.d("log1","category: $category ")
         apiInterface.getSources( category!!).enqueue(object:Callback<SourcesResponse>{
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<SourcesResponse>, response: Response<SourcesResponse>) {
               if(response.isSuccessful&&isAdded){
                   sources.clear()
                   response.body()?.sources?.let { sources.addAll(it)}
                   val adapter = ViewPagerAdapter(requireActivity(),sources,category)
                   viewPager2.adapter=adapter
                   viewPager2.adapter?.notifyDataSetChanged()
                   TabLayoutMediator(tabLayout,viewPager2){ tab,position ->
                       tab.text= sources[position].name
                     }.attach()
               }
                else{
                    Log.d("log","error:${response.errorBody()?.string()}")
               }
            }

             override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                 Log.d("log","source error")
             }

         })

        return  view



    }


}


