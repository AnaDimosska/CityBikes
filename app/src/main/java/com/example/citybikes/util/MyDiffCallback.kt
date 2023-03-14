package com.example.citybikes.util

import androidx.recyclerview.widget.DiffUtil
import com.example.citybikes.model.Network

class MyDiffCallback : DiffUtil.ItemCallback<Network>() {
    override fun areItemsTheSame(oldItem: Network, newItem: Network): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Network, newItem: Network): Boolean {
        return oldItem == newItem
    }
}