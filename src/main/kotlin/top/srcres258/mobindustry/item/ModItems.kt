package top.srcres258.mobindustry.item

import net.minecraft.world.item.Item
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister
import top.srcres258.mobindustry.MobIndustry
import top.srcres258.mobindustry.item.custom.MobItem

object ModItems {
    val ITEMS: DeferredRegister.Items = DeferredRegister.createItems(MobIndustry.MOD_ID)

    val MOB_ITEM: DeferredItem<Item> = registerItem("mob_item") {
        MobItem(Item.Properties())
    }

    private fun <T : Item> registerItem(name: String, item: () -> T) =
        ITEMS.register(name) { -> item() }

    fun register(eventBus: IEventBus) {
        ITEMS.register(eventBus)
    }

    fun registerClientExtensions(event: RegisterClientExtensionsEvent) {
        event.registerItem(MobItem.ClientItemExtensions, MOB_ITEM)
    }
}