package com.kai.covid_19indiastatistics.model

data class IndiaStats(  var lastRefreshed: String,
                        var data: Data,
                        var success: String,
                        var lastOriginUpdate: String )
