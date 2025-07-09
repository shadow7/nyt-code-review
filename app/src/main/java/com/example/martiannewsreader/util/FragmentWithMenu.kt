package com.example.martiannewsreader.util

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.martiannewsreader.ui.MainActivity
import com.example.martiannewsreader.viewmodel.SharedViewModel

abstract class FragmentWithMenu : Fragment(), MenuProvider {
    protected lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel = (requireActivity() as MainActivity).sharedViewModel
    }

    override fun onResume() {
        super.onResume()
        requireActivity().addMenuProvider(this)
    }

    override fun onStop() {
        super.onStop()
        requireActivity().removeMenuProvider(this)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        createMenu(menu, menuInflater)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean =
        onItemSelected(menuItem)

    protected abstract fun onItemSelected(menuItem: MenuItem): Boolean

    protected open fun createMenu(menu: Menu, menuInflater: MenuInflater) {}

    protected fun setActionBarTitle(title: String) {
        val supportActionBar = (requireActivity() as AppCompatActivity).supportActionBar
        supportActionBar?.title = title
    }

    protected fun getLanguage(): Languages {
        val languageOrdinal = PreferenceManager.getDefaultSharedPreferences(requireContext())
            .getInt(Constants.APP_LANGUAGE, Languages.ENGLISH.ordinal)
        return Languages.values()[languageOrdinal]
    }

    protected fun saveLanguage(language: Languages) {
        val editor = PreferenceManager.getDefaultSharedPreferences(requireContext())
            .edit()
        editor.putInt(Constants.APP_LANGUAGE, language.ordinal)
        editor.apply()
    }
}
