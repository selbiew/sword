package com.simplektx.game.screen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.simplektx.game.minigame.CombatMinigame
import com.simplektx.game.utils.draw
import com.simplektx.game.utils.write

class CombatMinigameRenderer(private val combatMinigame: CombatMinigame) {
    private lateinit var camera: OrthographicCamera
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var bitmapFont: BitmapFont
    private lateinit var spriteBatch: SpriteBatch
    private var isInitialized: Boolean = false

    fun initialize() {
        if (!isInitialized) {
            camera = OrthographicCamera()
            camera.setToOrtho(false, 800f, 800f)

            shapeRenderer = ShapeRenderer()
            spriteBatch = SpriteBatch()
            bitmapFont = BitmapFont()
            isInitialized = true
        }
    }

    fun render(deltaMs: Long) {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        camera.update()
        combatMinigame.update(deltaMs)
        spriteBatch.write(combatMinigame.interactions)
        shapeRenderer.draw(combatMinigame.playerAction, camera, Color.BLUE)
        shapeRenderer.draw(combatMinigame.enemyAction, camera, Color.RED)
    }
}