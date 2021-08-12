package org.laziskhu.amilkhu.ui.amiltools.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.laziskhu.amilkhu.data.source.remote.response.GetPaymentGatewayResponse
import org.laziskhu.amilkhu.databinding.ItemQrisBinding

class QrisAdapter(private val onClick: (GetPaymentGatewayResponse.Rekening) -> Unit) : ListAdapter<GetPaymentGatewayResponse.Rekening, QrisAdapter.QrisViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QrisViewHolder =
        QrisViewHolder.from(parent)

    override fun onBindViewHolder(holder: QrisViewHolder, position: Int) {
        val qris = getItem(position)
        holder.bind(qris)

        holder.binding.root.setOnClickListener {
            if (qris != null) {
                onClick(qris)
            }
        }

    }

    class QrisViewHolder(val binding: ItemQrisBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(qris: GetPaymentGatewayResponse.Rekening) {
            binding.itemQRIS = qris
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): QrisViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemQrisBinding.inflate(layoutInflater, parent, false)
                return QrisViewHolder(binding)
            }
        }
    }

    private companion object DiffCallback : DiffUtil.ItemCallback<GetPaymentGatewayResponse.Rekening>() {

        override fun areItemsTheSame(oldItem: GetPaymentGatewayResponse.Rekening, newItem: GetPaymentGatewayResponse.Rekening): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GetPaymentGatewayResponse.Rekening, newItem: GetPaymentGatewayResponse.Rekening): Boolean {
            return oldItem.idRekening == newItem.idRekening
        }

    }

}