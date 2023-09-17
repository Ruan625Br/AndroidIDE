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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.itsaky.androidide.R
import com.itsaky.androidide.databinding.LayoutResourceListItemBinding
import com.itsaky.androidide.extensions.getIcon
import com.itsaky.androidide.models.ResourceItem

class ResourceListAdapter(
  resources: List<ResourceItem>,
  private val onClick: ((ResourceItem, ViewHolder) -> Unit)? = null
) : RecyclerView.Adapter<ResourceListAdapter.ViewHolder>() {

  private val resources = resources.toMutableList()

  class ViewHolder(internal val binding: LayoutResourceListItemBinding) :
    RecyclerView.ViewHolder(binding.root)


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutResourceListItemBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    ))
  }

  override fun getItemCount(): Int {
    return resources.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.binding.apply {
      val resource = resources[position]
      val context = root.context

      val icon = resource.getIcon(context)
      val name = resource.name
      val info = resource.info

      Glide.with(context)
        .load(icon)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .override(72, 72)
        .placeholder(R.drawable.ic_layout_72dp)
        .into(resourceIcon)

      resourceName.text = name
      resourceInfo.text = info

      root.setOnClickListener {
        onClick?.invoke(resource, holder)
      }

    }
  }

}