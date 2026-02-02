package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleDictionary {
    private final Random random = new Random();
    private final List<String> words;

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    // Проверка, есть ли слово в словаре
    public boolean contains(String word) {
        return words.contains(word.toLowerCase());
    }

    // Выбор случайного слова для загадывания
    public String getRandomWord() {
        if (words.isEmpty()) return null;
        return words.get(random.nextInt(words.size()));
    }

    public List<String> getAllWords() {
        return new ArrayList<>(words);
    }

    public int size() {
        return words.size();
    }
}
