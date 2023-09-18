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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.itsaky.androidide.adapters.FileItemAdapter
import com.itsaky.androidide.databinding.FragmentDialogCreateFileBinding
import com.itsaky.androidide.models.FileItem
import com.itsaky.androidide.utils.FileItemTypeUtils
import com.itsaky.androidide.viewmodel.FileItemListViewModel


class DialogCreateFileFragment : BaseDialogFragment() {

  private var _binding: FragmentDialogCreateFileBinding? = null
  private val binding get() = _binding!!
  private var adapter: FileItemAdapter? = null
  private val viewModel: FileItemListViewModel by lazy {
    ViewModelProvider(this,
      ViewModelProvider.NewInstanceFactory())[FileItemListViewModel::class.java]
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?): View {
    _binding = FragmentDialogCreateFileBinding.inflate(inflater, container, false)
    return binding.root
  }


  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    viewModel.setup(FileItemTypeUtils.getFileItemTypeList())

    setupRecyclerView()
    observeViewModel()

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

      }
    }
    binding.list.adapter = adapter
  }

 private fun initTransition(reset: Boolean = false){
    if (reset) {
      binding.list.translationX = 0f
    }else {
      val translationX = -binding.list.width.toFloat()
      binding.list.translationX = translationX
    }
  }
}