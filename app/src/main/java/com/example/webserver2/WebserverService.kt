package com.example.webserver2

import android.app.Service
import android.content.Intent
import android.os.IBinder
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.*
import java.util.concurrent.TimeUnit

class WebServerService : Service() {

    private lateinit var server: NettyApplicationEngine

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        server = embeddedServer(Netty, 8080) {
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
        }
        server.start(wait = false)

        // Servisin tekrar başlatılmasını sağlamak için START_STICKY değerini döndür
        return START_STICKY
    }

    override fun onDestroy() {
        server.stop(1000, 1000, TimeUnit.HOURS)
        super.onDestroy()
    }
}
