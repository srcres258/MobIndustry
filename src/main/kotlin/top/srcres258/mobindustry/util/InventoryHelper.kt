package top.srcres258.mobindustry.util

import net.minecraft.core.BlockPos
import net.minecraft.world.Containers
import net.minecraft.world.SimpleContainer
import net.minecraft.world.level.Level
import net.neoforged.neoforge.items.IItemHandler

fun dropItemHandler(level: Level, position: BlockPos, itemHandler: IItemHandler) {
    val slots = itemHandler.slots
    val inventory = SimpleContainer(slots)

    for (i in 0 ..< slots) {
        inventory.setItem(i, itemHandler.getStackInSlot(i))
    }

    Containers.dropContents(level, position, inventory)
}