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
import android.graphics.drawable.Drawable
import com.itsaky.androidide.R
import com.itsaky.androidide.models.FileItem
import com.itsaky.androidide.models.FileItemSubtype
import com.itsaky.androidide.models.FileItemType
import kotlin.io.encoding.Base64

object FileItemTypeUtils {

  fun getFileItemTypeList(): MutableList<FileItemType> {
    val fileItemType = mutableListOf<FileItemType>()

    fileItemType.add(
      FileItemType(Type.ACTIVITY.typeName, Type.ACTIVITY, getFileItemSubtypesByType(Type.ACTIVITY)))
    fileItemType.add(
      FileItemType(Type.FRAGMENT.typeName, Type.FRAGMENT, getFileItemSubtypesByType(Type.FRAGMENT)))
    return fileItemType
  }

  fun getFileItemSubtypesByType(type: Type) =
    getFileItemSubtypeList().filter { it.subtype.type == type }

  private fun getFileItemSubtypeList(): MutableList<FileItemSubtype> {
    val subtypeList = mutableListOf<FileItemSubtype>()

    subtypeList.add(
      FileItemSubtype(Subtype.FRAGMENT_BLANK.subtypeName, Subtype.FRAGMENT_BLANK))
    subtypeList.add(
      FileItemSubtype(Subtype.FRAGMENT_LIST.subtypeName, Subtype.FRAGMENT_LIST))
    subtypeList.add(
      FileItemSubtype(Subtype.FRAGMENT_WITH_VIEW_MODEL.subtypeName, Subtype.FRAGMENT_WITH_VIEW_MODEL))

    subtypeList.add(
      FileItemSubtype(Subtype.BASIC_VIEWS_ACTIVITY.subtypeName, Subtype.BASIC_VIEWS_ACTIVITY))
    subtypeList.add(
      FileItemSubtype(Subtype.EMPTY_VIEWS_ACTIVITY.subtypeName, Subtype.EMPTY_VIEWS_ACTIVITY))
    subtypeList.add(
      FileItemSubtype(Subtype.FULLSCREEN_VIEWS_ACTIVITY.subtypeName, Subtype.FULLSCREEN_VIEWS_ACTIVITY))

    return subtypeList
  }

  fun getFileItemIcon(fileItem: FileItem, context: Context): Drawable {
    return ResourceManagerUtils.createDrawableWithText(fileItem.itemName[0].toString(), context)
  }
  enum class Type(val typeName: String) {
    ACTIVITY("Activity"),
    FRAGMENT("Fragment"),
    JAVA_CLASS("Java Class"),
    KOTLIN_CLASS_FILE("Kotlin Class/File")
  }

  enum class Subtype(val subtypeName: String, val type: Type) {
    FRAGMENT_BLANK("Fragment (Blank)",
    Type.FRAGMENT),
    FRAGMENT_LIST("Fragment (List)",
      Type.FRAGMENT),
    FRAGMENT_WITH_VIEW_MODEL(
      "Fragment with ViewModel",
      Type.FRAGMENT),
    BASIC_VIEWS_ACTIVITY("Basic Views Activity",
      Type.ACTIVITY),
    EMPTY_VIEWS_ACTIVITY(
      "Empty Views Activity",
      Type.ACTIVITY),
    FULLSCREEN_VIEWS_ACTIVITY("Fullscreen Views Activity",
      Type.ACTIVITY),

  }
}