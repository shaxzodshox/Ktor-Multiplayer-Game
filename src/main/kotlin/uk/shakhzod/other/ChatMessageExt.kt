package uk.shakhzod.other

import uk.shakhzod.data.models.ChatMessage

fun ChatMessage.matchesWord(word: String): Boolean {
    return message.lowercase().trim() == word.lowercase().trim()
}