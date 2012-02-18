package org.terasology.data.blocks.definitions

/**
 * Gravel is not fancy _yet_ - not sure if it should be its own class (think sand/gravel/etc falling down)
 * Or whether _all_ blocks should have the ability to fall down under certain circumstances (think cave-in)
 * version is for Serialization purposes (maybe?) and may not make sense for all blocks
 */
block {
    version = 1
    shape = "cube"

    hardness = 2
}