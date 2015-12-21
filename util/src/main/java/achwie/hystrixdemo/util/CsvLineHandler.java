package achwie.hystrixdemo.util;

/**
 * Can handle the line of a CSV file.
 * 
 * @author 17.12.2015, Achim Wiedemann
 */
public interface CsvLineHandler {
  /**
   * Handles the (preferably trimmed) field values of a CSV line.
   * 
   * @param values The field values of a CSV line.
   */
  public void handleLine(String[] values);
}