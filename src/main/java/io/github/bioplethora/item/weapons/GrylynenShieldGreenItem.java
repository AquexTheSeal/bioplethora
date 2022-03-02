package io.github.bioplethora.item.weapons;

public class GrylynenShieldGreenItem extends GrylynenShieldBaseItem {

    public GrylynenShieldGreenItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getCooldownReduction() {
        return 60;
    }

    @Override
    public int getArmorBonus() {
        return 2;
    }
}
