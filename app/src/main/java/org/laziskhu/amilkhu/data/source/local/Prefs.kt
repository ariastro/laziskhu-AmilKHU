package org.laziskhu.amilkhu.data.source.local

import com.chibatching.kotpref.KotprefModel

object Prefs : KotprefModel() {
    var token by stringPref()

}