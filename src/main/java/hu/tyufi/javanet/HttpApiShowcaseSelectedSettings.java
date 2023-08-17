package hu.tyufi.javanet;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import static java.lang.System.out;

public class HttpApiShowcaseSelectedSettings {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.google.com"))
                .GET()
                .build();


        HttpClient httpClient = HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("user", "pass".toCharArray());
                    }
                })
                .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_NONE))
                .cookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ALL))
                .build();

        HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

        out.println("Státusz: " + response.statusCode());
        out.println("Válasz: " + response.body());
        out.println("Cookie-k: ");
        httpClient.cookieHandler().ifPresent(
                ch -> ((CookieManager) ch).getCookieStore().getCookies().forEach(out::println));


    }
}
