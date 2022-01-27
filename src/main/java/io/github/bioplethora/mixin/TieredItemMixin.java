package io.github.bioplethora.mixin;

//@Mixin(TieredItem.class)
public class TieredItemMixin /*extends Item*/ {

    /*public int ecoLevel = 0;
    public int plethoLevel = 0;
    public int danLevel = 0;
    public int hellLevel = 0;
    public int eldLevel = 0;

    public TieredItemMixin(Properties properties) {
        super(properties);
    }

    public boolean hurtEnemy(ItemStack stack, LivingEntity entity, LivingEntity source) {
        super.hurtEnemy(stack, entity, source);

        if ((EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ECOHARMLESS.get(), source) != 0) && (entity instanceof IBioplethoraEntityClass)) {
            this.EnchEcoharmless(stack, entity, source);
        }
        if ((EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.PLETHONEUTRAL.get(), source) != 0) && (entity instanceof IBioplethoraEntityClass)) {
            this.EnchPlethoneutral(stack, entity, source);
        }
        if ((EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.DANGERUM.get(), source) != 0) && (entity instanceof IBioplethoraEntityClass)) {
            this.EnchDangerum(stack, entity, source);
        }
        if ((EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.HELLSENT.get(), source) != 0) && (entity instanceof IBioplethoraEntityClass)) {
            this.EnchHellsent(stack, entity, source);
        }
        if ((EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ELDERIA.get(), source) != 0) && (entity instanceof IBioplethoraEntityClass)) {
            this.EnchElderia(stack, entity, source);
        }

        return true;
    }

    public void EnchEcoharmless(ItemStack stack, LivingEntity entity, LivingEntity source) {
        if (((IBioplethoraEntityClass) entity).getBioplethoraClass() == BioplethoraEntityClasses.ECOHARMLESS) {
            entity.hurt(BioplethoraDamageSources.antibio(source, source), EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ECOHARMLESS.get(), source));

            ((PlayerEntity) source).displayClientMessage(new StringTextComponent("Antibio-Ecoharmless Level: "
                    + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ECOHARMLESS.get(), source)), (false));
        }
    }
    public void EnchPlethoneutral(ItemStack stack, LivingEntity entity, LivingEntity source) {
        if (((IBioplethoraEntityClass) entity).getBioplethoraClass() == BioplethoraEntityClasses.PLETHONEUTRAL) {
            entity.hurt(BioplethoraDamageSources.antibio(source, source), EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.PLETHONEUTRAL.get(), source));

            ((PlayerEntity) source).displayClientMessage(new StringTextComponent("Antibio-Plethoneutral Level: "
                    + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.PLETHONEUTRAL.get(), source)), (false));
        }
    }
    public void EnchDangerum(ItemStack stack, LivingEntity entity, LivingEntity source) {
        if (((IBioplethoraEntityClass) entity).getBioplethoraClass() == BioplethoraEntityClasses.DANGERUM) {
            entity.hurt(BioplethoraDamageSources.antibio(source, source), EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.DANGERUM.get(), source));

            ((PlayerEntity) source).displayClientMessage(new StringTextComponent("Antibio-Dangerum Level: "
                    + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.DANGERUM.get(), source)), (false));
        }
    }
    public void EnchHellsent(ItemStack stack, LivingEntity entity, LivingEntity source) {
        if (((IBioplethoraEntityClass) entity).getBioplethoraClass() == BioplethoraEntityClasses.HELLSENT) {
            entity.hurt(BioplethoraDamageSources.antibio(source, source), EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.HELLSENT.get(), source));

            ((PlayerEntity) source).displayClientMessage(new StringTextComponent("Antibio-Hellsent Level: "
                    + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.HELLSENT.get(), source)), (false));
        }
    }

    public void EnchElderia(ItemStack stack, LivingEntity entity, LivingEntity source) {
        if (((IBioplethoraEntityClass) entity).getBioplethoraClass() == BioplethoraEntityClasses.ELDERIA) {
            entity.hurt(DamageSource.playerAttack((PlayerEntity) source), EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ELDERIA.get(), source));

            ((PlayerEntity) source).displayClientMessage(new StringTextComponent("Antibio-Elderia Level: "
                    + EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ELDERIA.get(), source)), (false));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        if ((ecoLevel != 0) || (plethoLevel != 0) || (danLevel != 0) || (hellLevel != 0) || (eldLevel != 0)) {
            tooltip.add(new TranslationTextComponent("item.bioplethora.bioplethora_enchantment_bonus.desc").withStyle(TextFormatting.LIGHT_PURPLE));
        }

        if (ecoLevel != 0) {
            tooltip.add(new TranslationTextComponent("+" + ecoLevel + " " + "tooltip.bioplethora.enchantment_ecoharmless_bonus.text").withStyle(TextFormatting.GREEN));
        }
        if (plethoLevel != 0) {
            tooltip.add(new TranslationTextComponent("+" + plethoLevel + " " + "tooltip.bioplethora.enchantment_plethoneutral_bonus.text").withStyle(TextFormatting.GREEN));
        }
        if (danLevel != 0) {
            tooltip.add(new TranslationTextComponent("+" + danLevel + " " + "tooltip.bioplethora.enchantment_dangerum_bonus.text").withStyle(TextFormatting.GREEN));
        }
        if (hellLevel != 0) {
            tooltip.add(new TranslationTextComponent("+" + hellLevel + " " + "tooltip.bioplethora.enchantment_hellsent_bonus.text").withStyle(TextFormatting.GREEN));
        }
        if (eldLevel != 0) {
            tooltip.add(new TranslationTextComponent("+" + eldLevel + " " + "tooltip.bioplethora.enchantment_elderia_bonus.text").withStyle(TextFormatting.GREEN));
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int num, boolean bol) {
        super.inventoryTick(stack, world, entity, num, bol);

        PlayerEntity player = (PlayerEntity) entity;

        ecoLevel = Math.max(EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ECOHARMLESS.get(), player), 0);
        plethoLevel = Math.max(EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.PLETHONEUTRAL.get(), player), 0);
        danLevel = Math.max(EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.DANGERUM.get(), player), 0);
        hellLevel = Math.max(EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.HELLSENT.get(), player), 0);
        eldLevel = Math.max(EnchantmentHelper.getEnchantmentLevel(BioplethoraEnchantments.ELDERIA.get(), player), 0);
    }*/
}
