package de.uni_passau.fim.se2.st.mensawebapp.persistence.csv;

import com.google.common.collect.ImmutableMap;
import de.uni_passau.fim.se2.st.mensawebapp.persistence.csv.CSVReader.CSVRow;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * A reader for CSV files.
 *
 * <p>This reader will work on any kind of input that can be represented by a {@link
 * BufferedReader}. It expects that the first line of the CSV file contains the field names.
 */
public class CSVReader implements Iterable<CSVRow> {

    private final BufferedReader pReader;
    private final CharSequence pDelimiter;

    /**
     * Initialises a {@code CSVReader} using a {@link BufferedReader} as its input source.
     *
     * <p>This sets the default delimiter character to {@code ;}.
     *
     * @param pReader The input source
     */
    public CSVReader(final BufferedReader pReader) {
        this(pReader, ";");
    }

    /**
     * Initialises a {@code CSVReader} using a {@link BufferedReader} as its input source.
     *
     * <p>This allows to set the delimiter character to {@code pDelimiter}.
     *
     * @param pReader    The input source
     * @param pDelimiter The delimiter character
     */
    public CSVReader(final BufferedReader pReader, final CharSequence pDelimiter) {
        this.pReader = pReader;
        this.pDelimiter = pDelimiter;
    }

    /**
     * Provides the field names from the CSV file.
     *
     * @return The field names from the CSV file
     */
    public List<String> getFieldNames() {
        int bufferSize = 1000;
        String firstRow;
        try {
            pReader.mark(bufferSize);
            firstRow = pReader.readLine();
        } catch (IOException e) {
            return Collections.emptyList();
        }
        if (firstRow == null) {
            return Collections.emptyList();
        }
        try {
            pReader.reset();
        } catch (IOException e) {
            return Collections.emptyList();
        }
        return List.of(firstRow.trim().split(String.valueOf(pDelimiter)));
    }

    @Override
    public Iterator<CSVRow> iterator() {
        List<CSVRow> csvRows = new LinkedList<>();
        List<String> headerList = getFieldNames();
        List<String> lineList = pReader.lines().toList();
        if (lineList.isEmpty()) {
            return Collections.emptyIterator();
        }
        for (String s : lineList.subList(1, lineList.size())) {
            HashMap<String, String> rowMap = new HashMap<>();
            String[] splitRow = s.split(";");
            for (int i = 0; i < splitRow.length; i++) {
                rowMap.put(headerList.get(i), splitRow[i]);
            }
            csvRows.add(new CSVRow(rowMap));
        }
        return csvRows.iterator();
    }

    /**
     * Represents one data row of the CSV data.
     */
    public static class CSVRow {

        private final Map<String, String> entryMap;

        private CSVRow(final Map<String, String> pEntryMap) {
            entryMap = ImmutableMap.copyOf(pEntryMap);
        }

        /**
         * Returns the value to which the specified key is mapped, or {@code null} if this map contains
         * no mapping for the key.
         *
         * @param pKey the key whose associated value is to be returned
         * @return the value to which the specified key is mapped, or {@code null} if this map contains
         * no mapping for the key
         */
        public String get(final String pKey) {
            return entryMap.get(pKey);
        }

        /**
         * Provides the entry map of the row.
         *
         * <p>The map is a mapping from field name to value.
         *
         * @return The entry map of the row
         */
        public Map<String, String> getEntryMap() {
            return entryMap;
        }
    }
}
