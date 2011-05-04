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
	


	public static void main(String[] args) throws ClientProtocolException,
			IOException {
						
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http",80, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(schemeRegistry);

		SyncBasicHttpContext httpContext = new SyncBasicHttpContext(new BasicHttpContext());
		
		String url = "https://mail.google.com/";
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
