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

package com.itsaky.androidide.livedata

import android.os.AsyncTask
import com.itsaky.androidide.extensions.newDirectoryStream
import com.itsaky.androidide.models.ResourceItem
import com.itsaky.androidide.models.ResourceType
import com.itsaky.androidide.models.loadResourceItem
import com.itsaky.androidide.models.toResourceItem
import com.itsaky.androidide.utils.CloseableLiveData
import com.itsaky.androidide.utils.ColorUtils
import com.itsaky.androidide.utils.Failure
import com.itsaky.androidide.utils.ILogger
import com.itsaky.androidide.utils.Loading
import com.itsaky.androidide.utils.Stateful
import com.itsaky.androidide.utils.Success
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.file.DirectoryIteratorException
import java.nio.file.Path
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future

class ResourceListLiveData(private val path: Path, private val type: ResourceType) :
  CloseableLiveData<Stateful<List<ResourceItem>>>() {
  val log = ILogger.newInstance("ResourceListLiveData")
  private var future: Future<Unit>? = null


  init {
    loadValueTest()
  }


   fun loadValue() {
    value = Loading(value?.value)
    CoroutineScope(Dispatchers.IO).launch {
     val value: Stateful<List<ResourceItem>> = try {
        val resourceList = mutableListOf<ResourceItem>()

        if (type == ResourceType.COLOR){
          val colorList = ColorUtils.findAllColorsInXmlFiles(path.toFile())
          colorList.map { resourceList.add(it.toResourceItem()) }
          Success(resourceList as List<ResourceItem>)

        } else {
          path.newDirectoryStream().use { directoryStream ->
            for (path in directoryStream) {
              resourceList.add(path.loadResourceItem(type))
            }
            Success(resourceList as List<ResourceItem>)
          }
        }
      } catch (e: Exception) {
        Failure((value as Loading<List<ResourceItem>>).value, e)
      }

      withContext(Dispatchers.Main) {
        postValue(value)
      }

    }
  }

  fun loadValueTest() {
    future?.cancel(true)
    value = Loading(value?.value)
    future = (AsyncTask.THREAD_POOL_EXECUTOR as ExecutorService).submit<Unit> {
      val resourceList = mutableListOf<ResourceItem>()
      val value = try {
        if (type == ResourceType.COLOR) {
          val colorList = ColorUtils.findAllColorsInXmlFiles(path.toFile())
          colorList.map { resourceList.add(it.toResourceItem()) }
          Success(resourceList as List<ResourceItem>)

        } else {
          path.newDirectoryStream().use { directoryStream ->
            for (path in directoryStream) {
              try {
                resourceList.add(path.loadResourceItem(type))
              } catch (e: DirectoryIteratorException) {
                log.error("Failed to obtain resource", e)
              }
            }
            Success(resourceList as List<ResourceItem>)
          }
        }
      } catch (e: Exception){
        log.error("Failed to obtain resource", e)
        Failure(value, e)
      }
      postValue(value as Stateful<List<ResourceItem>>)
    }
  }

  override fun onActive() {
   loadValueTest()
  }

  override fun close() {
    future?.cancel(true)
  }
}