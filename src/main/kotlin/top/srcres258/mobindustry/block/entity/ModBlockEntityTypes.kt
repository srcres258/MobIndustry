package top.srcres258.mobindustry.block.entity

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.level.block.entity.BlockEntityType
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import top.srcres258.mobindustry.MobIndustry
import top.srcres258.mobindustry.block.ModBlocks
import top.srcres258.mobindustry.block.entity.custom.MobFarmBlockEntity
import top.srcres258.mobindustry.block.entity.renderer.MobFarmBlockEntityRenderer

object ModBlockEntityTypes {
    val BLOCK_ENTITY_TYPES: DeferredRegister<BlockEntityType<*>> =
        DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MobIndustry.MOD_ID)

    val MOB_FARM_BLOCK_ENTITY: DeferredHolder<BlockEntityType<*>, BlockEntityType<MobFarmBlockEntity>> =
        BLOCK_ENTITY_TYPES.register("mob_farm_block_entity") { ->
            BlockEntityType.Builder.of(
                ::MobFarmBlockEntity,
                ModBlocks.MOB_FARM.get()
            ).build(null)
        }

    fun register(eventBus: IEventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus)
    }

    fun registerRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerBlockEntityRenderer(MOB_FARM_BLOCK_ENTITY.get(), ::MobFarmBlockEntityRenderer)
    }
}