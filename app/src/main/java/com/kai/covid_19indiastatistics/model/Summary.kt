package com.kai.covid_19indiastatistics.model

data class Summary(  var total: String,
                     var confirmedButLocationUnidentified: String,
                     var confirmedCasesForeign: String,
                     var discharged: String,
                     var confirmedCasesIndian: String,
                     var deaths: String )