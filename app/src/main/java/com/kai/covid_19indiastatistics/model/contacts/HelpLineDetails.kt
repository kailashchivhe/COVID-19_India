package com.kai.covid_19indiastatistics.model.contacts

import com.kai.covid_19indiastatistics.model.contacts.Data


data class HelpLineDetails ( val success : Boolean,
							 val data : Data,
							 val lastRefreshed : String,
							 val lastOriginUpdate : String )