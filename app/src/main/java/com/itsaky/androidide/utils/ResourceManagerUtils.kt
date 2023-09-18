/*
 *  This file is part of AndroidIDE.
 *
 *  AndroidIDE is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  AndroidIDE is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with AndroidIDE.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.itsaky.androidide.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Xml
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.itsaky.androidide.extensions.getColorByAttr
import org.xmlpull.v1.XmlPullParser
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

object ResourceManagerUtils {

  fun createVectorDrawableFromXmlFile(file: File): Any? {
    try {
      val inputStream: InputStream = FileInputStream(file)
      return VectorDrawableCompat.createFromStream(inputStream, null)
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return file.absolutePath
  }

  fun getColor(colorValue: Int): ColorDrawable {

    return ColorDrawable(colorValue)
  }

  private fun getColorFromExternalFile(file: File, colorName: String): Int {
    try {
      val parser: XmlPullParser = Xml.newPullParser()
      val input = FileInputStream(file)
      parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
      parser.setInput(input, null)

      var eventType = parser.eventType
      while (eventType != XmlPullParser.END_DOCUMENT) {
        if (eventType == XmlPullParser.START_TAG && parser.name == "color" && parser.getAttributeValue(
            null, "name") == colorName
        ) {
          parser.nextToken()
          val colorValue = parser.text
          return Color.parseColor(colorValue)

        }
        eventType = parser.next()
      }
    } catch (e: Exception) {
      //e
    }
    return Color.BLACK
  }

  fun getLayout() {
  }

  fun createDrawableWithText(text: String, context: Context): Drawable {
    val backgroundColor = context.getColorByAttr(com.google.android.material.R.attr.colorPrimary)
    val textColor = context.getColorByAttr(com.google.android.material.R.attr.colorOnPrimary)

    return object : Drawable() {
      override fun draw(canvas: Canvas) {
        canvas.drawColor(backgroundColor)

        val paint = Paint().apply {
          color = textColor
          textSize = 48f
          textAlign = Paint.Align.CENTER
        }

        val x = bounds.width() / 2
        val y = ((bounds.height()) / 2) - ((paint.descent() + paint.ascent()) / 2)


        canvas.drawText(text, x.toFloat(), y, paint)
      }

      override fun setAlpha(alpha: Int) {}

      override fun setColorFilter(colorFilter: ColorFilter?) {}

      @Deprecated("Deprecated in Java",
        ReplaceWith("PixelFormat.TRANSLUCENT", "android.graphics.PixelFormat"))
      override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
      }
    }
  }

}