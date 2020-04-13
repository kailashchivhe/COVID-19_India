package com.kai.covid_19indiastatistics.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amulyakhare.textdrawable.TextDrawable
import com.kai.covid_19indiastatistics.R
import com.kai.covid_19indiastatistics.model.state.DistrictData
import kotlinx.android.synthetic.main.card_layout_state_item.view.*
import java.util.*
import kotlin.random.Random

class DistrictListAdapter(private val mAuthorList: List<DistrictData>) : RecyclerView.Adapter< DistrictListAdapter.DistrictViewHolder >()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): DistrictViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_district_item, parent, false)
        return DistrictViewHolder(view)
    }

    override fun onBindViewHolder(holder: DistrictViewHolder, position: Int)
    {
        holder.bind(mAuthorList[position])
    }

    override fun getItemCount(): Int
    {
        return mAuthorList.size
    }

    class DistrictViewHolder( itemView: View) : RecyclerView.ViewHolder( itemView )
    {
        @SuppressLint("SetTextI18n")
        fun bind(districtData: DistrictData) = with( itemView )
        {
            header_text.text = "CONFIRMED : ${(  districtData.confirmed  )}"
            sub_text.text = districtData.district.toUpperCase(Locale.ROOT);

            val drawable = TextDrawable.builder().buildRound( districtData.district[0].toString() , getColor( context ) )
            imageview.setImageDrawable(drawable)
        }
    }

    companion object{
        fun getColor( context: Context): Int
        {
            val random = Random.nextInt(0, 5 )
            when( random ){
                0 ->{
                    return context.resources.getColor( R.color.red )
                }
                1 ->{
                    return context.resources.getColor( R.color.blue )
                }
                2 ->{
                    return context.resources.getColor( R.color.yellow )
                }
                3 ->{
                    return context.resources.getColor( R.color.green )
                }
                4 ->{
                    return context.resources.getColor( R.color.purple )
                }
            }
            return context.resources.getColor( R.color.brown )
        }
    }
}
