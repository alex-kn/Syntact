package com.alexkn.syntact.presentation.common

import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.alexkn.syntact.R
import java.util.*


fun Fragment.flagDrawableOf(locale: Locale): Drawable {

    val resId = when (locale) {
        Locale.GERMAN -> R.drawable.de
        Locale.ENGLISH -> R.drawable.en
        Locale.ITALIAN -> R.drawable.it
        Locale.CHINESE -> R.drawable.zh
        Locale("swe") -> R.drawable.swe
        Locale("es") -> R.drawable.es
        else -> throw UnsupportedLanguageException(locale)
    }

    return ResourcesCompat.getDrawable(this.resources, resId, null)!!
}

class UnsupportedLanguageException(locale: Locale) : RuntimeException("Language $locale ist not supported")


