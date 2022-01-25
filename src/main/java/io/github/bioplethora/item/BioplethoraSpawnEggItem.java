package io.github.bioplethora.item;

import com.google.common.collect.Maps;
import io.github.bioplethora.registry.BioplethoraEntityClasses;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class BioplethoraSpawnEggItem extends SpawnEggItem {

    private static final Map<EntityType<?>, SpawnEggItem> BY_ID = Maps.newIdentityHashMap();
    protected final Supplier<? extends EntityType<?>> typeGetter;
    private final BioplethoraEntityClasses entityClass;
    private final boolean isBossEgg;

    public BioplethoraSpawnEggItem(Supplier<? extends EntityType<?>> typeIn, BioplethoraEntityClasses entityClass, Properties properties, boolean isBossEgg) {
        super(null, 0xFFFFFFF, 0xFFFFFFF, properties);
        this.typeGetter = typeIn;
        this.entityClass = entityClass;
        this.isBossEgg = isBossEgg;
    }

    public BioplethoraSpawnEggItem(Supplier<? extends EntityType<?>> typeIn, BioplethoraEntityClasses entityClass, Properties properties) {
        this(typeIn, entityClass, properties, false);
    }

    @Override
    public void fillItemCategory(ItemGroup group, NonNullList<ItemStack> items) {
        BY_ID.put(this.getType(null), this);
        super.fillItemCategory(group, items);
    }

    @Override
    public EntityType<?> getType(@Nullable CompoundNBT p_208076_1_) {
        return typeGetter.get();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("item.bioplethora." + bpEntityClass() + "_spawn_egg.desc").withStyle(bpClassColor()));
    }

    public String bpEntityClass() {
        if (this.entityClass == BioplethoraEntityClasses.ECOHARMLESS) {
            return "ecoharmless";
        } else if (this.entityClass == BioplethoraEntityClasses.PLETHONEUTRAL) {
            return "plethoneutral";
        } else if (this.entityClass == BioplethoraEntityClasses.DANGERUM) {
            return "dangerum";
        } else if (this.entityClass == BioplethoraEntityClasses.HELLSENT) {
            return "hellsent";
        } else if (this.entityClass == BioplethoraEntityClasses.ELDERIA) {
            return "elderia";
        } else {
            return "none";
        }
    }

    public TextFormatting bpClassColor() {
        if (this.entityClass == BioplethoraEntityClasses.ECOHARMLESS) {
            return TextFormatting.GREEN;
        } else if (this.entityClass == BioplethoraEntityClasses.PLETHONEUTRAL) {
            return TextFormatting.YELLOW;
        } else if (this.entityClass == BioplethoraEntityClasses.DANGERUM) {
            return TextFormatting.RED;
        } else if (this.entityClass == BioplethoraEntityClasses.HELLSENT) {
            return TextFormatting.LIGHT_PURPLE;
        } else if (this.entityClass == BioplethoraEntityClasses.ELDERIA) {
            return TextFormatting.AQUA;
        } else {
            return TextFormatting.WHITE;
        }
    }

    //unused for now
    public boolean isBossEgg(ItemStack stack) {
        return this.isBossEgg;
    }
}
