package com.rnm.data.local.model

import com.rnm.data.remote.model.Characters
import com.rnm.domain.model.Card

fun List<Characters>.toNewCardEntity(): List<CardEntity> {
    return this.map { character ->
        CardEntity(
            id = character.id ?: 0,
            isFavourite = false,
            isOwned = false,
            name = character.name,
            photoUrl = character.image,
            rarity = null,
            sellValue = null,
            upgradeCost = Card.UPGRADE_COST_1
        )
    }
}

fun List<CardEntity>.toCard(): List<Card> {
    return this.map { cardEntity ->
        Card(
            id = cardEntity.id,
            name = cardEntity.name,
            photoUrl = cardEntity.photoUrl,
            isFavourite = cardEntity.isFavourite,
            isOwned = cardEntity.isOwned,
            rarity = cardEntity.rarity,
            sellValue = cardEntity.sellValue,
            upgradeCost = cardEntity.upgradeCost
        )
    }
}

fun CardEntity.toCard(): Card {
    return Card(
        id = this.id,
        name = this.name,
        photoUrl = this.photoUrl,
        isFavourite = this.isFavourite,
        isOwned = this.isOwned,
        rarity = this.rarity,
        sellValue = this.sellValue,
        upgradeCost = this.upgradeCost
    )
}