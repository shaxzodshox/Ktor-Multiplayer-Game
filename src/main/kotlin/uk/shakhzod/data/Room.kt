package uk.shakhzod.data

import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.isActive

class Room(
    val name: String,
    val maxPlayers: Int,
    var players: List<Player> = listOf()
) {
    //This method will broadcast the message to everyone connected..
    suspend fun broadcast(message: String) {
        players.forEach { player ->
            //If player is not disconnected and still online..
            if (player.socket.isActive) {
                player.socket.send(Frame.Text(message))
            }
        }
    }

    //This method will broadcast the message to everyone connected except the current user..
    suspend fun broadcastToAllExcept(message: String, clientId : String) {
        players.forEach { player ->
            //If player is not disconnected and still online..
            if (player.clientId != clientId && player.socket.isActive) {
                player.socket.send(Frame.Text(message))
            }
        }
    }

    fun containsPlayer(username: String) : Boolean{
        return players.find { it.username == username } != null
    }
}