import java.io.File;
import java.text.DecimalFormat;

/**
 * Класс FileLister предоставляет методы для работы со списком файлов в директории,
 * включая вывод списка файлов и их размеров.
 */
public class FileLister {

    /**
     * Проверяет, является ли указанный путь директорией и существует ли она.
     *
     * @param folder Объект файла, представляющий директорию.
     * @return true, если директория существует и является директорией; иначе false.
     */
    public static boolean isValidDirectory(File folder) {
        return folder.exists() && folder.isDirectory();
    }

    /**
     * Отображает содержимое указанной директории, включая имена файлов и их размеры.
     *
     * @param folder Объект файла, представляющий директорию.
     */
    public static void listFilesWithSize(File folder) {
        File[] fileList = folder.listFiles();

        if (fileList != null && fileList.length > 0) {
            for (File file : fileList) {
                if (file.isFile()) {
                    System.out.println(file.getName() + " - " + readableFileSize(file.length()));
                } else if (file.isDirectory()) {
                    System.out.println(file.getName() + " - " + readableFileSize(getDirectorySize(file)));
                }
            }
        } else {
            System.out.println("Директория '" + folder.getPath() + "' пуста.");
        }
    }

    /**
     * Вычисляет размер директории, включая все вложенные файлы и папки.
     *
     * @param directory Директория, размер которой нужно вычислить.
     * @return Размер директории в байтах.
     */
    private static long getDirectorySize(File directory) {
        long size = 0;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    size += file.length();
                } else if (file.isDirectory()) {
                    size += getDirectorySize(file);
                }
            }
        }
        return size;
    }

    /**
     * Преобразует размер файла или директории в читаемый формат.
     *
     * @param size Размер в байтах.
     * @return Строка, представляющая размер в читаемом формате (например, КБ, МБ).
     */
    private static String readableFileSize(long size) {
        if(size <= 0) return "0";
        final String[] units = new String[]{"Б", "КБ", "МБ", "ГБ", "ТБ"};
        int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }
}
