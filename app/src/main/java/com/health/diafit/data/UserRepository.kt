package com.health.diafit.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.health.diafit.R
import com.health.diafit.data.local.HistoryDao
import com.health.diafit.data.local.UserPreference
import com.health.diafit.models.UserData
import com.health.diafit.data.local.entity.HistoryEntity
import com.health.diafit.util.PredictUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val historyDao: HistoryDao,
    private val userPreference: UserPreference,
    @ApplicationContext private val context: Context
) {
    fun getUsername() = userPreference.getUsername()

    suspend fun saveLanguage(language: String) {
        userPreference.saveLanguage(language)
    }

    fun getLanguage(): LiveData<String> {
        return userPreference.getLanguageSetting().asLiveData()
    }

    fun getHistories(): LiveData<ResultState<List<HistoryEntity>>> = liveData {
        emit(ResultState.Loading)
        try {
            val localData: LiveData<ResultState<List<HistoryEntity>>> = historyDao.getHistories().map {
                ResultState.Success(it)
            }
            emitSource(localData)
        } catch (e: IOException) {
            emit(ResultState.Error(R.string.http_error_default_message.toString()))
        } catch (e: Exception) {
            emit(ResultState.Error(R.string.an_unexpected_error_occurred.toString()))
        }
    }

    fun getCurrentHistory(): LiveData<ResultState<HistoryEntity>> = liveData {
        emit(ResultState.Loading)
        try {
            val localData: LiveData<ResultState<HistoryEntity>> = historyDao.getCurrentHistory().map { history ->
                if (history != null) {
                    ResultState.Success(history)
                } else {
                    ResultState.Error("Data tidak ditemukan")
                }
            }
            emitSource(localData)
        } catch (e: IOException) {
            emit(ResultState.Error(R.string.http_error_default_message.toString()))
        } catch (e: Exception) {
            emit(ResultState.Error(R.string.an_unexpected_error_occurred.toString()))
        }
    }

    suspend fun setUsername(username: String): ResultState<String> {
        return try {
            userPreference.saveUsername(username)
            ResultState.Success(context.getString(R.string.profile_updated))
        } catch (e: IOException) {
            ResultState.Error(R.string.http_error_default_message.toString())
        } catch (e: Exception) {
            ResultState.Error(R.string.an_unexpected_error_occurred.toString())
        }
    }

    fun setUserPredictData(userData: UserData) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = PredictUtil.assessDiabetesRisk(userData, context)
            val date = SimpleDateFormat("dd/MMM/yyyy HH:mm", Locale.getDefault())
            val currentDate = date.format(Date())

            val history = HistoryEntity(
                diabetesCategory = successResponse,
                hbA1cLevel = userData.hbA1cLevel,
                checkDate = currentDate,
                bloodGlucoseLevel = userData.bloodGlucoseLevel,
            )
            historyDao.insertHistory(history)
            emit(ResultState.Success(successResponse))
        } catch (e: IOException) {
            emit(ResultState.Error(R.string.http_error_default_message.toString()))
        } catch (e: Exception) {
            emit(ResultState.Error(R.string.an_unexpected_error_occurred.toString()))
        } catch (e: IllegalArgumentException) {
            emit(ResultState.Error(R.string.invalid_input_data.toString()))
        }
    }
}