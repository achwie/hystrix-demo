package achwie.hystrixdemo.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 
 * @author 17.12.2015, Achim Wiedemann
 *
 */
public class SimpleCsvReaderTest {
  @Test
  public void readLine_shouldTrimValuesOfLine() throws IOException {
    final InputStream is = new ByteArrayInputStream("  1, 2,    3,4   ".getBytes());
    final List<String[]> actualLines = new ArrayList<>();

    SimpleCsvReader.readLines(is, values -> actualLines.add(values));

    assertArrayEquals(new String[] { "1", "2", "3", "4" }, actualLines.get(0));
  }

  @Test
  public void readLine_whenMultipleLines_thenReadEachLine() throws IOException {
    final InputStream is = new ByteArrayInputStream("  1, 2,    3,4   \n5;  6, 7".getBytes());
    final List<String[]> actualLines = new ArrayList<>();

    SimpleCsvReader.readLines(is, values -> actualLines.add(values));

    assertArrayEquals(new String[] { "1", "2", "3", "4" }, actualLines.get(0));
    assertArrayEquals(new String[] { "5;  6", "7" }, actualLines.get(1));
  }

  @Test
  public void readLine_whenEmptyField_thenHandleItAsEmptyString() throws IOException {
    final InputStream is = new ByteArrayInputStream("  1, ,    ,  4,".getBytes());
    final List<String[]> actualLines = new ArrayList<>();

    SimpleCsvReader.readLines(is, values -> actualLines.add(values));

    assertArrayEquals(new String[] { "1", "", "", "4" }, actualLines.get(0));
  }

  @Test
  public void readLine_whenEmptyContent_thenNotCallHandler() throws IOException {
    final InputStream is = new ByteArrayInputStream("   ".getBytes());
    final List<Object> invocations = new ArrayList<>();

    SimpleCsvReader.readLines(is, values -> invocations.add(new Object()));

    assertEquals(0, invocations.size());
  }
}
