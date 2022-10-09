package uk.shakhzod.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import uk.shakhzod.data.Room
import uk.shakhzod.data.models.BasicApiResponse
import uk.shakhzod.data.models.CreateRoomRequest
import uk.shakhzod.data.models.RoomResponse
import uk.shakhzod.other.Constants.MAX_ROOM_SIZE
import uk.shakhzod.server

fun Route.createRoomRoute() {
    route("/api/createRoom") {
        post {
            val roomRequest = call.receiveOrNull<CreateRoomRequest>()
            if (roomRequest == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            //If there is already a room with the same name
            if (server.rooms[roomRequest.name] != null) {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(false, "Room already exists")
                )
            }

            //If there are no enough players
            if (roomRequest.maxPlayers < 2) {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(false, "The minimum room size is 2")
                )
                return@post
            }
            //If the number of players are more than the limit
            if (roomRequest.maxPlayers > MAX_ROOM_SIZE) {
                call.respond(
                    HttpStatusCode.OK,
                    BasicApiResponse(false, "The maximum room size is $MAX_ROOM_SIZE")
                )
                return@post
            }

            //If all the above conditions are not true, then create a new room with the given room name and number of maximum players
            val room = Room(roomRequest.name, roomRequest.maxPlayers)
            server.rooms[roomRequest.name] = room
            println("Room created: ${roomRequest.name}")

            call.respond(HttpStatusCode.OK, BasicApiResponse(true))
        }
    }
}

fun Route.getRoomsRoute() {
    route("/api/getRooms") {
        get {
            val searchQuery = call.parameters["searchQuery"]
            if (searchQuery == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val roomResult = server.rooms.filterKeys {
                it.contains(searchQuery, ignoreCase = true)
            }
            val roomResponses = roomResult.values.map {
                RoomResponse(it.name, it.maxPlayers, it.players.size)
            }.sortedBy { it.name }

            call.respond(HttpStatusCode.OK, roomResponses)
        }
    }
}

fun Route.joinRoomRoute() {
    route("/api/joinRoom") {
        get {
            val username = call.parameters["username"]
            val roomName = call.parameters["roomName"]
            if (username == null || roomName == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }

            val room = server.rooms[roomName]
            when {
                room == null -> {
                    call.respond(HttpStatusCode.OK, BasicApiResponse(false, "Room not found"))
                }
                room.containsPlayer(username) -> {
                    call.respond(HttpStatusCode.OK, BasicApiResponse(false, "A player with this name already joined."))
                }
                room.players.size >= room.maxPlayers -> {
                    call.respond(HttpStatusCode.OK, BasicApiResponse(false, "This room is already full"))
                }
                else -> {
                    call.respond(HttpStatusCode.OK, BasicApiResponse(true))
                }
            }
        }
    }
}