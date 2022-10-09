package uk.shakhzod

import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.sessions.*
import uk.shakhzod.plugins.*

val server = DrawingServer()
val gson = Gson()

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureSockets()
        configureSession()
        configureRouting()
        configureSerialization()
        configureMonitoring()
    }.start(wait = true)
}
