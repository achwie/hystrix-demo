package achwie.hystrixdemo.util;

import static org.junit.Assert.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.junit.Test;

/**
 * 
 * @author 02.01.2016, Achim Wiedemann
 *
 */
public class ServicesConfigTest {
  @Test
  public void getPropertyAsURI_shouldReturnUriStringAsUri() throws URISyntaxException {
    Properties props = new Properties();
    props.put("test", "https://www.example.org:8080/mytest");

    final ServicesConfig config = new ServicesConfig(props);

    final URI uri = config.getPropertyAsURI("test");

    assertEquals("https", uri.getScheme());
    assertEquals("www.example.org", uri.getHost());
    assertEquals(8080, uri.getPort());
    assertEquals("/mytest", uri.getPath());
  }

  @Test
  public void getPropertyAsURI_shouldReturnPort80_whenHttpUrlWithNoPortGivenExplicitly() throws URISyntaxException {
    Properties props = new Properties();
    props.put("test", "http://www.example.org/mytest");

    final ServicesConfig config = new ServicesConfig(props);

    final URI uri = config.getPropertyAsURI("test");

    assertEquals(80, uri.getPort());
  }

  @Test
  public void getPropertyAsURI_shouldReturnPort443_whenHttpsUrlWithNoPortGivenExplicitly() throws URISyntaxException {
    Properties props = new Properties();
    props.put("test", "https://www.example.org/mytest");

    final ServicesConfig config = new ServicesConfig(props);

    final URI uri = config.getPropertyAsURI("test");

    assertEquals(443, uri.getPort());
  }

  @Test
  public void getPropertyAsURI_shouldReturnMinusOneForPort_whenUrlWithUnknownProtocolAndNoPortGivenExplicitly() throws URISyntaxException {
    Properties props = new Properties();
    props.put("test", "ftp://www.example.org/mytest");

    final ServicesConfig config = new ServicesConfig(props);

    final URI uri = config.getPropertyAsURI("test");

    assertEquals(-1, uri.getPort());
  }
}
