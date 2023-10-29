package io.jadu.agrichain

import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import io.jadu.agrichain.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.customAppToolbar)



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(R.id.selectLanguageFragment,R.id.homeFragment),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        setupActionBarWithNavController(navController,appBarConfiguration)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            supportActionBar?.title = destination.label
        }

        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            // In order to get the expected behavior, you have to call default Navigation method manually
            NavigationUI.onNavDestinationSelected(item, navController)
            return@setOnItemSelectedListener true
        }
        binding.bottomNavigationView.itemIconTintList = null;

        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.WHITE
        }

        binding.addProductButton.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_productListItemFragment)
        }
    }
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
    fun showBottomNavigation(){
        binding.bottomNavigationView.visibility = View.VISIBLE
        binding.bottomAppBar.visibility = View.VISIBLE
        binding.addProductButton.visibility = View.VISIBLE
    }



    fun hideFabandBootomNav(){
        binding.addProductButton.visibility = View.GONE
        binding.bottomNavigationView.visibility = View.GONE
        binding.bottomAppBar.visibility = View.GONE
    }

    fun showFabandBottomNav(){
        binding.addProductButton.visibility = View.VISIBLE
        binding.bottomNavigationView.visibility = View.VISIBLE
        binding.bottomAppBar.visibility = View.VISIBLE
    }
    fun hideActionBar(){
        supportActionBar?.hide()
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
    fun showActionBar(){
        supportActionBar?.show()
    }

    fun setupActionBarColor(color: Int) {
        supportActionBar?.setBackgroundDrawable(ColorDrawable(color))
    }
}