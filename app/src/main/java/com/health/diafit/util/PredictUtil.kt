package com.health.diafit.util

import android.content.Context
import androidx.core.content.ContextCompat
import com.health.diafit.R
import com.health.diafit.models.UserData

object PredictUtil {

    fun setBmi(bodyWeight: String, height: String): Float {
        val heightInMeter = height.toFloat() / 100
        return bodyWeight.toFloat() / (heightInMeter * heightInMeter)
    }

    fun assessDiabetesRisk(userData: UserData, context: Context): String {
        val gender = userData.gender
        val age = userData.age
        val hypertension = userData.hypertension
        val heartDisease = userData.heartDisease
        val smokingHistory = userData.smokingHistory
        val bmi = userData.bmi
        val hba1cLevel = userData.hbA1cLevel
        val bloodGlucoseLevel = userData.bloodGlucoseLevel

        // Validate inputs
        if (!validateInputs(gender, age, smokingHistory, heartDisease, hypertension)) {
            throw IllegalArgumentException("Invalid input data")
        }

        // Cek Diabetes Tidak Terkendali
        if ((bloodGlucoseLevel != null && bloodGlucoseLevel > 140) ||
            (hba1cLevel != null && hba1cLevel >= 7.0) ||
            (isYesValue(hypertension) && isYesValue(heartDisease))) {
            return ContextCompat.getString(context, R.string.diabetes_tidak_terkendali)
        }

        // Cek Diabetes Terkendali
            if ((bloodGlucoseLevel != null && bloodGlucoseLevel in 126..140) ||
                (hba1cLevel != null && hba1cLevel in 6.5..7.0)) {
                if (isNoValue(hypertension) && isNoValue(heartDisease)) {
                    return ContextCompat.getString(context, R.string.diabetes_terkendali)
                }
                return ContextCompat.getString(context, R.string.diabetes_tidak_terkendali)
            }

        // Cek Pra-diabetes
        if ((bloodGlucoseLevel != null && bloodGlucoseLevel in 100..126) ||
            (hba1cLevel != null && hba1cLevel in 5.7..6.5) ||
            (bmi != null && bmi >= 25)) {

            // Spesifik Gender
            if (isFemaleValue(gender) && bmi != null && bmi >= 30) {
                return ContextCompat.getString(context, R.string.pra_diabetes)
            } else if (isMaleValue(gender) && bmi != null && bmi >= 27) {
                return ContextCompat.getString(context, R.string.pra_diabetes)
            }

            // Faktor resiko tambahan
            if (age != null && age > 45 ||
                isCurrentOrFormerSmoker(smokingHistory) ||
                isYesValue(hypertension)) {
                return ContextCompat.getString(context, R.string.pra_diabetes)
            }
        }

        return ContextCompat.getString(context, R.string.normal)
    }

    // Helper methods for more robust string comparison
    private fun isYesValue(value: String?): Boolean {
        return value?.lowercase()?.let {
            it == "yes" || it == "ya"
        } ?: false
    }

    private fun isNoValue(value: String?): Boolean {
        return value?.lowercase()?.let {
            it == "no" || it == "tidak"
        } ?: false
    }

    private fun isMaleValue(value: String?): Boolean {
        return value?.lowercase()?.let {
            it == "male" || it == "laki-laki"
        } ?: false
    }

    private fun isFemaleValue(value: String?): Boolean {
        return value?.lowercase()?.let {
            it == "female" || it == "perempuan"
        } ?: false
    }

    private fun isCurrentOrFormerSmoker(value: String?): Boolean {
        return value?.lowercase()?.let {
            it.contains("current") || it.contains("former") ||
                    it.contains("saat ini") || it.contains("pernah")
        } ?: false
    }

    // Validation methods
    private fun isValidGender(gender: String?): Boolean {
        val validGenders = listOf("male", "female", "laki-laki", "perempuan")
        return gender?.lowercase() in validGenders
    }

    private fun isValidAge(age: Int?): Boolean {
        return age != null && age in 1..90
    }

    private fun isValidSmokingHistory(smokingHistory: String?): Boolean {
        val validSmokingHistories = listOf(
            "no info", "never", "former", "current", "not current", "ever",
            "tidak ada info", "tidak pernah", "pernah", "saat ini", "tidak saat ini"
        )
        return smokingHistory?.lowercase() in validSmokingHistories
    }

    private fun isValidHeartDisease(heartDisease: String?): Boolean {
        val validHeartDiseases = listOf("yes", "no", "ya", "tidak")
        return heartDisease?.lowercase() in validHeartDiseases
    }

    private fun isValidHypertension(hypertension: String?): Boolean {
        val validHypertensions = listOf("yes", "no", "ya", "tidak")
        return hypertension?.lowercase() in validHypertensions
    }

    private fun validateInputs(
        gender: String?,
        age: Int?,
        smokingHistory: String?,
        heartDisease: String?,
        hypertension: String?
    ): Boolean {
        return isValidGender(gender) &&
                isValidAge(age) &&
                isValidSmokingHistory(smokingHistory) &&
                isValidHeartDisease(heartDisease) &&
                isValidHypertension(hypertension)
    }
}