package io.github.bioplethora.enums;

import io.github.bioplethora.entity.others.BPEffectEntity;

public enum BPEffectTypes {

    // Weapons
    FLAMING_SLASH("infernal_quarterstaff_slash", Model.FLAT, Animation.SPIN),
    SOUL_PURGE("infernal_quarterstaff_soul_purge", Model.FLAT, Animation.SPIN),
    AIR_JUMP("infernal_quarterstaff_air_jump", Model.FLAT, Animation.SPIN),
    FLAMING_SNIPE("infernal_quarterstaff_flaming_snipe", Model.FLAT, Animation.SPIN),
    AERIAL_SHOCKWAVE("crephoxl_hammer_shockwave", Model.FLAT, Animation.SMASH),

    // Entities
    MYLIOTHAN_ROAR("myliothan_roar", Model.FLAT, Animation.SPIN, 10.0),
    ALPHEM_KING_IMPACT("alphem_king_impact", Model.IMPACT, Animation.NONE_10, 4, 3, 3.0),
    ALPHANUM_FIRE("alphanum_fire", Model.IMPACT, Animation.SLOW_ROTATION, 3, 5, 55.0),
    ALPHEM_KING_BREATH("alphem_king_breath", Model.FLAT_VERTICAL, Animation.NONE_10, 7, 2, 3.0),
    SHACHATH_SLASH_FLAT("shachath_slash_flat", "shachath_slash", Model.FLAT, Animation.NONE_10, 10, 2, 2.5),
    SHACHATH_SLASH_FRONT("shachath_slash_front", "shachath_slash", Model.FLAT_VERTICAL_SIDE, Animation.NONE_10, 10, 2, 2.5)
    ;

    final String name;
    final String texture;
    final Model model;
    final Animation animation;
    final int frames;
    final int framesSpeed;
    final double scale;

    BPEffectTypes(String name, String texture, Model model, Animation animation, int frames, int framesSpeed, double scale) {
        this.name = name;
        this.texture = texture;
        this.model = model;
        this.animation = animation;
        this.frames = frames;
        this.framesSpeed = framesSpeed;
        this.scale = scale;
    }

    BPEffectTypes(String texture, Model model, Animation animation, int frames, int framesSpeed, double scale) {
        this(texture, texture, model, animation, frames, framesSpeed, scale);
    }

    BPEffectTypes(String texture, Model model, Animation animation, int frames, int framesSpeed) {
        this(texture, texture, model, animation, frames, framesSpeed, 1);
    }

    BPEffectTypes(String texture, Model model, Animation animation) {
        this(texture, texture, model, animation, 0, 0, 1);
    }

    BPEffectTypes(String texture, Model model, Animation animation, double scale) {
        this(texture, texture, model, animation, 0, 0, scale);
    }

    public String getName() {
        return name;
    }

    public String getTexture() {
        return texture;
    }

    public Model getModel() {
        return model;
    }

    public Animation getAnimation() {
        return animation;
    }

    public int getFrames() {
        return frames;
    }

    public int getFramesSpeed() {
        return framesSpeed;
    }

    public double getScale() {
        return scale;
    }

    public enum Model {

        FLAT("bp_effect_flat"),
        FLAT_VERTICAL("bp_effect_flat_vertical"),
        FLAT_VERTICAL_SIDE("bp_effect_flat_vertical_side"),
        IMPACT("bp_effect_impact")
        ;

        final String modelString;

        Model(String model) {
            this.modelString = model;
        }

        public String getModelString() {
            return modelString;
        }
    }

    public enum Animation {

        NONE_10("animation.bp_effect.none", 10),
        NONE_15("animation.bp_effect.none", 15),
        NONE_20("animation.bp_effect.none", 20),
        SPIN("animation.bp_effect.spin", 15),
        SMASH("animation.bp_effect.smash", 20),
        ASCEND("animation.bp_effect.ascend", 20),
        SLOW_ROTATION("animation.bp_effect.slow_rotation", Integer.MAX_VALUE)
        ;

        final String animationString;
        final int lifespan;

        Animation(String animation, int lifespan) {
            this.animationString = animation;
            this.lifespan = lifespan;
        }

        public String getAnimationString() {
            return animationString;
        }

        public int getLifespan() {
            return lifespan;
        }
    }
}
