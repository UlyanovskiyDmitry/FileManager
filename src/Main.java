import java.io.File;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Главный класс приложения, предоставляющий пользовательский интерфейс
 * для работы с функциями файлового менеджера.
 */
public class Main {

    /**
     * Точка входа в приложение.
     * Предоставляет меню с опциями для работы с файлами и директориями.
     *
     * @param args Аргументы командной строки.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\nФайловый менеджер:");
                System.out.println("1. Список файлов в директории");
                System.out.println("2. Копировать файл/папку");
                System.out.println("3. Выйти");
                System.out.print("Выберите действие: ");
                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        System.out.print("Введите путь к директории: ");
                        String dirPath = scanner.nextLine().trim();
                        File folder = new File(dirPath);

                        if (FileLister.isValidDirectory(folder)) {
                            FileLister.listFilesWithSize(folder);
                        } else {
                            System.err.println("Указанный путь не существует или не является директорией.");
                        }
                        break;
                    case "2":
                        System.out.print("Введите путь к исходному файлу/папке: ");
                        String sourcePath = scanner.nextLine().trim();

                        System.out.print("Введите путь директории назначения: ");
                        String destPath = scanner.nextLine().trim();

                        Path[] paths = FileCopier.createPaths(sourcePath, destPath);
                        Path source = paths[0];
                        Path destinationDir = paths[1];
                        Path destination = paths[2];

                        if (!FileCopier.validatePaths(source, destinationDir, destination)) {
                            System.err.println("Ошибка при копировании. Убедитесь, что пути введены корректно и не совпадают.");
                        } else {
                                FileCopier.copyFile(source, destination);
                        }
                        break;
                    case "3":
                        System.out.println("Выход из программы.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Неверный ввод. Пожалуйста, выберите действие из списка.");
                }
            } catch (Exception e) {
                System.err.println("Произошла ошибка: " + e.getMessage() + "\nПроверьте правильность написания и попробуйте еще раз.");
            }
        }
    }
}
