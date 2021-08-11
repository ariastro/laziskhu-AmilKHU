package org.laziskhu.amilkhu.ui.admintools.acceptattendance

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.laziskhu.amilkhu.R
import org.laziskhu.amilkhu.data.source.remote.response.GetWaitingAttendanceResponse
import org.laziskhu.amilkhu.databinding.ItemWaitingAttendanceBinding
import org.laziskhu.amilkhu.utils.Constants.IN_OFFICE_CODE
import org.laziskhu.amilkhu.utils.toGone
import org.laziskhu.amilkhu.utils.toVisible

class WaitingAttendanceAdapter(
    private val context: Context,
    private val onClick: (GetWaitingAttendanceResponse.WaitingAttendance) -> Unit,
    private val onClickShowImage: (GetWaitingAttendanceResponse.WaitingAttendance) -> Unit
) : ListAdapter<GetWaitingAttendanceResponse.WaitingAttendance, WaitingAttendanceAdapter.WaitingAttendanceViewHolder>(
        DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WaitingAttendanceViewHolder =
        WaitingAttendanceViewHolder.from(parent)

    override fun onBindViewHolder(holder: WaitingAttendanceViewHolder, position: Int) {
        val attendance = getItem(position)
        holder.bind(attendance)

        holder.binding.checkInTime.text = attendance.comingTime?.take(5)
        if (attendance.isInOffice == IN_OFFICE_CODE) {
            holder.binding.isInOffice.text = context.getString(R.string.di_kantor)
            holder.binding.notes.toGone()
        } else {
            holder.binding.isInOffice.text = context.getString(R.string.di_luar_kantor)
            holder.binding.notesLayout.toVisible()
        }

        holder.binding.btnSeePhoto.setOnClickListener {
            if (attendance != null) {
                onClickShowImage(attendance)
            }
        }

        holder.binding.root.setOnClickListener {
            if (attendance != null) {
                onClick(attendance)
            }
        }

    }

    class WaitingAttendanceViewHolder(val binding: ItemWaitingAttendanceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(waitingAttendance: GetWaitingAttendanceResponse.WaitingAttendance) {
            binding.itemWaitingAttendance = waitingAttendance
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): WaitingAttendanceViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemWaitingAttendanceBinding.inflate(layoutInflater, parent, false)
                return WaitingAttendanceViewHolder(binding)
            }
        }
    }

    private companion object DiffCallback :
        DiffUtil.ItemCallback<GetWaitingAttendanceResponse.WaitingAttendance>() {

        override fun areItemsTheSame(
            oldItem: GetWaitingAttendanceResponse.WaitingAttendance,
            newItem: GetWaitingAttendanceResponse.WaitingAttendance
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: GetWaitingAttendanceResponse.WaitingAttendance,
            newItem: GetWaitingAttendanceResponse.WaitingAttendance
        ): Boolean {
            return oldItem.idAttendence == newItem.idAttendence
        }

    }

}