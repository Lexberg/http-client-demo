package no.kristiania.httpclient;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class HttpServerTest {
    @Test
    void shouldReturnSuccessfulErrorCode() throws IOException {
        HttpServer Server = new HttpServer(10001);
        HttpClient client = new HttpClient("localhost", 10001, "/echo");
        assertEquals(200, client.getStatusCode());
    }
    @Test
    void shouldReturnUnsuccessfulErrorCode() throws IOException {
        HttpServer Server = new HttpServer(10002);
        HttpClient client = new HttpClient("localhost", 10002, "/echo?status=404");
        assertEquals(404, client.getStatusCode());
    }
}