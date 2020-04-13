package com.kai.covid_19indiastatistics.model

import java.io.Serializable


data class Regional( var loc: String,
                     var confirmedCasesForeign: String,
                     var discharged: String,
                     var confirmedCasesIndian: String,
                     var deaths:String ) : Serializable