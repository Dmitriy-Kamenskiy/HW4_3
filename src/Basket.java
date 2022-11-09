import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Basket {
    protected static String[] products;
    protected static int[] prices;

    protected static int[] productCounts;

    public Basket(String[] products, int[] prices) {
        Basket.products = products;
        Basket.prices = prices;
        productCounts = new int[Basket.prices.length];
    }

    public Basket(String[] products, int[] prices, int[] productCounts){
        Basket.products = products;
        Basket.prices = prices;
        Basket.productCounts = productCounts;
    }
    public void addToCart(int productNum, int amount) throws IOException {
        productCounts[productNum] += amount;
        saveTxt(Main.textFile);
    }

    public void printCart() {
        int productCount;
        int currentPrice;
        long sumProducts = 0;
        System.out.println("Ваша корзина:");
        for (int j = 0; j < productCounts.length; j++) {
            productCount = productCounts[j];
            currentPrice = prices[j];
            if (productCount == 0) {
                continue;
            }
            System.out.println(products[j] + " " + productCount +
                    " шт " + currentPrice + " руб/шт " + currentPrice * productCount + " руб в сумме");
            sumProducts += (long) currentPrice * productCount;
        }
        System.out.println("Итого " + sumProducts + " руб");
    }

    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile);) {
            for (String e : products)
                out.print(e + " ");
            out.println("");
            for (int e : prices)
                out.print(e + " ");
            out.println("");
            for (int e : productCounts)
                out.print(e + " ");
            out.println("");
            out.flush();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static Basket loadFromTxtFile(File textFile) throws IOException {
        FileReader reader = new FileReader(textFile);
        Scanner scanner = new Scanner(reader);
        ArrayList<String> arrayList = new ArrayList<>();
        String[] str1;
        String[] str2;
        String[] str3;
        while (scanner.hasNextLine()){
            arrayList.add(scanner.nextLine());
        }
        scanner.close();
        reader.close();

        str1 = arrayList.get(0).split(" ");
        str2 = arrayList.get(1).split(" ");
        str3 = arrayList.get(2).split(" ");

        for (int i = 0; i < str1.length; i++) {
            products[i] = str1[i];
            prices[i] = Integer.parseInt(str2[i]);
            productCounts[i] = Integer.parseInt(str3[i]);
        }
        return new Basket(products, prices, productCounts);
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }

//    public static int[] getProductCounts() {
//        return this.productCounts;
//    }
}