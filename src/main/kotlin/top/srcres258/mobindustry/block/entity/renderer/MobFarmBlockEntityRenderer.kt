package top.srcres258.mobindustry.block.entity.renderer

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import thedarkcolour.kotlinforforge.neoforge.forge.use
import top.srcres258.mobindustry.block.custom.MobFarmBlock
import top.srcres258.mobindustry.block.entity.custom.MobFarmBlockEntity
import top.srcres258.mobindustry.component.record.MobEntityRecord
import top.srcres258.mobindustry.item.custom.MobItem
import top.srcres258.mobindustry.util.clone

private const val SCALING_FACTOR = 0.4F

class MobFarmBlockEntityRenderer(
    private val context: BlockEntityRendererProvider.Context
) : BlockEntityRenderer<MobFarmBlockEntity> {
    override fun render(
        blockEntity: MobFarmBlockEntity,
        partialTick: Float,
        poseStack: PoseStack,
        bufferSource: MultiBufferSource,
        packedLight: Int,
        packedOverlay: Int
    ) {
        val entityRenderer = context.entityRenderer
        val stack = blockEntity.mobInventory.getStackInSlot(0)

        val level = blockEntity.level
        if (level != null && !stack.isEmpty) {
            val item = stack.item
            if (item is MobItem) {
                val mob = MobEntityRecord.getCacheMob(stack, level).clone()
                val state = level.getBlockState(blockEntity.blockPos)
                if (state.hasProperty(MobFarmBlock.FACING)) {
                    val direction = state.getValue(MobFarmBlock.FACING)
                    val yRot = direction.toYRot()
                    mob.yBodyRot = yRot
                    mob.yBodyRotO = yRot
                    mob.yHeadRot = yRot
                    mob.yHeadRotO = yRot
                }

                poseStack.use {
                    val scalingFactor = SCALING_FACTOR
                    poseStack.translate(0.5F, 0.0F, 0.5F)
                    poseStack.scale(scalingFactor, scalingFactor, scalingFactor)
                    poseStack.translate(0.0F, 0.15F / scalingFactor, 0.0F)

                    entityRenderer.render(
                        mob,
                        0.0,
                        0.0,
                        0.0,
                        0F,
                        1F,
                        poseStack,
                        bufferSource,
                        packedLight
                    )
                }
            }
        }
    }
}