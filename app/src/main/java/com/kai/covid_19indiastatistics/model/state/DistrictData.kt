package com.kai.covid_19indiastatistics.model.state


data class DistrictData ( val district : String,
						  val confirmed : Int,
						  val lastupdatedtime : String,
						  val delta : Delta )