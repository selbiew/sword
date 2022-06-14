package com.simplektx.game.entity

class Enemy : Entity() {
    override val name: String = "Enemy"
    override val maxHealth: Int = 5
    override val currentHealth: Int = maxHealth
}