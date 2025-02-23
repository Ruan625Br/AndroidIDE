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

package com.itsaky.androidide.actions.sidebar

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.itsaky.androidide.R
import com.itsaky.androidide.fragments.ResourceManagerFragment
import kotlin.reflect.KClass

/**
 * Sidebar action for opening the resource manager.
 *
 * @author Juan Nascimento
 */
class ResourceManagerSidebarAction(context: Context, override val order: Int) : AbstractSidebarAction(){
  override val id: String = "ide.editor.sidebar.resource-manager"
  override val fragmentClass: KClass<out Fragment> = ResourceManagerFragment::class

  init {
    label = context.getString(R.string.title_resource_manager)
    icon = ContextCompat.getDrawable(context, R.drawable.ic_resource_manager)
  }
}