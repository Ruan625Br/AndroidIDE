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

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.TypeSpec
import java.util.Locale

object KotlinClassBuilder {

  fun createClass(packageName: String, className: String): String {
    val ktClass = TypeSpec.classBuilder(className)
      .build()
    return ktClass.toKtFile(packageName, className).toString()

  }
  fun createInterface(packageName: String, className: String): String {
    val ktInterface = TypeSpec.interfaceBuilder(className)
      .build()

    return ktInterface.toKtFile(packageName, className).toString()
  }
  fun createSealedInterface(packageName: String, className: String): String {
    val ktInterface = TypeSpec.interfaceBuilder(className)
      .addModifiers(KModifier.SEALED)
      .build()

    return ktInterface.toKtFile(packageName, className).toString()
  }
  fun createDataClass(packageName: String, className: String): String {
    val dataClass  = TypeSpec.classBuilder(className)
      .addModifiers(KModifier.DATA)
      .build()
    return dataClass.toKtFile(packageName, className).toString()
  }

  fun createEnumClass(packageName: String, className: String): String {
    val ktClass  = TypeSpec.classBuilder(className)
      .addModifiers(KModifier.ENUM)
      .build()
    return ktClass.toKtFile(packageName, className).toString()
  }
  fun createSealedClass(packageName: String, className: String): String {
    val ktClass = TypeSpec.classBuilder(className)
      .addModifiers(KModifier.SEALED)
      .build()
    return ktClass.toKtFile(packageName, className).toString()
  }
  fun createAnnotation(packageName: String, className: String): String {
    val ktClass = TypeSpec.annotationBuilder(className)
      .build()
    return ktClass.toKtFile(packageName, className).toString()
  }
 fun createObject(packageName: String, className: String): String {
    val ktClass = TypeSpec.objectBuilder(className)
      .build()
    return ktClass.toKtFile(packageName, className).toString()
  }

  fun TypeSpec.toKtFile(packageName: String, className: String): FileSpec {
    val ktFile = FileSpec.builder(packageName, className)
      .addType(this)
      .build()


    return ktFile
  }


}