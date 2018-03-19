package com.iia.fabien.agregateur.views.activities

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.widget.DatePicker
import com.iia.fabien.agregateur.R
import com.iia.fabien.agregateur.views.fragments.ReservationListFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

const val ITEM_TYPE = "com.iia.fabien.agregateur.ITEM_TYPE"
val DATE_DISPLAY_FORMAT = SimpleDateFormat("dd/MM/yyyy")

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainTabsViewPager.adapter = SectionsPagerAdapter(supportFragmentManager)

        mainTabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(mainTabsViewPager))

        addResa.setOnClickListener {
            startActivity( Intent(this, CreateReservationActivity::class.java).apply {
                putExtra(ITEM_TYPE, mainTabs.selectedTabPosition)
            })
        }
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return ReservationListFragment().apply {
                arguments = Bundle().apply { putInt(ITEM_TYPE, position) }
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }

}


