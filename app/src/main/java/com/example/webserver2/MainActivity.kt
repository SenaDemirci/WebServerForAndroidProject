package com.example.webserver2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        embeddedServer(Netty, 8080, host = "localhost") {
            install(ContentNegotiation) {
                gson {}
            }
            routing {
                get("/") {
                    call.respond(mapOf("message" to "Hello Sena"))
                }
                get("/sena") {
                    call.respond(mapOf("message" to "Fetching data..."))
                }
            }
        }.start(wait = false)
    }
}