package com.example.netbond

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.navigation.findNavController
import com.example.netbond.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = getColor(R.color.background)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.root.setOnClickListener { hideKeyboard() }
        setUpBottomNav()
        setBtnAddBond()
        setContentView(binding.root)
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
        currentFocus?.clearFocus()
    }

    private fun setUpBottomNav() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.search -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.userSearchBarFragment)
                    true
                }
                R.id.home -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.feedFragment)
                    true
                }
                R.id.profile -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.userProfileFragment)
                    true
                }
                else -> {false}
            }
        }
    }


    private fun setBtnAddBond() {
        binding.fab.setOnClickListener{
            binding.fab.visibility = View.GONE
            findNavController(R.id.nav_host_fragment).navigate(R.id.bondCreationFragment)
        }
    }

}