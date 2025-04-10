package top.srcres258.mobindustry.datagen

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.data.PackOutput
import net.minecraft.world.item.Item
import net.neoforged.neoforge.client.model.generators.ItemModelProvider
import net.neoforged.neoforge.client.model.generators.ModelFile
import net.neoforged.neoforge.common.data.ExistingFileHelper
import top.srcres258.mobindustry.MobIndustry

class ModItemModelProvider(
    output: PackOutput,
    existingFileHelper: ExistingFileHelper
) : ItemModelProvider(output, MobIndustry.MOD_ID, existingFileHelper) {
    override fun registerModels() {
    }

    private fun itemWithoutTexture(item: Item) {
        val key = BuiltInRegistries.ITEM.getKey(item)
        getBuilder(key.toString())
            .parent(ModelFile.UncheckedModelFile("item/generated"))
    }
}