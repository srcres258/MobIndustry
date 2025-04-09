package top.srcres258.mobindustry.datagen

import net.minecraft.core.Direction
import net.minecraft.data.PackOutput
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import net.minecraft.world.level.block.state.properties.DirectionProperty
import net.neoforged.neoforge.client.model.generators.BlockStateProvider
import net.neoforged.neoforge.client.model.generators.ConfiguredModel
import net.neoforged.neoforge.client.model.generators.ModelFile
import net.neoforged.neoforge.common.data.ExistingFileHelper
import top.srcres258.mobindustry.MobIndustry
import top.srcres258.mobindustry.block.ModBlocks
import top.srcres258.mobindustry.block.custom.MobFarmBlock

private const val DEFAULT_ANGLE_OFFSET = 180

class ModBlockStateProvider(
    output: PackOutput,
    existingFileHelper: ExistingFileHelper
) : BlockStateProvider(output, MobIndustry.MOD_ID, existingFileHelper) {
    override fun registerStatesAndModels() {
        horizontalFacingBlockWithItem(
            ModBlocks.MOB_FARM.get(),
            models().getExistingFile(modLoc("block/mob_farm")),
            MobFarmBlock.FACING
        )
        simpleBlockWithItem(ModBlocks.MOB_BREEDER.get(),
            models().getExistingFile(modLoc("block/mob_breeder")))
    }

    private fun blockWithItem(block: Block, model: ModelFile = cubeAll(block)) {
        simpleBlockWithItem(block, model)
    }

    private fun horizontalFacingBlockWithItem(
        block: Block,
        model: ModelFile,
        facingProperty: DirectionProperty = BlockStateProperties.HORIZONTAL_FACING
    ) {
        getVariantBuilder(ModBlocks.MOB_FARM.get())
            .forAllStates { state ->
                configuredModelsWithRotationY(model, state.getValue(facingProperty))
            }
        simpleBlockItem(block, model)
    }
}

private fun configuredModelsWithRotationY(model: ModelFile, direction: Direction): Array<ConfiguredModel> =
    ConfiguredModel.builder()
        .modelFile(model)
        .rotationY((direction.toYRot().toInt() + DEFAULT_ANGLE_OFFSET) % 360)
        .build()