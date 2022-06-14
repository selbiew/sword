package com.simplektx.game.minigame

import com.simplektx.game.entity.Enemy
import com.simplektx.game.entity.Player
import com.simplektx.game.input.CombatInput

class CombatMinigame(val player: Player, val enemy: Enemy) {
    var playerAction: Action? = null
    var enemyAction: Action? = null

    fun update(deltaMs: Long) {
        playerAction?.update(deltaMs)
        enemyAction?.update(deltaMs)
    }

    fun receive(combatInput: CombatInput) {
        when (combatInput) {
            is CombatInput.SwingInput -> {
                playerAction = player.swing(combatInput)
            }
        }
    }

    fun receive(action: Action) {
        enemyAction = action
    }
}