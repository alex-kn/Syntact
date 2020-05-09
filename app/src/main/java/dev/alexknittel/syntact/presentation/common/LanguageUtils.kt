package dev.alexknittel.syntact.presentation.common

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.res.ResourcesCompat
import dev.alexknittel.syntact.R
import java.util.*



fun Resources.flagDrawableOf(locale: Locale): Drawable {

    val resId = when (locale) {
        Locale.GERMAN -> R.drawable.de
        Locale.ENGLISH -> R.drawable.en
        Locale.ITALIAN -> R.drawable.it
        Locale.FRENCH -> R.drawable.fr
        Locale("es") -> R.drawable.es
        else -> throw UnsupportedLanguageException(locale)
    }

    return ResourcesCompat.getDrawable(this, resId, null)!!
}

fun characterRegex() = Regex("[A-Za-zÄÜÖäüöẞßùûÿàâæçéèêëïîôœÙÛŸÀÂÆÇÉÈÊËÏÎÔŒáíñóúÁÍÑÓÚìòÌÒ]+")

class UnsupportedLanguageException(locale: Locale) : RuntimeException("Language $locale ist not supported")


