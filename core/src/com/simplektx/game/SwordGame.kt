package com.simplektx.game;

import com.badlogic.gdx.Screen
import com.simplektx.game.screen.CombatScreen
import ktx.app.KtxGame

class SwordGame : KtxGame<Screen>() {

    private lateinit var combatScreen: CombatScreen

    override fun create() {
        combatScreen = CombatScreen()
        addScreen(combatScreen)
        setScreen<CombatScreen>()
    }
}
