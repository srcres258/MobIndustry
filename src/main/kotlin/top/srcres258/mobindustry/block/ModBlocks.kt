package top.srcres258.mobindustry.block

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister
import top.srcres258.mobindustry.MobIndustry
import top.srcres258.mobindustry.block.custom.MobBreederBlock
import top.srcres258.mobindustry.block.custom.MobFarmBlock
import top.srcres258.mobindustry.item.ModItems
import top.srcres258.mobindustry.util.copy

private val WOODEN_FUNCTIONAL_BLOCK_PROPERTIES: BlockBehaviour.Properties =
    BlockBehaviour.Properties.of()
        .strength(1.0F)
        .sound(SoundType.WOOD)
        .noOcclusion()

object ModBlocks {
    val BLOCKS: DeferredRegister.Blocks = DeferredRegister.createBlocks(MobIndustry.MOD_ID)

    val MOB_FARM: DeferredBlock<Block> = registerBlockWithItem("mob_farm") {
        MobFarmBlock(WOODEN_FUNCTIONAL_BLOCK_PROPERTIES.copy())
    }
    val MOB_BREEDER: DeferredBlock<Block> = registerBlockWithItem("mob_breeder") {
        MobBreederBlock(WOODEN_FUNCTIONAL_BLOCK_PROPERTIES.copy())
    }

    private fun <T : Block> registerBlockWithItem(name: String, block: () -> T) =
        BLOCKS.register(name, block).also { defBlock ->
            registerBlockItem(name, defBlock)
        }

    private fun <T : Block> registerBlockItem(name: String, block: DeferredBlock<T>) {
        ModItems.ITEMS.register(name) { ->
            BlockItem(block.get(), Item.Properties())
        }
    }

    fun register(eventBus: IEventBus) {
        BLOCKS.register(eventBus)
    }
}