package com.example.muscuapp_vmob_1.domain.use_cases

import javax.inject.Inject

class CalculateChargeUseCase @Inject constructor() {
    operator fun invoke(percentStr: String, max: Float?): Float {
        if (max == null) return 0f
        val percent = percentStr.toFloatOrNull() ?: 0f
        return (percent / 100f) * max
    }
}