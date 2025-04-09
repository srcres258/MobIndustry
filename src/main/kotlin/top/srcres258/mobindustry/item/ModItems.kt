package top.srcres258.mobindustry.item

import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import top.srcres258.mobindustry.MobIndustry

object ModItems {
    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(MobIndustry.MOD_ID)

    fun register(eventBus: IEventBus) {
        ITEMS.register(eventBus)
    }
}