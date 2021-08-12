package org.laziskhu.amilkhu.ui.amiltools.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.laziskhu.amilkhu.data.source.remote.response.GetPaymentGatewayResponse
import org.laziskhu.amilkhu.databinding.ItemRekeningBinding

class RekeningAdapter(private val onClick: (GetPaymentGatewayResponse.Rekening) -> Unit) : ListAdapter<GetPaymentGatewayResponse.Rekening, RekeningAdapter.RekeningViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RekeningViewHolder =
        RekeningViewHolder.from(parent)

    override fun onBindViewHolder(holder: RekeningViewHolder, position: Int) {
        val rekening = getItem(position)
        holder.bind(rekening)

        holder.binding.root.setOnClickListener {
            if (rekening != null) {
                onClick(rekening)
            }
        }

    }

    class RekeningViewHolder(val binding: ItemRekeningBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(rekening: GetPaymentGatewayResponse.Rekening) {
            binding.itemRekening = rekening
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): RekeningViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRekeningBinding.inflate(layoutInflater, parent, false)
                return RekeningViewHolder(binding)
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