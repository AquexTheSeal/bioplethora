package io.github.bioplethora.item;

import io.github.bioplethora.enums.BPEntityClasses;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BioplethoraSpawnEggItem extends SpawnEggItem {

    protected static final List<BioplethoraSpawnEggItem> UNADDED_EGGS = new ArrayList<>();
    private final Lazy<? extends EntityType<?>> entityTypeSupplier;
    private final BPEntityClasses entityClass;

    public BioplethoraSpawnEggItem(final RegistryObject<? extends EntityType<?>> entityTypeSupplier, BPEntityClasses entityClass, Properties properties) {
        super(null, 0xFFFFFFF, 0xFFFFFFF, properties);
        this.entityTypeSupplier = Lazy.of(entityTypeSupplier);
        this.entityClass = entityClass;
        UNADDED_EGGS.add(this);
    }

    public static void initUnaddedEggs() {
        final Map<EntityType<?>, SpawnEggItem> EGGS = ObfuscationReflectionHelper.getPrivateValue(SpawnEggItem.class, null, "field_195987_b");
        DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior() {

            @Override
            public ItemStack execute(IBlockSource source, ItemStack stack) {
                Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
                EntityType<?> entitytype = ((SpawnEggItem) stack.getItem()).getType(stack.getTag());
                entitytype.spawn(source.getLevel(), stack, null, source.getPos().relative(direction),
                        SpawnReason.DISPENSER, direction != Direction.UP, false);
                stack.shrink(1);
                return stack;
            }
        };
        for (final SpawnEggItem egg : UNADDED_EGGS) {
            EGGS.put(egg.getType(null), egg);
            DispenserBlock.registerBehavior(egg, defaultDispenseItemBehavior);
        }
        UNADDED_EGGS.clear();
    }

    @Override
    public EntityType<?> getType(@Nullable final CompoundNBT p_208076_1_) {
        return entityTypeSupplier.get();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.bioplethora." + bpEntityClass() + "_spawn_egg.desc").withStyle(bpClassColor()));
    }

    public String bpEntityClass() {
        if (getEntityClass(BPEntityClasses.ECOHARMLESS)) {
            return "ecoharmless";
        } else if (getEntityClass(BPEntityClasses.PLETHONEUTRAL)) {
            return "plethoneutral";
        } else if (getEntityClass(BPEntityClasses.DANGERUM)) {
            return "dangerum";
        } else if (getEntityClass(BPEntityClasses.HELLSENT)) {
            return "hellsent";
        } else if (getEntityClass(BPEntityClasses.ELDERIA)) {
            return "elderia";
        } else {
            return "none";
        }
    }

    public TextFormatting bpClassColor() {
        if (getEntityClass(BPEntityClasses.ECOHARMLESS)) {
            return TextFormatting.GREEN;
        } else if (getEntityClass(BPEntityClasses.PLETHONEUTRAL)) {
            return TextFormatting.YELLOW;
        } else if (getEntityClass(BPEntityClasses.DANGERUM)) {
            return TextFormatting.RED;
        } else if (getEntityClass(BPEntityClasses.HELLSENT)) {
            return TextFormatting.LIGHT_PURPLE;
        } else if (getEntityClass(BPEntityClasses.ELDERIA)) {
            return TextFormatting.AQUA;
        } else {
            return TextFormatting.WHITE;
        }
    }

    public boolean getEntityClass(BPEntityClasses entityClass) {
        return this.entityClass == entityClass;
    }
}
