package no.kristiania.httpclient;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpClient {

    private int statusCode = 200;
    private final Map<String, String> headers = new HashMap<>();
    private String responseBody;

    public HttpClient(String hostname, int port, String requestTarget) throws IOException{
        Socket socket = new Socket(hostname, port);

        String request = "GET " + requestTarget +" HTTP/1.1\r\n" +
                "Host: " + hostname + "\r\n\r\n";
        socket.getOutputStream().write(request.getBytes());

        String line = readLine(socket);
        System.out.println(line);
        String[] parts = line.toString().split(" ");
        statusCode = Integer.parseInt(parts[1]);


        String headerLine;
        while(!(headerLine = readLine(socket)).isEmpty()) {
           int colonPos = headerLine.indexOf(':');
           String headerName = headerLine.substring(0, colonPos);
           String headerValue = headerLine.substring(colonPos+1).trim();

           headers.put(headerName, headerValue);
        }
        int contentLength = Integer.parseInt(getResponseHeader("Content-Length"));
        StringBuilder body = new StringBuilder();
        for (int i = 0; i < contentLength; i++){
            body.append((char)socket.getInputStream().read());
        }
        this.responseBody = body.toString();
    }

    static String readLine(Socket socket) throws IOException {
        StringBuilder line = new StringBuilder();
        int c;
        while ((c = socket.getInputStream().read()) != -1) {
            if (c == '\r') {
                socket.getInputStream().read();
                break;
            }
            line.append((char)c);
        }
        return line.toString();
    }

    public static void main(String[] args) throws IOException {
            new HttpClient("urlecho.appspot.com", 80, "/echo?status=200&body=Hello%20world!");
        }

    public int getStatusCode() {
        return statusCode;
    }

    public String getResponseHeader(String headerName) {
        return headers.get(headerName);
    }

    public String getResponseBody() {
        return responseBody;
    }
}
