package com.skdvp.rest314;

import com.skdvp.rest314.entity.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class Application {

    private static final String URL = "http://91.241.64.178:7081/api/users";

    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        // ----------------------------------------------------------------------------------------------------- //
        // Первый запрос //
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> firstResponseEntity = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        System.out.println(firstResponseEntity);
        // Достаём из заголовка id сессии //
        HttpHeaders firstResponseHeaders = firstResponseEntity.getHeaders();
        String setCookie = firstResponseHeaders.getFirst(HttpHeaders.SET_COOKIE);
        // Присваиваем header постоянную id сессиии //
        headers.add("Cookie", setCookie);

        // ----------------------------------------------------------------------------------------------------- //
        // Добавляем пользователя //
        User userJames = new User((long) 3, "James", "Brown", (byte) 33);
        HttpEntity<User> requestBodyJames = new HttpEntity<>(userJames, headers);
        ResponseEntity<String> create = restTemplate.postForEntity(URL, requestBodyJames, String.class);

        System.out.println();
        System.out.println("Первая часть кода");
        System.out.println(create.getBody()); // например 5ebfeb
        System.out.println();

        // ----------------------------------------------------------------------------------------------------- //
        // Изменяем пользователя //
        User userJamesToThomas = new User((long) 3, "Thomas", "Shelby", (byte) 33);
        HttpEntity<User> requestBodyThomas = new HttpEntity<>(userJamesToThomas, headers);
        ResponseEntity<String> update = restTemplate.exchange(URL, HttpMethod.PUT, requestBodyThomas, String.class);

        System.out.println();
        System.out.println("Вторая часть кода");
        System.out.println(update.getBody()); // например 5ebfeb
        System.out.println();

        // ----------------------------------------------------------------------------------------------------- //
        // Удаляем пользователя //
        ResponseEntity<String> delete = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, requestBodyThomas, String.class);

        System.out.println();
        System.out.println("Третья часть кода");
        System.out.println(delete.getBody()); // например 5ebfeb
        System.out.println();
        // ----------------------------------------------------------------------------------------------------- //

    }
}
