package top.srcres258.mobindustry.datagen

import net.minecraft.data.PackOutput
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import top.srcres258.mobindustry.MobIndustry

class ModBlockStateProvider(
    output: PackOutput,
    existingFileHelper: ExistingFileHelper
) : BlockStateProvider(output, MobIndustry.MOD_ID, existingFileHelper) {
    override fun registerStatesAndModels() {
    }
}