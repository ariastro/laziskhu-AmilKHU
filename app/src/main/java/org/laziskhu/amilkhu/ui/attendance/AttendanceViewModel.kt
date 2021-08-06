package org.laziskhu.amilkhu.ui.attendance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import org.laziskhu.amilkhu.data.source.AmilkhuRepository
import org.laziskhu.amilkhu.data.source.local.Prefs
import org.laziskhu.amilkhu.utils.Constants.timeOnlyFormat
import org.laziskhu.amilkhu.utils.Constants.timeStampFormat
import org.laziskhu.amilkhu.utils.format
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(private val repository: AmilkhuRepository) : ViewModel() {

    fun checkIn(latitude: String, longitude: String, isInOffice: Boolean, notes: String?, photo: File) = repository.checkIn(
        userId = Prefs.userId,
        checkInTime = LocalDateTime.now().format(timeOnlyFormat),
        latitude = latitude,
        longitude = longitude,
        isInOffice = isInOffice,
        photo = photo,
        notes = notes,
        date = LocalDate.now().format(timeStampFormat)
    ).asLiveData()

    fun checkOut(latitude: String, longitude: String) = repository.checkOut(
        userId = Prefs.userId,
        checkOutTime = LocalDateTime.now().format(timeOnlyFormat),
        latitude = latitude,
        longitude = longitude,
        date = LocalDate.now().format(timeStampFormat)
    ).asLiveData()

}