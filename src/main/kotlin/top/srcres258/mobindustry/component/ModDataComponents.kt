package top.srcres258.mobindustry.component

import net.minecraft.core.component.DataComponentType
import net.minecraft.core.registries.Registries
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import top.srcres258.mobindustry.MobIndustry
import top.srcres258.mobindustry.component.record.MobEntityRecord

object ModDataComponents {
    val DATA_COMPONENTS: DeferredRegister.DataComponents =
        DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MobIndustry.MOD_ID)

    val MOB_ENTITY: DeferredHolder<DataComponentType<*>, DataComponentType<MobEntityRecord>> =
        DATA_COMPONENTS.registerComponentType("mob_entity") { builder ->
            builder.persistent(MobEntityRecord.CODEC)
                .networkSynchronized(MobEntityRecord.STREAM_CODEC)
        }

    fun register(eventBus: IEventBus) {
        DATA_COMPONENTS.register(eventBus)
    }
}