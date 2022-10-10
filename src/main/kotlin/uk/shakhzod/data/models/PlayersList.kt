package uk.shakhzod.data.models

import uk.shakhzod.other.Constants.TYPE_PLAYERS_LIST

data class PlayersList(
    val players: List<PlayerData>
) : BaseModel(TYPE_PLAYERS_LIST)
