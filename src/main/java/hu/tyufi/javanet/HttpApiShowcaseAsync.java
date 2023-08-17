package hu.tyufi.javanet;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.out;

public class HttpApiShowcaseAsync {

    public static void main(String[] args) throws URISyntaxException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        HttpClient client = HttpClient.newBuilder()
                .executor(executorService)
                .build();

        List<URI> targets = Arrays.asList(
                new URI("https://postman-echo.com/get?foo1=bar1"),
                new URI("https://www.google.com"));

        List<CompletableFuture<String>> futures = targets.stream()
                .map(target -> client
                        .sendAsync(
                                HttpRequest.newBuilder(target).GET().build(),
                                HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body))
                .toList();

        out.println("Válaszok: ");
        futures.forEach(future -> out.println(future.join())); // join() blokkol, amíg a future be nem fejeződik
    }
}
