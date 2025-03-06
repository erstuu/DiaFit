package com.health.diafit.ui.fragment.inputdata

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.health.diafit.data.UserRepository
import com.health.diafit.models.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InputDataAdvancedViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    fun getUsername(): LiveData<String> {
        return repository.getUsername().asLiveData()
    }

    fun sendUserData(userData: UserData) = repository.setUserPredictData(userData)
}

