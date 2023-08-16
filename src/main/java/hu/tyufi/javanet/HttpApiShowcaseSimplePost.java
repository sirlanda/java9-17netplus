package hu.tyufi.javanet;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static java.lang.System.out;

public class HttpApiShowcaseSimplePost {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8000/"))
                .POST(HttpRequest.BodyPublishers.ofString("q=lovas"))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        out.println("Státusz: " + response.statusCode());
        out.println("Válasz: " + response.body());
    }
}
