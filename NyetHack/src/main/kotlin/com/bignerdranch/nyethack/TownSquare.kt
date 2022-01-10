package com.bignerdranch.nyethack

open class TownSquare : Room("The Town Square") {
    override val status = "Bustling"
    private var bellSound = "GWONG"

    final override fun enterRoom() {
        narrate("The villagers rally and cheer as the hero enters")
        ringBell()
    }

    fun ringBell(times: Int = 1) {
        narrate("The bell tower announces the hero's presence: \n")
        repeat(times) {
            narrate(bellSound)
        }
    }
}