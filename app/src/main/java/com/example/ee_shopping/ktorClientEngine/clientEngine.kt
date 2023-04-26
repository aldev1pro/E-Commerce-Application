package com.example.ee_shopping.ktorClientEngine

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

object KtorClient{

    private val json = Json{
        encodeDefaults = true
        ignoreUnknownKeys = true
        isLenient = true
    }

    val httpClient = HttpClient(Android){
        install(HttpTimeout){
            socketTimeoutMillis = 30_000
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 30_000
        }

        install(Logging){
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("TAG","LOG:$message")
                }
            }

        }
        install(JsonFeature){
            serializer = KotlinxSerializer(json)
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
        }

    }

}