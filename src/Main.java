
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static File textFile;
    public static void main(String[] args) throws IOException {

        Basket basket = new Basket(new String[] {"Хлеб", "Яблоки", "Молоко"}, new int[] {100, 200, 300});
        textFile = new File("basket.txt");
        if (textFile.exists()) {
            System.out.println("У Вас уже есть корзина для покупок");
            Basket.loadFromTxtFile(textFile);
        }
        else {
            System.out.println("Создана корзина для покупок");
        }

        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < basket.getProducts().length; i++) {
            System.out.println(i + 1 + ". " + basket.getProducts()[i] + " " + basket.getPrices()[i] + " руб/шт");
        }
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int productNumber = 0;
            int productCount = 0;
            System.out.println("Выберите товар и количество или введите `end`");
            String inputString = scanner.nextLine();
            if ("end".equals(inputString)) {
                basket.printCart();
                break;
            }
            String[] parts = inputString.split(" ");
            productNumber = Integer.parseInt(parts[0]) - 1;
            productCount = Integer.parseInt(parts[1]);
            basket.addToCart(productNumber,productCount);
        }
    }
}
