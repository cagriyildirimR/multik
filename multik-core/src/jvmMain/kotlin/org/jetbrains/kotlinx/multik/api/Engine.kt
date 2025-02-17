package org.jetbrains.kotlinx.multik.api

import java.util.*

/**
 * Engine Provider for JVM.
 */
public actual fun enginesProvider(): Map<EngineType, Engine> {
    val engineList = ServiceLoader.load(Engine::class.java).toList()

    if (engineList.isEmpty()) {
        error(
            """Fail to find engine. Consider to add one of the following dependencies: 
 - multik-default
 - multik-kotlin
 - multik-openblas"""
        )
    }

    return engineList.associateBy { it.type }
}
