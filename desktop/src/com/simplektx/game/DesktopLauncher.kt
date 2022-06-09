package com.simplektx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
fun main (arg: Array<String>) {
	val config = Lwjgl3ApplicationConfiguration()
	config.setForegroundFPS(60)
	config.setTitle("Sword Combat")
	config.setWindowedMode(800, 800)
	config.useVsync(true)
	Lwjgl3Application(SwordGame(), config)
}
