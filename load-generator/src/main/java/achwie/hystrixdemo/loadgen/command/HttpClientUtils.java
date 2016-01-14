package achwie.hystrixdemo.loadgen.command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;

/**
 * 
 * @author 12.01.2016, Achim Wiedemann
 *
 */
public class HttpClientUtils {
  public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  /**
   * Returns the charset of the request/response.
   * 
   * @param httpEntity The request/response to get the charset for.
   * @param defaultCharset The charset to return if the charset could not be
   *          detected from the request/response.
   * @return The charset of the request/response or the {@code defaultCharset}
   *         if the charset could not be detected from the request/response.
   */
  public static Charset getCharset(HttpEntity httpEntity, Charset defaultCharset) {
    final Header contentTypeHeader = httpEntity.getContentType();
    if (contentTypeHeader != null) {
      final String contentType = contentTypeHeader.getValue();
      if (StringUtils.isNotBlank(contentType)) {
        final String[] contentTypeParts = contentType.split("charset=");
        if (contentTypeParts.length == 2 && StringUtils.isNotBlank(contentTypeParts[1])) {
          return Charset.forName(contentTypeParts[1]);
        }
      }
    }

    return defaultCharset;
  }

  /**
   * Returns the body of the request/response as a byte array.
   * 
   * @param httpEntity The request/response to get the body for.
   * @return The body of the request/response as a byte array.
   * @throws IOException If there was a problem reading the body
   * @throws IllegalArgumentException If the content was too large to process.
   */
  public static byte[] getContent(HttpEntity httpEntity) throws IOException {
    final long contentLength = httpEntity.getContentLength();

    final long maxContentLength = Integer.MAX_VALUE;
    final ByteArrayOutputStream baos;
    if (contentLength > maxContentLength) {
      throw new IllegalArgumentException(String.format("Given content is too large (size: %d, max-size: %d)", contentLength, maxContentLength));
    } else if (contentLength >= 0) {
      baos = new ByteArrayOutputStream((int) contentLength);
    } else {
      baos = new ByteArrayOutputStream();
    }
    httpEntity.writeTo(baos);

    return baos.toByteArray();
  }
}
