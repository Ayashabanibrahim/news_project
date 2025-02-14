package com.example.newsapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.viewpager2.widget.ViewPager2
import com.example.newsapplication.apiModel.Constants.Companion.LANGUAGE_KEY
import com.example.newsapplication.apiModel.Constants.Companion.PREFS_NAME
import com.example.newsapplication.apiModel.Constants.Companion.THEME_KEY
import com.example.newsapplication.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import java.util.Locale


//lateinit var  drawerLayout:DrawerLayout
@Suppress("IMPLICIT_CAST_TO_ANY")
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
       lateinit var binding:ActivityMainBinding
       lateinit var  drawerLayout:DrawerLayout
       lateinit var view:View
    lateinit var sharedPreferences: SharedPreferences
    lateinit var  appBarConfiguration:AppBarConfiguration
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
      //  sharedPreferences=this.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
      //  loadTheme()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        view=binding.root
        //Apply saved setting using sharedPreference
         sharedPreferences=this.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE)
        // load saved language
        loadLanguage()
        // load saved theme mode
        loadTheme()

        // -------navigation drawer----
        //init toolbar
        val toolbar=binding.tolBar
        //set toolbar
        setSupportActionBar(toolbar)
        // init drawer layout
        drawerLayout = binding.main
        // init action bar drawer toggle
       val actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close)
        // add a drawer listener into  drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // show menu icon and back icon while drawer open
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //navigate fragments use navigationDrawer
        // init navHostFragment
        val navHost=supportFragmentManager.findFragmentById(R.id.host_fragment) as NavHostFragment
        // init navController using navHost
        val navController=navHost.navController
        // init navigationView
        val navigation=binding.navView
        //init appBarConfig
         appBarConfiguration= AppBarConfiguration(navController.graph,drawerLayout)
     //   NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration )
        // set up navigationDrawer with navController
        NavigationUI.setupWithNavController(navigation,navController)


       //  navigation items click
        navigation.setNavigationItemSelectedListener {item ->
            when(item.itemId){
                R.id.category -> navController.navigate(R.id.categoriesFragment)
                R.id.favourite -> navController.navigate(R.id.favFragment)
                R.id.settings -> navController.navigate(R.id.settingFragment)
                R.id.search -> navController.navigate(R.id.searchFragment)
                R.id.exit -> {
                    Toast.makeText(this,"Exit!",Toast.LENGTH_SHORT).show()
                    finishAffinity()
                }

            }
            drawerLayout.closeDrawers()
            true
        }

           // navigation.setNavigationItemSelectedListener(this)



    }


    override fun onSupportNavigateUp(): Boolean {
         val navController=findNavController(R.id.host_fragment)
        return NavigationUI.navigateUp(navController,appBarConfiguration) || super.onSupportNavigateUp()
    }
    @SuppressLint("WrongConstant")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
           R.id.category -> navigateToDestination(R.id.categoriesFragment)
            R.id.favourite -> navigateToDestination(R.id.favFragment)
            R.id.settings -> navigateToDestination(R.id.settingFragment)
            R.id.search -> navigateToDestination(R.id.searchFragment)
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
        val config=resources.configuration
        config.setLocale(local)
        resources.updateConfiguration(config,resources.displayMetrics)
     //   recreate()

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


/*

            "Light" ->{
                this.setTheme(R.style.LightTheme)
                Log.d("theme","light")
            }
            "Dark" -> {
                this.setTheme(R.style.DarkTheme)
                Log.d("theme","dark")
            }




            "Light" ->{
                binding.main.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
                binding.navView.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
                /*
             val layout: LinearLayout? =findViewById(R.id.settingLayout)
                layout?.setBackgroundColor(ContextCompat.getColor(this,R.color.white))
                val navigationView: NavigationView? = findViewById(R.id.nav_view)
                navigationView?.setBackgroundColor(ContextCompat.getColor(this,R.color.white))

                 */
            }
            "Dark" ->{
                binding.main.setBackgroundColor(ContextCompat.getColor(this,R.color.black))
                binding.navView.setBackgroundColor(ContextCompat.getColor(this,R.color.black))
                /*
                val layout: LinearLayout? =findViewById(R.id.settingLayout)
                layout?.setBackgroundColor(ContextCompat.getColor(this,R.color.black))
                val navigationView: NavigationView? = findViewById(R.id.nav_view)
                navigationView?.setBackgroundColor(ContextCompat.getColor(this,R.color.black))

                 */
            }

 */

        }
    }
}