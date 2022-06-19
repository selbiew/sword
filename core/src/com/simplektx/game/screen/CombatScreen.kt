package com.simplektx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.simplektx.game.entity.Enemy
import com.simplektx.game.entity.Player
import com.simplektx.game.input.CombatInputProcessor
import com.simplektx.game.minigame.CombatMinigame
import com.simplektx.game.utils.draw
import com.simplektx.game.utils.write

class CombatScreen : Screen {
    private lateinit var combatMinigame: CombatMinigame
    private lateinit var combatInputProcessor: CombatInputProcessor
    private lateinit var camera: OrthographicCamera
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var bitmapFont: BitmapFont
    private lateinit var spriteBatch: SpriteBatch
    private var isInitialized: Boolean = false

    override fun show() {
        if (!isInitialized) {
            combatMinigame = CombatMinigame(Player(), Enemy())
            combatInputProcessor = CombatInputProcessor(combatMinigame)
            Gdx.input.inputProcessor = combatInputProcessor

            camera = OrthographicCamera()
            camera.setToOrtho(false, 800f, 800f)

            shapeRenderer = ShapeRenderer()
            spriteBatch = SpriteBatch()
            bitmapFont = BitmapFont()
            isInitialized = true
        }
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        camera.update()
        combatMinigame.update((delta * 1000).toLong())
        spriteBatch.write(combatMinigame.interactions)
        shapeRenderer.draw(combatMinigame.playerAction, camera)
        shapeRenderer.draw(combatMinigame.enemyAction, camera)
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