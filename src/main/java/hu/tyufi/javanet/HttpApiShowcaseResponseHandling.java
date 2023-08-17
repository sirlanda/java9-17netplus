package hu.tyufi.javanet;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import static java.lang.System.out;

public class HttpApiShowcaseResponseHandling {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.google.com"))
                .GET()
                .build();


        HttpResponse<String> response =
                HttpClient.newHttpClient().send(
                        request,
                        BodyHandlers.ofString()
//                        BodyHandlers.ofByteArray()
//                        BodyHandlers.ofFile()
//                        BodyHandlers.ofLines() -- BodySubscriber<Stream<String>>
//                        BodyHandlers.fromLineSubscriber() -- Flow.Subscriber-hez adapter
//                        BodyHandlers.discarding() -- csudába a részletekkel
//                        BodyHandlers.replacing() -- mint az előző, de a válasz helyett a paraméterként megadott String-et adja vissza
                );

        out.println("Státusz: " + response.statusCode());
        out.println("URI: " + response.uri());
        out.println("Verzió: " + response.version());
        out.println("Headers: ");
        response.headers().map().forEach((k, v) -> out.println(k + ": " + v));
        out.println("Válasz: " + response.body());
    }
}
