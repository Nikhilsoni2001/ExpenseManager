package com.e.expensemanager.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.e.expensemanager.R
import com.e.expensemanager.ui.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : Fragment() {

    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tabLayout)
        setUpTabs()
        return view
    }

    private fun setUpTabs() {
        val adapter = ViewPagerAdapter(supportFragmentManager = childFragmentManager)
        adapter.addFragment(CreditFragment(), "Credit")
        adapter.addFragment(DebitFragment(), "Debit")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
//        tabLayout.getTabAt(0)!!.setIcon(R.id.)
    }
}