import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BrokenLine {
    private final List<Point> points;  // Список точек, через которые проходит линия

    // Конструктор для создания ломаной без указания параметров
    public BrokenLine() {
        this.points = new ArrayList<>();  // Инициализируем пустой список точек
    }

    // Конструктор для создания ломаной с набором точек
    public BrokenLine(List<Point> points) {
        this.points = points;
    }

    // Метод для добавления массива точек в ломаную
    public void addPoints(Point[] newPoints) {
        Collections.addAll(points, newPoints);
    }

    // Метод для сдвига указанной точки ломаной линии
    public void shiftPoint(int index, double shiftX, double shiftY) {
        if (points.isEmpty()) {
            System.out.println("Невозможно сместить точку, так как ломаная линия пуста.");
            return;  // Выходим из метода, если ломаная линия пуста
        }
        if (index >= 0 && index < points.size()) {
            Point pointToShift = points.get(index);
            pointToShift.shift(shiftX, shiftY);  // Сдвигаем выбранную точку
        } else {
            System.out.println("Некорректный индекс для точки.");
        }
    }

    // Метод для вычисления длины ломаной линии
    public double getLength() {
        double length = 0.0;
        for (int i = 1; i < points.size(); i++) {
            length += calculateDistance(points.get(i - 1), points.get(i));
        }
        return length;
    }

    // Метод для расчета расстояния между двумя точками
    private double calculateDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    // Метод для текстового представления ломаной линии
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Линия [");
        for (int i = 0; i < points.size(); i++) {
            sb.append(points.get(i));
            if (i < points.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // Основной метод для работы программы
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Point> allPoints = new ArrayList<>();  // Список всех точек
        List<BrokenLine> allBrokenLines = new ArrayList<>();    // Список всех ломаных линий

        while (true) {
            System.out.println("\nВыберите действие:");
            System.out.println("1. Создать новые точки");
            System.out.println("2. Создать ломаную линию из существующих точек");
            System.out.println("3. Создать пустую ломаную линию");
            System.out.println("4. Добавить точки в существующую ломаную линию");
            System.out.println("5. Смещать точку существующей ломаной линии");
            System.out.println("6. Получить длину существующей ломаной линии");
            System.out.println("7. Просмотреть все точки");
            System.out.println("8. Просмотреть все ломаные линии");
            System.out.println("9. Выход");

            int choice = getValidInteger(scanner);

            switch (choice) {
                case 1 -> {
                    // Создание новых точек
                    System.out.print("Введите координаты точек (в формате X Y через пробел): ");
                    String input = scanner.nextLine();
                    List<Point> createdPoints = Point.createPoints(input); // Создание точек из введенных данных
                    allPoints.addAll(createdPoints); // Добавляем все созданные точки в общий список
                    System.out.println("Создано точек: " + createdPoints.size());
                }
                case 2 -> {
                    // Создаем ломаную линию из существующих точек
                    if (allPoints.isEmpty()) {
                        System.out.println("Сначала создайте точки.");
                    } else {
                        System.out.println("Выберите точки для ломаной линии (введите индексы через пробел):");
                        for (int i = 0; i < allPoints.size(); i++) {
                            System.out.println(i + ": " + allPoints.get(i));
                        }
                        String inputLine = scanner.nextLine();
                        String[] indices = inputLine.split("\\s+"); // Разделение по пробелам
                        List<Point> linePoints = new ArrayList<>();
                        for (String index : indices) {
                            int idx = Integer.parseInt(index.trim());
                            if (idx >= 0 && idx < allPoints.size()) {
                                linePoints.add(allPoints.get(idx));
                            } else {
                                System.out.println("Некорректный индекс: " + index);
                            }
                        }
                        BrokenLine brokenLine = new BrokenLine(linePoints);
                        allBrokenLines.add(brokenLine);  // Добавляем новую ломаную линию
                        System.out.println("Создана новая ломаная линия: " + brokenLine);
                    }
                }
                case 3 -> {
                    // Создать пустую ломаную линию
                    BrokenLine emptyLine = new BrokenLine();  // Создание пустой ломаной линии
                    allBrokenLines.add(emptyLine);
                    System.out.println("Создана пустая ломаная линия: " + emptyLine);
                }
                case 4 -> {
                    // Добавление точек в существующую ломаную линию
                    if (allBrokenLines.isEmpty()) {
                        System.out.println("Сначала создайте ломаную линию.");
                    } else {
                        System.out.println("Выберите ломаную линию (0 - " + (allBrokenLines.size() - 1) + "):");
                        for (int i = 0; i < allBrokenLines.size(); i++) {
                            System.out.println(i + ": " + allBrokenLines.get(i));
                        }
                        int lineIndex = getValidIntegerInRange(scanner, allBrokenLines.size() - 1);
                        BrokenLine selectedLine = allBrokenLines.get(lineIndex);
                        System.out.println("Введите новые точки для добавления (в формате X Y через пробел):");
                        String inputPoints = scanner.nextLine();
                        List<Point> newPoints = Point.createPoints(inputPoints); // Создание новых точек
                        selectedLine.addPoints(newPoints.toArray(new Point[0]));  // Добавляем новые точки в ломаную
                        System.out.println("Обновленная ломаная линия: " + selectedLine);
                    }
                }
                case 5 -> {
                    // Смещение точки существующей ломаной линии
                    if (allBrokenLines.isEmpty()) {
                        System.out.println("Сначала создайте ломаную линию.");
                    } else {
                        System.out.println("Выберите ломаную линию (0 - " + (allBrokenLines.size() - 1) + "):");
                        for (int i = 0; i < allBrokenLines.size(); i++) {
                            System.out.println(i + ": " + allBrokenLines.get(i));
                        }
                        int lineIndex = getValidIntegerInRange(scanner, allBrokenLines.size() - 1);
                        BrokenLine selectedLine = allBrokenLines.get(lineIndex);
                        if (selectedLine.points.isEmpty()) {
                            System.out.println("Невозможно сместить точку, так как ломаная линия пуста.");
                        } else {
                            System.out.println("Выберите индекс точки для смещения (0 - " + (selectedLine.points.size() - 1) + "):");
                            int pointIndex = getValidIntegerInRange(scanner, selectedLine.points.size() - 1);
                            System.out.print("Введите смещение X для точки: ");
                            double shiftX = getValidDouble(scanner);
                            System.out.print("Введите смещение Y для точки: ");
                            double shiftY = getValidDouble(scanner);
                            selectedLine.shiftPoint(pointIndex, shiftX, shiftY);  // Сдвигаем точку
                            System.out.println("Обновленная ломаная линия: " + selectedLine);
                        }
                    }
                }
                case 6 -> {
                    // Получение длины ломаной линии
                    if (allBrokenLines.isEmpty()) {
                        System.out.println("Сначала создайте ломаную линию.");
                    } else {
                        System.out.println("Выберите ломаную линию (0 - " + (allBrokenLines.size() - 1) + "):");
                        for (int i = 0; i < allBrokenLines.size(); i++) {
                            System.out.println(i + ": " + allBrokenLines.get(i));
                        }
                        int lineIndex = getValidIntegerInRange(scanner, allBrokenLines.size() - 1);
                        BrokenLine selectedLine = allBrokenLines.get(lineIndex);
                        double length = selectedLine.getLength();  // Получаем длину ломаной
                        System.out.println("Длина ломаной линии: " + length);
                    }
                }
                case 7 -> {
                    // Просмотр всех точек
                    System.out.println("Все созданные точки:");
                    for (int i = 0; i < allPoints.size(); i++) {
                        System.out.println(i + ": " + allPoints.get(i));
                    }
                }
                case 8 -> {
                    // Просмотр всех ломаных линий
                    System.out.println("Все созданные ломаные линии:");
                    for (int i = 0; i < allBrokenLines.size(); i++) {
                        System.out.println(i + ": " + allBrokenLines.get(i));
                    }
                }
                case 9 -> {
                    // Выход
                    System.out.println("Выход из программы.");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        }
    }

    // Метод для получения корректного целого числа от пользователя
    private static int getValidInteger(Scanner scanner) {
        int number;
        while (true) {
            String input = scanner.nextLine();
            try {
                number = Integer.parseInt(input);  // Преобразуем строку в целое число
                if (number >= 0) {
                    break;
                } else {
                    System.out.println("Введите неотрицательное целое число.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Введите целое число.");
            }
        }
        return number;
    }

    // Метод для получения корректного вещественного числа от пользователя
    private static double getValidDouble(Scanner scanner) {
        double number;
        while (true) {
            String input = scanner.nextLine();
            try {
                number = Double.parseDouble(input);  // Преобразуем строку в число
                break;
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Введите числовое значение.");
            }
        }
        return number;
    }

    // Метод для получения корректного целого числа в заданном диапазоне
    private static int getValidIntegerInRange(Scanner scanner, int max) {
        int number;
        while (true) {
            number = getValidInteger(scanner);
            if (number >= 0 && number <= max) {
                break;
            } else {
                System.out.println("Введите число в диапазоне от " + 0 + " до " + max + ".");
            }
        }
        return number;
    }
}
