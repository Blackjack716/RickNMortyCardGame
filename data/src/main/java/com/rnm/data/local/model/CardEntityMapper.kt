package com.rnm.data.local.model

import com.rnm.data.remote.model.Characters

fun List<Characters>.toNewCard(): List<CardEntity> {
    return this.map { character ->
        CardEntity(
            id = character.id ?: 0,
            isFavourite = false,
            isOwned = false,
            name = character.name,
            photoUrl = character.image,
            rarity = null,
            sellValue = null,
            upgradeCost = CardEntity.UPGRADE_COST_1
        )
    }
}