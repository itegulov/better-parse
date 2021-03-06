package com.github.h0tk3y.betterParse.build

import org.gradle.api.plugins.ExtraPropertiesExtension
import kotlin.reflect.full.memberProperties

object Versions {
    val version: String = "0.5.0-M1"
    val kotlinVersion: String = "1.4.21"
    val serializationVersion: String = "1.0.1"
    val benchmarkVersion: String = "0.2.0-dev-20"
}

Versions.javaClass.kotlin.memberProperties.forEach { property ->
    val value = property.get(Versions)
    addGlobalProperty(property.name, value.toString())
}

gradle.allprojects { version = Versions.version }

fun addGlobalProperty(key: String, value: String) {
    System.setProperty("build.$key", value)

    fun ExtraPropertiesExtension.addExt() {
        set(key, value)
    }

    settings.extensions.extraProperties.addExt()
    gradle.allprojects { extensions.extraProperties.addExt() }
}
