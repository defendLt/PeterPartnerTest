package com.platdmit.peterpartnertest.app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.platdmit.peterpartnertest.R
import com.platdmit.peterpartnertest.app.utilities.ShowLoaderHandler
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
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