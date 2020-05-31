package com.example.android.kotlinrecyclerview.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.kotlinrecyclerview.databinding.ListItemSectionsBinding
import com.example.android.kotlinrecyclerview.entity.ViaplaySection

class MainFragmentAdapter(val clickListener : SectionCLickListener): ListAdapter<ViaplaySection,MainFragmentAdapter.ViewHolder>(LinksDiffCallBack()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemSectionsBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(
            item: ViaplaySection,
            clickListener: SectionCLickListener
        ) {
            binding.sections = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemSectionsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }


}


class LinksDiffCallBack : DiffUtil.ItemCallback<ViaplaySection>() {

    override fun areItemsTheSame(oldItem: ViaplaySection, newItem: ViaplaySection): Boolean {
        return oldItem.id == newItem.id
    }


    override fun areContentsTheSame(oldItem: ViaplaySection, newItem: ViaplaySection): Boolean {
        return oldItem == newItem
    }

}

class SectionCLickListener(val clickListener: (section: ViaplaySection) -> Unit) {
    fun onClick(section: ViaplaySection) = clickListener(section)
}
