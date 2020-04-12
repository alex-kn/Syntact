package com.alexkn.syntact.presentation.common

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import com.alexkn.syntact.R
import java.util.*


fun Resources.flagDrawableOf(locale: Locale): Drawable {

    val resId = when (locale) {
        Locale.GERMAN -> R.drawable.de
        Locale.ENGLISH -> R.drawable.en
        Locale.ITALIAN -> R.drawable.it
        Locale.CHINESE -> R.drawable.zh
        Locale.FRENCH -> R.drawable.fr
        Locale("swe") -> R.drawable.swe
        Locale("es") -> R.drawable.es
        else -> throw UnsupportedLanguageException(locale)
    }

    return ResourcesCompat.getDrawable(this, resId, null)!!
}

class UnsupportedLanguageException(locale: Locale) : RuntimeException("Language $locale ist not supported")


