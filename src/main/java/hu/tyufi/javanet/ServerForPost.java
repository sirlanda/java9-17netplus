package hu.tyufi.javanet;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * A simple http server to accept post request and return OK.
 */
public class ServerForPost {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                String requestBody = new String(exchange.getRequestBody().readAllBytes());
                String[] params = requestBody.split("&");
                StringBuilder responseBuilder = new StringBuilder();
                responseBuilder.append("OK. Ezt postáztad: ");
                for (String param : params) {
                    String[] keyValue = param.split("=");
                    String key = keyValue[0];
                    String value = keyValue[1];
                    responseBuilder.append(key).append(": ").append(value).append(";");
                }
                String response = responseBuilder.toString();
                System.out.println("Mondom:" + response);
                String encodedResponse = URLEncoder.encode(response, StandardCharsets.UTF_8);
                exchange.sendResponseHeaders(200, encodedResponse.length());
                OutputStream os = exchange.getResponseBody();
                os.write(encodedResponse.getBytes());
                os.flush();
                os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        }
    }
}