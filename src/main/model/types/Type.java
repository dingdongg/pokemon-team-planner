package model.types;

public interface Type {

    boolean isStrongOffensively(Type opposingType);

    boolean isWeakOffensively(Type opposingType);

    boolean isNoEffectOffensively(Type opposingType);

    boolean isStrongDefensively(Type opposingType);

    boolean isWeakDefensively(Type opposingType);

    boolean isNoEffectDefensively(Type opposingType);

}
