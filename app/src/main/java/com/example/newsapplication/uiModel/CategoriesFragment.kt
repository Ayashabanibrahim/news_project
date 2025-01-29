package com.example.newsapplication.uiModel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.findNavController
import com.example.newsapplication.R


class CategoriesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

      val view= inflater.inflate(R.layout.fragment_categories, container, false)
      view?.findViewById<CardView>(R.id.sport)?.setOnClickListener{
            val args=Bundle()
          args.putString("category","sports")
           findNavController().navigate(R.id.action_categoriesFragment_to_newsFragment,args)

       }
        /*

        view.findViewById<CardView>(R.id.business).setOnClickListener{
            val args=Bundle()
            args.putString("category","business")
            findNavController().navigate(R.id.action_categoriesFragment_to_newsFragment,args)
        }
        view.findViewById<CardView>(R.id.technology).setOnClickListener{
            val args=Bundle()
            args.putString("category","technology")
            findNavController().navigate(R.id.action_categoriesFragment_to_newsFragment,args)
        }
        view.findViewById<CardView>(R.id.science).setOnClickListener{
            val args=Bundle()
            args.putString("category","science")
            findNavController().navigate(R.id.action_categoriesFragment_to_newsFragment,args)
        }


         */


        return view
    }


}