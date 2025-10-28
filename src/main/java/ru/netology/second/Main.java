package ru.netology.second;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final String REMOTE_SERVICE_URI =
            "https://api.nasa.gov/planetary/apod?api_key=t2o37YALxcWFej4uZraudbysyECuXCNtugeLqhPJ";
    public static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        ConnectionConfig connConfig = ConnectionConfig.custom()
                .setConnectTimeout(5000, TimeUnit.SECONDS)
                .setSocketTimeout(5000, TimeUnit.MILLISECONDS)
                .build();

        BasicHttpClientConnectionManager cm = new BasicHttpClientConnectionManager();
        cm.setConnectionConfig(connConfig);

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent("Service task 1")
                .setConnectionManager(cm)
                .build();
// создание объекта запроса с произвольными заголовками
        HttpGet request = new HttpGet(REMOTE_SERVICE_URI);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
// отправка запроса
        CloseableHttpResponse response = httpClient.execute(request);
// вывод полученных заголовков
//        Arrays.stream(response.getHeaders()).forEach(System.out::println);
// чтение тела ответа
//        String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
//        System.out.println(body);

        Answer answer = mapper.readValue(
                response.getEntity().getContent(),
                Answer.class
        );

        String url = answer.getUrl();
        System.out.println(url);
        String[] parseUrl = url.split("/");
        String picName = parseUrl[parseUrl.length - 1];

        HttpGet imageRequest = new HttpGet(url);
        try (CloseableHttpResponse imageResponse = httpClient.execute(imageRequest);
             InputStream inputStream = imageResponse.getEntity().getContent();
             BufferedOutputStream bos = new BufferedOutputStream(
                     Files.newOutputStream(Paths.get("D:\\Games", picName))
             )) {

            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
        }

        response.close();
        httpClient.close();
    }
}
