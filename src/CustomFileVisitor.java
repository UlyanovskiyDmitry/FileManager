import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * CustomFileVisitor используется для обхода файловой структуры
 * и копирования всех файлов и поддиректорий из одного места в другое.
 */
public class CustomFileVisitor extends SimpleFileVisitor<Path> {
    private Path source;
    private Path destination;

    /**
     * Конструктор CustomFileVisitor.
     *
     * @param source Исходная директория для копирования.
     * @param destination Целевая директория для копирования.
     */
    public CustomFileVisitor(Path source, Path destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Вызывается перед посещением директории.
     *
     * @param dir Текущая посещаемая директория.
     * @param attrs Атрибуты файла директории.
     * @return Результат продолжения или прерывания обхода.
     * @throws IOException Если произошла ошибка ввода-вывода.
     */
    @Override
    public FileVisitResult preVisitDirectory(final Path dir, final BasicFileAttributes attrs) throws IOException {
        Path targetDir = destination.resolve(source.relativize(dir));
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }
        return FileVisitResult.CONTINUE;
    }

    /**
     * Вызывается при посещении файла.
     *
     * @param file Текущий посещаемый файл.
     * @param attrs Атрибуты файла.
     * @return Результат продолжения или прерывания обхода.
     * @throws IOException Если произошла ошибка ввода-вывода.
     */
    @Override
    public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
        Files.copy(file, destination.resolve(source.relativize(file)), StandardCopyOption.REPLACE_EXISTING);
        return FileVisitResult.CONTINUE;
    }
}
