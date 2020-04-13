package com.kai.covid_19indiastatistics.view

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.kai.covid_19indiastatistics.R
import com.kai.covid_19indiastatistics.adapters.HelplineListAdapter
import com.kai.covid_19indiastatistics.listeners.HelplineListListener
import com.kai.covid_19indiastatistics.model.contacts.HelpLineDetails
import com.kai.covid_19indiastatistics.model.contacts.Regional
import com.kai.covid_19indiastatistics.objects.CoronaIndianStatsProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class HelplineFragment : Fragment(), HelplineListListener
{
    private lateinit var mRecycleListView: RecyclerView
    private var mHelplineList: MutableList<Regional> = mutableListOf()
    private lateinit var mHelpLineDetails: HelpLineDetails
    private lateinit var progressBar: ProgressBar
    private lateinit var mHeaderView: View
    private lateinit var mListSeparator: View
    private lateinit var mHeaderText: TextView
    private lateinit var mMediaContainer: View
    private lateinit var mFacebookContainer: View
    private lateinit var mTwitterContainer: View
    private lateinit var mEmailContainer: View
    private lateinit var mFloatingButton: View
    private lateinit var mCentralHelpline: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View?
    {
        val view = inflater.inflate( R.layout.fragment_helpline, container, false)
        mRecycleListView = view.findViewById(R.id.recycler_view_list)
        mRecycleListView.setHasFixedSize(true)
        mRecycleListView.layoutManager = LinearLayoutManager( context )
        progressBar = view.findViewById( R.id.progress_circular )
        mHeaderView = view.findViewById( R.id.header_container );
        mListSeparator = view.findViewById( R.id.list_header_container )
        mHeaderText = view.findViewById( R.id.list_header_textview )
        mFloatingButton = view.findViewById( R.id.tollfree_container )
        mMediaContainer = view.findViewById( R.id.cardView1 )
        mFacebookContainer = view.findViewById( R.id.cardView2 )
        mTwitterContainer = view.findViewById( R.id.cardView3 )
        mEmailContainer = view.findViewById( R.id.cardView4 )
        mCentralHelpline = view.findViewById( R.id.central_helpline_cardview )

        getData()

        return view;
    }

    private fun getData()
    {
        progressBar.visibility = View.VISIBLE

        CoronaIndianStatsProvider.provideCoronaIndianStats().getHelpLineDetails().observeOn(
            AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result -> setData( result )
            }, { error ->
                error.printStackTrace()
            } )
    }

    private fun setData( result: HelpLineDetails )
    {
        mHelpLineDetails = result
        mHelplineList = result.data.contacts.regional as MutableList<Regional>

        loadRecyclerViewData()
        setListeners()

        progressBar.visibility = View.GONE
    }

    private fun setListeners()
    {
        mFloatingButton.setOnClickListener {
            openDialPad(resources.getString(R.string.toll_free_no))
        }

        mCentralHelpline.setOnClickListener {
            openDialPad("+91–11–23978046" )
        }
        mEmailContainer.setOnClickListener {
            context?.let { it1 -> getOpenEmailIntent(it1, mHelpLineDetails.data.contacts.primary.email) }
        }

        mFacebookContainer.setOnClickListener {
            context?.let { it1 -> getOpenFacebookIntent(it1, mHelpLineDetails.data.contacts.primary.facebook ) }
        }

        mTwitterContainer.setOnClickListener {
            context?.let { it1 -> getOpenTwitterIntent(it1, mHelpLineDetails.data.contacts.primary.twitter) }
        }

        mMediaContainer.setOnClickListener {
            context?.let { it1 -> getOpenMediaIntent(it1, mHelpLineDetails.data.contacts.primary.media[0]) }
        }
    }

    private fun loadRecyclerViewData()
    {
        mHeaderText.text = resources.getString(R.string.state_wise_helpline)

        mRecycleListView.adapter = HelplineListAdapter( mHelplineList ){
            onItemClicked( it )
        }
    }

    override fun onItemClicked(regional:Regional) {
        openDialPad( regional.number.split(",")[0] )
    }

    private fun openDialPad( number: String )
    {
        val intent = Intent()
        intent.action = Intent.ACTION_DIAL
        intent.data = Uri.parse("tel: $number")
        startActivity(intent)
    }

    fun getOpenFacebookIntent(context: Context, url: String)
    {
        try
        {
            val info: PackageInfo = context.packageManager.getPackageInfo("com.facebook.katana", 0)
            if( info.applicationInfo.enabled )
            {
                var facebookURL = ""
                if (info.versionCode >= 3002850)
                {
                    facebookURL = "fb://facewebmodal/f?href=$url";
                }
                else
                {
                    facebookURL = "fb://page/" + "MoHFWIndia" ;
                }
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(facebookURL)))
            }
        }
        catch (e: Exception)
        {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }

    fun getOpenTwitterIntent( context: Context, url: String )
    {
        try
        {
            val info: PackageInfo = context.packageManager.getPackageInfo("com.twitter.android", 0)
            if( info.applicationInfo.enabled )
            {
                val twitterURL = "twitter://user?user_id=2596143056"
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(twitterURL)))
            }
        }
        catch (e: Exception)
        {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }

    fun getOpenEmailIntent( context: Context, emailID: String)
    {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.setDataAndType( Uri.parse(emailID), "text/plain")
        context.startActivity(emailIntent)
    }

    fun getOpenMediaIntent( context: Context, url: String )
    {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(browserIntent)
    }

}