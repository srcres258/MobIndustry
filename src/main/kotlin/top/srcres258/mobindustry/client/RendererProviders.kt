package top.srcres258.mobindustry.client

import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.entity.EntityRendererProvider

object RendererProviders {
    private val minecraft: Minecraft = Minecraft.getInstance()

    fun createBlockEntityRendererContext(): BlockEntityRendererProvider.Context =
        BlockEntityRendererProvider.Context(
            minecraft.blockEntityRenderDispatcher,
            minecraft.blockRenderer,
            minecraft.itemRenderer,
            minecraft.entityRenderDispatcher,
            minecraft.entityModels,
            minecraft.font
        )

    fun createEntityRendererContext(): EntityRendererProvider.Context =
        EntityRendererProvider.Context(
            minecraft.entityRenderDispatcher,
            minecraft.itemRenderer,
            minecraft.blockRenderer,
            minecraft.gameRenderer.itemInHandRenderer,
            minecraft.resourceManager,
            minecraft.entityModels,
            minecraft.font
        )
}