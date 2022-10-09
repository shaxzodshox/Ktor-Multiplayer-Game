package uk.shakhzod.data.models

data class CreateRoomRequest(
    val name: String,
    val maxPlayers: Int
)