package com.platdmit.peterpartnertest.app

import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.platdmit.peterpartnertest.R
import com.platdmit.peterpartnertest.app.utilities.ShowLoaderHandler
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_nav_host.*

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity(), ShowLoaderHandler {
    private lateinit var navController : NavController
    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)

        actionBarInit()
        navigationInit()
        navigationHandlerInit()
        checkConnectStatus()
    }

    private fun actionBarInit() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun navigationInit() {
        navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )
        appBarConfiguration = AppBarConfiguration(navController.graph)

        toolbar_back_button.setOnClickListener {
            navController.previousBackStackEntry?.let {
                navController.popBackStack()
            }
        }
    }

    private fun navigationHandlerInit() {
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            toolbar_title.text = destination.label
            if(controller.currentBackStackEntry == null || destination.id == R.id.mainFragment){
                toolbar_back_button.visibility = View.GONE
            } else {
                toolbar_back_button.visibility = View.VISIBLE
            }
        }
    }

    private fun checkConnectStatus(){
        val cm : ConnectivityManager? = getSystemService()
        cm?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val capabilities = it.getNetworkCapabilities(it.activeNetwork)
                if (capabilities == null) loader_hover_desc.setText(R.string.message_fall_connect)
            } else {
                val activeNetwork = it.activeNetworkInfo
                if (activeNetwork?.isConnectedOrConnecting == false) loader_hover_desc.setText(R.string.message_fall_connect)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return (NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp())
    }

    override fun loaderVisible(status: Boolean) {
        if(status){
            loader_hover?.visibility = View.VISIBLE
        } else {
            loader_hover?.visibility = View.GONE
        }
    }
}