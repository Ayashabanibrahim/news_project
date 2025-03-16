package com.example.newsapplication.uiModel

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import android.widget.Spinner
import com.example.newsapplication.R
import com.example.newsapplication.apiModel.Constants.Companion.LANGUAGE_KEY
import com.example.newsapplication.apiModel.Constants.Companion.PREFS_NAME
import com.example.newsapplication.apiModel.Constants.Companion.SIZE_KEY
import com.example.newsapplication.databinding.FragmentSettingBinding
import java.util.Locale


@Suppress("IMPLICIT_CAST_TO_ANY")
class SettingFragment : Fragment() {
    private lateinit var binding:FragmentSettingBinding
    lateinit var sharedPreferences: SharedPreferences
    val languages = arrayOf("English","Arabic")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentSettingBinding.inflate(inflater,container,false)

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.title=getString(R.string.settings)

        sharedPreferences=requireContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)

        val spinnerLanguage =binding.spinnerLanguage
        val seekBar=binding.seekBar

        // spinner lang
        initSpinnerLanguage(spinnerLanguage)
        //set language when select another language
        setLanguage(spinnerLanguage)

        //seekbar
        loadSeekBarSize()
         changeTextSize(seekBar)



    }




    private fun initSpinnerLanguage(spinnerLanguage: Spinner) {
        val adapterLanguage = activity?.let {
            ArrayAdapter(
                //this
                it.applicationContext,
                //layout spinner
                R.layout.spinner_item,
                //  androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                //array
                languages
            )
        }
        //layout spinner drop created
        adapterLanguage?.setDropDownViewResource(R.layout.spinner_drop_item)
        //default layout drop spinner
        //  adapterLanguage?.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        //set adapter to spinner
        spinnerLanguage.adapter=adapterLanguage
        //get saved language
        val savedLanguage=sharedPreferences.getString(LANGUAGE_KEY,"English")
        //get position of saved language
        val positionLanguage=adapterLanguage?.getPosition(savedLanguage)
        //set saved language to spinner
        if (positionLanguage != null) {
            spinnerLanguage.setSelection(positionLanguage)
        }
    }
    private fun setLanguage(spinnerLanguage: Spinner) {
        spinnerLanguage?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = parent?.getItemAtPosition(position).toString()
                val savedLanguage=sharedPreferences.getString(LANGUAGE_KEY,"English")
                if(savedLanguage!=selectedLanguage){
                    changeLanguage(selectedLanguage)
                    saveLanguage(selectedLanguage)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
    }

    private fun saveLanguage(selectedLanguage: String) {
        //save language in shared preference
       sharedPreferences.edit().putString(LANGUAGE_KEY,selectedLanguage).apply()
    }

    private fun changeLanguage(selectedLanguage: String) {
        val local= when(selectedLanguage){
            "English" -> Locale("en")
            "Arabic" -> Locale("ar")
            else -> Locale.getDefault()
        }

        Locale.setDefault(local)
        val config = Configuration()
        config.setLocale(local)
        resources.updateConfiguration(config, resources.displayMetrics)
        (activity as? MainActivity)?.recreate()
    }
    private fun loadSeekBarSize( ) {
        binding.seekBar.progress= sharedPreferences.getFloat(SIZE_KEY,25f).toInt()
    }
    private fun changeTextSize(seekBar: SeekBar) {
        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val minSize=15f
                val mxSize=40f
                val size=minSize+(p1.toFloat()/seekBar.max)*(mxSize-minSize)
                saveSize(p1.toFloat())
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
    }
    private fun saveSize(selectedSize:Float) {
        sharedPreferences.edit().putFloat(SIZE_KEY,selectedSize).apply()
    }



     
}

