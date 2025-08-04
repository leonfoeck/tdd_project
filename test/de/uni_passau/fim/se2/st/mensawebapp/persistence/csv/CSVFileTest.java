package de.uni_passau.fim.se2.st.mensawebapp.persistence.csv;

import de.uni_passau.fim.se2.st.mensawebapp.business.service.CalendarService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CSVFileTest {

    @TempDir
    private static Path correctPath;
    private static int validYear;
    private static int validWeekOfYear;
    private static URI correctURI;

    private static File testFile;

    private static CSVFile downloadedFile;

    @AfterEach
    public void cleanup() throws IOException {
        try (Stream<Path> stream = Files.walk(correctPath)) {
            stream.filter(Files::isRegularFile).map(Path::toFile).forEach(File::delete);
        }
    }

    @BeforeAll
    public static void setup() throws URISyntaxException, MalformedURLException {
        LocalDate today = LocalDate.now();
        validYear = CalendarService.getYear(today);
        validWeekOfYear = CalendarService.getWeekNumber(today);
        correctURI = new URI("http://mensadata.lukasczyk.name");
        testFile = correctPath.resolve(String.format("%d-%d.csv", validYear, validWeekOfYear)).toFile();
        downloadedFile = new CSVFile(correctPath, correctURI, validYear, validWeekOfYear);
    }

    @Test
    void test_Constructor_pWeekNotValid() {
        assertThrows(IllegalStateException.class, () -> new CSVFile(correctPath, correctURI, validYear, 60));
    }

    @Test
    void test_Constructor_pWeekValid() {
        assertDoesNotThrow(() -> new CSVFile(correctPath, correctURI, validYear, validWeekOfYear));
    }

    @Test
    void test_Constructor_pWeekNotValid2() {
        assertThrows(IllegalStateException.class, () -> new CSVFile(correctPath, correctURI, validYear, -1));
    }

    @Test
    void test_download_correctInput() throws IOException {
        assertTrue(downloadedFile.download());
    }

    @Test
    void test_download_FileCreated() throws IOException {
        if (downloadedFile.download()) {
            assertTrue(testFile.exists());
        }
    }

    @Test
    void test_download_CorrectFileCreated() throws IOException {
        File differentFile = correctPath.resolve("test.csv").toFile();
        URL correctURL = correctURI.resolve(String.format("%s/%d-%d.csv", correctURI, validYear, validWeekOfYear)).toURL();
        ReadableByteChannel readableByteChannel = Channels.newChannel(correctURL.openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(differentFile);
        FileChannel fileChannel = fileOutputStream.getChannel();
        fileChannel.transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
        fileOutputStream.close();
        if (downloadedFile.download()) {
            assertEquals(-1L, Files.mismatch(differentFile.toPath(), testFile.toPath()));
        }
    }

    @Test
    void test_exist_doesExist() throws IOException {
        if (downloadedFile.download()) {
            assertTrue(downloadedFile.exists());
        }
    }

    @Test
    void test_exist_doesntExist() {
        assertFalse(downloadedFile.exists());
    }

    @Test
    void test_delete_Worked() throws IOException {
        downloadedFile.download();
        assertTrue(downloadedFile.delete());
    }

    @Test
    void test_delete_didntWork() {
        assertFalse(downloadedFile.delete());
    }

    @Test
    void test_delete_fileReallyGotDeleted() throws IOException {
        downloadedFile.download();
        downloadedFile.delete();
        assertFalse(downloadedFile.exists());
    }

    @Test
    void test_hasAge_hasntAged() throws IOException {
        downloadedFile.download();
        assertFalse(downloadedFile.hasAge(10));
    }

    @Test
    void test_hasAge_true() throws IOException, InterruptedException {
        downloadedFile.download();
        TimeUnit.SECONDS.sleep(2);
        assertTrue(downloadedFile.hasAge(1));
    }

    @Test
    void test_delete_withAge_shouldDelete() throws IOException, InterruptedException {
        downloadedFile.download();
        TimeUnit.SECONDS.sleep(2);
        assertTrue(downloadedFile.delete(1));
    }

    @Test
    void test_delete_withAge_shouldntWork() throws IOException {
        downloadedFile.download();
        assertFalse(downloadedFile.delete(10));
    }

    @Test
    void test_delete_withAge_fullyDeleted() throws InterruptedException, IOException {
        downloadedFile.download();
        TimeUnit.SECONDS.sleep(2);
        downloadedFile.delete(1);
        assertFalse(downloadedFile.exists());
    }

    @Test
    void test_provideFile() {
        assertEquals(testFile, downloadedFile.provideFile());
    }
}
