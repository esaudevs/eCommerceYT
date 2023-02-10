package com.esaudev.ecommerceyt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.esaudev.ecommerceyt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fMain) as NavHostFragment?
        val navController = navHostFragment?.navController

        binding.bnvMainMenu.apply {
            navController?.let { navController ->
                NavigationUI.setupWithNavController(
                    this,
                    navController
                )
                setOnItemSelectedListener { item ->
                    NavigationUI.onNavDestinationSelected(item, navController)
                    true
                }
                setOnItemReselectedListener {
                    val selectedMenuItemNavGraph =
                        navController.graph.findNode(it.itemId) as NavGraph
                    selectedMenuItemNavGraph.let { menuGraph ->

                        navController.popBackStack(menuGraph.startDestinationId, false)
                    }
                }
            }
        }
    }
}