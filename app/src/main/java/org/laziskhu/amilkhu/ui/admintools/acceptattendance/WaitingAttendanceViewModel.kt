package org.laziskhu.amilkhu.ui.admintools.acceptattendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.laziskhu.amilkhu.data.source.AmilkhuRepository
import org.laziskhu.amilkhu.data.source.local.Prefs
import org.laziskhu.amilkhu.utils.getCurrentDate
import javax.inject.Inject

@HiltViewModel
class WaitingAttendanceViewModel @Inject constructor(private val repository: AmilkhuRepository) : ViewModel() {

    fun getWaitingAttendance() = repository.getWaitingAttendance(getCurrentDate()).asLiveData()

}