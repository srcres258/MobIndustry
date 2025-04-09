package top.srcres258.mobindustry.datagen

import net.minecraft.core.HolderLookup
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider
import top.srcres258.mobindustry.MobIndustry
import java.util.concurrent.CompletableFuture

class ModGlobalLootModifierProvider(
    output: PackOutput,
    registries: CompletableFuture<HolderLookup.Provider>
) : GlobalLootModifierProvider(output, registries, MobIndustry.MOD_ID) {
    override fun start() {
    }
}