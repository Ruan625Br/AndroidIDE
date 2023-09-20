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

package com.itsaky.androidide.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itsaky.androidide.livedata.ResourceListLiveData
import com.itsaky.androidide.models.ResourceInfo
import com.itsaky.androidide.models.ResourceItem
import com.itsaky.androidide.utils.CloseableLiveData
import com.itsaky.androidide.utils.Stateful
import java.io.Closeable
import java.nio.file.Paths

class ResourceListViewModel : ViewModel() {

  private val _resourceInfoLiveData = MutableLiveData<ResourceInfo>()
  private val resourceInfo: LiveData<ResourceInfo> = _resourceInfoLiveData
  private val _resourceListLiveData = ResourceListMapLiveData(resourceInfo)
  val resourceListLiveData: LiveData<Stateful<List<ResourceItem>>>
    get() = _resourceListLiveData
  val resourceListStateful: Stateful<List<ResourceItem>>
    get() = _resourceListLiveData.value!!

  fun setup(resourceInfo: ResourceInfo) {
    _resourceInfoLiveData.value = resourceInfo
  }

  private class ResourceListMapLiveData(
    private val resourceInfoLiveData: LiveData<ResourceInfo>,
  ) : MediatorLiveData<Stateful<List<ResourceItem>>>(), Closeable {

    private var liveData: CloseableLiveData<Stateful<List<ResourceItem>>>? = null

    init {
      addSource(resourceInfoLiveData) { updateSource() }
    }

    private fun updateSource() {
      liveData?.let {
        removeSource(it)
        it.close()
      }
      val resourceInfo = resourceInfoLiveData.value!!
      val path = Paths.get(resourceInfo.file.absolutePath)
      val liveData = ResourceListLiveData(path, resourceInfo.resourceType)

      this.liveData = liveData
      addSource(liveData) { value = it }
    }

    fun reload() {
      when (val liveData = liveData) {
        is ResourceListLiveData -> liveData.loadValueTest()
      }
    }

    override fun close() {
      liveData?.let {
        removeSource(it)
        it.close()
        this.liveData = null
      }
    }

  }
}