import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;
import org.apache.http.util.EntityUtils;


public class SSLTester {
	public static final int DEFAULT_MAX_CONNECTIONS = 10;
	public static final int DEFAULT_SOCKET_TIMEOUT = 10 * 1000;
	public static final int DEFAULT_MAX_RETRIES = 5;
	private static final String ENCODING = "UTF-8";
	private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
	private static final String ENCODING_GZIP = "gzip";

	private static int maxConnections = DEFAULT_MAX_CONNECTIONS;
	private static int socketTimeout = DEFAULT_SOCKET_TIMEOUT;
	private static DefaultHttpClient httpClient;
	private static HttpContext httpContext;

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		BasicHttpParams httpParams = new BasicHttpParams();
				
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http",80, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(schemeRegistry);

		httpContext = new SyncBasicHttpContext(new BasicHttpContext());
		httpClient = new DefaultHttpClient(cm, httpParams);
		String url = "https://prod.evidence.com/index.aspx?class=UIX&proc=Login";
		//url = "https://github.com";
		HttpGet httpget = new HttpGet(url);
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = httpclient.execute(httpget);
		HttpEntity entity = response.getEntity();
		System.out.println("----------------------------------------");
		System.out.println(response.getStatusLine());
		System.out.println(response.getLastHeader("Content-Encoding"));
		System.out.println(response.getLastHeader("Content-Length"));
		System.out.println("----------------------------------------");

		if (entity != null) {
			String content = EntityUtils.toString(entity);
			System.out.println(content);
			System.out.println("----------------------------------------");
			System.out.println("Uncompressed size: " + content.length());
		}
	}
}
