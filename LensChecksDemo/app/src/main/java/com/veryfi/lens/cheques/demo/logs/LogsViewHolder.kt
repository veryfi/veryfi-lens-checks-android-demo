package com.veryfi.lens.cheques.demo.logs

import androidx.recyclerview.widget.RecyclerView
import com.veryfi.lens.cheques.demo.databinding.LogsListItemBinding

class LogsViewHolder(private val itemBinding: LogsListItemBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun onBind(log: Log) {
        itemBinding.logsTitle.text = log.title
        itemBinding.logsMessage.text = log.message
    }
}