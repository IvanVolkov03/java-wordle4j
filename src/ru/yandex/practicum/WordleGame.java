package ru.yandex.practicum;

import java.io.PrintWriter;
import java.io.Serial;
import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ
в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее
не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */

public class WordleGame {
    private final String answer;
    private int steps = 6;
    private final WordleDictionary dictionary;
    private final List<String> history = new ArrayList<>();
    private final Set<String> givenHints = new HashSet<>();  // Слова, уже выданные как подсказки
    private final PrintWriter log;

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this.dictionary = dictionary;
        this.log = log;
        this.answer = dictionary.getRandomWord();
        log.println("Игра инициализирована. Загаданное слово: " + answer);
    }

    // Анализ совпадения букв
    public String analyze(String guess) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            char g = guess.charAt(i);
            if (g == answer.charAt(i)) {
                result.append("🟩"); // Верное место
            } else if (answer.indexOf(g) != -1) {
                result.append("🟨"); // Есть в слове
            } else {
                result.append("⬜"); // Нет в слове
            }
        }
        return result.toString();
    }

    // Логика подсказки
    public String getHint() {
        log.println("Пользователь запросил подсказку.");
        if (history.isEmpty()) return dictionary.getRandomWord();

        List<String> shuffledWords = new ArrayList<>(dictionary.getAllWords());
        Collections.shuffle(shuffledWords);

        for (String word : shuffledWords) {
            if (isValidHint(word) && !history.contains(word)
                    && !givenHints.contains(word)
                    && !word.equals(answer)) {
                givenHints.add(word);
                log.println("Выдана новая подсказка: " + word);
                return word;
            }
        }
        return dictionary.getRandomWord();
    }

    private boolean isValidHint(String word) {
        // Берем слова, где буквы на своих местах (из истории)
        for (String attempt : history) {
            for (int i = 0; i < 5; i++) {
                if (attempt.charAt(i) == answer.charAt(i)) {
                    if (word.charAt(i) != answer.charAt(i)) return false;
                }
            }
        }
        return true;
    }

    public void registerStep(String word) {
        steps--;
        history.add(word);
        log.println("Шаг зафиксирован: " + word + ". Осталось шагов: " + steps);
    }

    public boolean isWin(String word) {
        return answer.equals(word);
    }

    public boolean canContinue() {
        return steps > 0;
    }

    public int getSteps() {
        return steps;
    }

    public String getAnswer() {
        return answer;
    }

    public static class WordleGameException extends Exception {

        @Serial
        private static final long serialVersionUID = 2271712293092767565L;

        public WordleGameException(String message) {
            super(message);
        }
    }

    public static class WordNotFoundException extends WordleGameException {

        @Serial
        private static final long serialVersionUID = 2839830577705095759L;
        
        public WordNotFoundException(String word) {
            super("Слово '" + word + "' не найдено в словаре.");
        }
    }

    public static class InvalidWordLengthException extends WordleGameException {

        @Serial
        private static final long serialVersionUID = 2510651373681248114L;

        public InvalidWordLengthException() {
            super("Слово должно состоять ровно из 5 букв.");
        }
    }
}

