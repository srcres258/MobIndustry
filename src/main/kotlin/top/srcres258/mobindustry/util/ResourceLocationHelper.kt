package top.srcres258.mobindustry.util

import net.minecraft.resources.ResourceLocation

fun isResourceLocationStringValid(location: String): Boolean =
    ResourceLocation.tryParse(location) != null