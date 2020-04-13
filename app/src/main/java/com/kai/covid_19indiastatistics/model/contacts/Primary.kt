package com.kai.covid_19indiastatistics.model.contacts

import com.google.gson.annotations.SerializedName

data class Primary (val number : String,
					@SerializedName("number-tollfree") val number_tollfree : Int,
					val email : String,
					val twitter : String,
					val facebook : String,
					val media : List<String> )