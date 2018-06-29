package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpUrlConnection {

    public static void main(String[] args) throws Exception {
        URL WebPage = new URL("https://www.yahoo.com/");
        URLConnection urlc = WebPage.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();
    }
}
