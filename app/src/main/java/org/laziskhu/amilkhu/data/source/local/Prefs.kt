package org.laziskhu.amilkhu.data.source.local

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.enumpref.enumValuePref
import org.laziskhu.amilkhu.utils.Role

object Prefs : KotprefModel() {
    var userId by stringPref()
    var token by nullableStringPref(null)
    var role by enumValuePref(Role.USER)

    var isCheckedIn by booleanPref(false)
    var isCheckedOut by booleanPref(false)
    var checkInDate by nullableStringPref(null)

}