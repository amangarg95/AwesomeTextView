package com.amangarg.library

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.res.ResourcesCompat
import com.amangarg.library.R.*
import java.util.*


class AwesomeTextView : AppCompatTextView {

    private val viewContext: Context
    private var additionalText = arrayOf("")
    private var textViewValue = ""
    private var textViewSpannableValue = SpannableString("")
    private var iconResourceId = 0

    constructor(context: Context) : super(context) {
        this.viewContext = context
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        this.viewContext = context
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.viewContext = context
    }

    fun setFontAwesomeText(
        text: String,
        vararg additionalText: String = arrayOf("")
    ) {
        this.additionalText = additionalText as Array<String>
        val splitTextList = text.split(" ")

        if (splitTextList[0] == "fa-regular") {
            iconResourceId = getResourceId(splitTextList[1].replace("-", "_") + "")
            setTextValue(REGULAR)
        } else if (splitTextList[0] == "fa-solid") {
            iconResourceId = getResourceId(splitTextList[1].replace("-", "_") + "_solid")
            setTextValue(SOLID)
        } else if (splitTextList[0] == "fa-brands") {
            iconResourceId = getResourceId(splitTextList[1].replace("-", "_") + "_brand")
            setTextValue(BRAND)
        } else {
            setTextValue()
        }
    }

    private fun getResourceId(regularName: String): Int {
        return viewContext.resources.getIdentifier(regularName, "string", viewContext.packageName)
    }

    private fun setTextValue(fontType: Int = 0) {
        if (iconResourceId != 0) {
            setFont(fontType)
            textViewValue = viewContext.getString(iconResourceId)

        }
        additionalText.iterator().forEach {
            textViewValue += it
        }
        textViewSpannableValue = SpannableString(textViewValue)
        text = textViewSpannableValue
    }

    fun setAlternateTextColour(colourId: Int) {
        var startIndex = 1
        if (iconResourceId == 0) {
            startIndex = 0
        }
        textViewSpannableValue.setSpan(
            ForegroundColorSpan(Color.parseColor(resources.getString(colourId))),
            startIndex,
            textViewSpannableValue.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text = textViewSpannableValue
    }

    fun setAlternateTextFont(font: Int) {
        var startIndex = 1
        if (iconResourceId == 0) {
            startIndex = 0
        }
        textViewSpannableValue.setSpan(
            CustomTypefaceSpan(FontCache[context, font]!!),
            startIndex,
            textViewSpannableValue.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        text = textViewSpannableValue
    }

    private fun setFont(fontType: Int) {
        when (fontType) {
            BRAND -> this.typeface = FontCache[context, font.fa_brands_400]
            SOLID -> this.typeface = FontCache[context, font.fa_solid_900]
            REGULAR -> this.typeface = FontCache[context, font.fa_regular_400]
            else -> {}
        }
    }

    companion object {
        private const val REGULAR = 1
        private const val SOLID = 2
        private const val BRAND = 3
    }

    object FontCache {
        private val fontCacheMap = Hashtable<Int, Typeface?>()
        operator fun get(context: Context?, fontId: Int): Typeface? {
            var typeface = fontCacheMap[fontId]
            if (typeface == null) {
                typeface = try {
                    ResourcesCompat.getFont(context!!, fontId)
                } catch (e: Exception) {
                    return null
                }
                fontCacheMap[fontId] = typeface
            }
            return typeface
        }
    }
}