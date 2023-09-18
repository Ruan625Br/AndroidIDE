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
import com.itsaky.androidide.databinding.LayoutFileListItemBinding
import com.itsaky.androidide.models.FileItem
import com.itsaky.androidide.utils.FileItemTypeUtils

class FileItemAdapter(
  fileItemTypeList: List<FileItem>,
  private val onFileItemClick: ((FileItem, ViewHolder) -> Unit)? = null
) : RecyclerView.Adapter<FileItemAdapter.ViewHolder>() {

  private val fileItemTypeList = fileItemTypeList.toMutableList()

  class ViewHolder(internal val binding: LayoutFileListItemBinding) :
    RecyclerView.ViewHolder(binding.root)


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutFileListItemBinding.inflate(
      LayoutInflater.from(parent.context),
      parent,
      false
    ))
  }

  override fun getItemCount(): Int {
    return fileItemTypeList.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.binding.apply {
      val fileItem = fileItemTypeList[position]
      val context = root.context

      val name = fileItem.itemName
      val icon = FileItemTypeUtils.getFileItemIcon(fileItem, context)

      Glide.with(context)
        .load(icon)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .override(40, 40)
        .placeholder(R.drawable.ic_code)
        .into(fileIcon)

      fileName.text = name

      root.setOnClickListener { onFileItemClick?.invoke(fileItem, holder) }
    }
  }
}