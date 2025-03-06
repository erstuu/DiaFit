package com.health.diafit.ui.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.health.diafit.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun saveLanguage(language: String) {
        viewModelScope.launch {
            userRepository.saveLanguage(language)
        }
    }

    fun getLanguage(): LiveData<String> {
        return userRepository.getLanguage()
    }

    fun getUsername() = userRepository.getUsername().asLiveData()

}