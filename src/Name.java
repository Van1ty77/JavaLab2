import java.util.Scanner;

public class Name {
    private  String lastName;   // Фамилия
    private final String firstName;  // Личное имя
    private  String middleName; // Отчество

    // Конструктор для всех трех параметров
    public Name(String lastName, String firstName, String middleName) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
    }

    // Методы для получения данных
    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    // Сеттеры для изменения фамилии и отчества
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    // Метод toString для формирования строки
    @Override
    public String toString() {
        StringBuilder fullName = new StringBuilder();
        if (lastName != null && !lastName.isEmpty()) {
            fullName.append(lastName);
        }
        if (firstName != null && !firstName.isEmpty()) {
            if (fullName.length() > 0) fullName.append(" ");
            fullName.append(firstName);
        }
        if (middleName != null && !middleName.isEmpty()) {
            if (fullName.length() > 0) fullName.append(" ");
            fullName.append(middleName);
        }
        return fullName.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String lastName, firstName, middleName;

        // Ввод имени с клавиатуры
        System.out.println("Введите фамилию (нажмите Enter, чтобы пропустить):");
        lastName = scanner.nextLine().trim();

        System.out.println("Введите имя:");
        firstName = scanner.nextLine().trim();

        System.out.println("Введите отчество (нажмите Enter, чтобы пропустить):");
        middleName = scanner.nextLine().trim();

        // Проверка, если все три ввода пустые
        if (lastName.isEmpty() && firstName.isEmpty() && middleName.isEmpty()) {
            System.out.println("Вы не ввели ни одной строки. Программа завершена.");
            return; // Завершаем программу
        }

        // Создаем объект Name на основе ввода
        Name userName = new Name(
                lastName.isEmpty() ? null : lastName,
                firstName.isEmpty() ? null : firstName,
                middleName.isEmpty() ? null : middleName
        );

        // Вывод результата
        System.out.println("Вы ввели имя: " + userName);
    }
}
