package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class WordleDictionaryLoaderTest {
    @TempDir
    Path tempDir;
    @Test
    void testLoadAndCleanData() throws IOException {
        // Создаем временный файл словаря
        Path tempFile = tempDir.resolve("test_words.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(tempFile)) {
            writer.write("Яблоко\n");  // 6 букв (должно игнорироваться)
            writer.write(" Поезд \n"); // 5 букв + пробелы (должно стать "поезд")
            writer.write("Алёна\n");   // 5 букв + ё (должно стать "алена")
            writer.write("Дом\n");     // 3 буквы (должно игнорироваться)
        }
        WordleDictionary dict = WordleDictionaryLoader.load(tempFile.toString());

        // Проверка результатов
        assertEquals(2, dict.size(), "Должно остаться только 2 слова");
        assertTrue(dict.contains("поезд"), "Слово должно быть очищено от пробелов");
        assertTrue(dict.contains("алена"), "Буква ё должна быть заменена на е");
        assertFalse(dict.contains("яблоко"), "Слова длиннее 5 букв не должны загружаться");
    }
}
