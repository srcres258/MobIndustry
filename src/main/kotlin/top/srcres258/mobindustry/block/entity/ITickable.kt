package top.srcres258.mobindustry.block.entity

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.state.BlockState

interface ITickable {
    fun tick(level: Level, blockPos: BlockPos, blockState: BlockState)
}