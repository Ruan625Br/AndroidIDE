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
import com.itsaky.androidide.models.FileItem
import com.itsaky.androidide.models.FileItemSubtype
import com.itsaky.androidide.models.FileItemType

object FileItemTypeUtils {

  fun getFileItemTypeList(): MutableList<FileItemType> {
    val fileItemType = mutableListOf<FileItemType>()

    fileItemType.add(
      FileItemType(
        type = Type.ACTIVITY,
        subTypeList = getFileItemSubtypesByType(Type.ACTIVITY))
    )
    fileItemType.add(
      FileItemType(
        type = Type.FRAGMENT,
        subTypeList = getFileItemSubtypesByType(Type.FRAGMENT))
    )
    fileItemType.add(
      FileItemType(
        type = Type.JAVA_CLASS,
        subTypeList = getFileItemSubtypesByType(Type.JAVA_CLASS))
    )
    fileItemType.add(
      FileItemType(
        type = Type.KOTLIN_CLASS_FILE,
        subTypeList = getFileItemSubtypesByType(Type.KOTLIN_CLASS_FILE))
    )


    return fileItemType
  }

  fun getFileItemSubtypesByType(type: Type) =
    getFileItemSubtypeList().filter { it.fileSubtype.type == type }

  private fun getFileItemSubtypeList(): MutableList<FileItemSubtype> {
    val subtypeList = mutableListOf<FileItemSubtype>()

    subtypeList.add(
      FileItemSubtype(Subtype.FRAGMENT_BLANK.subtypeName, Subtype.FRAGMENT_BLANK))
    subtypeList.add(
      FileItemSubtype(Subtype.FRAGMENT_LIST.subtypeName, Subtype.FRAGMENT_LIST))
    subtypeList.add(
      FileItemSubtype(Subtype.FRAGMENT_WITH_VIEW_MODEL.subtypeName,
        Subtype.FRAGMENT_WITH_VIEW_MODEL))

    subtypeList.add(
      FileItemSubtype(Subtype.BASIC_VIEWS_ACTIVITY.subtypeName, Subtype.BASIC_VIEWS_ACTIVITY))
    subtypeList.add(
      FileItemSubtype(Subtype.EMPTY_VIEWS_ACTIVITY.subtypeName, Subtype.EMPTY_VIEWS_ACTIVITY))
    subtypeList.add(
      FileItemSubtype(Subtype.FULLSCREEN_VIEWS_ACTIVITY.subtypeName,
        Subtype.FULLSCREEN_VIEWS_ACTIVITY))

    subtypeList.add(
      FileItemSubtype(Subtype.CLASS_JAVA.subtypeName, Subtype.CLASS_JAVA))
    subtypeList.add(
      FileItemSubtype(Subtype.INTERFACE_JAVA.subtypeName, Subtype.INTERFACE_JAVA))
    subtypeList.add(
      FileItemSubtype(Subtype.ENUM_JAVA.subtypeName,
        Subtype.ENUM_JAVA))
    subtypeList.add(
      FileItemSubtype(Subtype.ANNOTATION_JAVA.subtypeName,
        Subtype.ANNOTATION_JAVA))

    subtypeList.add(
      FileItemSubtype(Subtype.CLASS_KOTLIN.subtypeName, Subtype.CLASS_KOTLIN))
    subtypeList.add(
      FileItemSubtype(Subtype.FILE_KOTLIN.subtypeName, Subtype.FILE_KOTLIN))
    subtypeList.add(
      FileItemSubtype(Subtype.INTERFACE_KOTLIN.subtypeName,
        Subtype.INTERFACE_KOTLIN))
    subtypeList.add(
      FileItemSubtype(Subtype.SEALED_INTERFACE_KOTLIN.subtypeName,
        Subtype.SEALED_INTERFACE_KOTLIN))
    subtypeList.add(
      FileItemSubtype(Subtype.DATA_CLASS_KOTLIN.subtypeName, Subtype.DATA_CLASS_KOTLIN))
    subtypeList.add(
      FileItemSubtype(Subtype.ENUM_CLASS_KOTLIN.subtypeName, Subtype.ENUM_CLASS_KOTLIN))
    subtypeList.add(
      FileItemSubtype(Subtype.SEALED_CLASS_KOTLIN.subtypeName,
        Subtype.INTERFACE_KOTLIN))
    subtypeList.add(
      FileItemSubtype(Subtype.ANNOTATION_KOTLIN.subtypeName,
        Subtype.ANNOTATION_KOTLIN))
    subtypeList.add(
      FileItemSubtype(Subtype.OBJECT_KOTLIN.subtypeName,
        Subtype.OBJECT_KOTLIN))


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

    CLASS_JAVA("Class", Type.JAVA_CLASS),
    INTERFACE_JAVA("Interface", Type.JAVA_CLASS),
    ENUM_JAVA("Enum", Type.JAVA_CLASS),
    ANNOTATION_JAVA("Annotation", Type.JAVA_CLASS),

    CLASS_KOTLIN("Class", Type.KOTLIN_CLASS_FILE),
    FILE_KOTLIN("File", Type.KOTLIN_CLASS_FILE),
    INTERFACE_KOTLIN("Interface", Type.KOTLIN_CLASS_FILE),
    SEALED_INTERFACE_KOTLIN("Sealed interface", Type.KOTLIN_CLASS_FILE),
    DATA_CLASS_KOTLIN("Data class", Type.KOTLIN_CLASS_FILE),
    ENUM_CLASS_KOTLIN("Enum class", Type.KOTLIN_CLASS_FILE),
    SEALED_CLASS_KOTLIN("Sealed class", Type.KOTLIN_CLASS_FILE),
    ANNOTATION_KOTLIN("Annotation", Type.KOTLIN_CLASS_FILE),
    OBJECT_KOTLIN("Object", Type.KOTLIN_CLASS_FILE)

  }

  enum class Tag(val tagName: String){
    NAME("test"),
  }

  enum class ID(val id: Int){
    FRAGMENT_BLANK_ID(1)
  }

}