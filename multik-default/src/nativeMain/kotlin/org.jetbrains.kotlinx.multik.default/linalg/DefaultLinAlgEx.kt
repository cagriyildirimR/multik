/*
 * Copyright 2020-2022 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license.
 */

package org.jetbrains.kotlinx.multik.default.linalg

import org.jetbrains.kotlinx.multik.api.linalg.LinAlgEx
import org.jetbrains.kotlinx.multik.api.linalg.Norm
import org.jetbrains.kotlinx.multik.api.linalg.dot
import org.jetbrains.kotlinx.multik.kotlin.linalg.KELinAlg
import org.jetbrains.kotlinx.multik.kotlin.linalg.KELinAlgEx
import org.jetbrains.kotlinx.multik.ndarray.complex.Complex
import org.jetbrains.kotlinx.multik.ndarray.complex.ComplexDouble
import org.jetbrains.kotlinx.multik.ndarray.complex.ComplexFloat
import org.jetbrains.kotlinx.multik.ndarray.data.*
import org.jetbrains.kotlinx.multik.openblas.linalg.NativeLinAlg
import org.jetbrains.kotlinx.multik.openblas.linalg.NativeLinAlgEx

public actual object DefaultLinAlgEx : LinAlgEx {

    actual override fun <T : Number> inv(mat: MultiArray<T, D2>): NDArray<Double, D2> = NativeLinAlgEx.inv(mat)

    actual override fun invF(mat: MultiArray<Float, D2>): NDArray<Float, D2> = NativeLinAlgEx.invF(mat)

    actual override fun <T : Complex> invC(mat: MultiArray<T, D2>): NDArray<T, D2> = NativeLinAlgEx.invC(mat)

    actual override fun <T : Number, D : Dim2> solve(a: MultiArray<T, D2>, b: MultiArray<T, D>): NDArray<Double, D> =
        NativeLinAlgEx.solve(a, b)

    actual override fun <D : Dim2> solveF(a: MultiArray<Float, D2>, b: MultiArray<Float, D>): NDArray<Float, D> =
        NativeLinAlgEx.solveF(a, b)

    actual override fun <T : Complex, D : Dim2> solveC(a: MultiArray<T, D2>, b: MultiArray<T, D>): NDArray<T, D> =
        NativeLinAlgEx.solveC(a, b)

    actual override fun normF(mat: MultiArray<Float, D2>, norm: Norm): Float = NativeLinAlgEx.normF(mat, norm)

    actual override fun norm(mat: MultiArray<Double, D2>, norm: Norm): Double = NativeLinAlgEx.norm(mat, norm)

    actual override fun <T : Number> qr(mat: MultiArray<T, D2>): Pair<D2Array<Double>, D2Array<Double>> =
        NativeLinAlgEx.qr(mat)

    actual override fun qrF(mat: MultiArray<Float, D2>): Pair<D2Array<Float>, D2Array<Float>> = NativeLinAlgEx.qrF(mat)

    actual override fun <T : Complex> qrC(mat: MultiArray<T, D2>): Pair<D2Array<T>, D2Array<T>> =
        NativeLinAlgEx.qrC(mat)

    actual override fun <T : Number> plu(mat: MultiArray<T, D2>): Triple<D2Array<Double>, D2Array<Double>, D2Array<Double>> =
        NativeLinAlgEx.plu(mat)

    actual override fun pluF(mat: MultiArray<Float, D2>): Triple<D2Array<Float>, D2Array<Float>, D2Array<Float>> =
        NativeLinAlgEx.pluF(mat)

    actual override fun <T : Complex> pluC(mat: MultiArray<T, D2>): Triple<D2Array<T>, D2Array<T>, D2Array<T>> =
        NativeLinAlgEx.pluC(mat)

    actual override fun <T : Number> eig(mat: MultiArray<T, D2>): Pair<D1Array<ComplexDouble>, D2Array<ComplexDouble>> =
        KELinAlgEx.eig(mat)

    actual override fun eigF(mat: MultiArray<Float, D2>): Pair<D1Array<ComplexFloat>, D2Array<ComplexFloat>> =
        KELinAlgEx.eigF(mat)

    actual override fun <T : Complex> eigC(mat: MultiArray<T, D2>): Pair<D1Array<T>, D2Array<T>> =
        KELinAlgEx.eigC(mat)

    actual override fun <T : Number> eigVals(mat: MultiArray<T, D2>): D1Array<ComplexDouble> =
        KELinAlgEx.eigVals(mat)

    actual override fun eigValsF(mat: MultiArray<Float, D2>): D1Array<ComplexFloat> = KELinAlgEx.eigValsF(mat)

    actual override fun <T : Complex> eigValsC(mat: MultiArray<T, D2>): D1Array<T> = KELinAlgEx.eigValsC(mat)

    actual override fun <T : Number> dotMM(a: MultiArray<T, D2>, b: MultiArray<T, D2>): NDArray<T, D2> =
        when (a.dtype) {
            DataType.FloatDataType, DataType.DoubleDataType -> NativeLinAlg.dot(a, b)
            else -> KELinAlg.dot(a, b)
        }

    actual override fun <T : Complex> dotMMComplex(a: MultiArray<T, D2>, b: MultiArray<T, D2>): NDArray<T, D2> =
        NativeLinAlg.dot(a, b)

    actual override fun <T : Number> dotMV(a: MultiArray<T, D2>, b: MultiArray<T, D1>): NDArray<T, D1> =
        when (a.dtype) {
            DataType.FloatDataType, DataType.DoubleDataType -> NativeLinAlg.dot(a, b)
            else -> KELinAlg.dot(a, b)
        }

    actual override fun <T : Complex> dotMVComplex(a: MultiArray<T, D2>, b: MultiArray<T, D1>): NDArray<T, D1> =
        NativeLinAlg.dot(a, b)

    actual override fun <T : Number> dotVV(a: MultiArray<T, D1>, b: MultiArray<T, D1>): T =
        when (a.dtype) {
            DataType.FloatDataType, DataType.DoubleDataType -> NativeLinAlg.dot(a, b)
            else -> KELinAlg.dot(a, b)
        }

    actual override fun <T : Complex> dotVVComplex(a: MultiArray<T, D1>, b: MultiArray<T, D1>): T =
        NativeLinAlg.dot(a, b)
}