package de.uni_passau.fim.se2.st.mensawebapp.business.service;

import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Additive;
import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Allergen;
import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class IngredientServiceTest {

    @Test
    void test_provideAllergens_notEmpty() {
        assertFalse(IngredientService.provideAllergens().isEmpty());
    }

    @Test
    void test_provideAllergens_allAllergens() {
        assertEquals(List.of(Allergen.values()), IngredientService.provideAllergens());
    }

    @Test
    void test_provideTags_allTags() {
        assertEquals(List.of(Tag.values()), IngredientService.provideTags());
    }

    @Test
    void test_provideAdditives_allAdditives() {
        assertEquals(List.of(Additive.values()), IngredientService.provideAdditives());
    }
}
