package com.kai.covid_19indiastatistics.helper

import com.kai.covid_19indiastatistics.model.state.StateDetails
import io.reactivex.Observable

class CoronaStateStats( private val coronaIndianStatsAPI: CoronaIndianStatsAPI )
{
    fun getStateDetails(): Observable<ArrayList<StateDetails>>
    {
        return coronaIndianStatsAPI.getStateDetails()
    }
}