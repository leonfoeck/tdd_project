package de.uni_passau.fim.se2.st.mensawebapp.business.service;

import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Additive;
import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Allergen;
import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Dish;
import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Tag;
import de.uni_passau.fim.se2.st.mensawebapp.persistence.csv.CSVFile;
import de.uni_passau.fim.se2.st.mensawebapp.persistence.csv.CSVParser;
import de.uni_passau.fim.se2.st.mensawebapp.persistence.exception.CSVParserException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DishService {

    private final String pDateTimePattern;
    private final Path pStoragePath;
    private final URI pBaseURI;

    private final long maxAge;

    private final List<CSVFile> files;

    /**
     * Instantiates a new service for {@link Dish}es.
     *
     * @param pDateTimePattern A pattern for date representation
     * @param pStoragePath     The path to store the downloaded CSV files
     * @param pBaseURI         The base URI prefix to download the CSV files from
     * @param pMaxAge          The maximum age of a file before it will be deleted by the application
     */
    public DishService(
            final String pDateTimePattern,
            final Path pStoragePath,
            final URI pBaseURI,
            final long pMaxAge) {
        this.pDateTimePattern = pDateTimePattern;
        this.pStoragePath = pStoragePath;
        this.pBaseURI = pBaseURI;
        maxAge = pMaxAge;
        files = new LinkedList<>();
    }

    /**
     * Provides the list of {@link Dish}es for a given date.
     *
     * @param pSelectedDate The selected date
     * @return A list of {@link Dish}es for that day
     * @throws CSVLoadException In case the CSV file could not be loaded
     */
    public synchronized List<Dish> getDishes(final LocalDate pSelectedDate) throws CSVLoadException {
        CSVFile file;
        try {
            file = new CSVFile(pStoragePath, pBaseURI, CalendarService.getYear(pSelectedDate),
                    CalendarService.getWeekNumber(pSelectedDate));
        } catch (IllegalStateException | MalformedURLException exception) {
            throw new CSVLoadException("Couldn't load CSVFile");
        }
        files.add(file);
        CSVParser csvParser = new CSVParser(file, DateTimeFormatter.ofPattern(pDateTimePattern));
        List<Dish> parsedDishesList;
        try {
            parsedDishesList = csvParser.parseDishes();
        } catch (CSVParserException | IOException e) {
            throw new CSVLoadException("Couldn't parse CSVFile");
        }

        return parsedDishesList.stream().filter(dish -> dish.date().equals(pSelectedDate)).collect(Collectors.toList());
    }

    /**
     * Filter the {@link Dish}es for a given day.
     *
     * <p>Filtering criteria are lists of {@link Additive}s, {@link Allergen}s, and {@link Tag}s.
     *
     * @param pSelectedDate      The selected date
     * @param pSelectedAdditives A list of selected {@link Additive}s
     * @param pSelectedAllergens A list of selected {@link Allergen}s
     * @param pSelectedTags      A list of selected {@link Tag}s
     * @return A list of {@link Dish}es that are suitable for the selection criteria
     * @throws CSVLoadException In case the CSV file could not be loaded
     */
    public synchronized List<Dish> filterDishes(
            final LocalDate pSelectedDate,
            final Collection<Additive> pSelectedAdditives,
            final Collection<Allergen> pSelectedAllergens,
            final Collection<Tag> pSelectedTags)
            throws CSVLoadException {
        return getDishes(pSelectedDate).stream().filter(dish -> Collections.disjoint(dish.tags(), pSelectedTags)
                && Collections.disjoint(dish.additives(), pSelectedAdditives) && Collections.disjoint(
                dish.allergens(), pSelectedAllergens)).collect(Collectors.toList());
    }

    /**
     * Delete old data.
     *
     * <p>Old data is data that was downloaded longer than {@link #maxAge} seconds ago.
     *
     * @see #maxAge
     */
    public synchronized void deleteOldData() {
        files.forEach(d -> d.delete(maxAge));
    }

    /**
     * Delete all data.
     */
    public synchronized void deleteAllData() {
        files.forEach(CSVFile::delete);
    }
}
