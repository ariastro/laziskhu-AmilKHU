package org.laziskhu.amilkhu.ui.attendance.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.laziskhu.amilkhu.data.source.remote.response.HistoryAttendance
import org.laziskhu.amilkhu.databinding.ItemHistoryBinding
import org.laziskhu.amilkhu.utils.Constants.ACCEPTED
import org.laziskhu.amilkhu.utils.Constants.DENIED
import org.laziskhu.amilkhu.utils.Constants.DITERIMA
import org.laziskhu.amilkhu.utils.Constants.DITOLAK
import org.laziskhu.amilkhu.utils.Constants.MENUNGGU
import org.laziskhu.amilkhu.utils.Constants.WAITING
import org.laziskhu.amilkhu.utils.Constants.dateOnlyFormat
import org.laziskhu.amilkhu.utils.Constants.monthOnlyFormat
import org.laziskhu.amilkhu.utils.Constants.timeStampFormat
import org.laziskhu.amilkhu.utils.format
import org.laziskhu.amilkhu.utils.toLocalDate

class HistoryAdapter(private val onClick: (HistoryAttendance) -> Unit) :
    ListAdapter<HistoryAttendance, HistoryAdapter.HistoryViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder =
        HistoryViewHolder.from(parent)

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = getItem(position)
        holder.bind(history)

        holder.binding.checkInTime.text = history.comingTime?.take(5)
        val date = history.date?.toLocalDate(timeStampFormat)?.format(dateOnlyFormat)
        val month = history.date?.toLocalDate(timeStampFormat)?.format(monthOnlyFormat)
        holder.binding.date.text = date
        holder.binding.month.text = month
        holder.binding.notes.text = history.catatan
        holder.binding.notes.isGone = history.catatan.isNullOrEmpty()
        holder.binding.checkOutTime.text = history.homeTime?.take(5) ?: "-"
        holder.binding.status.text = history.status
//            when (history.status) {
//                WAITING -> MENUNGGU
//                ACCEPTED -> DITERIMA
//                DENIED -> DITOLAK
//                else -> MENUNGGU
//            }

        holder.binding.root.setOnClickListener {
            if (history != null) {
                onClick(history)
            }
        }

    }

    class HistoryViewHolder(val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(historyAttendance: HistoryAttendance) {
            binding.itemHistory = historyAttendance
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HistoryViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemHistoryBinding.inflate(layoutInflater, parent, false)
                return HistoryViewHolder(binding)
            }
        }
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<HistoryAttendance>() {

        override fun areItemsTheSame(
            oldItem: HistoryAttendance,
            newItem: HistoryAttendance
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: HistoryAttendance,
            newItem: HistoryAttendance
        ): Boolean {
            return oldItem.idAttendence == newItem.idAttendence
        }

    }

}