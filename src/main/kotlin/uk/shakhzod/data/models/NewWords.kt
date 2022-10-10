package uk.shakhzod.data.models

import uk.shakhzod.other.Constants.TYPE_NEW_WORDS

data class NewWords(
    val newWords: List<String>
) : BaseModel(TYPE_NEW_WORDS)
