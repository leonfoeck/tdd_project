package de.uni_passau.fim.se2.st.mensawebapp.persistence.csv;

import com.google.common.base.Preconditions;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Path;
import java.util.Date;

/**
 * A utility class to deal with a CSV file.
 */
public class CSVFile {

    private final Path downloadPath;

    private final URL downloadURL;


    /**
     * Initialises the {@code CSVFile} class.
     *
     * @param pStoragePath The path to store the CSV file after downloading it
     * @param pBaseURL     The base URL for the CSV files to get downloaded
     * @param pYear        The year for the menu
     * @param pWeek        The week for the menu
     * @throws MalformedURLException In case the resulting URL is invalid
     * @throws IllegalStateException In case the week number is illegal
     */
    public CSVFile(final Path pStoragePath, final URI pBaseURL, final int pYear, final int pWeek)
            throws MalformedURLException {
        Preconditions.checkState(pWeek > 0 && pWeek < 54);
        downloadPath = pStoragePath.resolve(String.format("%d-%d.csv", pYear, pWeek));
        downloadURL = pBaseURL.resolve(String.format("%s/%d-%d.csv", pBaseURL, pYear, pWeek)).toURL();
    }

    /**
     * Delete the file from the storage.
     *
     * @return Whether deletion was successful
     */
    public boolean delete() {
        return downloadPath.toFile().delete();
    }

    /**
     * Delete the file if its age is more than {@code pAgeSeconds}.
     *
     * @param pAgeSeconds The minimum age of the file in seconds to be deleted
     * @return Whether deletion was successful
     */
    public boolean delete(final long pAgeSeconds) {
        if (hasAge(pAgeSeconds)) {
            return delete();
        } else {
            return false;
        }
    }

    /**
     * Download the file.
     *
     * @return Whether the download was successful
     * @throws IOException In case of I/O errors
     */
    public boolean download() throws IOException {
        InputStream stream;
        try {
            stream = downloadURL.openStream();
        } catch (FileNotFoundException e) {
            return false;
        }
        try (final ReadableByteChannel readableByteChannel = Channels.newChannel(
                stream); final FileOutputStream fileOutputStream = new FileOutputStream(downloadPath.toFile())) {
            final long numberOfBytes = fileOutputStream.getChannel()
                    .transferFrom(readableByteChannel, 0, Long.MAX_VALUE);
            return numberOfBytes > 0;
        }
    }

    /**
     * Check whether the file exists on the storage.
     *
     * @return Whether the file exists
     */
    public boolean exists() {
        return downloadPath.toFile().exists();
    }

    /**
     * Checks whether the file already is older than {@code pAgeSeconds} seconds.
     *
     * @param pAgeSeconds The minimum age of the file in seconds
     * @return Whether the file is older than the given number of seconds
     */
    public boolean hasAge(final long pAgeSeconds) {
        Date newDate = new Date();
        long diff = newDate.getTime() - downloadPath.toFile().lastModified();
        return diff > pAgeSeconds * 1000;
    }

    /**
     * Provides the file object for the downloaded CSV file.
     *
     * @return The file object for the downloaded CSV file.
     */
    public File provideFile() {
        return downloadPath.toFile();
    }
}
