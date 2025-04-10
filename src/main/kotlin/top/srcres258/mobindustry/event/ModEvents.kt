package top.srcres258.mobindustry.event

import net.minecraft.core.component.DataComponents
import net.minecraft.nbt.CompoundTag
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.Mob
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.CustomData
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent
import top.srcres258.mobindustry.MobIndustry
import top.srcres258.mobindustry.component.ModDataComponents
import top.srcres258.mobindustry.component.record.MobEntityRecord
import top.srcres258.mobindustry.item.ModItems

@EventBusSubscriber(modid = MobIndustry.MOD_ID)
object ModEvents {
    @SubscribeEvent
    fun onPlayerInteractEntity(event: PlayerInteractEvent.EntityInteractSpecific) {
        if (!event.level.isClientSide && event.hand == InteractionHand.MAIN_HAND) {
            val player = event.entity
            if (player.getItemInHand(event.hand).isEmpty) {
                val entity = event.target
                if (entity is Mob) {
                    // Set MOB_ENTITY data component.
                    val mobItemStack = ItemStack(ModItems.MOB_ITEM.get())
                    val mobRecord = MobEntityRecord.of(entity)
                    mobItemStack.set(ModDataComponents.MOB_ENTITY.get(), mobRecord)

                    // Set ENTITY_DATA data component.
                    val mobNbt = CompoundTag()
                    entity.save(mobNbt)
                    if (mobNbt.contains("Pos")) {
                        // We don't need the position data.
                        mobNbt.remove("Pos")
                    }
                    val entityData = CustomData.of(mobNbt)
                    mobItemStack.set(DataComponents.ENTITY_DATA, entityData)

                    player.setItemInHand(event.hand, mobItemStack)
                    entity.discard()
                    event.isCanceled = true
                }
            }
        }
    }
}