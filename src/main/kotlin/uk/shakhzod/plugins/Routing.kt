package uk.shakhzod.plugins

import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import uk.shakhzod.routes.createRoomRoute
import uk.shakhzod.routes.gameWebSocketRoute
import uk.shakhzod.routes.getRoomsRoute
import uk.shakhzod.routes.joinRoomRoute

fun Application.configureRouting() {
    install(Routing) {
        createRoomRoute()
        getRoomsRoute()
        joinRoomRoute()
        gameWebSocketRoute()
    }
}
