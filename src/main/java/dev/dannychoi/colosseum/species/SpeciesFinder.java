package dev.dannychoi.colosseum.species;

// This class is essentially used to get an instance of a certain species.
public class SpeciesFinder {
    public static Species getByType(SpeciesType type) {
        switch (type) {
            case DOG: return new SpeciesDog();
            case ARCHER: return new SpeciesArcher();
            case SPACEWALKER: return new SpeciesSpacewalker();
        }

        // If we get here, it means I made a new class but forgot to add it to above switch statement.
        return null;
    }
}
