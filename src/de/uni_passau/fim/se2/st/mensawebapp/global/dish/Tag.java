package de.uni_passau.fim.se2.st.mensawebapp.global.dish;

import java.util.Arrays;
import java.util.Optional;

/** Models the tags of a {@link Dish}. */
public enum Tag {

  /** Geflügel */
  G("Geflügel"),

  /** Schweinefleisch */
  S("Schweinefleisch"),

  /** Rindfleisch */
  R("Rindfleisch"),

  /** Fisch */
  F("Fisch"),

  /** Alkohol */
  A("Alkohol"),

  /** Vegetarisch */
  V("Vegetarisch"),

  /** Vegan */
  VG("Vegan"),

  /** Mensa Vital */
  MV("Mensa Vital"),

  /** Juradistl */
  J("Juradistl"),

  /** Bioland */
  BL("Bioland"),

  /** Lamm */
  L("Lamm"),

  /** Wild */
  W("Wild"),

  /** DE-ÖKO-006 mit ausschließlich biologisch erzeugten Rohstoffen */
  B("DE-ÖKO-006 mit ausschließlich biologisch erzeugten Rohstoffen");

  private final String tag;

  Tag(final String pTag) {
    tag = pTag;
  }

  /**
   * Provides the name of the tag.
   *
   * @return The name of the tag
   */
  public String getTag() {
    return tag;
  }

  /**
   * Provides a tag for a name.
   *
   * @param pName The name of the tag
   * @return An optional tag if one is found with the given name
   */
  public static Optional<Tag> getTagForName(final String pName) {
    return Arrays.stream(values()).filter(t -> t.getTag().equals(pName)).findFirst();
  }

  /**
   * Provides a tag for a token.
   *
   * @param pToken The token of the tag
   * @return An optional tag if one is found with the given token
   */
  public static Optional<Tag> getTagForToken(final String pToken) {
    return Arrays.stream(values()).filter(t -> t.name().equals(pToken)).findFirst();
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return getTag();
  }
}
