package com.example.matchify.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.matchify.R
import com.example.matchify.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    var currentLeagueId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Preparing the Nav Controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Setting up the Navigation for the Bottom Nav Bar
        binding.bottomNavigationBar.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.leaguesFragment -> {
                    navController.navigate(R.id.leaguesFragment, null)
                    true
                }

                R.id.fixturesFragment -> {
                    navigateToFragmentWithLeagueId(R.id.fixturesFragment)
                    true
                }

                R.id.standingsFragment -> {
                    navigateToFragmentWithLeagueId(R.id.standingsFragment)
                    true
                }

                else -> false
            }
        }
    }

    private fun navigateToFragmentWithLeagueId(fragmentResourceId: Int) {
        // Sending the Current League Id for all the Fragments in the Bundle
        val leagueId = currentLeagueId ?: 39
        val bundle = bundleOf("leagueId" to leagueId)
        navController.navigate(fragmentResourceId, bundle)
    }

    fun setSelectedNavItem(id: Int) {
        // Changing the Select Tab in the Bottom Nav Bar
        binding.bottomNavigationBar.selectedItemId = id
    }
}