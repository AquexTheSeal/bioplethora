package io.github.bioplethora.api.mixin;

public class EndBiomeMixinAssistant {

    /*
    public static SimplexNoiseGenerator generator = new SimplexNoiseGenerator(new Random());
    public static SimplexNoiseGenerator generator2 = new SimplexNoiseGenerator(new Random());

    public static SimplexNoiseGenerator endGenerator = new SimplexNoiseGenerator(new Random());

    public static float shiftFloatBit(int input, int amt) {
        return input / (float) (amt + 1);
    }

    public static double get(float x, float z) {
        return ((getRandomNoise(endGenerator, x, z)/2f)) + (getRandomNoise(null, x, z)/2f);
    }

    public static Biome getBiome(float x, float z, Biome defaultBiome, Registry<Biome> lookupRegistry) {
        for (Biome b : ForgeRegistries.BIOMES.getValues().stream().filter((biome -> biome.getBiomeCategory() == Biome.Category.NETHER)).collect(Collectors.toList())) {
            double noise = get(x + getWeightRangeForBiome(b),z + getWeightForBiome(b));
            if (
                    noise <= getWeightForBiome(b) + getWeightRangeForBiome(b) &&
                            noise >= getWeightForBiome(b) - getWeightRangeForBiome(b)
            ) {
                if (lookupRegistry == null) {
                    return b;
                } else {
                    return lookupRegistry.get(b.getRegistryName());
                }
            }
        }
        return defaultBiome;
    }

    private static final HashMap<Biome, Float> TEST_BIOMES_WEIGHT_RANGES = new HashMap<>();
    private static final HashMap<ResourceLocation, Biome> TEST_BIOMES = new HashMap<>();
    private static final HashMap<Biome, Float> TEST_BIOME_WEIGHTS = new HashMap<>();

    @Mod.EventBusSubscriber(modid = Bioplethora.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class TestRegistryEvent {

        @SubscribeEvent
        public static void registerEvent(RegistryEvent.Register<Biome> event) {
            register(Biomes.NETHER_WASTES.getRegistryName(), 10, 10);
            register(Biomes.SOUL_SAND_VALLEY.getRegistryName(), 10, 10);
            register(Biomes.BASALT_DELTAS.getRegistryName(), 10, 10);
        }
    }

    private static void register(ResourceLocation registryName, float weight, float weightRange) {
        TEST_BIOMES.put(registryName, ForgeRegistries.BIOMES.getValue(registryName));
        TEST_BIOME_WEIGHTS.put(getBiomes()[TEST_BIOME_WEIGHTS.size()],weight);
        TEST_BIOMES_WEIGHT_RANGES.put(getBiomes()[TEST_BIOMES_WEIGHT_RANGES.size()], weightRange);
    }

    public static Biome[] getBiomes() {
        return TEST_BIOMES.values().toArray(new Biome[0]);
    }

    public static float getWeightForBiome(Biome biome) {
        return TEST_BIOME_WEIGHTS.get(biome);
    }

    public static float getWeightRangeForBiome(Biome biome) {
        return TEST_BIOMES_WEIGHT_RANGES.get(biome);
    }

    public static float getRandomNoise(SimplexNoiseGenerator noiseGenerator, float x, float z) {
        if (noiseGenerator == null) {
            return (((float) generator.getValue(
                    x/100f,z/100f,
                    generator2.getValue(x / 100f, z / 100f)
            ))*200f);
        } else {
            float i = x / 2;
            float j = z / 2;
            float k = x % 2;
            float l = z % 2;
            float f = 100.0F - MathHelper.sqrt(x * x + z * z) * 8.0F;
            f = MathHelper.clamp(f, -100.0F, 80.0F);

            for(int i1 = -12; i1 <= 12; ++i1) {
                for(int j1 = -12; j1 <= 12; ++j1) {
                    long k1 = (long)(i + i1);
                    long l1 = (long)(j + j1);
                    if (k1 * k1 + l1 * l1 > 4096L && noiseGenerator.getValue((double)k1, (double)l1) < (double)-0.9F) {
                        float f1 = (MathHelper.abs((float)k1) * 3439.0F + MathHelper.abs((float)l1) * 147.0F) % 13.0F + 9.0F;
                        float f2 = k - i1 * 2;
                        float f3 = l - j1 * 2;
                        float f4 = 100.0F - MathHelper.sqrt(f2 * f2 + f3 * f3) * f1;
                        f4 = MathHelper.clamp(f4, -100.0F, 80.0F);
                        f = Math.max(f, f4);
                    }
                }
            }

            return f;
        }
    }*/
}
