package com.example.intro.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.example.intro.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        setupNavigationController()
    }

    private fun setupNavigationController() {
        navController = mNavHostFragment.findNavController()
        mBottomNavigation.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
}
