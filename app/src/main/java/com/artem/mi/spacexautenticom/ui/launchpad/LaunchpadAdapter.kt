package com.artem.mi.spacexautenticom.ui.launchpad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.artem.mi.spacexautenticom.R
import com.artem.mi.spacexautenticom.model.LaunchpadData
import com.google.android.material.textview.MaterialTextView

class LaunchpadAdapter(
    private val onItemSelect: (suiteId: String) -> Unit
) : ListAdapter<LaunchpadData, LaunchpadAdapter.ViewHolder>(LaunchpadDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.launchpad_item, parent, false)



        return ViewHolder(view, onItemSelect)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        itemView: View,
        private val onItemSelect: (suiteId: String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val launchpadName: MaterialTextView = itemView.findViewById(R.id.launchpad_name)

        init {
            itemView.setOnClickListener {
                onItemSelect(getItem(absoluteAdapterPosition).site_id)
            }
        }

        fun bind(item: LaunchpadData) {
            launchpadName.text = item.site_name_long

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