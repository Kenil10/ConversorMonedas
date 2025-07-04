import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import static java.net.http.HttpClient.newHttpClient;

public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner eleccion = new Scanner(System.in);

        while(true){

            System.out.println("**********"+" BIENVENIDO AL CONVERSOR DE MONEDAS " + "**************");
            System.out.println("1) Dólares USD >>> soles PEN");
            System.out.println("2) soles PEN >>> Dólares USD");
            System.out.println("3) Dólares USD >>> Real Brasileño BRL");
            System.out.println("4) Real Brasileño BRL >>> Dólares USD");
            System.out.println("5) Peso Colombiano COP >>> Peso Mexicano MXN");
            System.out.println("6) Peso Mexicano MXN >>> Peso Colombiano COP");
            System.out.println("7) Salir ");
            System.out.println("*********************************************************************");
            System.out.println("Elija una opción válida y escriba el número:");

            var opcion = eleccion.nextInt();

            if (opcion == 7){
                break;
            }else{
                System.out.println("Ingrese el monto a Convertir: ");

                var monto = eleccion.nextInt();

                System.out.println("usted eligió la opción N°: " + opcion + " y el monto a convertir  es: " + monto);

                String moneda = "";
                String convertido = "";

                switch(opcion){
                    case 1:
                        moneda = "USD";
                        convertido = "PEN";
                        break;
                    case 2:
                        moneda = "PEN";
                        convertido = "USD";
                        break;
                    case 3:
                        moneda = "USD";
                        convertido = "BRL";
                        break;
                    case 4:
                        moneda = "BRL";
                        convertido = "USD";
                        break;
                    case 5:
                        moneda = "COP";
                        convertido = "MXN";
                        break;
                    case 6:
                        moneda = "MXN";
                        convertido = "COP";
                        break;

                }

                String direccion = "https://v6.exchangerate-api.com/v6/cde36c64e152bb45f591a599/latest/" + moneda;

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();

                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

                JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

                String codigo = convertido;
                double value = conversionRates.get(codigo).getAsDouble();

                var valorFinal = monto*value;

                System.out.println("El Valor de: " + monto + " "+ moneda + " corresponde al valor final de >>> " + valorFinal + " " + codigo);
                }

        }
    }
}
