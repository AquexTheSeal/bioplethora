package io.github.bioplethora.registry;

public class BioplethoraEntityClasses {

    // For regular mobs, the default mob class
    public static final BioplethoraEntityClasses NONE = new BioplethoraEntityClasses();

    // For harmless Bioplethora mobs
    public static final BioplethoraEntityClasses ECOHARMLESS = new BioplethoraEntityClasses();

    // For mobs that only attack when bothered or hurt
    public static final BioplethoraEntityClasses PLETHONEUTRAL = new BioplethoraEntityClasses();

    // For completely hostile mobs
    public static final BioplethoraEntityClasses DANGERUM = new BioplethoraEntityClasses();

    // For stronger versions of the dangerum class
    public static final BioplethoraEntityClasses HELLSENT = new BioplethoraEntityClasses();

    // For eldritch powered/sized mobs
    public static final BioplethoraEntityClasses ELDERIA = new BioplethoraEntityClasses();
}
