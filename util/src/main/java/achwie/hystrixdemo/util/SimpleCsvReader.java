package achwie.hystrixdemo.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A very simple CSV reader with no support for escaping, quoting, and such.
 * 
 * @author 17.12.2015, Achim Wiedemann
 */
public class SimpleCsvReader {
  /**
   * <p>
   * Reads comma separated lines from the given {@link InputStream} and passes
   * the (trimmed) field values of each line to a handler. The handler will not
   * be called if the input stream is empty or only contains whitespace.
   * </p>
   * <p>
   * <strong>NOTE:</strong> This is a very basic implementation that can't
   * handle escaping, quoting, and such.
   * </p>
   * 
   * @param is The input stream to read from (will not be closed by this method)
   * @param lineHandler The handler to handle the field values of each line
   * @throws IOException If something went wrong reading from the input stream
   */
  public static void readLines(InputStream is, CsvLineHandler lineHandler) throws IOException {
    final BufferedReader r = new BufferedReader(new InputStreamReader(is));
    String line;
    while ((line = r.readLine()) != null) {
      final String[] values = line.trim().split("\\s*,\\s*");
      if (values.length > 0 && values[0].trim().length() > 0) {
        lineHandler.handleLine(values);
      }
    }
  }
}
