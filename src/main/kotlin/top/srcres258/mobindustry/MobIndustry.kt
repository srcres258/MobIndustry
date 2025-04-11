package top.srcres258.mobindustry

import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.neoforge.client.event.EntityRenderersEvent
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import top.srcres258.mobindustry.block.ModBlocks
import top.srcres258.mobindustry.block.entity.ModBlockEntityTypes
import top.srcres258.mobindustry.component.ModDataComponents
import top.srcres258.mobindustry.item.ModCreativeModeTabs
import top.srcres258.mobindustry.item.ModItems

@Mod(MobIndustry.MOD_ID)
object MobIndustry {
    const val MOD_ID = "mobindustry"

    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    init {
        LOGGER.log(Level.INFO, "Loading $MOD_ID...")

        ModCreativeModeTabs.register(MOD_BUS)
        ModBlocks.register(MOD_BUS)
        ModItems.register(MOD_BUS)
        ModDataComponents.register(MOD_BUS)
        ModBlockEntityTypes.register(MOD_BUS)
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
    object ClientModEvents {
        @SubscribeEvent
        fun onRegisterClientExtensions(event: RegisterClientExtensionsEvent) {
            ModItems.registerClientExtensions(event)
        }

        @SubscribeEvent
        fun onRegisterRenderers(event: EntityRenderersEvent.RegisterRenderers) {
            ModBlockEntityTypes.registerRenderers(event)
        }
    }
}