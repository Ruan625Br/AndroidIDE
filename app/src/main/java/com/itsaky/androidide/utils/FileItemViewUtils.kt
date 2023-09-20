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

package com.itsaky.androidide.utils

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.itsaky.androidide.R
import com.itsaky.androidide.models.FileItemSubtype
import com.itsaky.androidide.models.FileItemView

object FileItemViewUtils {

  fun getSubtypeViews(subtype: FileItemTypeUtils.Subtype, context: Context): List<FileItemView> {
    return when (subtype){
      FileItemTypeUtils.Subtype.FRAGMENT_BLANK -> createFragmentBlankViews(context)
      FileItemTypeUtils.Subtype.FRAGMENT_LIST -> createFragmentListViews(context)
      FileItemTypeUtils.Subtype.FRAGMENT_WITH_VIEW_MODEL -> TODO()
      FileItemTypeUtils.Subtype.BASIC_VIEWS_ACTIVITY -> TODO()
      FileItemTypeUtils.Subtype.EMPTY_VIEWS_ACTIVITY -> TODO()
      FileItemTypeUtils.Subtype.FULLSCREEN_VIEWS_ACTIVITY -> TODO()
      FileItemTypeUtils.Subtype.CLASS_JAVA -> listOf(getTextInputLayout(hint = "Name", context = context))
      FileItemTypeUtils.Subtype.INTERFACE_JAVA -> listOf(getTextInputLayout(hint = "Name", context = context))
      FileItemTypeUtils.Subtype.ENUM_JAVA -> listOf(getTextInputLayout(hint = "Name", context = context))
      FileItemTypeUtils.Subtype.ANNOTATION_JAVA -> listOf(getTextInputLayout(hint = "Name", context = context))
      FileItemTypeUtils.Subtype.CLASS_KOTLIN -> listOf(getTextInputLayout(hint = "Name", context = context))
      FileItemTypeUtils.Subtype.FILE_KOTLIN -> listOf(getTextInputLayout(hint = "Name", context = context))
      FileItemTypeUtils.Subtype.INTERFACE_KOTLIN -> listOf(getTextInputLayout(hint = "Name", context = context))
      FileItemTypeUtils.Subtype.SEALED_INTERFACE_KOTLIN -> listOf(getTextInputLayout(hint ="Name",
        context = context))
      FileItemTypeUtils.Subtype.DATA_CLASS_KOTLIN -> listOf(getTextInputLayout(hint = "Name", context = context))
      FileItemTypeUtils.Subtype.ENUM_CLASS_KOTLIN -> listOf(getTextInputLayout(hint = "Name", context = context))
      FileItemTypeUtils.Subtype.SEALED_CLASS_KOTLIN -> listOf(getTextInputLayout(hint = "Name", context = context))
      FileItemTypeUtils.Subtype.ANNOTATION_KOTLIN -> listOf(getTextInputLayout(hint = "Name", context = context))
      FileItemTypeUtils.Subtype.OBJECT_KOTLIN -> listOf(getTextInputLayout(hint = "Name", context = context))
    }
  }

  private fun createFragmentBlankViews(context: Context): List<FileItemView> {
    val inputName = getTextInputLayout(hint ="Fragment Name", context = context)
    val inputLayoutName = getTextInputLayout(hint ="Fragment Layout Name", context = context)

    return listOf(inputName, inputLayoutName)
  }

  private fun createFragmentListViews(context: Context): List<FileItemView> {
    val columnList = listOf("1 (List)", "2 (Grid)", "3", "4")

    val inputKind = getTextInputLayout(
      hint = "Object Kind",
      text = "Item",
      context = context)
    val inputClassName = getTextInputLayout(
      hint = "Fragment class name",
      text = "ItemFragment",
      context =  context)
    val autoCompleteColumnCount = createAutoCompleteTextView(
      hint = "Column Count",
      objects = columnList,
      context =  context)
    val inputContentLayoutFileName = getTextInputLayout(
      hint ="Object content layout file name",
      text = "fragment_item",
      context = context)
    val inputListLayoutFileName = getTextInputLayout(
      hint ="List layout file name",
      text = "fragment_item_list",
      context = context)
    val inputAdapterFileName = getTextInputLayout(
      hint ="Adapter class name",
      text = "MyItemRecyclerViewAdapter",
      context = context)

    return listOf(inputKind, inputClassName, autoCompleteColumnCount, inputContentLayoutFileName,
      inputListLayoutFileName, inputAdapterFileName)

  }

  @SuppressLint("MissingInflatedId", "InflateParams")
  private fun getTextInputLayout(hint: String, text: String = "", context: Context): FileItemView {
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(R.layout.form_create_file_item, null)
    val base = view.findViewById<ConstraintLayout>(R.id.layoutInputLayout)

    val textInputLayout = view.findViewById<TextInputLayout>(R.id.eInputLayout)
    val textInputEditText = view.findViewById<TextInputEditText>(R.id.eInputEditText)

    base.visibility = View.VISIBLE
    textInputLayout.hint = hint
    textInputEditText.setText(text)


    return FileItemView(view, textInputLayout)
  }

  @SuppressLint("InflateParams")
  private fun createAutoCompleteTextView(hint: String,objects: List<String>, selectedIndex: Int = 0,
    context: Context): FileItemView {
    val inflater = LayoutInflater.from(context)
    val view = inflater.inflate(R.layout.form_create_file_item, null)
    val base = view.findViewById<ConstraintLayout>(R.id.layoutAutoComplete)
    val base2 = view.findViewById<ConstraintLayout>(R.id.layoutInputLayout)

    val menuType = view.findViewById<TextInputLayout>(R.id.menuType)
    val autoCompleteTextView = view.findViewById<MaterialAutoCompleteTextView>(R.id.autoCompleteTextView)
    val adapter = ArrayAdapter(context , android.R.layout.simple_dropdown_item_1line, objects)

    //autoCompleteTextView.setAdapter(adapter)
    menuType.hint = hint
    autoCompleteTextView.setSimpleItems(objects.toTypedArray())
    autoCompleteTextView.setText(objects[selectedIndex])
    base.visibility = View.VISIBLE
    base2.visibility = View.GONE

    return FileItemView(view, autoCompleteTextView)
  }

  private fun createToggleGroup(context: Context) {
    val toggleGroup = MaterialButtonToggleGroup(context)
    toggleGroup.layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.WRAP_CONTENT,
      ViewGroup.LayoutParams.WRAP_CONTENT
    )
    toggleGroup.isSingleSelection = true
    toggleGroup.isSelectionRequired = true
  }

  fun getTextInputLayout(position: Int, fileItemViewList: List<FileItemView>): String {
    val textInput = (fileItemViewList[position].child as TextInputLayout)
    return textInput.editText?.text.toString().trim()
  }

  fun getExtensionSimple(fileItemSubtype: FileItemSubtype): String? {
    return when (fileItemSubtype.fileSubtype){
      FileItemTypeUtils.Subtype.FRAGMENT_BLANK -> null
      FileItemTypeUtils.Subtype.FRAGMENT_LIST -> null
      FileItemTypeUtils.Subtype.FRAGMENT_WITH_VIEW_MODEL -> null
      FileItemTypeUtils.Subtype.BASIC_VIEWS_ACTIVITY -> null
      FileItemTypeUtils.Subtype.EMPTY_VIEWS_ACTIVITY -> null
      FileItemTypeUtils.Subtype.FULLSCREEN_VIEWS_ACTIVITY -> null
      FileItemTypeUtils.Subtype.CLASS_JAVA -> ".java"
      FileItemTypeUtils.Subtype.INTERFACE_JAVA -> ".java"
      FileItemTypeUtils.Subtype.ENUM_JAVA -> ".java"
      FileItemTypeUtils.Subtype.ANNOTATION_JAVA -> ".java"
      FileItemTypeUtils.Subtype.CLASS_KOTLIN -> ".kt"
      FileItemTypeUtils.Subtype.FILE_KOTLIN -> ".kt"
      FileItemTypeUtils.Subtype.INTERFACE_KOTLIN -> ".kt"
      FileItemTypeUtils.Subtype.SEALED_INTERFACE_KOTLIN -> ".kt"
      FileItemTypeUtils.Subtype.DATA_CLASS_KOTLIN -> ".kt"
      FileItemTypeUtils.Subtype.ENUM_CLASS_KOTLIN -> ".kt"
      FileItemTypeUtils.Subtype.SEALED_CLASS_KOTLIN -> ".kt"
      FileItemTypeUtils.Subtype.ANNOTATION_KOTLIN -> ".kt"
      FileItemTypeUtils.Subtype.OBJECT_KOTLIN -> ".kt"
    }
  }
}