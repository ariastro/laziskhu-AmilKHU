package org.laziskhu.amilkhu.ui.attendance.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.laziskhu.amilkhu.data.source.AmilkhuRepository
import org.laziskhu.amilkhu.data.source.local.Prefs
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val repository: AmilkhuRepository) : ViewModel() {

    fun getHistoryAttendance() = repository.getHistoryAttendance(Prefs.userId).asLiveData()

}