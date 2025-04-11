package top.srcres258.mobindustry.block.entity.custom

import net.minecraft.core.BlockPos
import net.minecraft.core.HolderLookup
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.Tag
import net.minecraft.network.protocol.Packet
import net.minecraft.network.protocol.game.ClientGamePacketListener
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.state.BlockState
import net.neoforged.neoforge.items.ItemStackHandler
import top.srcres258.mobindustry.block.entity.ITickable
import top.srcres258.mobindustry.block.entity.ModBlockEntityTypes
import top.srcres258.mobindustry.util.IProgressAccessor
import top.srcres258.mobindustry.util.dropItemHandler

private const val DEFAULT_MAX_PROGRESS = 200

class MobFarmBlockEntity(
    pos: BlockPos,
    blockState: BlockState
) : BlockEntity(ModBlockEntityTypes.MOB_FARM_BLOCK_ENTITY.get(), pos, blockState), ITickable, IProgressAccessor {
    val mobInventory: ItemStackHandler = object : ItemStackHandler(1) {
        override fun getStackLimit(slot: Int, stack: ItemStack): Int = 1
    }
    override var progress: Int = 0
    override var maxProgress: Int = DEFAULT_MAX_PROGRESS

    override fun saveAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.saveAdditional(tag, registries)

        tag.put("mob_inventory", mobInventory.serializeNBT(registries))
        tag.putInt("progress", progress)
        tag.putInt("max_progress", maxProgress)
    }

    override fun loadAdditional(tag: CompoundTag, registries: HolderLookup.Provider) {
        super.loadAdditional(tag, registries)

        mobInventory.deserializeNBT(registries, tag.getCompound("mob_inventory"))
        progress = tag.getInt("progress")
        maxProgress = if (tag.contains("max_progress", Tag.TAG_ANY_NUMERIC.toInt())) {
            tag.getInt("max_progress")
        } else {
            DEFAULT_MAX_PROGRESS
        }
    }

    override fun tick(level: Level, blockPos: BlockPos, blockState: BlockState) {
    }

    fun drops() {
        val level = level
        if (level != null) {
            dropItemHandler(level, worldPosition, mobInventory)
        }
    }

    fun clearContents() {
        mobInventory.setStackInSlot(0, ItemStack.EMPTY)
    }

    override fun getUpdatePacket(): Packet<ClientGamePacketListener> =
        ClientboundBlockEntityDataPacket.create(this)

    override fun getUpdateTag(registries: HolderLookup.Provider): CompoundTag =
        saveWithoutMetadata(registries)
}