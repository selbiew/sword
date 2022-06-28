package com.simplektx.game.screen

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import com.simplektx.game.entity.Entity
import com.simplektx.game.minigame.CombatMinigame
import com.simplektx.game.utils.draw
import com.simplektx.game.utils.write
import ktx.graphics.use

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
        shapeRenderer.draw(combatMinigame.playerPlannedInput, camera)

        draw(combatMinigame.player, Vector2(20f, 20f))
        draw(combatMinigame.enemy, Vector2(680f, 680f))
    }

    private fun draw(entity: Entity, start: Vector2, color: Color = Color.BLACK) {
        var currentColor = shapeRenderer.color
        shapeRenderer.color = color
        shapeRenderer.use(ShapeRenderer.ShapeType.Line, camera) {
            shapeRenderer.rect(start.x, start.y, 100f, 100f)
        }
        shapeRenderer.color = currentColor

        spriteBatch.write(
            "Health: ${entity.currentHealth}/${entity.maxHealth}",
            bitmapFont, Vector2(start.x + 5, start.y + 80f)
        )
        spriteBatch.write(
            "Name: ${entity.javaClass.simpleName}",
            bitmapFont, Vector2(start.x + 5,  start.y + 100f)
        )
    }
}