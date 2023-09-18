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
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.itsaky.androidide.extensions.getColorByAttr

abstract class BaseDialogFragment() : DialogFragment() {

  override fun onStart() {
    super.onStart()
    dialog?.window?.apply {
      setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
      setBackgroundDrawable(getBackground())
    }
  }


  private fun getBackground(cornerSize: Float = 30f): MaterialShapeDrawable {
    val colorSurfaceContainerHigh = requireContext().getColorByAttr(
      com.google.android.material.R.attr.colorSurfaceContainerLow)
    val shapeModel = ShapeAppearanceModel.builder().setAllCorners(CornerFamily.ROUNDED, cornerSize)
    val shapeDrawable = MaterialShapeDrawable(shapeModel.build())
    shapeDrawable.setTint(colorSurfaceContainerHigh)

    return shapeDrawable

  }
}