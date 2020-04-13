package com.kai.covid_19indiastatistics.model.contacts

data class Contacts (val primary : Primary,
					 val regional : List<Regional> )