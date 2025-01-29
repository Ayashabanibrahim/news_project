package com.example.newsapplication.uiModel

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatDelegate
import com.example.newsapplication.LANGUAGE_KEY
import com.example.newsapplication.PREFS_NAME
import com.example.newsapplication.R
import com.example.newsapplication.THEME_KEY
import java.util.Locale


@Suppress("IMPLICIT_CAST_TO_ANY")
class SettingFragment : Fragment() {
    val languages = arrayOf("English", "Arabic")
    val modes= arrayOf("Light","Dark")
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       val view= inflater.inflate(R.layout.fragment_setting, container, false)
       // loadTheme()
        sharedPreferences=requireContext().getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
        val spinnerLanguage = view.findViewById<Spinner>(R.id.spinner_language)
        val spinnerTheme=view.findViewById<Spinner>(R.id.spinner_theme)
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

        val adapterTheme=activity?.let {
            ArrayAdapter(
                requireContext(),
                R.layout.spinner_item,
                modes
            )
        }
        adapterTheme?.setDropDownViewResource(R.layout.spinner_drop_item)
        spinnerTheme.adapter=adapterTheme

        //get saved language
       val savedLanguage=sharedPreferences.getString(LANGUAGE_KEY,"English")
        //get position of saved language
        val positionLanguage=adapterLanguage?.getPosition(savedLanguage)
        //set saved language to spinner
        if (positionLanguage != null) {
            spinnerLanguage.setSelection(positionLanguage)
        }
        //set save theme
        val savedTheme= sharedPreferences.getString(THEME_KEY,"Light")
        val positionTheme=adapterTheme?.getPosition(savedTheme)
        positionTheme?.let { spinnerTheme.setSelection(it) }

        //set language when select another language
        spinnerLanguage?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = parent?.getItemAtPosition(position).toString()
                if(savedLanguage!=selectedLanguage){
                    changeLanguage(selectedLanguage)
                    saveLanguage(selectedLanguage)
                }
               // Toast.makeText(activity,selectedLanguage, Toast.LENGTH_LONG).show()

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}

        }
        spinnerTheme.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            @SuppressLint("SuspiciousIndentation")
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
              val  selectedTheme=parent?.getItemAtPosition(position).toString()
                if(selectedTheme!=savedTheme){
                    changeTheme(selectedTheme,view)
                    saveTheme(selectedTheme)

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
        return view
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
        val config=resources.configuration
        config.setLocale(local)
        resources.updateConfiguration(config,resources.displayMetrics)
        requireActivity().recreate()
    }
    private fun saveTheme(selectedTheme: String) {
        sharedPreferences.edit().putString(THEME_KEY,selectedTheme).apply()
    }
    @SuppressLint("ResourceAsColor")
    private fun changeTheme(selectedTheme: String, view: View?) {
         when(selectedTheme){

            "Light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "Dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }

/*

             "Light" ->{
                 requireActivity().setTheme(R.style.LightTheme)
                 Log.d("theme","light")
             }
             "Dark" ->{
                 requireActivity().setTheme(R.style.DarkTheme)
                 Log.d("theme","dark")
             }




             "Light" ->{
                 val layout: LinearLayout? =view?.findViewById(R.id.settingLayout)
                 layout?.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
                 val navigationView: NavigationView? = view?.findViewById(R.id.nav_view)
                 navigationView?.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.white))
             }
             "Dark" ->{
                 val layout: LinearLayout? =view?.findViewById(R.id.settingLayout)
                 layout?.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.black))
                 val navigationView: NavigationView? = view?.findViewById(R.id.nav_view)
                 navigationView?.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.black))
             }

              */



        }
        
     refreshFragment()
     //   requireActivity().recreate()

      //  view?.invalidate()
    }
    private fun refreshFragment() {
        val currentFragment = parentFragmentManager.findFragmentById(R.id.frame_layout)
        currentFragment?.let {
            parentFragmentManager.beginTransaction()
                .detach(it)
                .attach(it)
                .commit()
        }

    }

    fun loadTheme(){
        val savedTheme= sharedPreferences.getString(THEME_KEY,"Light")
        when(savedTheme){
            "Light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "Dark" -> {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }

    }

     
}

