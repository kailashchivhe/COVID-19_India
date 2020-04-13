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
import com.kai.covid_19indiastatistics.model.contacts.Regional
import kotlinx.android.synthetic.main.card_layout_state_item.view.*
import java.util.*
import kotlin.random.Random

class HelplineListAdapter(private val mAuthorList: List<Regional>, private val listener: (Regional) -> Unit) : RecyclerView.Adapter< HelplineListAdapter.HelpViewHolder >()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int ): HelpViewHolder
    {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_layout_state_item, parent, false)
        return HelpViewHolder(view)
    }

    override fun onBindViewHolder(holder: HelpViewHolder, position: Int)
    {
        holder.bind(mAuthorList[position], listener)
    }

    override fun getItemCount(): Int
    {
        return mAuthorList.size
    }

    class HelpViewHolder( itemView: View) : RecyclerView.ViewHolder( itemView )
    {
        @SuppressLint("SetTextI18n")
        fun bind(regional: Regional, listener: (Regional) -> Unit) = with( itemView )
        {
            header_text.text = "CALL : ${regional.number.split(",")[0]}"
            sub_text.text = regional.loc.toUpperCase(Locale.ROOT);
            val drawable = TextDrawable.builder().buildRound( regional.loc[0].toString() , getColor(context) )
            imageview.setImageDrawable(drawable)
            cardView.setOnClickListener{ listener(regional) }
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