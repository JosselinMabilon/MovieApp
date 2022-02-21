package com.example.forest.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.example.forest.R
import com.example.forest.databinding.FragmentSettingBinding
import com.example.forest.viewmodel.SettingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseFragment() {

    private val viewModel: SettingViewModel by viewModels()

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun layoutRes(): Int {
        return R.layout.fragment_setting
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        when (requireContext().resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> { binding.darkModeSwitch.isChecked = false }
            Configuration.UI_MODE_NIGHT_YES -> { binding.darkModeSwitch.isChecked = true }
        }
        binding.darkModeSwitch.isChecked = viewModel.loadDarkModeSwitchData()
        checkChipDarkMode()
    }

    private fun checkChipDarkMode() {
        binding.darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.saveDarkModeSwitchData(binding.darkModeSwitch.isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else {
                viewModel.saveDarkModeSwitchData(binding.darkModeSwitch.isChecked)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}