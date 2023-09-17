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

package com.itsaky.androidide.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.itsaky.androidide.R
import com.itsaky.androidide.models.ResourceItem
import com.itsaky.androidide.models.ResourceType
import com.itsaky.androidide.utils.ResourceManagerUtils

fun ResourceItem.getIcon(context: Context): Any? {
 return when(type){
    ResourceType.DRAWABLE -> ContextCompat.getDrawable(context, R.drawable.ic_file_image)
    ResourceType.COLOR -> ResourceManagerUtils.getColor(info.toInt())
    ResourceType.LAYOUT -> AppCompatResources.getDrawable(context, R.drawable.ic_layout_72dp)!!
  }
}