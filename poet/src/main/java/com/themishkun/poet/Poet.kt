@file:JvmName("Poet")
package com.themishkun.poet



fun main(args: Array<String>) {
    try {
        val instancesToGenerate = args.firstOrNull()?.toInt() ?: throw IllegalArgumentException()
        for (i in 0..instancesToGenerate) {
            generateComponentFeature("$i")
            generateSubcomponentFeature("$i")
        }
        generateMainSubcomponent((0..instancesToGenerate).map { it.toString() })
    }catch (e: Exception){
        println("Usage: generate <number of features>")
    }
}

fun getRelativePath(packageName: String) = "../$packageName/src/main/java/com/themishkun/$packageName"

