package com.kai.covid_19indiastatistics.listeners

import com.kai.covid_19indiastatistics.model.Regional

interface StateListListener{
    fun onItemClicked( regional: Regional )
}