package top.srcres258.mobindustry.datagen

import net.minecraft.data.PackOutput
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.common.data.ExistingFileHelper
import top.srcres258.mobindustry.MobIndustry

class ModItemModelProvider(
    output: PackOutput,
    existingFileHelper: ExistingFileHelper
) : ItemModelProvider(output, MobIndustry.MOD_ID, existingFileHelper) {
    override fun registerModels() {
    }
}