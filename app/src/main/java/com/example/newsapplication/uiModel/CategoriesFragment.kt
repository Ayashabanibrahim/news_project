package com.example.newsapplication.uiModel

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.example.newsapplication.R
import com.example.newsapplication.apiModel.Constants.Companion.LANGUAGE_KEY
import com.example.newsapplication.apiModel.Constants.Companion.PREFS_NAME
import com.example.newsapplication.databinding.FragmentCategoriesBinding


class CategoriesFragment : Fragment() {
    private lateinit var binding:FragmentCategoriesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentCategoriesBinding.inflate(inflater,container,false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val sharedPreferences=requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val selectedLanguage=sharedPreferences.getString(LANGUAGE_KEY,"English")
        binding.sport.setOnClickListener{
            val args=Bundle()
            if(selectedLanguage=="Arabic"){
                showDialog()
            }else {
                args.putString("category", "sports")
                findNavController().navigate(R.id.action_categoriesFragment_to_newsFragment, args)
                (activity as MainActivity).supportActionBar?.title = getString(R.string.sports)
            }
        }


        binding.business.setOnClickListener{
            val args=Bundle()
            if(selectedLanguage=="Arabic"){
                showDialog()
            }else {
                args.putString("category", "business")
                findNavController().navigate(R.id.action_categoriesFragment_to_newsFragment, args)
                (activity as MainActivity).supportActionBar?.title = getString(R.string.business)
            }
        }
        binding.technology.setOnClickListener{
            val args=Bundle()
            if(selectedLanguage=="Arabic"){
                showDialog()
            }else {
                args.putString("category", "technology")
                findNavController().navigate(R.id.action_categoriesFragment_to_newsFragment, args)
                (activity as MainActivity).supportActionBar?.title = getString(R.string.technology)
            }
        }
       binding.science.setOnClickListener{
            val args=Bundle()
           if(selectedLanguage=="Arabic"){
               showDialog()
           }else {
               args.putString("category", "science")
               findNavController().navigate(R.id.action_categoriesFragment_to_newsFragment, args)
               (activity as MainActivity).supportActionBar?.title = getString(R.string.science)
           }
        }
        binding.health.setOnClickListener{
            val args=Bundle()
            if(selectedLanguage=="Arabic"){
                showDialog()
            }else {
                args.putString("category", "health")
                findNavController().navigate(R.id.action_categoriesFragment_to_newsFragment, args)
                (activity as MainActivity).supportActionBar?.title = getString(R.string.health)
            }
        }
        binding.entertainment.setOnClickListener{
            val args=Bundle()
            if(selectedLanguage=="Arabic"){
                showDialog()
            }else {
                args.putString("category", "entertainment")
                findNavController().navigate(R.id.action_categoriesFragment_to_newsFragment, args)
                (activity as MainActivity).supportActionBar?.title =
                    getString(R.string.entertainment)
            }
        }




    }

    private fun showDialog() {
         val builder=AlertDialog.Builder(requireActivity())
        val dialogView=layoutInflater.inflate(R.layout.dialog_layout,null)
        builder.setView(dialogView)
        val alertDialog=builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogView.findViewById<Button>(R.id.btnOk).setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.show()

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).supportActionBar?.title=getString(R.string.app_name)
    }


}