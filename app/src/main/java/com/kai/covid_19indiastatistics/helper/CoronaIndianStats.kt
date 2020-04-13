package com.kai.covid_19indiastatistics.helper

import com.kai.covid_19indiastatistics.model.IndiaStats
import com.kai.covid_19indiastatistics.model.contacts.HelpLineDetails
import io.reactivex.Observable

class CoronaIndianStats( private val coronaIndianStatsAPI: CoronaIndianStatsAPI )
{
    fun getIndianStats(): Observable<IndiaStats>
    {
        return coronaIndianStatsAPI.getIndianStatistics()
    }

    fun getHelpLineDetails(): Observable<HelpLineDetails>
    {
        return coronaIndianStatsAPI.getHelpLineDetails()
    }
}