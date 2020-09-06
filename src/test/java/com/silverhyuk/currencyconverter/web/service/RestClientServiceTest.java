package com.silverhyuk.currencyconverter.web.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class RestClientServiceTest {

    @Autowired
    private RestClientService restClientService;

    @Test
    public void requestTest() throws Exception{
        //Given,  When
        String request = restClientService.runService();

        JsonElement jsonParser = JsonParser.parseString(request);
        JsonObject jsonObject = jsonParser.getAsJsonObject();
        JsonObject quotes = jsonObject.get("quotes").getAsJsonObject();

        //Then
        assertThat(request).isNotNull();
        assertThat(jsonObject.get("success").toString()).isEqualTo("true");
        assertThat(quotes.get("USDKRW")).isNotNull();
    }
}