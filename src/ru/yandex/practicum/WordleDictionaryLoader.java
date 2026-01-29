package ru.yandex.practicum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {
    public static WordleDictionary load(String fileName) throws IOException {
        List<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Очистка от пробелов
                String word = line.trim();

                // Приводим к нижнему регистру
                word = word.toLowerCase();

                // Заменяем ё на е
                word = word.replace('ё', 'е');

                // Фильтрация: нужны слова из 5 букв
                if (word.length() == 5) {
                    words.add(word);
                }
            }
        }
        if (words.isEmpty()) throw new IOException("Файл словаря пуст или не содержит подходящих слов.");
        return new WordleDictionary(words);
    }
}
