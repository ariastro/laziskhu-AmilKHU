package org.laziskhu.amilkhu.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.laziskhu.amilkhu.data.source.AmilkhuRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AmilkhuRepository) : ViewModel() {

}