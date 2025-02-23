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

import com.itsaky.androidide.data.projectInfo.ProjectInfo
import com.itsaky.androidide.utils.TimeUtils
import java.io.File

class ProjectInfoDetails(
  val name: String,
  val file: File,
  val cache: ProjectInfoCache
)

fun ProjectInfoDetails.toProjectInfo(): ProjectInfo = ProjectInfo(
  name = name,
  file = file,
  cache = cache
)

fun ProjectInfo.toProjectInfoDetails(): ProjectInfoDetails = ProjectInfoDetails(
  name = name,
  file = file,
  cache = cache
)

fun File.toProjectInfoDetails(): ProjectInfoDetails  {
  val currentTime = TimeUtils.getCurrentTime()
  val file = this
  val cache = ProjectInfoCache(currentTime)

  return ProjectInfoDetails(
    file = file,
    name = name,
    cache = cache
  )
}