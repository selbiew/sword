package com.simplektx.game.entity

import com.simplektx.game.minigame.action.Action
import com.simplektx.game.minigame.action.NoAction
import com.simplektx.game.minigame.interaction.Interaction

abstract class Entity(var name: String, var maxHealth: Int) {
    var action: Action = NoAction()
    var currentHealth: Int = maxHealth

    val isExecutingAction: Boolean get() = (action.isExecuting)

    abstract fun process(interaction: Interaction)
}