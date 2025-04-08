import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConversorDeMoedas {
    public static void main(String[] args) {
        String moedaOrigem = "USD";
        String moedaDestino = "BRL";
        double valor = 1.0;

        try {
            String apiKey = "9bad948558e1aa9a554542e9";
            String urlStr = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + moedaOrigem;

            URL url = new URL(urlStr);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            StringBuilder resposta = new StringBuilder();
            String linha;

            while ((linha = in.readLine()) != null) {
                resposta.append(linha);
            }
            in.close();

            JSONObject json = new JSONObject(resposta.toString());
            System.out.println("Resposta da API: " + json.toString(2));

            JSONObject rates = json.getJSONObject("conversion_rates");
            double taxa = rates.getDouble(moedaDestino);
            double resultado = valor * taxa;

            System.out.printf("Resultado da conversão: %.2f %s = %.2f %s%n", valor, moedaOrigem, resultado, moedaDestino);

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
