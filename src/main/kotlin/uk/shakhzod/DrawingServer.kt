package uk.shakhzod

import uk.shakhzod.data.Player
import uk.shakhzod.data.Room
import java.util.concurrent.ConcurrentHashMap

class DrawingServer {
    val rooms = ConcurrentHashMap<String, Room>()
    val players = ConcurrentHashMap<String, Player>()

    fun playerJoined(player: Player) {
        players[player.clientId] = player
    }

    fun getRoomWithClientId(clientId: String): Room? {
        val filteredRooms = rooms.filterValues { room ->
            room.players.find { player ->
                player.clientId == clientId
            } != null
        }
        return if(filteredRooms.values.isEmpty()){
            //If did not find the room with the corresponding user
            null
        }
        else{
            filteredRooms.values.toList()[0] //There can be only a single room..Therefore [0]. As a user cannot be in multiple rooms within a game.
        }
    }
}