package com.example.newsapplication.uiModel

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.newsapplication.R
import com.example.newsapplication.apiModel.Constants.Companion.LANGUAGE_KEY
import com.example.newsapplication.apiModel.Constants.Companion.PREFS_NAME
import com.example.newsapplication.apiModel.Constants.Companion.THEME_KEY
import com.example.newsapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import java.util.Locale


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
       lateinit var binding:ActivityMainBinding
       lateinit var  drawerLayout:DrawerLayout
       lateinit var view:View
    lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        view=binding.root
        //Apply saved setting using sharedPreference
         sharedPreferences=this.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
        // -------navigation drawer----
        initNavigationDrawer()
        // load saved language
        loadLanguage()

        // load saved theme mode
        loadTheme()

        //toggle theme
        toggleTheme()





    }



    private fun initNavigationDrawer() {
        //init toolbar
        val toolbar=binding.tolBar
        //set toolbar
        setSupportActionBar(toolbar)
        // init drawer layout
        drawerLayout = binding.main
        // init action bar drawer toggle
        val actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,
            R.string.nav_open,
            R.string.nav_close
        )
        // add a drawer listener into  drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        //navigate fragments use navigationDrawer
        // init navHostFragment
        val navHost=supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
        // init navController using navHost
        val navController=navHost.navController
        // init navigationView
        val navigation=binding.navView
        // set up navigationDrawer with navController
        NavigationUI.setupWithNavController(navigation,navController)


        navigation.setNavigationItemSelectedListener(this)

    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
           R.id.category -> {
               binding.search.visibility=View.GONE
               binding.searchView.visibility=View.GONE
               binding.themeMode.visibility=View.VISIBLE
               navigateToDestination(R.id.categoriesFragment)
           }

            R.id.settings ->{
                binding.search.visibility=View.GONE
                binding.searchView.visibility=View.GONE
                binding.themeMode.visibility=View.GONE
                navigateToDestination(R.id.settingFragment)
            }
            R.id.exit -> {
                Toast.makeText(this,"Exit!",Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
        }

        drawerLayout.closeDrawers()
        return true
    }
    private fun navigateToDestination(destinationId:Int){
        val navHost=supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
        val navController=navHost.navController
        navController.navigate(destinationId)
    }
    private fun loadLanguage() {
        val selectedLanguage= sharedPreferences.getString(LANGUAGE_KEY,"English")
        val local= when(selectedLanguage){
            "English" -> Locale("en")
            "Arabic" -> Locale("ar")
            else -> Locale.getDefault()
        }
        Locale.setDefault(local)
        val config= Configuration()
        config.setLocale(local)
        resources.updateConfiguration(config,resources.displayMetrics)

    }

    private fun toggleTheme() {
        binding.themeMode.setOnClickListener {
            changeTheme()
            Handler(Looper.getMainLooper()).postDelayed({
                changeModeIcon()
            },3300)
        }
    }

    private fun changeTheme(){
        val savedTheme= sharedPreferences.getString(THEME_KEY,"Light")
        if(savedTheme=="Light") {
            saveTheme("Dark")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            saveTheme("Light")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

    }
    private fun changeModeIcon(){
        val savedTheme= sharedPreferences.getString(THEME_KEY,"Light")
        if(savedTheme=="Light") {
            binding.themeMode.setImageResource(R.drawable.dark)
        }
        else{
            binding.themeMode.setImageResource(R.drawable.light)
        }
    }
    private fun saveTheme(selectedTheme: String) {
        sharedPreferences.edit().putString(THEME_KEY,selectedTheme).apply()
    }

    fun loadTheme(){
        val savedTheme= sharedPreferences.getString(THEME_KEY,"Light")
        when(savedTheme){

            "Light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.themeMode.setImageResource(R.drawable.dark)
            }
            "Dark" -> {
                 AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.themeMode.setImageResource(R.drawable.light)
            }

        }

    }
}