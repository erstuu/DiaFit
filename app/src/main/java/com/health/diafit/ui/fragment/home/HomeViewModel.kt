package com.health.diafit.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.health.diafit.data.ResultState
import com.health.diafit.data.UserRepository
import com.health.diafit.data.local.entity.HistoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getUsername(): LiveData<String> {
        return userRepository.getUsername().asLiveData()
    }

    fun getCurrentHistory(): LiveData<ResultState<HistoryEntity>> {
        return userRepository.getCurrentHistory()
    }
}