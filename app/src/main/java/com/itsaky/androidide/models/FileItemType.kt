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

package com.itsaky.androidide.models

import com.itsaky.androidide.utils.FileItemTypeUtils

data class FileItemType(val type: FileItemTypeUtils.Type, val name: String = type.typeName , val subTypeList: List<FileItemSubtype>) : FileItem(){

  override val itemName: String
    get() = name
  override val isSubtype: Boolean
    get() = false

  override val fileType: FileItemTypeUtils.Type
    get() = type

}

abstract class FileItem() {

  abstract val itemName: String
  abstract val isSubtype: Boolean
  abstract val fileType: FileItemTypeUtils.Type
}
