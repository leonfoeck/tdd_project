package de.uni_passau.fim.se2.st.mensawebapp.persistence.exception;

/** An exception signaling that something went wrong during CSV parsing. */
public class CSVParserException extends RuntimeException {

  /**
   * Instantiates a new {@code CSVParserException} object.
   *
   * @param pArgs The exception message
   */
  public CSVParserException(final String pArgs) {
    super(pArgs);
  }
}
