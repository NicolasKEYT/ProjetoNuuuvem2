package com.seuprojeto.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.*;
import java.net.http.*;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class ReportHandler
     implements RequestHandler<APIGatewayProxyRequestEvent,APIGatewayProxyResponseEvent> {

    private static final HttpClient http = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(
        APIGatewayProxyRequestEvent event,
        Context context) {

      try {
        String base = System.getenv("API_BASE_URL");
        URI uri = URI.create(base + "/filmes");
        HttpRequest req = HttpRequest.newBuilder(uri).GET().build();
        HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());

        // Desserializa lista completa
        List<Map<String,Object>> filmes = mapper.readValue(
          resp.body(),
          new TypeReference<List<Map<String,Object>>>(){}
        );

        // Calcula média
        double media = filmes.stream()
          .mapToDouble(f -> {
            Object v = f.get("imdbRatingAsDouble");
            return (v instanceof String)
                   ? Double.parseDouble((String)v)
                   : ((Number)v).doubleValue();
          })
          .average()
          .orElse(0.0);


        List<Map<String,Object>> topRated = filmes.stream()
          .filter(f -> {
            Object v = f.get("imdbRatingAsDouble");
            double rating = (v instanceof String)
                            ? Double.parseDouble((String)v)
                            : ((Number)v).doubleValue();
            return rating > 8.0;
          })
          .collect(Collectors.toList());

        
        List<Map<String,Object>> uniqueByTitle = new ArrayList<>();
        Set<String> seenTitles = new HashSet<>();
        for (Map<String,Object> f : topRated) {
          String title = (String) f.get("Title");
          if (seenTitles.add(title)) {
            uniqueByTitle.add(f);
          }
        }

     
        Map<String,Object> result = new HashMap<>();
        result.put("media", media);
        result.put("filmesNotaAlta", uniqueByTitle);

        String body = mapper.writeValueAsString(result);
        return new APIGatewayProxyResponseEvent()
                  .withStatusCode(200)
                  .withHeaders(Map.of("Content-Type","application/json"))
                  .withBody(body);

      } catch (Exception e) {
        String err;
        try {
          err = mapper.writeValueAsString(Map.of("error", e.getMessage()));
        } catch (Exception ex) {
          err = "{\"error\":\""+ex.getMessage()+"\"}";
        }
        return new APIGatewayProxyResponseEvent()
                  .withStatusCode(500)
                  .withHeaders(Map.of("Content-Type","application/json"))
                  .withBody(err);
      }
    }
}
