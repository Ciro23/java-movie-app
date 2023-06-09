package it.tino.javamovieapp.network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public interface ConnectionProvider {

    URLConnection getConnection(URL url) throws IOException;

    String getResponseBody(HttpURLConnection connection) throws IOException;
}
