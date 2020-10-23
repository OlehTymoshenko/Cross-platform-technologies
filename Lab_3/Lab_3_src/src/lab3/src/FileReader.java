package lab3.src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Класс для чтения массива байт из файла
 */
public class FileReader {

    private String _path;

    /**
     * Конструктор для создания объект для чтения байтов
     * из заданного в параметре файла
     *
     * @param path путь к файлу
     */
    public  FileReader(String path) {
        _path = path;
    }

    /**
     * Читает все содержимое файла и возращает это содержимое
     * как массив байтов
     *
     * @return содержимое файла в виде массива байтов
     */
    public byte[] getBytes() {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(_path);
            return fis.readAllBytes();
        }
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if(fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        return null;
    }
}
