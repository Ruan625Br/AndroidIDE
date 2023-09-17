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

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsaky.androidide.R
import com.itsaky.androidide.adapters.ResourceListAdapter
import com.itsaky.androidide.databinding.FragmentResourceListBinding
import com.itsaky.androidide.models.ResourceInfo
import com.itsaky.androidide.models.ResourceItem
import com.itsaky.androidide.utils.Failure
import com.itsaky.androidide.utils.ILogger
import com.itsaky.androidide.utils.Stateful
import com.itsaky.androidide.viewmodel.ResourceListViewModel

@Suppress("DEPRECATION")
class ResourceListFragment :
  FragmentWithBinding<FragmentResourceListBinding>(R.layout.fragment_resource_list,
    FragmentResourceListBinding::bind) {

  private var adapter: ResourceListAdapter? = null
  private var resourceInfo: ResourceInfo? = null

  private val viewModel: ResourceListViewModel by lazy {
    ViewModelProvider(this,
      ViewModelProvider.NewInstanceFactory())[ResourceListViewModel::class.java]
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      resourceInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) it.getParcelable(
        ARG_RESOURCE_INFO, ResourceInfo::class.java)
      else it.getParcelable(ARG_RESOURCE_INFO)

    }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    resourceInfo?.let { viewModel.setup(it) }
    setupRecyclerView()
    observeViewModel()


  }

  private fun observeViewModel() {
    viewModel.resourceListLiveData.observe(viewLifecycleOwner) { onResourceListChanged(it) }
  }

  private fun setupRecyclerView() {
    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL
    binding.list.layoutManager = layoutManager
  }


  private fun onResourceListChanged(stateful: Stateful<List<ResourceItem>>) {
    val log = ILogger.newInstance("ResourceListFragment")
    val resourceList = if (stateful is Failure) null else stateful.value

    val throwable = (stateful as? Failure)?.throwable
    if (throwable != null) {
      log.error("Failed to obtain resources", throwable.toString())
    }

    if (resourceList != null) {
      updateAdapterResourceList()
    }
  }

  private fun updateAdapterResourceList() {
    val resources = viewModel.resourceListStateful.value!!
    adapter = ResourceListAdapter(resources) { _, _ ->

    }
    binding.list.adapter = adapter

  }

  companion object {

    private const val ARG_RESOURCE_INFO = "resourceInfo"

    @JvmStatic
    fun newInstance(resourceInfo: ResourceInfo) = ResourceListFragment().apply {
      arguments = Bundle().apply {
        putParcelable(ARG_RESOURCE_INFO, resourceInfo)

      }
    }
  }
}