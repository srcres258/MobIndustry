package top.srcres258.mobindustry.item

import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import top.srcres258.mobindustry.MobIndustry
import top.srcres258.mobindustry.block.ModBlocks

object ModCreativeModeTabs {
    val CREATIVE_MODE_TABS: DeferredRegister<CreativeModeTab> =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MobIndustry.MOD_ID)

    val MOB_INDUSTRY_TAB: DeferredHolder<CreativeModeTab, CreativeModeTab> = CREATIVE_MODE_TABS.register("mob_industry_tab") { ->
        CreativeModeTab.builder()
            .title(Component.translatable("creativetab.mobindustry.mob_industry_tab"))
            .icon { ItemStack(ModBlocks.MOB_FARM.get()) }
            .displayItems { parameters, output ->
                output.accept(ModBlocks.MOB_FARM)
            }
            .build()
    }

    fun register(eventBus: IEventBus) {
        CREATIVE_MODE_TABS.register(eventBus)
    }
}