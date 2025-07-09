package com.example.martiannewsreader.ui.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.martiannewsreader.R
import com.example.martiannewsreader.util.FragmentWithMenu
import com.example.martiannewsreader.util.Languages
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ListFragment : FragmentWithMenu() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: ArticleListItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
        setupArticleList()
    }

    override fun onItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }

            R.id.settings_action_button -> {
                findNavController().navigate(R.id.action_listFragment_to_settingsFragment)
                true
            }

            else -> false
        }

    override fun createMenu(menu: Menu, menuInflater: MenuInflater) {
        super.createMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.settings_menu, menu)
    }

    private fun setupUI(view: View) {
        progressBar = view.findViewById(R.id.articles_progress)
        recyclerView = view.findViewById(R.id.rv_articles)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ArticleListItemAdapter(sharedViewModel.articlesFlow.value) { position ->
            sharedViewModel.selectArticle(position) {
                findNavController().navigate(R.id.action_listFragment_to_detailFragment)
            }
        }
        recyclerView.adapter = adapter
    }

    private fun setupArticleList() {
        sharedViewModel.isLoadingArticlesFlow
            .onEach { isLoading ->
                progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
            .launchIn(lifecycleScope)

        sharedViewModel.articlesFlow
            .onEach { articles ->
                adapter.setItems(articles)
            }.launchIn(lifecycleScope)

        sharedViewModel.selectedLanguageFlow.onEach { language ->
            if (isVisible) {
                when (language) {
                    Languages.ENGLISH -> setActionBarTitle(resources.getString(R.string.articles_xml))
                    Languages.MARTIAN -> setActionBarTitle(resources.getString(R.string.articles_martian_xml))
                }
            }
        }.launchIn(lifecycleScope)
    }
}
