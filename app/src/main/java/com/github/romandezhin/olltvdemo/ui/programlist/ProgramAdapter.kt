package com.github.romandezhin.olltvdemo.ui.programlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.github.romandezhin.olltvdemo.databinding.ProgramListContentBinding
import com.github.romandezhin.olltvdemo.domain.model.Program

class ProgramAdapter(
    private val values: MutableList<Program>,
    private val onClickListener: View.OnClickListener
) : RecyclerView.Adapter<ProgramAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ProgramListContentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        Glide
            .with(holder.icon)
            .load(item.icon)
            .override(Target.SIZE_ORIGINAL)
            .into(holder.icon)
        holder.content.text = item.name

        with(holder.itemView) {
            tag = position
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    fun addItems(list: List<Program>) {
        val currentSize = itemCount
        if (currentSize == 0) {
            values.addAll(list)
            notifyItemRangeInserted(0, list.size)
        } else
            if (values[0].id == list[0].id) {
                values.addAll(list.subList(itemCount, list.size))
                notifyItemRangeInserted(currentSize, list.size - currentSize)
            } else if (values[itemCount - 1] == list[list.size - 1]) {
                values.addAll(0, list.subList(0, list.size - currentSize))
                notifyItemRangeInserted(0, list.size - currentSize)
            }
    }

    fun getFirstItemId(): Int {
        return values[0].id
    }

    fun getLastItemId(): Int {
        return values.last().id
    }

    inner class ViewHolder(binding: ProgramListContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val icon: ImageView = binding.icon
        val content: TextView = binding.content
    }

}