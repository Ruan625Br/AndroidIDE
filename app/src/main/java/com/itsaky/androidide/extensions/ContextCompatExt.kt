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

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.annotation.StyleableRes
import androidx.appcompat.widget.TintTypedArray
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Extensions for ContextCompat.
 *
 * @author Ruan625Br
 */
@SuppressLint("RestrictedApi")
fun Context.obtainStyledAttributesCompat(
  set: AttributeSet? = null,
  @StyleableRes attrs: IntArray,
  @AttrRes defStyleAttr: Int = 0,
  @StyleRes defStyleRes: Int = 0
): TintTypedArray =
  TintTypedArray.obtainStyledAttributes(this, set, attrs, defStyleAttr, defStyleRes)

@OptIn(ExperimentalContracts::class)
@SuppressLint("RestrictedApi")
inline fun <R> TintTypedArray.use(block: (TintTypedArray) -> R): R {
  contract {
    callsInPlace(block, InvocationKind.EXACTLY_ONCE)
  }
  return try {
    block(this)
  } finally {
    recycle()
  }
}
