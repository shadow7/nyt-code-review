package com.example.martiannewsreader.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.martiannewsreader.R
import com.example.martiannewsreader.util.FragmentWithMenu
import com.example.martiannewsreader.util.Languages
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * This is the Settings Page
 * Features:
 * - Language Picker
 * - Switch to Jetpack Compose UI
 */
@AndroidEntryPoint
class SettingsFragment : FragmentWithMenu() {
    private lateinit var englishButton: RadioButton
    private lateinit var martianButton: RadioButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_settings, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
        setupSettingsUI()
    }

    private fun setLanguage(language: Languages) {
        saveLanguage(language)
        sharedViewModel.setLanguage(language)
    }

    override fun onItemSelected(menuItem: MenuItem): Boolean =
        when (menuItem.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }

            else -> false
        }

    private fun setupUI(view: View) {
        englishButton = view.findViewById(R.id.english_picker_button)
        martianButton = view.findViewById(R.id.martian_picker_button)

        englishButton.setOnClickListener {
            setLanguage(Languages.ENGLISH)
        }

        martianButton.setOnClickListener {
            setLanguage(Languages.MARTIAN)
        }
    }

    private fun setupSettingsUI() {
        sharedViewModel.selectedLanguageFlow.onEach { language ->
            val isEnglish = language == Languages.ENGLISH
            englishButton.isChecked = isEnglish
            martianButton.isChecked = !isEnglish
        }.launchIn(lifecycleScope)
    }
}
