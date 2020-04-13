package com.kai.covid_19indiastatistics.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kai.covid_19indiastatistics.MainActivity
import com.kai.covid_19indiastatistics.model.IndiaStats
import com.kai.covid_19indiastatistics.model.Regional
import com.kai.covid_19indiastatistics.objects.CoronaIndianStatsProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.kai.covid_19indiastatistics.R
import com.kai.covid_19indiastatistics.listeners.StateListListener
import com.kai.covid_19indiastatistics.adapters.StateListAdapter
import kotlinx.android.synthetic.main.grid_layout_summary.*


class MainFragment : Fragment(),
    StateListListener
{
    private lateinit var mRecycleListView: RecyclerView
    private var mStateList: MutableList<Regional> = mutableListOf()
    private lateinit var mIndiaStats: IndiaStats
    private lateinit var progressBar: ProgressBar
    private lateinit var mHeaderView: View
    private lateinit var mListSeparator: View
    private lateinit var mHeaderText: TextView
    private lateinit var mFloatingActionButton: FloatingActionButton

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View?
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

        CoronaIndianStatsProvider.provideCoronaIndianStats().getIndianStats().observeOn(
            AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result -> setData( result )
            }, { error ->
                error.printStackTrace()
            } )
    }

    private fun setData( result: IndiaStats )
    {
        sortList( result.data.regional )
        mIndiaStats = result
        loadRecyclerViewData()
        setSummaryData()
        mHeaderView.visibility = View.VISIBLE;
        mListSeparator.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun setSummaryData()
    {
        cardView1_textview2.text = mIndiaStats.data.summary.total
        cardView2_textview2.text = mIndiaStats.data.summary.discharged
        cardView3_textview2.text = mIndiaStats.data.summary.deaths
    }

    private fun loadRecyclerViewData()
    {
        mStateList = mIndiaStats.data.regional

        mHeaderText.text = resources.getString(R.string.state_wise_summary)

        mRecycleListView.adapter = StateListAdapter( mStateList ){
            onItemClicked( it )
        }
    }

    private fun sortList( list: MutableList<Regional> )
    {
        val n = list.size
        for (i in 0 until n )
        {
            for ( j in 0 until (n - i - 1) )
            {

                if ( ( Integer.parseInt( list.get(j).confirmedCasesIndian  ) + Integer.parseInt( list.get(j).confirmedCasesForeign ) ) <
                    ( Integer.parseInt( list.get( j +1 ).confirmedCasesIndian ) + Integer.parseInt( list.get( j +1 ).confirmedCasesForeign ) ) )
                {
                    val regional = list[j]
                    list[j] = list[j+1]
                    list[j+1] = regional
                }
            }
        }
    }

    override fun onItemClicked(regional: Regional)
    {
        val bundle = Bundle()
        bundle.putSerializable( "regional", regional )
        val intent = Intent( MainActivity.OPEN_DISTRICT_FRAGMENT )
        intent.putExtras( bundle )
        context?.let { LocalBroadcastManager.getInstance(it).sendBroadcast(intent) }
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