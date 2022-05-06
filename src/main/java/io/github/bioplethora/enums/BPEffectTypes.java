package io.github.bioplethora.enums;

public enum BPEffectTypes {

    // Infernal Quarterstaff
    FLAMING_SLASH("infernal_quarterstaff_slash", Animation.SPIN),
    SOUL_PURGE("infernal_quarterstaff_soul_purge", Animation.SPIN),
    AIR_JUMP("infernal_quarterstaff_air_jump", Animation.SPIN),
    FLAMING_SNIPE("infernal_quarterstaff_flaming_snipe", Animation.SPIN),

    // Crephoxl Hammer
    AERIAL_SHOCKWAVE("crephoxl_hammer_shockwave", Animation.SMASH)
    ;

    String texture;
    Animation animation;

    BPEffectTypes(String texture, Animation animation) {
        this.texture = texture;
        this.animation = animation;
    }

    public String getTexture() {
        return texture;
    }

    public Animation getAnimation() {
        return animation;
    }

    public enum Animation {

        SPIN("animation.bp_effect.spin", 15),
        SMASH("animation.bp_effect.smash", 20),
        ;

        String animationString;
        int lifespan;

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
