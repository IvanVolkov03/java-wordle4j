package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

class WordleDictionaryTest {

    @Test
    void testContainsIgnore() {
        List<String> words = Arrays.asList("арбуз", "банан");
        WordleDictionary dictionary = new WordleDictionary(words);
        // Проверяем, что поиск работает для разных регистров
        assertTrue(dictionary.contains("АРБУЗ"), "Должно находить слово в верхнем регистре");
        assertTrue(dictionary.contains("арбуз"), "Должно находить слово в нижнем регистре");
        assertFalse(dictionary.contains("дыня"), "Не должно находить слово, которого нет в списке");
    }

    @Test
    void testGetRandomWord() {
        List<String> words = Arrays.asList("якорь");
        WordleDictionary dictionary = new WordleDictionary(words);
        assertEquals("якорь", dictionary.getRandomWord());
    }

    @Test
    void testDictionarySize() {
        List<String> words = Arrays.asList("один", "два", "три");
        WordleDictionary dictionary = new WordleDictionary(words);
        assertEquals(3, dictionary.size());
    }
}
