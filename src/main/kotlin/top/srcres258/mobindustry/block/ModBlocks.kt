package top.srcres258.mobindustry.block

import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import top.srcres258.mobindustry.MobIndustry

object ModBlocks {
    val BLOCKS: DeferredRegister.Blocks = DeferredRegister.createBlocks(MobIndustry.MOD_ID)

    fun register(eventBus: IEventBus) {
        BLOCKS.register(eventBus)
    }
}