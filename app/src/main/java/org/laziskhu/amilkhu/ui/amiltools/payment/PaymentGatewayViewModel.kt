package org.laziskhu.amilkhu.ui.amiltools.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.laziskhu.amilkhu.data.source.AmilkhuRepository
import org.laziskhu.amilkhu.data.source.local.Prefs
import javax.inject.Inject

@HiltViewModel
class PaymentGatewayViewModel @Inject constructor(private val repository: AmilkhuRepository) : ViewModel() {

    fun getRekening() = repository.getPaymentGateway(0).asLiveData()

    fun getQRIS() = repository.getPaymentGateway(1).asLiveData()

}