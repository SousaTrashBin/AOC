package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class AdventInputFetcher {
    Properties properties = new Properties();

    AdventInputFetcher(){
        try (InputStream propertiesInput = new FileInputStream(".properties")) {
            properties.load(propertiesInput);
            System.out.println("properties file successfully loaded");
        } catch (IOException e) {
            System.out.println("properties file couldn't be loaded");
            throw new RuntimeException(e);
        }
    }

    public void fetchAdventInput(int year,int day) {
        if (!properties.containsKey("session")){
            throw new RuntimeException("session key doesn't exist");
        }

        String sessionId = properties.getProperty("session");
        URI inputURI = URI.create("https://adventofcode.com/" + year + "/day/" + day + "/input");

        try(HttpClient httpClient = HttpClient.newHttpClient()){
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(inputURI)
                    .header("Cookie", "session=" + sessionId)
                    .GET()
                    .build();
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String input = response.body();

            Path outputPath = Path.of("inputFiles/Day" + day + ".txt");
            Files.createDirectories(outputPath.getParent());

            Files.writeString(outputPath, input, StandardOpenOption.CREATE_NEW);
            System.out.println("input fetched successfully");
        }catch (IOException | InterruptedException e) {
            System.err.println("error fetching input: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        AdventInputFetcher adventInputFetcher = new AdventInputFetcher();
        for (int day = 13; day <= 25; day++) {
            adventInputFetcher.fetchAdventInput(2020,day);
        }
    }
}
