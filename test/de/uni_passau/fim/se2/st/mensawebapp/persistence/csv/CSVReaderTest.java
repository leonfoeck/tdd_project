package de.uni_passau.fim.se2.st.mensawebapp.persistence.csv;

import de.uni_passau.fim.se2.st.mensawebapp.business.service.CalendarService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CSVReaderTest {

    @TempDir
    private static Path correctPath;
    private static List<String> testList;
    private static CSVFile file;

    @BeforeAll
    public static void setup() throws URISyntaxException, MalformedURLException {
        LocalDate lastWeek = LocalDate.of(2022, 11, 28);
        int validYear = CalendarService.getYear(lastWeek);
        int validWeekOfYear = CalendarService.getWeekNumber(lastWeek);
        URI correctURI = new URI("http://mensadata.lukasczyk.name");
        testList = new LinkedList<>();
        testList.add("datum");
        testList.add("tag");
        testList.add("warengruppe");
        testList.add("name");
        testList.add("kennz");
        testList.add("stud");
        testList.add("bed");
        testList.add("gast");
        file = new CSVFile(correctPath, correctURI, validYear, validWeekOfYear);
    }

    @Test
    void test_getFieldNames_emptyFile() throws IOException {
        File emptyFile = new File("fail.txt");
        emptyFile.createNewFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(emptyFile, StandardCharsets.UTF_8))) {
            CSVReader csvReader = new CSVReader(reader);
            List<String> headerList = csvReader.getFieldNames();
            assertEquals(Collections.emptyList(), headerList);
        }
        emptyFile.delete();
    }

    @Test
    void test_getFieldNames() throws IOException {
        file.download();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.provideFile(), StandardCharsets.UTF_8))) {
            CSVReader csvReader = new CSVReader(reader);
            List<String> headerList = csvReader.getFieldNames();
            assertEquals(testList, headerList);
        }
    }

    @Test
    void test_getFieldNames_Consistency() throws IOException {
        file.download();
        try (BufferedReader reader = new BufferedReader(new FileReader(file.provideFile(), StandardCharsets.UTF_8))) {
            CSVReader csvReader = new CSVReader(reader);
            csvReader.getFieldNames();
            List<String> headerList = csvReader.getFieldNames();
            assertEquals(testList, headerList);
        }
    }

    @Test
    void test_iterator_For_Empty_File() throws IOException {
        File emptyFile = new File("fail.txt");
        emptyFile.createNewFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(emptyFile, StandardCharsets.UTF_8))) {
            CSVReader csvReader = new CSVReader(reader);
            Iterator<CSVReader.CSVRow> iterator = csvReader.iterator();
            assertEquals(Collections.emptyIterator(), iterator);
        }
        emptyFile.delete();
    }

    @Test
    void test_iterator_For_CorrectFile() throws IOException {
        file.download();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(file.provideFile(), StandardCharsets.UTF_8)); BufferedReader testReader = new BufferedReader(
                new FileReader(file.provideFile(), StandardCharsets.UTF_8))) {
            CSVReader csvReader = new CSVReader(reader);
            List<Map<String, String>> hashMapList = new LinkedList<>();
            List<String> headerList = csvReader.getFieldNames();
            List<String> lineList = testReader.lines().toList();
            for (String s : lineList.subList(1, lineList.size())) {
                HashMap<String, String> rowMap = new HashMap<>();
                String[] splitRow = s.split(";");
                for (int i = 0; i < splitRow.length; i++) {
                    rowMap.put(headerList.get(i), splitRow[i]);
                }
                hashMapList.add(rowMap);
            }
            List<Map<String, String>> testHashMaplist = new LinkedList<>();
            Iterator<CSVReader.CSVRow> it = csvReader.iterator();
            it.forEachRemaining(d -> testHashMaplist.add(d.getEntryMap()));
            assertEquals(hashMapList, testHashMaplist);
        }
    }
}
