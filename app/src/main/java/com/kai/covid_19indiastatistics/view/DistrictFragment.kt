package com.kai.covid_19indiastatistics.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kai.covid_19indiastatistics.R
import com.kai.covid_19indiastatistics.adapters.DistrictListAdapter
import com.kai.covid_19indiastatistics.adapters.StateListAdapter
import com.kai.covid_19indiastatistics.model.IndiaStats
import com.kai.covid_19indiastatistics.model.Regional
import com.kai.covid_19indiastatistics.model.state.DistrictData
import com.kai.covid_19indiastatistics.model.state.StateDetails
import com.kai.covid_19indiastatistics.objects.CoronaIndianStatsProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.grid_layout_summary.*

class DistrictFragment : Fragment()
{
    private lateinit var mRecycleListView: RecyclerView
    private var mDistrictList: MutableList<DistrictData> = mutableListOf()
    private lateinit var progressBar: ProgressBar
    private lateinit var mHeaderView: View
    private lateinit var mListSeparator: View
    private lateinit var mHeaderText: TextView
    private lateinit var mRegional: Regional
    private lateinit var mFloatingActionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        mRegional = arguments?.getSerializable( "regional" ) as Regional
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View?
    {
        val view = inflater.inflate( R.layout.fragment_main, container, false)

        mRecycleListView = view.findViewById(R.id.recycler_view_list)
        mRecycleListView.setHasFixedSize(true)
        mRecycleListView.layoutManager = LinearLayoutManager( context )
        progressBar = view.findViewById( R.id.progress_circular )
        mHeaderView = view.findViewById( R.id.header_container );
        mListSeparator = view.findViewById( R.id.list_header_container )
        mHeaderText = view.findViewById( R.id.list_header_textview )
        mFloatingActionButton = view.findViewById( R.id.fab )

        mFloatingActionButton.setOnClickListener {
            setHelplineFragment()
        }

        getData()

        return view;
    }

    private fun getData()
    {
        mHeaderView.visibility = View.INVISIBLE;
        mListSeparator.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE

        CoronaIndianStatsProvider.provideCoronaStateStats().getStateDetails().observeOn(
            AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result -> setData( result )
            }, { error ->
                error.printStackTrace()
            } )
    }

    private fun setData( result: ArrayList<StateDetails> )
    {
        mDistrictList = getDistrictList(result) as MutableList<DistrictData>

        loadRecyclerViewData()

        setSummaryData()

        mHeaderView.visibility = View.VISIBLE;
        mListSeparator.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun getDistrictList(stateDetailsList: ArrayList<StateDetails>): List<DistrictData>
    {
        var districtList: List<DistrictData> = mutableListOf()
        for( state in stateDetailsList )
        {
            if( mRegional.loc.equals( "Telengana", true) )
            {
                if (state.state.equals("Telangana", true)) {
                    districtList = sortList(state.districtData as MutableList<DistrictData>)
                    break;
                }
            }
            else {
                if (state.state.equals(mRegional.loc, true)) {
                    districtList = sortList(state.districtData as MutableList<DistrictData>)
                    break;
                }
            }
        }
        return districtList
    }

    private fun setSummaryData()
    {
        cardView1_textview2.text = ( Integer.parseInt( mRegional.confirmedCasesIndian ) + Integer.parseInt( mRegional.confirmedCasesForeign ) ).toString()
        cardView2_textview2.text = mRegional.discharged
        cardView3_textview2.text = mRegional.deaths
    }

    @SuppressLint("SetTextI18n")
    private fun loadRecyclerViewData()
    {
        mHeaderText.text = "${( mRegional.loc.toUpperCase() )} ${ resources.getString(R.string.district_wise_summary) }"
        mRecycleListView.adapter = DistrictListAdapter( mDistrictList )
        mRecycleListView.adapter?.notifyDataSetChanged()
    }

    private fun sortList(list: MutableList<DistrictData> ): MutableList<DistrictData> {
        list.sortByDescending { it.confirmed }
        return  list
    }

    private fun setHelplineFragment()
    {
        val fragment = HelplineFragment()
        activity?.let {
            ViewCompat.getTransitionName(mFloatingActionButton)?.let { it1 ->
                it.supportFragmentManager.beginTransaction()
                    .addSharedElement( mFloatingActionButton, it1 )
                    .addToBackStack("HelplineFragment" )
                    .replace(R.id.fragment_container, fragment, "HelplineFragment")
                    .commit()
            }
        }
    }
}
