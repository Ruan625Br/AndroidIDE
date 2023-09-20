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

import android.annotation.SuppressLint
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsaky.androidide.adapters.FileItemAdapter
import com.itsaky.androidide.databinding.FragmentBottomSheetCreateFileBinding
import com.itsaky.androidide.models.FileItem
import com.itsaky.androidide.models.FileItemSubtype
import com.itsaky.androidide.models.FileItemView
import com.itsaky.androidide.utils.FileItemTypeUtils
import com.itsaky.androidide.utils.FileItemViewUtils
import com.itsaky.androidide.viewmodel.FileItemListViewModel


class BottomSheetCreateFileFragment(
  private val onFileItemClick: ((List<FileItemView>, FileItemSubtype) -> Boolean)? = null
) : BottomSheetDialogFragment() {

  private var _binding: FragmentBottomSheetCreateFileBinding? = null
  private val binding get() = _binding!!
  private var adapter: FileItemAdapter? = null

  private val viewModel: FileItemListViewModel by lazy {
    ViewModelProvider(this,
      ViewModelProvider.NewInstanceFactory())[FileItemListViewModel::class.java]
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View {
    _binding = FragmentBottomSheetCreateFileBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    viewModel.setup(FileItemTypeUtils.getFileItemTypeList())

    setupRecyclerView()
    observeViewModel()
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  private fun observeViewModel() {
    viewModel.fileItemList.observe(viewLifecycleOwner) { onFileItemListChanged(it) }
  }

  private fun setupRecyclerView() {
    val layoutManager = LinearLayoutManager(requireContext())
    layoutManager.orientation = LinearLayoutManager.VERTICAL
    binding.list.layoutManager = layoutManager
  }

  private fun onFileItemListChanged(fileItemList: List<FileItem>) {
    initTransition(true)
    adapter = FileItemAdapter(fileItemList) { fileItem, _ ->
      if (!fileItem.isSubtype) {
        initTransition(false)
        viewModel.setup(FileItemTypeUtils.getFileItemSubtypesByType(fileItem.fileType))
      } else {
        initTransition(false)
        onSubtypeClick((fileItem as FileItemSubtype))
      }
      updateTitle(fileItem)
    }
    binding.list.adapter = adapter
  }

  private fun onSubtypeClick(subtype: FileItemSubtype){
    setupLayoutCreateFile(subtype)
  }

  private fun setupLayoutCreateFile(file: FileItemSubtype){
    val fileItemViewList = FileItemViewUtils.getSubtypeViews(file.fileSubtype, requireContext())

    fileItemViewList.forEachIndexed { _, fileItem ->
      binding.layoutCreateFile.container.addView(fileItem.parent)
    }
    binding.layoutCreateFile.base.translationX = binding.layoutCreateFile.base.width.toFloat()
    binding.layoutCreateFile.base.visibility = View.VISIBLE
    binding.layoutCreateFile.base.animate()
      .translationX(0f)
      .setDuration(300)
      .start()

    binding.layoutCreateFile.buttonFinish.setOnClickListener {
     val result = onFileItemClick?.invoke(fileItemViewList, file)?: false
      if (result){
        dismiss()
      }
    }

  }

  @SuppressLint("SetTextI18n")
  private fun updateTitle(fileItem: FileItem){
    binding.title.text = "New " + fileItem.fileType.typeName
    binding.title.alpha = 0f
    binding.title.animate().alpha(1f).setDuration(300).start()

  }

  private fun initTransition(reset: Boolean = false){
    if (reset) {
      binding.list.animate().translationX(0f).setDuration(300).start()
    }else {
      val translationX = -binding.list.width.toFloat()
      binding.list.animate().translationX(translationX).setDuration(300).start()
    }
  }

}