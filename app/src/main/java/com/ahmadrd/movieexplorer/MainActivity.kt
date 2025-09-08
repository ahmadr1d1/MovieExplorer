package com.ahmadrd.movieexplorer

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ahmadrd.movieexplorer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.container) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val navView: BottomNavigationView = binding.navView

        val activeIndicatorColor = Color.TRANSPARENT
        val colorStateList = ColorStateList.valueOf(activeIndicatorColor)
        navView.itemActiveIndicatorColor = colorStateList

        val navHostFragment =
            supportFragmentManager.findFragmentById(
                R.id.nav_host_fragment_activity_main
            ) as? NavHostFragment
                ?: throw IllegalStateException("NavHostFragment not found")
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (doubleBackToExitPressedOnce) {
                    finish()
                    return
                }

                doubleBackToExitPressedOnce = true
                Toast.makeText(this@MainActivity,
                    R.string.one_more, Toast.LENGTH_SHORT).show()

                handler.postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            }
        })
    }
}