package com.example.martiannewsreader.ui.compose

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.findNavController
import com.example.martiannewsreader.R
import com.example.martiannewsreader.util.FragmentWithMenu

/**
 * Home page, choose between Compose / XML version
 */
class HomeFragment : FragmentWithMenu() {
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
                    HomeContent(
                        modifier = Modifier.padding(16.dp),
                        openXMLFlow = {
                            findNavController().navigate(R.id.action_homeFragment_to_listXMLFragment)
                        },
                        openComposeFlow = {
                            findNavController().navigate(R.id.action_homeFragment_to_listComposeFragment)
                        },
                    )
                }
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.reloadArticles()
    }

    override fun createMenu(menu: Menu, menuInflater: MenuInflater) {
        super.createMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.settings_menu, menu)
    }

    override fun onItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            R.id.settings_action_button -> {
                findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
                true
            }

            else -> false
        }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    openXMLFlow: () -> Unit,
    openComposeFlow: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.choose_an_article_list),
            textAlign = TextAlign.Center,
            modifier = modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp, top = 0.dp)
        )
        Button(
            onClick = { openXMLFlow() },
        ) {
            Text(text = stringResource(id = R.string.open_xml))
        }
        Button(
            onClick = { openComposeFlow() },
        ) {
            Text(text = stringResource(id = R.string.open_compose))
        }
    }
}