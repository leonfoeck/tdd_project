package de.uni_passau.fim.se2.st.mensawebapp.business.service;

import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Additive;
import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Allergen;
import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Dish;
import de.uni_passau.fim.se2.st.mensawebapp.global.dish.Tag;
import de.uni_passau.fim.se2.st.mensawebapp.persistence.csv.CSVFile;
import de.uni_passau.fim.se2.st.mensawebapp.persistence.csv.CSVParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class DishServiceTest {
    static DishService dishService;

    private static CSVFile testFile;
    @TempDir
    private static Path correctPath;

    private static LocalDate legitDate;

    @BeforeEach
    void setup() throws MalformedURLException, URISyntaxException {
        URI correctURI = new URI("http://mensadata.lukasczyk.name");
        legitDate = LocalDate.of(2023, 12, 1);
        dishService = new DishService("dd.MM.yyyy", correctPath, correctURI, 5L);
        testFile = new CSVFile(correctPath, correctURI, CalendarService.getYear(legitDate),
                CalendarService.getWeekNumber(legitDate));
    }

    @Test
    void test_getDishes_Sunday() throws CSVLoadException {
        LocalDate date = LocalDate.of(2023, 12, 3);
        assertTrue(dishService.getDishes(date).isEmpty());
    }

    @Test
    void test_getDishes_WrongURI() throws URISyntaxException {
        URI wrongURI = new URI("htt://mensadata.lukasczyk.name");
        DishService dishService = new DishService("dd.MM.yyyy", correctPath, wrongURI, 5L);
        LocalDate date = LocalDate.of(2023, 12, 3);
        assertThrows(CSVLoadException.class, () -> dishService.getDishes(date));
    }

    @Test
    void test_getDishes_FileDoesntExist() {
        LocalDate date = LocalDate.of(1999, 12, 3);
        assertThrows(CSVLoadException.class, () -> dishService.getDishes(date));
    }

    @Test
    void test_getDishes() throws IOException, CSVLoadException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        CSVParser csvParser = new CSVParser(testFile, dateTimeFormatter);
        List<Dish> dishList = new LinkedList<>();
        for (Dish dish : csvParser.parseDishes()) {
            if (dish.date().equals(legitDate)) {
                dishList.add(dish);
            }
        }
        assertEquals(dishList, dishService.getDishes(legitDate));
    }

    @Test
    void test_filterDishes() throws IOException, CSVLoadException {
        List<Additive> pSelectedAdditives = new LinkedList<>();
        pSelectedAdditives.add(Additive.E);
        pSelectedAdditives.add(Additive.A);
        List<Allergen> pSelectedAllergens = new LinkedList<>();
        pSelectedAllergens.add(Allergen.A);
        pSelectedAllergens.add(Allergen.HE);
        pSelectedAllergens.add(Allergen.HH);
        List<Tag> pSelectedTags = new LinkedList<>();
        pSelectedTags.add(Tag.A);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        CSVParser csvParser = new CSVParser(testFile, dateTimeFormatter);
        List<Dish> dishList = new LinkedList<>();
        for (Dish dish : csvParser.parseDishes()) {
            if (dish.date().equals(legitDate) && Collections.disjoint(dish.tags(), pSelectedTags)
                    && Collections.disjoint(dish.additives(), pSelectedAdditives) && Collections.disjoint(dish.allergens(),
                    pSelectedAllergens)) {
                dishList.add(dish);
            }
        }
        assertEquals(dishList,
                dishService.filterDishes(legitDate, pSelectedAdditives, pSelectedAllergens, pSelectedTags));
    }

    @Test
    void test_deletedAllData() throws CSVLoadException, URISyntaxException, IOException {
        dishService.getDishes(legitDate);
        dishService.getDishes(LocalDate.of(2023, 12, 5));
        dishService.deleteAllData();
        assertTrue(isEmpty(correctPath));
    }

    @Test
    void test_deleteOldData_deleted() throws CSVLoadException, InterruptedException, IOException {
        dishService.getDishes(legitDate);
        dishService.getDishes(LocalDate.of(2023, 12, 5));
        TimeUnit.SECONDS.sleep(6);
        dishService.deleteOldData();
        assertTrue(isEmpty(correctPath));
    }

    @Test
    void test_deleteOldData_notDeleted() throws CSVLoadException, InterruptedException, IOException {
        dishService.getDishes(legitDate);
        TimeUnit.SECONDS.sleep(6);
        dishService.getDishes(LocalDate.of(2023, 12, 5));
        dishService.deleteOldData();
        assertFalse(isEmpty(correctPath));
    }

    private boolean isEmpty(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> directory = Files.newDirectoryStream(path)) {
                return !directory.iterator().hasNext();
            }
        }
        return false;
    }
}