package com.wesoft.demo

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.wesoft.demo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initViewPager()
    }

    private fun initViewPager() {

        val fragments = ArrayList<Fragment>()
        val fragment1 = ListFragment.newInstance("1")
        fragments.add(fragment1)
        /*val fragment2 = ListFragment.newInstance("2")
        fragments.add(fragment2)
        val fragment3 = ListFragment.newInstance("3")
        fragments.add(fragment3)
        val fragment4 = ListFragment.newInstance("4")
        fragments.add(fragment4)
        val fragment5 = ListFragment.newInstance("5")
        fragments.add(fragment5)*/

        adapter = ViewPagerAdapter(supportFragmentManager, fragments)
        mBinding.viewPager.adapter = adapter
        mBinding.viewPager.offscreenPageLimit = 3

        //init bottom navigation bar
        mBinding.bottomNavigation.enableAnimation(false)
        mBinding.bottomNavigation.enableItemShiftingMode(false)
        mBinding.bottomNavigation.enableShiftingMode(false)
        mBinding.bottomNavigation.itemIconTintList = null

        mBinding.bottomNavigation.setupWithViewPager(mBinding.viewPager)

        mBinding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.news_menu -> {

                }
                R.id.like_menu -> {

                }
                R.id.kind_menu -> {

                }
                R.id.shopping_menu -> {

                }
                R.id.me_menu -> {

                }
            }
            true
        }
    }

    private class ViewPagerAdapter(fragmentManager: FragmentManager, var fragments: ArrayList<Fragment>) : FragmentPagerAdapter(fragmentManager) {

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size
    }
}
