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
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Xml
import androidx.core.graphics.drawable.toDrawable
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
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
        ){
          parser.nextToken()
          val colorValue = parser.text
          return Color.parseColor(colorValue)

        }
        eventType = parser.next()
      }
    } catch (e: Exception){
      //e
    }
    return Color.BLACK
  }

  fun getLayout() {
  }
}