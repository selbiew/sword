package com.simplektx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.simplektx.game.entity.Enemy
import com.simplektx.game.entity.Player
import com.simplektx.game.input.CombatInputProcessor
import com.simplektx.game.minigame.CombatMinigame

class CombatScreen : Screen {
    private lateinit var combatMinigame: CombatMinigame
    private lateinit var combatInputProcessor: CombatInputProcessor
    private lateinit var combatMinigameRenderer: CombatMinigameRenderer
    private var isInitialized: Boolean = false

    override fun show() {
        if (!isInitialized) {
            combatMinigame = CombatMinigame(Player(), Enemy("Goblin", 5))
            combatMinigameRenderer = CombatMinigameRenderer(combatMinigame)
            combatMinigameRenderer.initialize()

            combatInputProcessor = CombatInputProcessor(combatMinigame)
            Gdx.input.inputProcessor = combatInputProcessor

            isInitialized = true
        }
    }

    override fun render(delta: Float) {
        combatMinigameRenderer.render((delta * 1000).toLong())
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun hide() {
    }

    override fun dispose() {
    }
}