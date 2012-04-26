package ar.com.restba.connectors.con;

import static com.restfb.util.StringUtils.isBlank;
import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.restfb.FacebookClient;
import com.restfb.exception.FacebookJsonMappingException;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonException;
import com.restfb.json.JsonObject;
import com.restfb.util.ReflectionUtils;

/**
 * Represents a <a href="http://developers.facebook.com/docs/api">Graph API
 * Connection type</a>.
 * 
 * @author <a href="http://restfb.com">Mark Allen</a>
 */
public class RestBAIterator<T> implements Iterable<List<T>> {
  private FacebookClient facebookClient;
  private Class<T> connectionType;
  private List<T> data;
  private String previousPageUrl;
  private String nextPageUrl;

  /**
   * @see java.lang.Iterable#iterator()
   * @since 1.6.7
   */
  public Iterator<List<T>> iterator() {
    return new Ite<T>(this);
  }

  /**
   * Iterator over connection pages.
   * 
   * @author <a href="http://restfb.com">Mark Allen</a>
   * @since 1.6.7
   */
  

  /**
   * Creates a connection with the given {@code jsonObject}.
   * 
   * @param facebookClient
   *          The {@code FacebookClient} used to fetch additional pages and map
   *          data to JSON objects.
   * @param json
   *          Raw JSON which must include a {@code data} field that holds a JSON
   *          array and optionally a {@code paging} field that holds a JSON
   *          object with next/previous page URLs.
   * @param connectionType
   *          Connection type token.
   * @throws FacebookJsonMappingException
   *           If the provided {@code json} is invalid.
   * @since 1.6.7
   */
  @SuppressWarnings("unchecked")
  public RestBAIterator(FacebookClient facebookClient, String json, Class<T> connectionType) {
    List<T> data = new ArrayList<T>();

    if (json == null)
      throw new FacebookJsonMappingException("You must supply non-null connection JSON.");

    JsonObject jsonObject = null;

    try {
      jsonObject = new JsonObject(json);
    } catch (JsonException e) {
      throw new FacebookJsonMappingException("The connection JSON you provided was invalid: " + json, e);
    }

    // Pull out data
    JsonArray jsonData = jsonObject.getJsonArray("data");
    for (int i = 0; i < jsonData.length(); i++)
      data.add(connectionType.equals(JsonObject.class) ? (T) jsonData.get(i) : facebookClient.getJsonMapper()
        .toJavaObject(jsonData.get(i).toString(), connectionType));

    // Pull out paging info, if present
    if (jsonObject.has("paging")) {
      JsonObject jsonPaging = jsonObject.getJsonObject("paging");
      previousPageUrl = jsonPaging.has("previous") ? jsonPaging.getString("previous") : null;
      nextPageUrl = jsonPaging.has("next") ? jsonPaging.getString("next") : null;
    } else {
      previousPageUrl = null;
      nextPageUrl = null;
    }

    this.data = unmodifiableList(data);
    this.facebookClient = facebookClient;
    this.connectionType = connectionType;
  }

  /**
   * Fetches the next page of the connection. Designed to be used by
   * {@link ConnectionIterator}.
   * 
   * @return The next page of the connection.
   * @since 1.6.7
   */
  protected RestBAIterator<T> fetchNextPage() {
//    return facebookClient.fetchConnectionPage(getNextPageUrl(), connectionType);
	  return null;
  }

  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return ReflectionUtils.toString(this);
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object object) {
    return ReflectionUtils.equals(this, object);
  }

  /**
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    return ReflectionUtils.hashCode(this);
  }

  /**
   * Data for this connection.
   * 
   * @return Data for this connection.
   */
  public List<T> getData() {
    return data;
  }

  /**
   * This connection's "previous page of data" URL.
   * 
   * @return This connection's "previous page of data" URL, or {@code null} if
   *         there is no previous page.
   * @since 1.5.3
   */
  public String getPreviousPageUrl() {
    return previousPageUrl;
  }

  /**
   * This connection's "next page of data" URL.
   * 
   * @return This connection's "next page of data" URL, or {@code null} if there
   *         is no next page.
   * @since 1.5.3
   */
  public String getNextPageUrl() {
    return nextPageUrl;
  }

  /**
   * Does this connection have a previous page of data?
   * 
   * @return {@code true} if there is a previous page of data for this
   *         connection, {@code false} otherwise.
   */
  public boolean hasPrevious() {
    return !isBlank(getPreviousPageUrl());
  }

  /**
   * Does this connection have a next page of data?
   * 
   * @return {@code true} if there is a next page of data for this connection,
   *         {@code false} otherwise.
   */
  public boolean hasNext() {
    return !isBlank(getNextPageUrl());
  }
}