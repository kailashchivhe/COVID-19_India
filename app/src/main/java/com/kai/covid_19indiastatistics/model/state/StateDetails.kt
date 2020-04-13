package com.kai.covid_19indiastatistics.model.state


data class StateDetails ( val state : String,
						  val districtData : List<DistrictData> )