package com.example.webserver2

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import java.util.concurrent.TimeUnit


class MyForegroundService : Service() {

    private lateinit var server: NettyApplicationEngine

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        server = embeddedServer(Netty, 8080) {
            install(ContentNegotiation) {
                gson {}
            }
            routing {
                get("/") {
                    call.respond(mapOf("message" to "Hello Sena"))
                }
                get("/getData") {
                    call.respond(mapOf("message" to "Fetching data..."))
                }
            }
        }
        server.start(wait = false)

        Thread {
            while (true) {
                Log.e("Serviceeeeee", "Service is running...")
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        //val intent = Intent(this, MyForegroundService::class.java)
        startService(intent)
        return null
    }

    override fun onDestroy() {
        try {
            Log.e("serviceeeeee destroy", "serviceeeee destroy")
            server.stop(1000, 1000, TimeUnit.HOURS)
        } catch (e: Exception) {
            e.printStackTrace()
        }
//        val intent = Intent(this, MyForegroundService::class.java)
//        startService(intent)
    }
}