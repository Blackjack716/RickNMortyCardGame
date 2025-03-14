package com.rnm.domain.model

enum class SortType {
    Owned, Fav, Id, CharacterName, Rarity
}

fun SortType.toInt(): Int {
    return this.ordinal
}

fun Int.toSortType(): SortType {
    return when(this) {
        SortType.Fav.ordinal -> SortType.Fav
        SortType.Id.ordinal -> SortType.Id
        SortType.Owned.ordinal -> SortType.Owned
        SortType.CharacterName.ordinal -> SortType.CharacterName
        SortType.Rarity.ordinal -> SortType.Rarity
        else -> SortType.Id
    }
}