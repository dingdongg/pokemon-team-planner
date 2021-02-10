package model.types;

public class NormalType implements Type {
    @Override
    public boolean isStrongOffensively(Type opposingType) {
        return false;
    }

    @Override
    public boolean isWeakOffensively(Type opposingType) {
        return false;
    }

    @Override
    public boolean isNoEffectOffensively(Type opposingType) {
        return false;
    }

    @Override
    public boolean isStrongDefensively(Type opposingType) {
        return false;
    }

    @Override
    public boolean isWeakDefensively(Type opposingType) {
        return false;
    }

    @Override
    public boolean isNoEffectDefensively(Type opposingType) {
        return false;
    }
}
