package top.srcres258.mobindustry.client

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack

open class ItemRenderer {
    protected val minecraft: Minecraft = Minecraft.getInstance()
    protected val renderer = Renderer()
    val rendererSupplier: () -> BlockEntityWithoutLevelRenderer = { renderer }

    open fun renderByItem(
        stack: ItemStack,
        displayContext: ItemDisplayContext,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
    }

    protected inner class Renderer : BlockEntityWithoutLevelRenderer(
        minecraft.blockEntityRenderDispatcher,
        minecraft.entityModels,
    ) {
        override fun renderByItem(
            stack: ItemStack,
            displayContext: ItemDisplayContext,
            poseStack: PoseStack,
            buffer: MultiBufferSource,
            packedLight: Int,
            packedOverlay: Int
        ) {
            this@ItemRenderer.renderByItem(stack, displayContext, poseStack, buffer, packedLight, packedOverlay)
        }
    }
}