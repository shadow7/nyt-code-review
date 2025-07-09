package com.example.martiannewsreader.ui.xml

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.martiannewsreader.R
import com.example.martiannewsreader.util.FragmentWithMenu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class DetailFragment : FragmentWithMenu() {
    private lateinit var title: TextView
    private lateinit var image: ImageView
    private lateinit var body: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
    }

    override fun onItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }

            R.id.settings_action_button -> {
                findNavController().navigate(R.id.action_detailFragment_to_settingsFragment)
                true
            }

            else -> false
        }

    override fun createMenu(menu: Menu, menuInflater: MenuInflater) {
        super.createMenu(menu, menuInflater)
        menuInflater.inflate(R.menu.settings_menu, menu)
    }

    private fun setupUI(view: View) {
        title = view.findViewById(R.id.article_title)
        image = view.findViewById(R.id.article_image)
        body = view.findViewById(R.id.article_body)

        sharedViewModel.selectedArticleFlow.onEach { selectedArticle ->
            if (selectedArticle != null) {
                title.text = selectedArticle.title
                Glide
                    .with(view)
                    .load(selectedArticle.imageUrl)
                    .into(image)
                body.text = selectedArticle.body
            } else {
                body.text = getString(R.string.invalid_article)
            }
        }.launchIn(lifecycleScope)
    }
}
