package top.srcres258.mobindustry

import net.neoforged.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

@Mod(MobIndustry.MOD_ID)
object MobIndustry {
    const val MOD_ID = "mobindustry"

    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger(MOD_ID)

    init {
    }
}