package top.srcres258.mobindustry.component.record

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import io.netty.buffer.ByteBuf
import net.minecraft.core.component.DataComponents
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.nbt.Tag
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.Mob
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.component.CustomData
import net.minecraft.world.level.Level
import top.srcres258.mobindustry.component.ModDataComponents
import top.srcres258.mobindustry.item.custom.MobItem
import top.srcres258.mobindustry.util.isResourceLocationStringValid
import java.lang.ref.WeakReference

private val DEFAULT_ENTITY_TYPE: EntityType<*> = EntityType.PIG

private val EntityType<*>.id: ResourceLocation
    get() = BuiltInRegistries.ENTITY_TYPE.getKey(this)

private const val MOB_ENTITY_TAG_KEY = "mob_entity"

private fun isEntityTypeExisting(entityTypeId: ResourceLocation): Boolean =
    BuiltInRegistries.ENTITY_TYPE.let { entityTypeReg ->
        entityTypeReg.get(entityTypeId) != entityTypeReg.get(null as ResourceLocation?)
    }

data class MobEntityRecord(val entityId: String) {
    companion object {
        val CODEC: Codec<MobEntityRecord> = RecordCodecBuilder.create { instance ->
            instance.group(
                Codec.STRING.fieldOf("entityId").forGetter { it.entityId }
            ).apply(instance, ::MobEntityRecord)
        }
        val STREAM_CODEC: StreamCodec<ByteBuf, MobEntityRecord> = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, { it.entityId },
            ::MobEntityRecord
        )

        fun default(): MobEntityRecord = MobEntityRecord(DEFAULT_ENTITY_TYPE.id.toString())

        fun of(entity: Mob): MobEntityRecord = MobEntityRecord(
            entity.type.id.toString()
        )

        fun get(stack: ItemStack): MobEntityRecord? {
            if (stack.item !is MobItem) {
                throw IllegalArgumentException("Tried to set Mob Entity Data Component to non-MobItem: " +
                        stack.hoverName)
            }
            convert(stack)
            return stack.get(ModDataComponents.MOB_ENTITY.get())
        }

        fun getOrCreate(stack: ItemStack): MobEntityRecord = get(stack) ?: default()

        private fun convert(stack: ItemStack) {
            if (stack.item !is MobItem) {
                return
            }
            if (stack.has(ModDataComponents.MOB_ENTITY.get())) {
                return
            }
            val customData = stack.get(DataComponents.CUSTOM_DATA)
            if (customData == null) {
                setEmptyMobEntityDataComponent(stack)
                return
            }

            val customTag = customData.copyTag()
            if (!customTag.contains(MOB_ENTITY_TAG_KEY, Tag.TAG_STRING.toInt())) {
                setEmptyMobEntityDataComponent(stack)
                return
            }
            val mobEntityId = customTag.getString(MOB_ENTITY_TAG_KEY)
            customTag.remove(MOB_ENTITY_TAG_KEY)
            if (customTag.isEmpty) {
                stack.remove(DataComponents.CUSTOM_DATA)
            } else {
                stack.set(DataComponents.CUSTOM_DATA, CustomData.of(customTag))
            }
            if (!isResourceLocationStringValid(mobEntityId)) {
                setEmptyMobEntityDataComponent(stack)
                return
            }
            if (!isEntityTypeExisting(ResourceLocation.parse(mobEntityId))) {
                setEmptyMobEntityDataComponent(stack)
                return
            }
            val mobEntityRecord = MobEntityRecord(mobEntityId)
            stack.set(ModDataComponents.MOB_ENTITY.get(), mobEntityRecord)
        }

        private fun setEmptyMobEntityDataComponent(stack: ItemStack) {
            stack.set(ModDataComponents.MOB_ENTITY.get(), default())
        }

        fun getCacheMob(stack: ItemStack, level: Level): Mob =
            getOrCreate(stack).getCacheMob(level)
    }

    private var mobCache = WeakReference<Mob>(null)

    fun getCacheMob(level: Level): Mob =
        mobCache.get() ?: run {
            val mob = createMob(level, null)
            mobCache = WeakReference(mob)
            mob
        }

    fun createMob(level: Level, stack: ItemStack?): Mob {
        val entityTypeResLoc = ResourceLocation.parse(entityId)
        val entityType = BuiltInRegistries.ENTITY_TYPE.get(entityTypeResLoc)
        val entity = entityType.create(level) ?: throw IllegalStateException(
            "Failed to create an entity of entity type $entityId, this should not happen."
        )
        val mobEntity = entity as? Mob ?: throw IllegalStateException(
            "The entity type $entityId should be a Mob for MobEntityRecord, but it is not."
        )
        if (stack != null) {
            val customName = stack.get(DataComponents.CUSTOM_NAME)
            if (customName != null) {
                mobEntity.customName = customName
            }
        }
        mobEntity.hurtTime = 0
        mobEntity.yHeadRot = 0F
        mobEntity.yHeadRotO = 0F
        return mobEntity
    }

    val entityType: EntityType<Mob>
        get() = BuiltInRegistries.ENTITY_TYPE.get(ResourceLocation.parse(entityId)) as EntityType<Mob>
}
