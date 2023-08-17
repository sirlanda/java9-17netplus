package hu.tyufi.javanet;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static java.lang.System.out;

/**
 * Hát ez marhára nem csinálja azt, amit akarok.
 * Lehet, hogy a szerver nem támogatja a push-olást?
 */
public class HttpApiShowcasePushPromiseHandler {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://go.dev/serverpush"))
                .build();
        CompletableFuture<HttpResponse<String>> responseFuture = client.sendAsync(request,
                BodyHandlers.ofString(),
                pushPromiseHandler());
        HttpResponse<String> response = responseFuture.get(); // Megvárunk!
        out.println("Státusz: " + response.statusCode());
        out.println("Válasz: " + response.body());
    }

    private static HttpResponse.PushPromiseHandler<String> pushPromiseHandler() {
        return (HttpRequest initiatingRequest,
                HttpRequest pushPromiseRequest,
                Function<BodyHandler<String>,
                        CompletableFuture<HttpResponse<String>>> acceptor) -> {
            acceptor.apply(BodyHandlers.ofString())
                    .thenAccept(resp -> {
                        out.println(" Pushed response: " + resp.uri() + ", headers: " + resp.headers());
                    })
                    .join();
            out.println("Promise request: " + pushPromiseRequest.uri());
            out.println("Promise request: " + pushPromiseRequest.headers());
        };
    }
}
