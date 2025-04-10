package top.srcres258.mobindustry.item.custom.renderer

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.model.EntityModel
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.entity.EntityRenderers
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import thedarkcolour.kotlinforforge.neoforge.forge.use
import top.srcres258.mobindustry.client.ItemRenderer
import top.srcres258.mobindustry.client.RendererProviders
import top.srcres258.mobindustry.component.record.MobEntityRecord

class MobItemRenderer : ItemRenderer() {
    private val mobRendererMap: MutableMap<EntityType<Mob>, MobRenderer<Mob, EntityModel<Mob>>> =
        mutableMapOf()

    override fun renderByItem(
        stack: ItemStack,
        displayContext: ItemDisplayContext,
        poseStack: PoseStack,
        buffer: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        val level = minecraft.level
        if (level != null) {
            val cacheMob = MobEntityRecord.getCacheMob(stack, level)

            run {
                var mobRenderer = mobRendererMap[cacheMob.type]
                if (mobRenderer == null) {
                    val renderContext = RendererProviders.createEntityRendererContext()
                    val entityRendererMap = EntityRenderers.createEntityRenderers(renderContext)
                    val entityRenderer = entityRendererMap[cacheMob.type]
                    if (entityRenderer != null) {
                        mobRenderer = entityRenderer as? MobRenderer<Mob, EntityModel<Mob>>
                            ?: throw IllegalStateException("The EntityRenderer for ${cacheMob.type.description} " +
                                    "is not a MobRenderer.")
                        mobRendererMap[cacheMob.type as EntityType<Mob>] = mobRenderer
                    }
                }
            }

            run {
                val mobRenderer = mobRendererMap[cacheMob.type]
                if (mobRenderer != null) {
                    poseStack.use {
                        poseStack.scale(1.5F, 1.5F, 1.5F)
                        mobRenderer.render(cacheMob, 0F, 1F, poseStack, buffer, packedLight)
                    }
                    return
                }
            }
        }

        // Render missing texture if no mob was found.
        val missingStack = ItemStack(Items.BARRIER)
        minecraft.itemRenderer.render(
            missingStack,
            displayContext,
            false,
            poseStack,
            buffer,
            packedLight,
            packedOverlay,
            minecraft.modelManager.missingModel
        )
    }
}