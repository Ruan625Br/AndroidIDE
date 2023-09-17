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

import android.graphics.Color
import com.itsaky.androidide.models.ColorInfo
import java.io.File
import java.nio.charset.Charset
import java.util.regex.Pattern

object ColorUtils {

  fun findAllColorsInXmlFiles(file: File): List<ColorInfo> {
    val results = mutableListOf<ColorInfo>()
    val log = ILogger.newInstance("ColorUtils")

    if (!file.isDirectory)
      return results

    val xmlFilePattern = Pattern.compile(".*\\.xml$")

    file.listFiles { _, name -> xmlFilePattern.matcher(name).matches() }
      ?.forEach { xmlFile ->
        try {
          val xmlContent = xmlFile.readText(Charset.defaultCharset())
          val pattern = "<color name=\"([^\"]+)\">#([A-Fa-f0-9]+)</color>".toPattern()
          val matcher = pattern.matcher(xmlContent)

          while (matcher.find()) {
            val colorName = matcher.group(1)
            val colorHex = matcher.group(2)
            val colorValue = Color.parseColor("#$colorHex")
            results.add(ColorInfo(xmlFile, colorName!!, colorValue))
          }
        } catch (e: Exception) {
          log.error("Failed to get colors", e)

        }
      }

    return results
  }

}