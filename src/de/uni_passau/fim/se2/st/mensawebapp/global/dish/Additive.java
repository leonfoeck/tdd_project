package de.uni_passau.fim.se2.st.mensawebapp.global.dish;

import java.util.Arrays;
import java.util.Optional;

/** Models the additives of a {@link Dish}. */
public enum Additive {

  /** Farbstoff */
  A("Farbstoff"),

  /** Konservierungsstoff */
  B("Konservierungsstoff"),

  /** Antioxidationsmittel */
  C("Antioxidationsmittel"),

  /** Geschmacksverstärker */
  D("Geschmacksverstärker"),

  /** Geschwefelt */
  E("Geschwefelt"),

  /** Geschwärzt */
  F("Geschwärzt"),

  /** Gewachst */
  G("Gewachst"),

  /** Phosphat */
  H("Phosphat"),

  /** Säuerungsmittel Saccarin */
  I("Säuerungsmittel Saccharin"),

  /** Säuerungsmittel Aspartam (enthält Phenylalaninquelle) */
  J("Säuerungsmittel Aspartam (enthält Phenylalaninquelle)"),

  /** Säuerungsmittel Cyclamat */
  K("Säuerungsmittel Cyclamat"),

  /** Säuerungsmittel Acesulfam */
  L("Säuerungsmittel Acesulfam"),

  /** Chininhaltig */
  M("Chininhaltig"),

  /** Coffeinhaltig */
  N("Coffeinhaltig"),

  /** Gentechnisch verändert */
  O("Gentechnisch verändert"),

  /** Sulfide */
  P("Sulfide"),

  /** Phenylalanin */
  Q("Phenylalanin");

  private final String additive;

  Additive(final String pAdditive) {
    additive = pAdditive;
  }

  /**
   * Provides the name of the additive.
   *
   * @return The name of the additive
   */
  public String getAdditive() {
    return additive;
  }

  /**
   * Provides an additive for a given index.
   *
   * <p>Implementation note: the additives are given by numbers 1 through 17 in the STWNO's CSV
   * files. An index from this range maps to its corresponding additive. For unknown indices, this
   * method returns an empty {@link Optional}.
   *
   * @param pIndex The index of the additive
   * @return An optional additive if one is found
   */
  public static Optional<Additive> getAdditiveForIndex(final int pIndex) {
    if ((pIndex > 0) && (pIndex < 18)) {
      return Optional.of(Additive.valueOf(String.valueOf((char) (64 + pIndex))));
    }
    return Optional.empty();
  }

  /**
   * Provides an additive for a name.
   *
   * @param pName The name of the additive
   * @return An optional additive if one of the given name is found
   */
  public static Optional<Additive> getAdditiveForName(final String pName) {
    return Arrays.stream(values()).filter(a -> a.getAdditive().equals(pName)).findFirst();
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return getAdditive();
  }
}
