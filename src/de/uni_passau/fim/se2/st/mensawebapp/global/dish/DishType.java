package de.uni_passau.fim.se2.st.mensawebapp.global.dish;

/** Models the type of {@link Dish}. */
public enum DishType {

  /** Vorspeise */
  APPETISER("Vorspeise"),

  /** Hauptspeise */
  MAIN("Hauptspeise"),

  /** Beilage */
  SIDE("Beilage"),

  /** Nachspeise */
  DESSERT("Nachspeise");

  private final String value;

  DishType(final String pValue) {
    value = pValue;
  }

  /**
   * Provides the type of the {@link Dish}.
   *
   * @return The type of the {@link Dish}
   */
  public String getValue() {
    return value;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    return String.format("DishType %s", getValue());
  }
}
