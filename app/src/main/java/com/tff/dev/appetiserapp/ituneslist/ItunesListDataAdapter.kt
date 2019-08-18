package com.tff.dev.appetiserapp.ituneslist

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import com.tff.dev.appetiserapp.R
import com.tff.dev.appetiserapp.databinding.ListTunesItemBinding
import com.tff.dev.appetiserapp.room.ItunesData
import kotlinx.android.synthetic.main.list_tunes_item.view.*

/**
 * Data adapter for the recycler view of the iTunes list
 *
 * @param clickForMoreDetailsListener method that is triggered when an item is clicked
 *
 */

class ItunesListDataAdapter (private val clickForMoreDetailsListener: (ItunesData)->Unit): RecyclerView.Adapter<ItunesListDataAdapter.ViewHolder>(), Filterable{


    private var itunesList: List<ItunesData> = emptyList()
    private var itunesListAll: List<ItunesData> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val binding: ListTunesItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.list_tunes_item, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itunesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(itunesList[position])
    }

    inner class ViewHolder(val binding: ListTunesItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ItunesData){
            itemView.itunes_card.setOnClickListener {
                clickForMoreDetailsListener(item)
            }
            binding.item = item
            binding.executePendingBindings()
        }
    }

    /**
     * Set the list that will be displayed in the recycler view.
     * The value will be assigned to itunesList
     *
     * @param itunesList the list to be displayed
     */

    fun setItunesList(itunesList: List<ItunesData>) {
        this.itunesList = itunesList
        itunesListAll = itunesList
    }


    override fun getFilter(): Filter {

        var filter = object : Filter(){
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()
                val filterResults = FilterResults()

                if(itunesListAll.isNotEmpty()){
                    itunesList = itunesListAll
                    if (charString.isNotEmpty()) {

                        var filteredList: List<ItunesData> = listOf()
                        for (ItunesData in itunesListAll) {

                            if (ItunesData.trackName.toLowerCase().contains(charString.toLowerCase())) {
                                filteredList += ItunesData
                            }
                        }

                        itunesList = filteredList

                    }
                    filterResults.values = itunesList

                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                notifyDataSetChanged()

                if(itunesList.isNotEmpty()){
                    if (results != null && results.count > 0) {
                        itunesList = results.values as List<ItunesData>
                    }
                }

            }
        }
        return filter
    }

}