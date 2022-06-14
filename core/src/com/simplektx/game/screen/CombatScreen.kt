package com.simplektx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.simplektx.game.Line
import com.simplektx.game.enemy.AttackGenerator
import com.simplektx.game.entity.Enemy
import com.simplektx.game.entity.Player
import com.simplektx.game.input.CombatInputProcessor
import com.simplektx.game.minigame.CombatMinigame
import com.simplektx.game.utils.draw
import ktx.graphics.use

class CombatScreen : Screen {
    private lateinit var combatMinigame: CombatMinigame
    private lateinit var combatInputProcessor: CombatInputProcessor
    private lateinit var camera: OrthographicCamera
    private lateinit var shapeRenderer: ShapeRenderer
    private lateinit var bitmapFont: BitmapFont
    private lateinit var spriteBatch: SpriteBatch
    private var attackGenerator: AttackGenerator = AttackGenerator()
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
        shapeRenderer.draw(combatMinigame.playerAction, camera)
//        attackGenerator.update(delta)
//        attackGenerator.lines.forEach {it.update(delta)}
//        attackGenerator.lines.removeAll {it.timeRemainingMs <= 0}
//        combatInputProcessor.lines.forEach { it.update(delta) }
//        combatInputProcessor.lines.removeAll { it.timeRemainingMs <= 0 }
//
//        attackGenerator.lines.forEach { shapeRenderer.draw(it, camera, Color.RED) }
//        combatInputProcessor.lines.forEach { shapeRenderer.draw(it, camera) }

//        spriteBatch.use {
//            when (blockedAttack(attackGenerator.lines, combatInputProcessor.lines)) {
//                BlockType.HIT -> {
//                    bitmapFont.color = Color.GREEN
//                    bitmapFont.draw(spriteBatch, "Nice!", 700f, 700f)
//                }
//                BlockType.MISS -> {
//                    bitmapFont.color = Color.RED
//                    bitmapFont.draw(spriteBatch, "BAD!!!", 700f, 700f)
//                }
//            }
//        }
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

    private fun blockedAttack (attacks: MutableList<Line>, blocks: MutableList<Line>): BlockType {
        if (blocks.isEmpty()) return BlockType.NO_ATTEMPT
        for (block in blocks) {
            for (attack in attacks) {
                if (block.intersects(attack)) {
                    return BlockType.HIT
                }
            }
        }

        return BlockType.MISS
    }

    enum class BlockType {
        HIT, MISS, NO_ATTEMPT
    }
}