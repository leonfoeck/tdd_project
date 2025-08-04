package de.uni_passau.fim.se2.st.mensawebapp.global.dish;

import java.util.Arrays;
import java.util.Optional;

/** Models the allergens of a {@link Dish}. */
public enum Allergen {

  /** Gluten */
  A("Gluten"),

  /** Weizengluten */
  AA("Weizengluten"),

  /** Roggengluten */
  AB("Roggengluten"),

  /** Gerstengluten */
  AC("Gerstengluten"),

  /** Hafergluten */
  AD("Hafergluten"),

  /** Dinkelgluten */
  AE("Dinkelgluten"),

  /** Kamutgluten */
  AF("Kamutgluten"),

  /** Krebstiere */
  B("Krebstiere"),

  /** Eier */
  C("Eier"),

  /** Fisch */
  D("Fisch"),

  /** Erdnüsse */
  E("Erdnüsse"),

  /** Soja */
  F("Soja"),

  /** Milch und Milchprodukte */
  G("Milch und Milchprodukte"),

  /** Nuss */
  H("Nuss"),

  /** Mandel */
  HA("Mandel"),

  /** Haselnuss */
  HB("Haselnuss"),

  /** Walnuss */
  HC("Walnuss"),

  /** Cashew */
  HD("Cashew"),

  /** Pecannuss */
  HE("Pecannuss"),

  /** Paranuss */
  HF("Paranuss"),

  /** Pistazie */
  HG("Pistazie"),

  /** Macadamianuss */
  HH("Macadamianuss"),

  /** Queenslandnuss */
  HI("Queenslandnuss"),

  /** Sellerie */
  I("Sellerie"),

  /** Senf */
  J("Senf"),

  /** Sesamsamen */
  K("Sesamsamen"),

  /** Schwefeldioxid und Sulfide */
  L("Schwefeldioxid und Sulfide"),

  /** Lupinen */
  M("Lupinen"),

  /** Weichtiere */
  N("Weichtiere"),

  /** Nitrat */
  O("Nitrat"),

  /** Nitritpökelsalz */
  P("Nitritpökelsalz");

  private final String allergen;

  Allergen(final String pAllergen) {
    allergen = pAllergen;
  }

  /**
   * Provides the name of the allergen.
   *
   * @return The name of the allergen
   */
  public String getAllergen() {
    return allergen;
  }

  /**
   * Provides an allergen for a given name.
   *
   * @param pName The name of the allergen
   * @return An optional allergen if one of the given name is found
   */
  public static Optional<Allergen> getAllergenForName(final String pName) {
    return Arrays.stream(values()).filter(a -> a.getAllergen().equals(pName)).findFirst();
  }

  /**
   * Provides an allergen for a given token.
   *
   * @param pToken The token of the allergen
   * @return An optional allergen if one is found for the given token
   */
  public static Optional<Allergen> getAllergenForToken(final String pToken) {
    return Arrays.stream(values()).filter(a -> a.name().equals(pToken)).findFirst();
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return getAllergen();
  }
}
