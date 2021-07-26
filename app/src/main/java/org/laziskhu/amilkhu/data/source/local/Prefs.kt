package org.laziskhu.amilkhu.data.source.local

import com.chibatching.kotpref.KotprefModel
import com.chibatching.kotpref.enumpref.enumValuePref
import org.laziskhu.amilkhu.utils.Role

object Prefs : KotprefModel() {
    var token by nullableStringPref(null)
    var role by enumValuePref(Role.USER)
}