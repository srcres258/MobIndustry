package top.srcres258.mobindustry.datagen

import net.minecraft.core.HolderLookup
import net.minecraft.core.RegistrySetBuilder
import net.minecraft.data.PackOutput
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider
import top.srcres258.mobindustry.MobIndustry
import java.util.concurrent.CompletableFuture

class ModDatapackProvider(
    output: PackOutput,
    registries: CompletableFuture<HolderLookup.Provider>
) : DatapackBuiltinEntriesProvider(output, registries, BUILDER, setOf(MobIndustry.MOD_ID)) {
    companion object {
        val BUILDER: RegistrySetBuilder = RegistrySetBuilder()
    }
}