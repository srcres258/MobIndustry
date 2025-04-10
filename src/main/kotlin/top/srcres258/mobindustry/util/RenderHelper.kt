package top.srcres258.mobindustry.util

import net.minecraft.world.entity.LivingEntity

fun LivingEntity.resetForRendering() {
    hurtTime = 0
    yBodyRot = 0F
    yBodyRotO = 0F
    yHeadRot = 0F
    yHeadRotO = 0F
}