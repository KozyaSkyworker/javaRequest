package com.company;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

//Подсчитать среднее количество частоты встречающихся символов (вывести в консоль). Определить символ,
// у которого частота будет наиболее приближена к полученному среднему значению
// (вывести в консоль сам символ и значение кода в UTF-8)

public class Main {

    public static void  urlRequest (String stringURL) throws IOException{
        URL url = new URL(stringURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null){
            content.append(inputLine);
        }

        in.close();

        con.disconnect();

        charactersFrequency(content);
    }

    public static void charactersFrequency(StringBuilder content){
        System.out.println("\nОтвет: " + content);

        Map<Character,Integer> frequencies = new HashMap<>();
        for (char ch : content.toString().toCharArray())
            frequencies.put(ch, frequencies.getOrDefault(ch, 0) + 1);

        int freqValueSum = 0;
        System.out.println("\nЧастоты: ");
        for (Map.Entry<Character,Integer> entry : frequencies.entrySet()){
            System.out.println(entry.getKey() + " : " + entry.getValue());
            freqValueSum += entry.getValue();
        }

        double freqArithmeticMean = (double) freqValueSum / frequencies.size();

        System.out.println("\nСреднее значение частоты: " + freqArithmeticMean);

        System.out.println("\nСимволы, которые соответствуют условию наиболее близкого значения частоты к среднему значанию: ");
        for (Map.Entry<Character,Integer> entry : frequencies.entrySet()){
            if(entry.getValue() == Math.round(freqArithmeticMean))
                System.out.print(entry.getKey() + "(" + (int) entry.getKey() + ") " );
        }
    }

    public static void main(String[] args) throws IOException {

        int randomNum = ThreadLocalRandom.current().nextInt(0, 1000);

        String url = "http://numbersapi.com/" + randomNum + "/trivia" ;

        urlRequest(url);
    }
}
