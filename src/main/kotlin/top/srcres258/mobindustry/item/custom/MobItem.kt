package top.srcres258.mobindustry.item.custom

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer
import net.minecraft.core.Direction
import net.minecraft.core.component.DataComponents
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.MobSpawnType
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.Spawner
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.gameevent.GameEvent
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions
import top.srcres258.mobindustry.component.record.MobEntityRecord
import top.srcres258.mobindustry.item.custom.renderer.MobItemRenderer
import top.srcres258.mobindustry.util.createDoubleList

class MobItem(properties: Properties) : Item(properties) {
    object ClientItemExtensions : IClientItemExtensions {
        private val renderer = MobItemRenderer()

        override fun getCustomRenderer(): BlockEntityWithoutLevelRenderer = renderer.rendererSupplier()
    }

    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level
        if (level is ServerLevel) {
            val itemStack = context.itemInHand
            val blockPos = context.clickedPos
            val direction = context.clickedFace
            val blockState = level.getBlockState(blockPos)

            val itemStackRecord = MobEntityRecord.getOrCreate(itemStack)
            val itemStackEntityType = itemStackRecord.entityType
            if (level.getBlockEntity(blockPos) is Spawner) {
                val spawner = level.getBlockEntity(blockPos) as Spawner
                spawner.setEntityId(itemStackEntityType, level.random)
                level.sendBlockUpdated(blockPos, blockState, blockState, Block.UPDATE_ALL)
                level.gameEvent(context.player, GameEvent.BLOCK_CHANGE, blockPos)
                itemStack.shrink(1)
            } else {
                val targetBlockPos = if (blockState.getCollisionShape(level, blockPos).isEmpty) {
                    blockPos
                } else {
                    blockPos.relative(direction)
                }

                val spawnedMob = itemStackEntityType.spawn(
                    level,
                    itemStack,
                    context.player,
                    targetBlockPos,
                    MobSpawnType.SPAWN_EGG,
                    true,
                    blockPos != targetBlockPos && direction == Direction.UP
                )
                if (spawnedMob != null) {
                    val entityData = itemStack.get(DataComponents.ENTITY_DATA)
                    if (entityData != null && !entityData.isEmpty) {
                        val nbt = entityData.copyTag()
                        if (nbt.contains("Pos")) {
                            // Discard the position data since we want to spawn the mob right at where the player
                            // has clicked.
                            nbt.remove("Pos")
                        }
                        nbt.put("Pos", createDoubleList(
                            targetBlockPos.x.toDouble() + 0.5,
                            targetBlockPos.y.toDouble(),
                            targetBlockPos.z.toDouble() + 0.5
                        ))
                        spawnedMob.load(nbt)
                    }

                    itemStack.shrink(1)
                    level.gameEvent(context.player, GameEvent.ENTITY_PLACE, blockPos)
                }
            }

            return InteractionResult.CONSUME
        } else {
            return InteractionResult.SUCCESS
        }
    }

    override fun getName(stack: ItemStack): Component {
        val record = MobEntityRecord.get(stack)
        return if (record == null) {
            super.getName(stack)
        } else {
            Component.translatable("${descriptionId}.arguments", record.entityType.description.string)
        }
    }
}