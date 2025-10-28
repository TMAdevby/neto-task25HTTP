package ru.netology.first;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.ConnectionConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpHeaders;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static final String REMOTE_SERVICE_URI = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
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

        List<Fact> facts = mapper.readValue(
                response.getEntity().getContent(),
                new TypeReference<List<Fact>>() {
                }
        );
//        facts.forEach(System.out::println);

        facts.stream().filter(fact -> fact.getUpvotes() != null && fact.getUpvotes() > 0)
                .forEach(System.out::println);

        response.close();
        httpClient.close();
    }
}