package ru.yandex.practicum;

import java.io.PrintWriter;
import java.io.Serial;
import java.util.*;

public class WordleGame {
    private final String answer;
    private int steps = 6;
    private final WordleDictionary dictionary;
    private final List<String> history = new ArrayList<>();
    private final Set<String> givenHints = new HashSet<>();  // Слова, уже выданные как подсказки
    private final PrintWriter log;
    private static final int TARGET_WORD_LENGTH = 5;

    public WordleGame(WordleDictionary dictionary, PrintWriter log) {
        this.dictionary = dictionary;
        this.log = log;
        this.answer = dictionary.getRandomWord();
        log.println("Игра инициализирована. Загаданное слово: " + answer);
    }

    // Анализ совпадения букв
    public String analyze(String guess) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < TARGET_WORD_LENGTH; i++) {
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

        // Один раз создам "маску" известных зеленых букв
        // Это займет очень мало, максимум 5 * 5 = 25 итераций (количество попыток * на кол-во букв)
        char[] mask = new char[TARGET_WORD_LENGTH];
        for (String attempt : history) {
            for (int i = 0; i < TARGET_WORD_LENGTH; i++) {
                if (attempt.charAt(i) == answer.charAt(i)) {
                    mask[i] = answer.charAt(i);
                }
            }
        }

        List<String> shuffledWords = new ArrayList<>(dictionary.getAllWords());
        Collections.shuffle(shuffledWords);

        for (String word : shuffledWords) {
            if (isValidHint(word, mask) && !history.contains(word)
                    && !givenHints.contains(word)
                    && !word.equals(answer)) {
                givenHints.add(word);
                log.println("Выдана новая подсказка: " + word);
                return word;
            }
        }
        return dictionary.getRandomWord();
    }

    private boolean isValidHint(String word, char[] mask) {
        for (int i = 0; i < TARGET_WORD_LENGTH; i++) {
            // Если в маске есть буква, а в слове на этом месте — нет, слово не подходит
            if (mask[i] != '\0' && word.charAt(i) != mask[i]) {
                return false;
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


}

