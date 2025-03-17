package com.example.newsapplication.uiModel

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.R
import com.example.newsapplication.viewModel.NewsViewModel
import com.example.newsapplication.adapterModel.Adapter
import com.example.newsapplication.apiModel.Constants.Companion.PREFS_NAME
import com.example.newsapplication.databinding.FragmentDataBinding


class DataFragment( ) : Fragment()  {
    private   val TAG = "DataFragment"
    private lateinit var binding: FragmentDataBinding
    private val viewModel:NewsViewModel by  viewModels()
    private val newsViewModel:NewsViewModel by  activityViewModels()
  private lateinit var recycler:RecyclerView
   private lateinit var adapter: Adapter
    lateinit var sharedPreferences: SharedPreferences
    lateinit  var sourceId:String
    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=FragmentDataBinding.inflate(inflater,container,false)

            return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences=requireContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
        recycler=binding.recycler
        sourceId=arguments?.getString("sourceId","")?:""
        adapter= Adapter(sharedPreferences,newsViewModel,sourceId)
        recycler.layoutManager=LinearLayoutManager(context)
        recycler.adapter= this.adapter
        Log.d(TAG, "onViewCreated: $sourceId")
        setSearchTextQuery()
        getResult()


    }

    private fun getResult() {
        binding.progressBar.show()
        viewModel.setSourceId(sourceId)
        viewModel.sourceId.observe(viewLifecycleOwner, Observer {newsSourceId ->
            if(newsSourceId==sourceId)
            viewModel.getResult(sourceId,"")
        })
        Log.d(TAG, "getResult: $sourceId")

        viewModel.articles.observe(viewLifecycleOwner, Observer { articles->
            articles.let {article->
                if(article.isEmpty()){
                    if(viewModel.query.value!="")
                        binding.notFound.visibility=View.GONE
                    else
                        binding.notFound.visibility=View.VISIBLE

                }
                else if( adapter.newsList !=article) {
                    adapter.submitList(article)
                    if(viewModel.query.value=="")
                    binding.notFound.visibility=View.GONE
                }
            }
            binding.progressBar.hide()
            newsViewModel.selectedPositionStates[sourceId]?.let {
                recycler.post {
                    recycler.layoutManager?.scrollToPosition(it+1)
                }
                Log.d(TAG, "getResult: $it")
            }
            Log.d(TAG, "map: ${newsViewModel.selectedPositionStates}")



        })
    }

    private fun setSearchTextQuery() {
        val activity=requireActivity()
        val searchView=activity.findViewById<androidx.appcompat.widget.SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                if(query!=null) {
                    val q = query?.takeIf { it.isNotEmpty() } ?: ""
                    if (q.isNotEmpty()) {
                        viewModel.setQuery(q?:" ")
                        Log.d(TAG, "onQueryTextSubmit: $q")
                    } else {
                        viewModel.setQuery("")
                        Log.d(TAG, "onQueryTextSubmit: empty query")
                    }
                }
                else{
                    Log.d(TAG, "onQueryTextSubmit: query is null")
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null) {
                    val q = newText?.takeIf { it.isNotEmpty() } ?: ""
                    if (q.isNotEmpty()) {
                        viewModel.setQuery(q?:" ")
                        Log.d(TAG, "onQueryTextSubmit: $q")
                    } else {
                        viewModel.setQuery("")
                        Log.d(TAG, "onQueryTextSubmit:  f query")
                    }
                }else{
                    Log.d(TAG, "onQueryTextChange: newtext is null")
                }


                return true
            }
        })
    }





    companion object {
        fun newInstance(sourceId: String): DataFragment {
         val fragment= DataFragment()
           val args=Bundle()
           args.putString("sourceId",sourceId)
           fragment.arguments=args
            return  fragment
        }
    }




}