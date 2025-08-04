package de.uni_passau.fim.se2.st.mensawebapp.global.dish;

import com.google.common.base.Preconditions;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/** Models a single dish. */
public record Dish(
    DishType type,
    String name,
    Set<Additive> additives,
    Set<Allergen> allergens,
    Set<Tag> tags,
    BigDecimal studentPrice,
    BigDecimal staffPrice,
    BigDecimal guestPrice,
    LocalDate date) {

  /**
   * A builder for a {@link Dish}.
   *
   * <p>Note that you have to fill at least the values {@link #type}, {@link #name}, {@link
   * #studentPrice}, {@link #staffPrice}, {@link #guestPrice}, and {@link #date}.
   */
  public static class Builder {

    private DishType type;
    private String name;
    private Set<Additive> additives = new LinkedHashSet<>();
    private Set<Allergen> allergens = new LinkedHashSet<>();
    private Set<Tag> tags = new LinkedHashSet<>();
    private BigDecimal studentPrice;
    private BigDecimal staffPrice;
    private BigDecimal guestPrice;
    private LocalDate date;

    /**
     * Sets the type of the {@link Dish}.
     *
     * @param pDishType The new dish type, must not be {@code null}
     * @return The instance of the builder
     * @see DishType
     */
    public Builder setType(final DishType pDishType) {
      type = Preconditions.checkNotNull(pDishType);
      return this;
    }

    /**
     * Sets the name of the {@link Dish}.
     *
     * @param pName The new name of the type, must not be {@code null}
     * @return The instance of the builder
     */
    public Builder setName(final String pName) {
      name = Preconditions.checkNotNull(pName);
      return this;
    }

    /**
     * Overrides the set of additives of the {@link Dish}.
     *
     * @param pAdditives The new collection of additives
     * @return The instance of the builder
     * @see Additive
     */
    public Builder setAdditives(final Collection<Additive> pAdditives) {
      additives = new LinkedHashSet<>(pAdditives);
      return this;
    }

    /**
     * Adds an {@link Additive} to the set of additives of the {@link Dish}.
     *
     * @param pAdditive The new additive
     * @return The instance of the builder
     * @see Additive
     */
    public Builder addAdditive(final Additive pAdditive) {
      additives.add(pAdditive);
      return this;
    }

    /**
     * Overrides the set of allergens of the {@link Dish}.
     *
     * @param pAllergens The new collection of allergens
     * @return The instance of the builder
     * @see Allergen
     */
    public Builder setAllergens(final Collection<Allergen> pAllergens) {
      allergens = new LinkedHashSet<>(pAllergens);
      return this;
    }

    /**
     * Adds an {@link Allergen} to the set of allergens of the {@link Dish}.
     *
     * @param pAllergen The new allergen
     * @return The instance of the builder
     * @see Allergen
     */
    public Builder addAllergen(final Allergen pAllergen) {
      allergens.add(pAllergen);
      return this;
    }

    /**
     * Overrides the set of tags of the {@link Dish}.
     *
     * @param pTags The new collection of tags
     * @return The instance of the builder
     * @see Tag
     */
    public Builder setTags(final Collection<Tag> pTags) {
      tags = new LinkedHashSet<>(pTags);
      return this;
    }

    /**
     * Adds a {@link Tag} to the set of tags of the {@link Dish}.
     *
     * @param pTag The new tag
     * @return The instance of the builder
     * @see Allergen
     */
    public Builder addTag(final Tag pTag) {
      tags.add(pTag);
      return this;
    }

    /**
     * Sets the student price of the {@link Dish}.
     *
     * @param pPrice The new price, must be greater than 0
     * @return The instance of the builder
     */
    public Builder setStudentPrice(final BigDecimal pPrice) {
      Preconditions.checkState(pPrice.signum() > 0);
      studentPrice = pPrice;
      return this;
    }

    /**
     * Sets the staff price of the {@link Dish}.
     *
     * @param pPrice The new price, must be greater than 0
     * @return The instance of the builder
     */
    public Builder setStaffPrice(final BigDecimal pPrice) {
      Preconditions.checkState(pPrice.signum() > 0);
      staffPrice = pPrice;
      return this;
    }

    /**
     * Sets the guest price of the {@link Dish}.
     *
     * @param pPrice The new price, must be greater than 0
     * @return The instance of the builder
     */
    public Builder setGuestPrice(final BigDecimal pPrice) {
      Preconditions.checkState(pPrice.signum() > 0);
      guestPrice = pPrice;
      return this;
    }

    /**
     * Sets the date, the {@link Dish} appears on the menu.
     *
     * @param pDate The new date
     * @return The instance of the builder
     */
    public Builder setDate(final LocalDate pDate) {
      date = Preconditions.checkNotNull(pDate);
      return this;
    }

    /**
     * Builds the final {@link Dish} object and returns it.
     *
     * @return The generated {@link Dish} object
     */
    public Dish build() {
      Preconditions.checkNotNull(type);
      Preconditions.checkNotNull(name);
      Preconditions.checkNotNull(studentPrice);
      Preconditions.checkNotNull(staffPrice);
      Preconditions.checkNotNull(guestPrice);
      Preconditions.checkNotNull(date);

      return new Dish(
          type, name, additives, allergens, tags, studentPrice, staffPrice, guestPrice, date);
    }
  }
}
