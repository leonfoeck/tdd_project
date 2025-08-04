package de.uni_passau.fim.se2.st.mensawebapp.persistence.csv;

import de.uni_passau.fim.se2.st.mensawebapp.global.dish.*;
import de.uni_passau.fim.se2.st.mensawebapp.persistence.exception.CSVParserException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static de.uni_passau.fim.se2.st.mensawebapp.global.dish.Additive.getAdditiveForIndex;
import static de.uni_passau.fim.se2.st.mensawebapp.global.dish.Allergen.getAllergenForToken;

/**
 * A parser for the CSV files of STWNO.
 */
public class CSVParser {

    private final CSVFile pCSVFile;
    private final DateTimeFormatter pDateTimeFormatter;

    /**
     * Instantiates a new {@code CSVParser} object.
     *
     * @param pCSVFile           The CSV file object
     * @param pDateTimeFormatter A formatter for dates
     */
    public CSVParser(final CSVFile pCSVFile, final DateTimeFormatter pDateTimeFormatter) {
        this.pCSVFile = pCSVFile;
        this.pDateTimeFormatter = pDateTimeFormatter;
    }

    /**
     * Parses the content of the CSV file to a list of {@link Dish}es.
     *
     * @return A list of {@link Dish}es
     * @throws IOException In case of I/O errors
     */
    public List<Dish> parseDishes() throws IOException {
        if (!pCSVFile.download() || !pCSVFile.exists()) {
            throw new CSVParserException("File doesn't exist.");
        }
        try (BufferedReader reader = new BufferedReader(
                new FileReader(pCSVFile.provideFile(), StandardCharsets.UTF_8))) {
            CSVReader csvReader = new CSVReader(reader);
            List<Dish> dishList = new LinkedList<>();
            for (CSVReader.CSVRow row : csvReader) {
                dishList.add(
                        new Dish(getDishtype(row), getName(row), getAdditives(row), getAllergens(row), getTags(row),
                                getBigDecimal(row, "stud"), getBigDecimal(row, "bed"), getBigDecimal(row, "gast"),
                                LocalDate.parse(row.get("datum"), pDateTimeFormatter)));
            }
            return dishList;
        }
    }

    private BigDecimal getBigDecimal(CSVReader.CSVRow row, String pers) {
        return new BigDecimal(row.get(pers).replace(",", "."));
    }

    private Set<Tag> getTags(CSVReader.CSVRow row) {
        Set<Tag> tags = new HashSet<>();
        String[] tagsAsString = row.get("kennz").split(",");
        for (String tag : tagsAsString) {
            Optional<Tag> optionalTag = (Tag.getTagForToken(tag));
            optionalTag.ifPresent(tags::add);
        }
        return tags;

    }

    private Set<Allergen> getAllergens(CSVReader.CSVRow row) {
        String allergenString = row.get("name");
        if (!allergenString.contains("(")) {
            return Collections.emptySet();
        }
        String[] allergies = allergenString.split("\\(")[1].replaceAll("[^A-Z]+", " ").trim().split(" ");
        Set<Allergen> allergens = new TreeSet<>();
        for (String ch : allergies) {
            Optional<Allergen> allergen = getAllergenForToken(ch);
            allergen.ifPresent(allergens::add);
        }
        return allergens;
    }

    private Set<Additive> getAdditives(CSVReader.CSVRow row) {
        String additiveString = row.get("name");
        if (!additiveString.contains("(")) {
            return Collections.emptySet();
        }
        String[] additives = additiveString.split("\\(")[1].replaceAll("\\D+", " ").trim().split(" ");
        Set<Additive> addis = new TreeSet<>();
        for (String digit : additives) {
            if (!digit.isEmpty()) {
                Optional<Additive> allergen = getAdditiveForIndex(Integer.parseInt(digit));
                allergen.ifPresent(addis::add);
            }
        }
        return addis;
    }

    private String getName(CSVReader.CSVRow row) {
        String name = row.get("name");
        if (!name.contains("(")) {
            return name.trim();
        } else {
            return row.get("name").split("\\(")[0].trim();
        }
    }

    private DishType getDishtype(CSVReader.CSVRow row) {
        char firstChar = row.get("warengruppe").charAt(0);
        return switch (firstChar) {
            case 'H' -> DishType.MAIN;
            case 'S' -> DishType.APPETISER;
            case 'B' -> DishType.SIDE;
            case 'N' -> DishType.DESSERT;
            default -> null;
        };
    }
}
