package com.artem.mi.spacexautenticom.ui.launchpad

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artem.mi.spacexautenticom.databinding.LaunchpadItemBinding
import com.artem.mi.spacexautenticom.model.LaunchpadData

class LaunchpadAdapter(
    private val onItemSelect: (suiteId: String) -> Unit
) : ListAdapter<LaunchpadData, LaunchpadAdapter.ViewHolder>(LaunchpadDiffCallback()) {

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

        fun bind(item: LaunchpadData, onItemSelect: (suiteId: String) -> Unit) =
            with(binding) {
                launchpadName.text = item.site_name_long

                root.setOnClickListener {
                    onItemSelect(item.site_id)
                }
            }
    }
}

private class LaunchpadDiffCallback : DiffUtil.ItemCallback<LaunchpadData>() {
    override fun areItemsTheSame(oldItem: LaunchpadData, newItem: LaunchpadData): Boolean {
        return oldItem.site_id == newItem.site_id
    }

    override fun areContentsTheSame(oldItem: LaunchpadData, newItem: LaunchpadData): Boolean {
        return oldItem == newItem
    }

}