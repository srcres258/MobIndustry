package top.srcres258.mobindustry.util

import net.minecraft.client.renderer.LightTexture
import net.minecraft.core.BlockPos
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.level.BlockAndTintGetter
import net.minecraft.world.level.LightLayer

fun LivingEntity.resetForRendering() {
    hurtTime = 0
    yBodyRot = 0F
    yBodyRotO = 0F
    yHeadRot = 0F
    yHeadRotO = 0F
}

fun calculateLightLevel(level: BlockAndTintGetter, pos: BlockPos): Int =
    LightTexture.pack(
        level.getBrightness(LightLayer.BLOCK, pos),
        level.getBrightness(LightLayer.SKY, pos)
    )