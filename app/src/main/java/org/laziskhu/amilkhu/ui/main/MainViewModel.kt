package org.laziskhu.amilkhu.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import org.laziskhu.amilkhu.data.source.AmilkhuRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AmilkhuRepository) : ViewModel() {

    fun getProfile(id: String) = repository.getProfile(id).stateIn(viewModelScope, SharingStarted.Lazily, null).asLiveData()

}