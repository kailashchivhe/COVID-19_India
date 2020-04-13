package com.kai.covid_19indiastatistics.listeners

import com.kai.covid_19indiastatistics.model.contacts.Regional

interface HelplineListListener {
    fun onItemClicked( regional: Regional)
}