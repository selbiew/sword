package com.simplektx.game.entity

import com.simplektx.game.input.CombatInput
import com.simplektx.game.minigame.action.Block
import com.simplektx.game.minigame.action.Stab
import com.simplektx.game.minigame.action.Swing

class Player : Entity() {
    override val name: String = "Player"
    override val maxHealth: Int = 10
    override val currentHealth: Int = maxHealth


    // TODO: Use callbacks / signals so that prep can be interrupted but not execution
    fun swing(swingInput: CombatInput.SwingInput): Swing {
        executingAction = true
        return Swing(1, 1, 1000, 10 * swingInput.distance.toLong(),
                     swingInput.start, swingInput.end)
    }

    fun stab(stabInput: CombatInput.StabInput): Stab {
        // TODO: Fix executionTime
        executingAction = true
        return Stab(1, 1, 1000, 1000,
            stabInput.center)
    }

    fun block(blockInput: CombatInput.BlockInput): Block {
        executingAction = true
        return Block(blockInput.distance, blockInput.start, blockInput.end)
    }
}
