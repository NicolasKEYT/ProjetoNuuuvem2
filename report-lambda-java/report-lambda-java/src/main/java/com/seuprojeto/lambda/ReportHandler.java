package com.seuprojeto.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.*;
import java.net.http.*;
import java.net.URI;
import com.fasterxml.jackson.databind.*;
import java.util.*;

public class ReportHandler
    implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private static final HttpClient http = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public APIGatewayProxyResponseEvent handleRequest(
        APIGatewayProxyRequestEvent event, Context context) {

        try {
            String base = System.getenv("API_BASE_URL");
            URI uri = URI.create(base + "/filmes");
            HttpRequest req = HttpRequest.newBuilder(uri).GET().build();
            HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());

            Filme[] filmes = mapper.readValue(resp.body(), Filme[].class);
            Map<String,Object> stats = new HashMap<>();
            stats.put("total", filmes.length);
            double anoMedio = Arrays.stream(filmes)
                                    .mapToInt(Filme::getAno)
                                    .average().orElse(0);
            stats.put("anoMedio", anoMedio);

            return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(mapper.writeValueAsString(stats));

        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent()
                .withStatusCode(500)
                .withBody("{\"error\":\""+e.getMessage()+"\"}");
        }
    }
}
