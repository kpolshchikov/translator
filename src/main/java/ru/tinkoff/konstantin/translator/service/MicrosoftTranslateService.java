package ru.tinkoff.konstantin.translator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.tinkoff.konstantin.translator.model.Text;
import ru.tinkoff.konstantin.translator.utils.Parser;

import java.util.List;

@Service
public class MicrosoftTranslateService implements TranslateService {

    private static final String URL_FORMAT = "https://microsoft-translator-text.p.rapidapi.com/translate?from=%s&to=%s&api-version=3.0";
    @Value("${header.api.key.name}")
    private String apiKeyHeaderName;
    @Value("${header.api.key.value}")
    private String apiKeyValue;

    @Override
    public List translate(Text text, String from, String to)
            throws HttpClientErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(apiKeyHeaderName, apiKeyValue);
        HttpEntity<List<Text>> request =
                new HttpEntity<>(Parser.parse(text), headers);
        return (new RestTemplate()).postForObject(
                String.format(URL_FORMAT, from, to), request, List.class);
    }
}
