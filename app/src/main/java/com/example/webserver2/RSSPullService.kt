package com.example.webserver2

import android.app.IntentService
import android.content.Intent

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

class RSSPullService : IntentService(RSSPullService::class.simpleName){
    override fun onHandleIntent(intent: Intent?) {
        // Gets data from the incoming Intent
        var dataString = intent?.dataString

        embeddedServer(Netty, 8080, host = "localhost") {
            install(ContentNegotiation) {
                gson {}
            }
            routing {
                get("/") {
                    call.respond(mapOf("message" to "Hello Android !"))
                }
                get("/getData") {
                    call.respond(mapOf("message" to "Fetching data..."))
                }
            }
        }.start(wait = false)

    }
}