package com.kai.covid_19indiastatistics.objects

import com.kai.covid_19indiastatistics.helper.CoronaIndianStats
import com.kai.covid_19indiastatistics.helper.CoronaIndianStatsAPI
import com.kai.covid_19indiastatistics.helper.CoronaStateStats

object CoronaIndianStatsProvider {
    private val coronaIndianStatsAPI = CoronaIndianStatsAPI.createIndianStatistics()
    private val coronaStateStatsAPI = CoronaIndianStatsAPI.createStateStatistics()

    fun provideCoronaIndianStats(): CoronaIndianStats
    {
        return CoronaIndianStats( coronaIndianStatsAPI )
    }

    fun provideCoronaStateStats(): CoronaStateStats
    {
        return CoronaStateStats( coronaStateStatsAPI )
    }
}