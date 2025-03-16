package com.example.newsapplication.viewModel

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.newsapplication.apiModel.Constants.Companion.APIKEY
import com.example.newsapplication.apiModel.RetrofitInstance.Companion.apiInterface
import com.example.newsapplication.dataModel.Article
import com.example.newsapplication.dataModel.NewsResponse
import com.example.newsapplication.dataModel.Source
import com.example.newsapplication.dataModel.SourcesResponse
import retrofit2.Call
import retrofit2.Response

class NewsViewModel(val state:SavedStateHandle):ViewModel() {
    private   val TAG = "NewsViewModel"
     val category =MutableLiveData<String>()
    val sourceId =MutableLiveData<String>()
   val query =MutableLiveData<String>()
    val combinedLiveData=MediatorLiveData<Pair<String,String>>().apply {
        addSource(sourceId){source ->
            value=Pair(source,query.value?:"")
        }
        addSource(query){q->
            value=Pair(sourceId.value?:"",q)
        }
    }
    var lastSelectedTabPosition:Int=0
    var selectedPositionStates= mutableMapOf<String,Parcelable?>()

     private val _sources=MutableLiveData<MutableList<Source>>()
    val sources:LiveData<MutableList<Source>> get() = _sources
   private  val _articles=MutableLiveData<MutableList<Article>>()
    val articles:LiveData<MutableList<Article>> get() = _articles

    init {
        combinedLiveData.observeForever { (source,query)->
         getResult(source,query)
        }
    }
    fun getSources(){
        val categoryValue=category.value.toString()?:""
        apiInterface.getSources(categoryValue).enqueue(object:retrofit2.Callback<SourcesResponse>{

            override fun onResponse(
                call: Call<SourcesResponse>,
                response: Response<SourcesResponse>
            ) {
                if(response.isSuccessful){
                    response.body()?.sources.let {
                        _sources.postValue(it?.toMutableList())
                    }?: Log.e(TAG,"no sources found")
                }
            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ", t)
            }

        })
    }

    fun getResult(source: String, query: String) {
            apiInterface.getResults(source,query)
                .enqueue(object : retrofit2.Callback<NewsResponse> {
                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: Response<NewsResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.articles.let {
                                _articles.postValue(it?.toMutableList())
                            } ?: Log.e(TAG, "no articles found")
                        } else {
                            Log.d("log2", "error:${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        Log.d("log2", "source error")
                    }

                })
    }

    fun setCategory(category: String) {
        this.category.postValue(category)
    }

    fun setSourceId(sourceId: String) {
          this.sourceId.postValue(sourceId)
    }
    fun setQuery(query: String) {
        this.query.postValue(query)
    }


}