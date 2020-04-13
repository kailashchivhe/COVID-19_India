package com.kai.covid_19indiastatistics.model

data class Data( var summary: Summary,
                 var regional: MutableList<Regional> );