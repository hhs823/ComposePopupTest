package com.example.testapplication1

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.testapplication1.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewPager2()
    }

    private fun setupViewPager2() = with(binding.pager) {
        adapter = object : FragmentStateAdapter(this@MainActivity) {
            override fun getItemCount(): Int = 3
            override fun createFragment(position: Int): Fragment = TestFragment.newInstance(position)
        }
        offscreenPageLimit = 3
        isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, this) { tab, position ->
            tab.text = position.toString()
        }.attach()
    }
}
