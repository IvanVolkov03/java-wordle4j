package ru.yandex.practicum;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.PrintWriter;
import java.util.Arrays;

class WordleGameTest {
    private WordleGame game;
    static class StubDictionary extends WordleDictionary {
        public StubDictionary() {
            // Передаем тестовый набор слов в конструктор родителя
            super(Arrays.asList("экран", "арена", "печка"));
        }
        @Override
        public String getRandomWord() {
            return "экран"; // Всегда возвращаем одно слово для предсказуемости теста
        }
    }

    @BeforeEach
    void setUp() {
        game = new WordleGame(new StubDictionary(), new PrintWriter(System.out, true));
    }

    @Test
    void testAnalyzeFullMatch() {
        // Все буквы на своих местах
        assertEquals("🟩🟩🟩🟩🟩", game.analyze("экран"));
    }

    @Test
    void testAnalyzePartialMatch() {
        // Слово "канат" против "экран"
        String result = game.analyze("канат");
        assertTrue(result.contains("🟨") || result.contains("🟩") || result.contains("⬜️"));
    }

    @Test
    void testAnalyzePartialMatch2() {
        // Слово "канат" против "экран"
        assertEquals("🟨🟨🟨🟩⬜", game.analyze("канат"));
    }

    @Test
    void testAnalyzeNoMatch() {
        // Нет букв из искомого слова
        assertEquals("⬜⬜⬜⬜⬜", game.analyze("пилот"));
    }

    @Test
    void testIsWin() {
        assertTrue(game.isWin("экран"));
        assertFalse(game.isWin("арена"));
    }
}
