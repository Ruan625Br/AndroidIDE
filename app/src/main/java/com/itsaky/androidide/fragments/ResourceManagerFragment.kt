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

package com.itsaky.androidide.fragments

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayoutMediator
import com.itsaky.androidide.R
import com.itsaky.androidide.adapters.ResourceListStateAdapter
import com.itsaky.androidide.databinding.FragmentResourceManagerBinding
import com.itsaky.androidide.models.ResourceInfo
import com.itsaky.androidide.models.ResourceType
import com.itsaky.androidide.projects.ProjectManagerImpl
import java.io.File

class ResourceManagerFragment :
  FragmentWithBinding<FragmentResourceManagerBinding>(R.layout.fragment_resource_manager,
    FragmentResourceManagerBinding::bind) {


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val projectPath = ProjectManagerImpl.getInstance().projectPath
    val resourceInfoList = mutableListOf<ResourceInfo>()

    val drawablePath = "$projectPath/app/src/main/res/drawable"
    val colorPath = "$projectPath/app/src/main/res/values"
    val layoutPath = "$projectPath/app/src/main/res/layout"

    resourceInfoList.add(ResourceInfo(
      name = "Drawable",
      file = File(drawablePath),
      resourceType = ResourceType.DRAWABLE))
    resourceInfoList.add(ResourceInfo(
      name = "Color",
      file = File(colorPath),
      resourceType = ResourceType.COLOR))
    resourceInfoList.add(ResourceInfo(
      name = "Layout",
      file = File(layoutPath),
      resourceType = ResourceType.LAYOUT))

    val adapter = ResourceListStateAdapter(requireActivity().supportFragmentManager, lifecycle,
      resourceInfoList)
    binding.viewPager.adapter = adapter
    TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
      tab.text = resourceInfoList[position].name
    }.attach()


  }

}