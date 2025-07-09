package com.example.martiannewsreader.ui.compose

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.martiannewsreader.R
import com.example.martiannewsreader.util.FragmentWithMenu
import com.example.martiannewsreader.util.Languages
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * This is a Jetpack Compose version of [ListFragment]
 */
class ListComposeFragment : FragmentWithMenu() {

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
                    val isLoading by sharedViewModel.isLoadingArticlesFlow.collectAsState()
                    val articles by sharedViewModel.articlesFlow.collectAsState()

                    ArticleListContent(
                        isLoading = isLoading,
                        articles = articles,
                        onArticleSelected = { index ->
                            sharedViewModel.selectArticle(index) {
                                findNavController().navigate(R.id.action_listComposeFragment_to_detailsComposeFragment)
                            }
                        })
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    /// MENU

    override fun onItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }

            R.id.settings_action_button -> {
                findNavController().navigate(R.id.action_listComposeFragment_to_settingsFragment)
                true
            }

            else -> false
        }

    override fun createMenu(menu: Menu, menuInflater: MenuInflater) {
        super.createMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.settings_menu, menu)
    }

    private fun setupUI() {
        sharedViewModel.selectedLanguageFlow.onEach { language ->
            if (isVisible) {
                when (language) {
                    Languages.ENGLISH -> setActionBarTitle(resources.getString(R.string.articles_compose))
                    Languages.MARTIAN -> setActionBarTitle(resources.getString(R.string.articles_martian_compose))
                }
            }
        }.launchIn(lifecycleScope)
    }
}