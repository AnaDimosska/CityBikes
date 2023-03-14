package com.example.citybikes.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.citybikes.R
import com.example.citybikes.model.Network
import com.example.citybikes.util.MyDiffCallback

class MyAdapter(val onClickListener: OnClickListener) :
    RecyclerView.Adapter<MyAdapter.BikeViewHolder>() {

    interface OnClickListener {
        fun onItemClick(position: Int)
    }

    inner class BikeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = MyDiffCallback()

    val differ = AsyncListDiffer(this, differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BikeViewHolder {
        return BikeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.bike_item,
                parent,
                false
            )
        )

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Network>) {
        differ.submitList(list)
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = if (constraint.isNullOrEmpty()) {
                    differ.currentList.toMutableList()
                } else {
                    differ.currentList.filter {
                        it.name.contains(constraint, ignoreCase = true)
                    }
                }

                return FilterResults().apply {
                    values = filteredList
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                submitList(results?.values as? List<Network> ?: emptyList())
            }
        }
    }

    override fun onBindViewHolder(holder: BikeViewHolder, position: Int) {
        val bike = differ.currentList[position]
        val countryCode = bike.location.city
        val twoLetterCode = bike.location.country
        val flagUrl = "https://flagcdn.com/16x12/${twoLetterCode.lowercase()}.png\n"
        holder.itemView.apply {
            findViewById<TextView>(R.id.companyName).text = bike.name
            findViewById<TextView>(R.id.companyCityAndCode).text = "$countryCode $twoLetterCode"
             Glide.with(this).load(flagUrl).into(findViewById(R.id.flag_image))
            setOnClickListener {
                Log.d("clicked", "item $position")
                onClickListener.onItemClick(position)
            }
        }

    }

}