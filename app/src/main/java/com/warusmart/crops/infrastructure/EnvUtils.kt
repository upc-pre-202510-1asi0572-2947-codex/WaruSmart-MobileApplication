// app/src/main/java/com/example/chaquitaclla_appmovil_android/EnvUtils.kt
package com.warusmart.crops.infrastructure

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Utility object for loading environment variables from asset files.
 */
object EnvUtils {
    fun loadEnv(context: Context, fileName: String): Map<String, String> {
        val envMap = mutableMapOf<String, String>()
        val inputStream = context.assets.open(fileName)
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            reader.lineSequence().forEach { line ->
                val parts = line.split("=", limit = 2)
                if (parts.size == 2) {
                    envMap[parts[0].trim()] = parts[1].trim()
                }
            }
        }
        return envMap
    }
}