import java.io.*;
import java.util.Arrays;

public class Basket {
    protected String[] products; // массив названий продуктов
    protected int[] prices; // массив цен
    protected int[] productNumbers; // массив с количествами продуктов

    // конструктор, принимающий массив цен и названий продуктов;
    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        productNumbers = new int[products.length];
    }

    // конструктор, принимающий массивы продуктов, цен и количества. Для чтения из файла
    public Basket(String[] products, int[] prices, int[] productNumbers) {
        this.products = products;
        this.prices = prices;
        this.productNumbers = productNumbers;
    }

    //   addToCart(int productNum, int amount) - метод добавления amount штук продукта номер productNum в корзину;
    public void addToCart(int productNum, int amount) {
        productNumbers[productNum] += amount;
    }

    //   printCart() - метод вывода на экран покупательской корзины.
    public void printCart() {
        int sumProducts = 0; // переменная для подсчета итоговой суммы покупок
        System.out.println("Ваша корзина: ");
        for (int i = 0; i < products.length; i++) {
            if (productNumbers[i] != 0) {
                System.out.println(String.format("%s: %s шт по %s руб/шт ", products[i], productNumbers[i], prices[i]));
                sumProducts += prices[i] * productNumbers[i];
            }
        }
        System.out.println(String.format("Итого: %s рублей", sumProducts));
    }

    //   saveTxt(File textFile) - метод сохранения корзины в текстовый файл;
    //   использовать встроенные сериализаторы нельзя;
    public void saveTxt(File textFile) throws IOException {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String product : products) {
                out.printf(product + "|");
            }
            out.printf("\n");
            for (int i = 0; i < products.length; i++) {
                out.printf(prices[i] + "|");
            }
            out.printf("\n");
            for (int i = 0; i < products.length; i++) {
                out.printf(productNumbers[i] + "|");
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    //   static Basket loadFromTxtFile(File textFile) - статический(!) метод восстановления объекта корзины
    //   из текстового файла, в который ранее была она сохранена;
    public static Basket loadFromTxtFile(File textFile) {

        try (FileReader out = new FileReader(textFile)) {
            int currentByte = 0;
            StringBuilder s = new StringBuilder();
            while ((currentByte = out.read()) != -1) {
                s.append(Character.toString(currentByte));
            }

            String[] parts = s.toString().split("\\|");
// создаем массивы для получения корзины из файла
            String[] product = new String[parts.length / 3];
            int[] price = new int[parts.length / 3];
            int[] productNumber = new int[parts.length / 3];

            for (int i = 0; i < parts.length / 3; i++) {
                product[i] = parts[i];
                price[i] = Integer.parseInt(parts[i + parts.length / 3]);
                productNumber[i] = Integer.parseInt(parts[i + parts.length / 3 * 2]);
            }

            return new Basket(product, price, productNumber);

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
//   геттеры, которые вы посчитаете нужными.
}
