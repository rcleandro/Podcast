package br.com.leandro.podcast

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.leandro.podcast.databinding.ActivityMainBinding

/**
 * MainActivity.
 *
 * Main activity responsible for displaying the application.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_details, R.id.navigation_player
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> navigateToHome()
                R.id.navigation_details -> navigateToDetails()
                R.id.navigation_player -> navigateToPlayer()
                else -> false
            }
        }
    }

    private fun navigateToHome(): Boolean {
        navController.navigate(R.id.navigation_home)
        return true
    }

    private fun navigateToDetails(): Boolean {
        return viewModel.feed.value?.let {
            navController.navigate(R.id.navigation_details)
            true
        } ?: run {
            onShowDialog(getString(R.string.select_an_rss_link))
            false
        }
    }

    private fun navigateToPlayer(): Boolean {
        return when {
            viewModel.feed.value == null -> {
                onShowDialog(getString(R.string.select_an_rss_link))
                false
            }
            viewModel.podcast.value == null -> {
                onShowDialog(getString(R.string.select_a_podcast_chapter))
                false
            }
            else -> {
                navController.navigate(R.id.navigation_player)
                true
            }
        }
    }

    private fun onShowDialog(text: String) {
        val alert = Dialog(this, R.style.dialogTheme)
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alert.setContentView(R.layout.dialog_view)
        this.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        val textView: TextView = alert.findViewById(R.id.text)
        val buttonOk: Button = alert.findViewById(R.id.buttonOk)

        textView.text = text

        alert.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alert.show()

        buttonOk.setOnClickListener {
            alert.dismiss()
        }
    }
}