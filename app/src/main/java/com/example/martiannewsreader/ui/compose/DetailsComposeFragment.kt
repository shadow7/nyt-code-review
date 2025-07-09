package com.example.martiannewsreader.ui.compose

import ArticleDetailsContent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.navigation.fragment.findNavController
import com.example.martiannewsreader.R
import com.example.martiannewsreader.util.FragmentWithMenu

/**
 * This is a Jetpack Compose version of [ListFragment]
 */
class DetailsComposeFragment : FragmentWithMenu() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_compose, container, false)
        val composeView = view.findViewById<ComposeView>(R.id.compose_view)
        composeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MaterialTheme {
                    val selectedItem by sharedViewModel.selectedArticleFlow.collectAsState()
                    ArticleDetailsContent(article = selectedItem)
                }
            }
        }
        return view
    }

    /// MENU

    override fun onItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }

            R.id.settings_action_button -> {
                findNavController().navigate(R.id.action_detailComposeFragment_to_settingsFragment)
                true
            }

            else -> false
        }

    override fun createMenu(menu: Menu, menuInflater: MenuInflater) {
        super.createMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.settings_menu, menu)
    }
}