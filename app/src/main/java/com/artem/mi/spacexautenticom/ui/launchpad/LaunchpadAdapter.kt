package com.artem.mi.spacexautenticom.ui.launchpad

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artem.mi.spacexautenticom.databinding.LaunchpadItemBinding
import com.artem.mi.spacexautenticom.data.network.LaunchpadData
import com.artem.mi.spacexautenticom.ui.core.ClickListener

class LaunchpadAdapter(
    private val onItemSelect: ClickListener<String>
) : ListAdapter<LaunchpadData, LaunchpadAdapter.ViewHolder>(LaunchpadDiffCallback()), ShowList {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LaunchpadItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onItemSelect)
    }

    inner class ViewHolder(
        private val binding: LaunchpadItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LaunchpadData, onItemSelect: ClickListener<String>) =
            with(binding) {
                launchpadName.text = item.siteNameLong

                root.setOnClickListener {
                    onItemSelect.onClick(item.siteId)
                }
            }
    }

    override fun load(list: List<LaunchpadData>) {
        submitList(list)
    }
}

private class LaunchpadDiffCallback : DiffUtil.ItemCallback<LaunchpadData>() {
    override fun areItemsTheSame(oldItem: LaunchpadData, newItem: LaunchpadData): Boolean {
        return oldItem.siteId == newItem.siteId
    }

    override fun areContentsTheSame(oldItem: LaunchpadData, newItem: LaunchpadData): Boolean {
        return oldItem == newItem
    }
}