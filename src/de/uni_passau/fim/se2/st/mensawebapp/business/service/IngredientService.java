package de.uni_passau.fim.se2.st.mensawebapp.business.service;

import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Additive;
import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Allergen;
import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Tag;

import java.util.List;

/**
 * Provides the various types of ingredients.
 */
public final class IngredientService {

    private IngredientService() {
    }

    /**
     * Provides the list of all {@link Additive}s known to the application.
     *
     * @return The list of all {@link Additive}s known to the application
     * @see Additive
     */
    public static List<Additive> provideAdditives() {
        return List.of(Additive.values());
    }

    /**
     * Provides the list of all {@link Allergen}s known to the application.
     *
     * @return The list of all {@link Allergen}s known to the application
     * @see Allergen
     */
    public static List<Allergen> provideAllergens() {
        return List.of(Allergen.values());
    }

    /**
     * Provides the list of all {@link Tag}s known to the application.
     *
     * @return The list of all {@link Tag}s known to the application
     * @see Tag
     */
    public static List<Tag> provideTags() {
        return List.of(Tag.values());
    }
}
