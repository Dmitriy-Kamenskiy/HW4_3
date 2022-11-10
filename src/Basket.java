import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.toList;

public class Basket implements Serializable{
    protected static String[] products;
    protected static int[] prices;

    protected static int[] productCounts;

    public Basket(String[] products, int[] prices) {
        Basket.products = products;
        Basket.prices = prices;
        productCounts = new int[Basket.prices.length];
    }

    public Basket(String[] products, int[] prices, int[] productCounts) {
        Basket.products = products;
        Basket.prices = prices;
        Basket.productCounts = productCounts;
    }

    public void addToCart(int productNum, int amount) {
        productCounts[productNum] += amount;
        saveBin(Main.binFile);
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

    public void saveBin(File file)  {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))){
            oos.writeObject(products);
            oos.writeObject(prices);
            oos.writeObject(productCounts);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void loadFromBinFile(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        products = (String[]) ois.readObject();
        prices = (int[]) ois.readObject();
        productCounts = (int[]) ois.readObject();
    }

    public void saveTxt(File textFile) {
        try (PrintWriter out = new PrintWriter(textFile)) {
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
        while (scanner.hasNextLine()) {
            arrayList.add(scanner.nextLine());
        }
        scanner.close();
        reader.close();

        ArrayList<String[]> stringArrayList = (ArrayList<String[]>) arrayList.stream()
                .map((s) -> s.split(" ")).collect(toList());
        products = stringArrayList.get(0).clone();
        for (int i = 0; i < products.length; i++) {
            prices[i] = Integer.parseInt(stringArrayList.get(1)[i]);
            productCounts[i] = Integer.parseInt(stringArrayList.get(2)[i]);
        }
        return new Basket(products, prices, productCounts);
    }

    public String[] getProducts() {
        return products;
    }

    public int[] getPrices() {
        return prices;
    }
}