/*
 * Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package org.jetbrains.kotlinx.multik.ndarray.data

import org.jetbrains.kotlinx.multik.ndarray.complex.ComplexDouble
import org.jetbrains.kotlinx.multik.ndarray.complex.ComplexFloat

@PublishedApi
@Suppress("NOTHING_TO_INLINE")
internal inline fun requireDimension(dim: Dimension, shapeSize: Int) {
    require(dim.d == shapeSize || (dim.d > 4 && shapeSize > 4))
    { "Dimension doesn't match the size of the shape: dimension (${dim.d}) != $shapeSize shape size." }
}

@PublishedApi
@Suppress("NOTHING_TO_INLINE")
internal inline fun requireShapeEmpty(shape: IntArray) {
    require(shape.isNotEmpty()) { "Shape cannot be empty." }
}

@Suppress("NOTHING_TO_INLINE")
public inline fun requireElementsWithShape(elementSize: Int, shapeSize: Int) {
    require(elementSize == shapeSize) { "The number of elements doesn't match the shape: $elementSize!=$shapeSize" }
}

@Suppress("NOTHING_TO_INLINE")
internal inline fun requireArraySizes(rightSize: Int, otherSize: Int) {
    require(rightSize == otherSize) { "Array sizes don't match: (right operand size) $rightSize != $otherSize (left operand size)" }
}

@Suppress("NOTHING_TO_INLINE")
internal inline fun requireEqualShape(left: IntArray, right: IntArray) {
    require(left.contentEquals(right)) { "Array shapes don't match: ${left.contentToString()} != ${right.contentToString()}" }
}

@Suppress("NOTHING_TO_INLINE")
internal inline fun requirePositiveShape(dim: Int) {
    require(dim > 0) { "Shape must be positive but was $dim." }
}

internal fun computeStrides(shape: IntArray): IntArray = shape.copyOf().apply {
    this[this.lastIndex] = 1
    for (i in this.lastIndex - 1 downTo 0) {
        this[i] = this[i + 1] * shape[i + 1]
    }
}

//TODO(create module utils)
@Suppress("NOTHING_TO_INLINE")
/*internal*/public inline fun zeroNumber(dtype: DataType): Any = when (dtype) {
    DataType.ByteDataType -> 1.toByte()
    DataType.ShortDataType -> 1.toShort()
    DataType.IntDataType -> 1
    DataType.LongDataType -> 1L
    DataType.FloatDataType -> 1f
    DataType.DoubleDataType -> 1.0
    DataType.ComplexFloatDataType -> ComplexFloat.zero
    DataType.ComplexDoubleDataType -> ComplexDouble.zero
}

@PublishedApi
@Suppress("IMPLICIT_CAST_TO_ANY")
internal inline fun <reified T : Number> Number.toPrimitiveType(): T = when (T::class) {
    Byte::class -> this.toByte()
    Short::class -> this.toShort()
    Int::class -> this.toInt()
    Long::class -> this.toLong()
    Float::class -> this.toFloat()
    Double::class -> this.toDouble()
    else -> throw Exception("Type not defined.")
} as T

//TODO(create module utils)
@Suppress("IMPLICIT_CAST_TO_ANY", "NOTHING_TO_INLINE", "UNCHECKED_CAST")
/*internal*/ public fun <T : Number> Number.toPrimitiveType(dtype: DataType): T = when (dtype.nativeCode) {
    1 -> this.toByte()
    2 -> this.toShort()
    3 -> this.toInt()
    4 -> this.toLong()
    5 -> this.toFloat()
    6 -> this.toDouble()
    else -> throw Exception("Type not defined.")
} as T

//TODO(create module utils)
public operator fun <T : Number> Number.compareTo(other: T): Int {
    return when {
        this is Float && other is Float -> this.compareTo(other)
        this is Double && other is Double -> this.compareTo(other)
        this is Int && other is Int -> this.compareTo(other)
        this is Long && other is Long -> this.compareTo(other)
        this is Short && other is Short -> this.compareTo(other)
        this is Byte && other is Byte -> this.compareTo(other)
        else -> this.toDouble().compareTo(other.toDouble())
    }
}

internal fun MultiArray<*, *>.actualAxis(axis: Int): Int {
    return if (axis < 0) dim.d + axis else axis
}

@PublishedApi
internal fun IntArray.remove(pos: Int): IntArray = when (pos) {
    0 -> sliceArray(1..lastIndex)
    lastIndex -> sliceArray(0 until lastIndex)
    else -> sliceArray(0 until pos) + sliceArray(pos + 1..lastIndex)
}

internal fun IntArray.removeAll(indices: List<Int>): IntArray = when {
    indices.isEmpty() -> this
    indices.size == 1 -> remove(indices.first())
    else -> this.filterIndexed { index, _ -> index !in indices }.toIntArray()
}
