package top.srcres258.mobindustry.util

import net.minecraft.nbt.CompoundTag
import net.minecraft.world.entity.Entity

fun <E : Entity> E.clone(): E {
    val newObj = type.create(level()) ?: throw IllegalStateException("Failed to clone Entity ${type.description} " +
            "(${type.descriptionId})")
    val nbt = CompoundTag()
    save(nbt)
    newObj.load(nbt)
    return newObj as? E ?: throw IllegalStateException("The cloned Entity ${newObj.type.descriptionId} is not of " +
            "the expected type ${type.descriptionId}, which is abnormal.")
}