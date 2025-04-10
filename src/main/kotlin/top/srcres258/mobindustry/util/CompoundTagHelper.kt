package top.srcres258.mobindustry.util

import net.minecraft.nbt.DoubleTag
import net.minecraft.nbt.ListTag

fun createDoubleList(vararg values: Double): ListTag =
    ListTag().also { result ->
        for (value in values) {
            result.add(DoubleTag.valueOf(value))
        }
    }