package com.example.newstestapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.example.newstestapp.R
import com.example.newstestapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CoroutineScope  {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job



    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        Log.i("main", "start activity")

        job = Job()





        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)



        Log.i("main", "main 1")
        launch {

            val i = getTest()
            val j = getTest2()
            //val i = async(Dispatchers.IO){getTest()}
                //val j = async(Dispatchers.IO) { getTest2() }

            endProcess(i, j)


        }


      /*  GlobalScope.launch(Dispatchers.Main){
            getTest()
        }*/
       /* runBlocking {
            launch {
                getTest()
            }
        }*/
        Log.i("main", "main 2")

        //val f = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main);

        //val navController = f?.findNavController();
        val navController = findNavController(R.id.nav_host_fragment_content_main)




        //appBarConfiguration = AppBarConfiguration(navController!!.graph)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)
        NavigationUI.setupWithNavController(binding.bottomNav,navController)


        navController.addOnDestinationChangedListener { _, _, _ ->
            binding.toolbar.navigationIcon = null
        }


        /* binding.fab.setOnClickListener { view ->
             Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 .setAnchorView(R.id.fab)
                 .setAction("Action", null).show()
         }*/

       /* binding.bottomNav.setOnItemSelectedListener { item ->

            when(item.itemId){
                R.id.list2 ->{
                    navController.navigate(R.id.action_FirstFragment_to_ThirdFragment)
                    binding.toolbar.navigationIcon = null
                    return@setOnItemSelectedListener true
                }
            }

            return@setOnItemSelectedListener true
        }*/

    }


    fun endProcess(boolean1: Boolean, boolean2: Boolean) =  Log.i("main","end: $boolean1 and $boolean2")


    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    suspend  fun  getTest() : Boolean{
        Log.i("main", "start1 coroutines")
        for(i in 1..100 ){
            delay(100)
        }
        Log.i("main", "end1 coroutines")
        return true;
    }

    suspend fun getTest2() : Boolean{
        Log.i("main", "start2 coroutines")
        for(i in 1..50 ){
            delay(100)
        }
        Log.i("main", "end2 coroutines")
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}