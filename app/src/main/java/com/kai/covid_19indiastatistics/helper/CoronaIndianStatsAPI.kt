package com.kai.covid_19indiastatistics.helper

import com.kai.covid_19indiastatistics.model.IndiaStats
import com.kai.covid_19indiastatistics.model.state.StateDetails
import com.kai.covid_19indiastatistics.model.contacts.HelpLineDetails
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface CoronaIndianStatsAPI {
    @GET("/covid19-in/stats/latest" )
    fun getIndianStatistics(): Observable<IndiaStats>

    @GET("/v2/state_district_wise.json" )
    fun getStateDetails(): Observable<ArrayList<StateDetails>>

    @GET( "/covid19-in/contacts" )
    fun getHelpLineDetails():Observable<HelpLineDetails>

    companion object Factory
    {
        fun createIndianStatistics(): CoronaIndianStatsAPI
        {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.rootnet.in" )
                .build()

            return retrofit.create(CoronaIndianStatsAPI::class.java);
        }

        fun createStateStatistics(): CoronaIndianStatsAPI
        {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.covid19india.org" )
                .build()

            return retrofit.create(CoronaIndianStatsAPI::class.java);
        }
    }
}