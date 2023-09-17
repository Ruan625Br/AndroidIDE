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

package com.itsaky.androidide.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.itsaky.androidide.fragments.ResourceListFragment
import com.itsaky.androidide.models.ResourceInfo

class ResourceListStateAdapter(
  fragmentManager: FragmentManager,
  lifecycle: Lifecycle,
  private val resourceInfoList: List<ResourceInfo>
): FragmentStateAdapter(fragmentManager, lifecycle) {

  override fun getItemCount(): Int = resourceInfoList.size

  override fun createFragment(position: Int): Fragment {
    return ResourceListFragment.newInstance(resourceInfoList[position])
  }
}