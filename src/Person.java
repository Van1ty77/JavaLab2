import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Person {
    private final Name name;    // Имя человека
    private final int height;    // Рост человека
    private Person father;       // Отец (другой человек)

    // Конструктор для Person, принимающий объект Name и рост
    public Person(Name name, int height) {
        this.name = name;
        this.height = height;
    }

    // Метод для установки отца
    public void setFather(Person father) {
        this.father = father;

        // Если у человека нет фамилии, берем фамилию отца
        if ((name.getLastName() == null || name.getLastName().isEmpty()) && father != null) {
            this.name.setLastName(father.name.getLastName());
        }

        // Если у человека нет отчества, берем имя отца и добавляем суффикс "ович"
        if ((name.getMiddleName() == null || name.getMiddleName().isEmpty()) && father != null) {
            if (father.name.getFirstName() != null) {
                this.name.setMiddleName(father.name.getFirstName() + "ович");
            }
        }
    }

    // Метод toString для вывода имени и роста человека с учетом отца
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(name.toString()).append(", рост: ").append(height);
        if (father != null) {
            result.append(", отец: ").append(father.name.toString());
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Person> people = new ArrayList<>(); // Список всех созданных людей

        // Основное меню выбора
        while (true) {
            System.out.println("\nВыберите опцию:");
            System.out.println("1. Создать человека");
            System.out.println("2. Задать отца существующему человеку");
            System.out.println("3. Показать всех людей");
            System.out.println("4. Выход");
            System.out.print("Введите номер опции: ");
            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" -> {
                    System.out.println("Создание нового человека:");
                    Person person = createPerson(scanner);
                    people.add(person); // Добавляем созданного человека в список
                    System.out.println("\nЧеловек добавлен: " + person);
                }
                case "2" -> {
                    if (people.size() < 2) {
                        System.out.println("Для этого действия нужно как минимум 2 человека (человек и отец).");
                    } else {
                        assignFather(scanner, people);
                    }
                }
                case "3" -> showAllPeople(people);
                case "4" -> {
                    System.out.println("Выход.");
                    return;
                }
                default -> System.out.println("Неверный ввод. Попробуйте снова.");
            }
        }
    }

    // Метод для создания объекта Person через ввод с клавиатуры (без отца)
    private static Person createPerson(Scanner scanner) {
        // Ввод имени с клавиатуры
        System.out.print("Введите фамилию (нажмите Enter, чтобы пропустить): ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Введите имя: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Введите отчество (нажмите Enter, чтобы пропустить): ");
        String middleName = scanner.nextLine().trim();

        // Создаем объект Name
        Name name = new Name(
                lastName.isEmpty() ? null : lastName,
                firstName.isEmpty() ? null : firstName,
                middleName.isEmpty() ? null : middleName
        );

        // Ввод роста с проверкой
        int height;
        while (true) {
            System.out.print("Введите рост: ");
            try {
                height = Integer.parseInt(scanner.nextLine().trim());
                if (height > 0) {
                    break;  // Если рост положительный, выходим из цикла
                } else {
                    System.out.println("Рост должен быть положительным числом. Попробуйте снова.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод. Введите числовое значение.");
            }
        }

        // Создаем и возвращаем объект Person
        return new Person(name, height);
    }

    // Метод для назначения отца существующему человеку
    private static void assignFather(Scanner scanner, List<Person> people) {
        System.out.println("\nСписок доступных людей:");
        for (int i = 0; i < people.size(); i++) {
            System.out.println((i + 1) + ". " + people.get(i).toString());
        }

        // Ввод индекса человека, которому будет задан отец
        System.out.print("Введите номер человека, которому хотите задать отца: ");
        int childIndex = Integer.parseInt(scanner.nextLine()) - 1;

        // Ввод индекса отца
        System.out.print("Введите номер человека, которого хотите сделать отцом: ");
        int fatherIndex = Integer.parseInt(scanner.nextLine()) - 1;

        // Установка отца для выбранного человека
        Person child = people.get(childIndex);
        Person father = people.get(fatherIndex);
        child.setFather(father);

        System.out.println("\nОбновленный человек: " + child);
    }

    // Метод для вывода всех людей
    private static void showAllPeople(List<Person> people) {
        if (people.isEmpty()) {
            System.out.println("Пока что людей нет.");
        } else {
            System.out.println("\nСписок всех людей:");
            for (Person person : people) {
                System.out.println(person);
            }
        }
    }
}