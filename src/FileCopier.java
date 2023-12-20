import java.io.IOException;
import java.nio.file.*;

/**
 * Класс FileCopier предоставляет методы для копирования файлов и директорий.
 */
public class FileCopier {

    /**
     * Создает объекты Path на основе строковых путей.
     *
     * @param sourcePath Строковый путь исходного файла или директории.
     * @param destinationDirPath Строковый путь директории назначения.
     * @return Массив объектов Path, включающий исходный путь, директорию назначения и конечный путь.
     */
    public static Path[] createPaths(String sourcePath, String destinationDirPath) {
        Path source = Paths.get(sourcePath).toAbsolutePath().normalize();
        Path destinationDir = Paths.get(destinationDirPath).toAbsolutePath().normalize();
        Path destination = destinationDir.resolve(source.getFileName());
        return new Path[]{source, destinationDir, destination};
    }

    /**
     * Проверяет валидность путей для операции копирования.
     *
     * @param source Исходный путь файла или директории.
     * @param destinationDir Директория назначения.
     * @param destination Целевой путь файла или директории.
     * @return Возвращает true, если пути валидны; иначе false.
     */
    public static boolean validatePaths(Path source, Path destinationDir, Path destination) {
        return !(source.equals(destination) || !Files.exists(source) || !Files.isDirectory(destinationDir)
                || destination.startsWith(source));
    }

    /**
     * Выполняет копирование файла или директории из одного места в другое.
     *
     * @param source Путь к исходному файлу или директории.
     * @param destination Путь к директории назначения.
     * @throws IOException Если происходит ошибка ввода-вывода во время операции.
     */
    public static void copyFile(Path source, Path destination) throws IOException {
        if (Files.isDirectory(source)) {
            copyDirectory(source, destination);
        } else {
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
        }
        System.out.println("Копирование успешно: " + source + " -> " + destination);
    }

    /**
     * Рекурсивно копирует содержимое директории.
     *
     * @param source Исходная директория для копирования.
     * @param destination Целевая директория для копирования.
     * @throws IOException Если происходит ошибка ввода-вывода во время копирования.
     */
        private static void copyDirectory(Path source, Path destination) throws IOException {
            Files.walkFileTree(source, new CustomFileVisitor(source, destination));
        }
}
