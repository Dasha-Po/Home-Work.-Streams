import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        File file = new File("basket.json");
        File fileLog = new File("log.csv");
        ClientLog clientLog = new ClientLog();
        String[] products = {"Хлеб", "Яблоки", "Молоко", "Гречневая крупа"}; // массив продуктов
        int[] prices = {100, 200, 300, 150}; // массив со стоимостью
        Basket basket = new Basket(products, prices);
        // проверяем, есть ли файл с корзиной
        if (file.exists()) {
            System.out.println("Корзина загружена из файла");
            basket = Basket.loadFromJsonFile(file);
            basket.printCart();
        } else {
            try {
                System.out.println("Файл для корзины был " + (file.createNewFile() ? "создан" : "не создан"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Список возможных товаров для покупки:");
        for (int i = 0; i < products.length; i++) {
            System.out.println(String.format("%s. %s %s рублей за шт", (i + 1), products[i], prices[i]));
        }

        while (true) {
            System.out.println("Выберите товар и количество или введите end");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                break;
            }
            String[] parts = input.split(" ");
            if (parts.length != 2) {
                System.out.println("Необходимо ввести 2 числа через пробел, например: 1 1");
                System.out.println("Попробуйте еще раз");
                continue;
            }
            try {
                if (Integer.parseInt(parts[0]) <= 0 || Integer.parseInt(parts[0]) > products.length) {
                    System.out.println("Вы ввели некорректный номер продукта, введите данные снова");
                    continue;
                }
                if (Integer.parseInt(parts[1]) < 0) {
                    System.out.println("Вы ввели отрицательное количество товаров, попробуйте еще раз");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Введены не числа. Для верной работы нужно ввести два числа через пробел, например: 1 1");
                continue;
            }
            int productNumber = Integer.parseInt(parts[0]) - 1; // номер продукта в исходном массиве
            int productCount = Integer.parseInt(parts[1]); // количество продукта, которое добавим в корзину
            basket.addToCart(productNumber, productCount);
            basket.saveJson(file);
            clientLog.log(productNumber, productCount);
        }
        basket.printCart();
        clientLog.exportAsCSV(fileLog);
    }
}
