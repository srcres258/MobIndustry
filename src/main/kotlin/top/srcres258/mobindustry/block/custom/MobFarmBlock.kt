package top.srcres258.mobindustry.block.custom

import com.mojang.serialization.MapCodec
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionHand
import net.minecraft.world.ItemInteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.minecraft.world.phys.BlockHitResult
import top.srcres258.mobindustry.block.entity.custom.MobFarmBlockEntity
import top.srcres258.mobindustry.item.custom.MobItem

class MobFarmBlock(properties: Properties) : BaseEntityBlock(properties) {
    companion object {
        val CODEC: MapCodec<MobFarmBlock> = simpleCodec(::MobFarmBlock)

        val FACING: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
    }

    init {
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH))
    }

    override fun codec(): MapCodec<out BaseEntityBlock> = CODEC

    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity =
        MobFarmBlockEntity(pos, state)

    // NOTE to override this method and return this value,
    // otherwise this block will **NOT** be rendered by the game and consequently
    // become invisible!!!
    override fun getRenderShape(state: BlockState): RenderShape = RenderShape.MODEL

    override fun rotate(state: BlockState, rotation: Rotation): BlockState =
        state.setValue(FACING, rotation.rotate(state.getValue(FACING)))

    override fun mirror(state: BlockState, mirror: Mirror): BlockState =
        state.rotate(mirror.getRotation(state.getValue(FACING)))

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(FACING)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState =
        defaultBlockState().setValue(FACING, context.horizontalDirection.opposite)

    override fun onRemove(
        state: BlockState,
        level: Level,
        pos: BlockPos,
        newState: BlockState,
        movedByPiston: Boolean
    ) {
        if (state.block != newState.block) {
            val blockEntity = level.getBlockEntity(pos)
            if (blockEntity is MobFarmBlockEntity) {
                blockEntity.drops()
                level.updateNeighbourForOutputSignal(pos, this)
            }

            // From NeoForge's official documentation:
            //
            // To make sure that caches can correctly update their stored capability, modders must call
            // level.invalidateCapabilities(pos) whenever a capability changes, appears, or disappears.
            level.invalidateCapabilities(pos)
        }

        super.onRemove(state, level, pos, newState, movedByPiston)
    }

    override fun useItemOn(
        stack: ItemStack,
        state: BlockState,
        level: Level,
        pos: BlockPos,
        player: Player,
        hand: InteractionHand,
        hitResult: BlockHitResult
    ): ItemInteractionResult {
        val blockEntity = level.getBlockEntity(pos)
        if (blockEntity is MobFarmBlockEntity) {
            val mobInventory = blockEntity.mobInventory
            if (mobInventory.getStackInSlot(0).isEmpty && !stack.isEmpty && stack.item is MobItem) {
                // If the mob inventory is empty and the player is holding a mob item,
                // add the item to the mob inventory and decrease the player's stack size.
                mobInventory.setStackInSlot(0, stack.split(1))
                if (stack.isEmpty) {
                    // If the stack went empty, set the player's corresponding inventory slot to the
                    // empty item stack.
                    player.setItemInHand(hand, ItemStack.EMPTY)
                }
                blockEntity.setChanged()
                level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1F, 2F)
            } else if (stack.isEmpty) {
                // If the player is not holding an item,
                // remove the first item form the mob inventory and give it to the player.
                val stackExtracted = mobInventory.extractItem(0, 1, false)
                if (!stackExtracted.isEmpty) {
                    player.setItemInHand(hand, stackExtracted)
                    blockEntity.clearContents()
                    blockEntity.setChanged()
                    level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1F, 1F)
                }
            }

            return ItemInteractionResult.SUCCESS
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
    }

    override fun <T : BlockEntity?> getTicker(
        level: Level,
        state: BlockState,
        blockEntityType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return super.getTicker(level, state, blockEntityType)
    }
}