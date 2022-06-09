package com.simplektx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.ScreenUtils
import com.simplektx.game.Line
import com.simplektx.game.enemy.AttackGenerator
import com.simplektx.game.input.CombatInputProcessor
import com.simplektx.game.utils.draw
import ktx.graphics.use

class CombatScreen : Screen {
    private lateinit var combatInputProcessor: CombatInputProcessor
    private lateinit var camera: OrthographicCamera
    private lateinit var shapeRenderer: ShapeRenderer
    private var attackGenerator: AttackGenerator = AttackGenerator()
    private var isInitialized: Boolean = false

    override fun show() {
        if (!isInitialized) {
            combatInputProcessor = CombatInputProcessor()
            Gdx.input.inputProcessor = combatInputProcessor

            camera = OrthographicCamera()
            camera.setToOrtho(false, 800f, 800f)

            shapeRenderer = ShapeRenderer()
        }
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        camera.update()

        attackGenerator.update(delta)
        attackGenerator.lines.forEach {it.update(delta)}
        attackGenerator.lines.removeAll {it.timeRemainingMs <= 0}
        attackGenerator.lines.forEach { shapeRenderer.draw(it, camera) }

        combatInputProcessor.lines.forEach { it.update(delta) }
        combatInputProcessor.lines.removeAll { it.timeRemainingMs <= 0 }
        combatInputProcessor.lines.forEach { shapeRenderer.draw(it, camera) }
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