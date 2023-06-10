package it.tino.javamovieapp.network;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.stream.Collectors;

@Component
public class HttpConnectionProvider implements ConnectionProvider {

    @Override
    public URLConnection getConnection(URL url) throws IOException {
        return url.openConnection();
    }

    @Override
    public String getResponseBody(HttpURLConnection connection) throws IOException {
        InputStream inputStream;
        inputStream = connection.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.lines().collect(Collectors.joining());
    }
}
