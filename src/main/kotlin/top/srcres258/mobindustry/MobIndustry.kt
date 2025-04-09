package top.srcres258.mobindustry

import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import top.srcres258.mobindustry.block.ModBlocks
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
    }
}