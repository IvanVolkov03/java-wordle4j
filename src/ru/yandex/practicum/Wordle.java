package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import ru.yandex.practicum.WordleGame.WordleGameException;
import ru.yandex.practicum.WordleGame.WordNotFoundException;
import ru.yandex.practicum.WordleGame.InvalidWordLengthException;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */

public class Wordle {
    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(new FileWriter("game.log", StandardCharsets.UTF_8, true), true);
             Scanner scanner = new Scanner(System.in)) {
            log.println("\n--- ЗАПУСК НОВОЙ СЕССИИ ---");
            WordleDictionary dictionary = WordleDictionaryLoader.load("words_ru.txt");
            WordleGame game = new WordleGame(dictionary, log);
            System.out.println("Добро пожаловать в Wordle! (Enter для подсказки)");

            // Игровой цикл
            while (game.canContinue()) {
                System.out.print("\nВаше слово (попыток " + game.getSteps() + "): ");
                String input = scanner.nextLine().trim().toLowerCase().replace('ё', 'е');

                // Логика подсказки
                if (input.isEmpty()) {
                    String hint = game.getHint();
                    System.out.println("Компьютер советует: " + hint.toUpperCase());
                    continue;
                }
                try {
                    if (input.length() != 5) {
                        throw new InvalidWordLengthException();
                    }
                    if (!dictionary.contains(input)) {
                        throw new WordNotFoundException(input);
                    }
                    // Если проверки прошли
                    if (game.isWin(input)) {
                        System.out.println("ПОБЕДА! Вы угадали слово.");
                        log.println("Игрок победил.");
                        return;
                  }
                    String analysis = game.analyze(input);
                    System.out.println("Результат: " + input.toUpperCase());
                    System.out.println("Подсказка: " + analysis);
                    game.registerStep(input);
                } catch (WordleGameException e) {
                    // Обработка игровых ситуаций без прерывания программы
                    System.out.println("Внимание: " + e.getMessage());
                    log.println("Игровая ошибка: " + e.getMessage());
                }
            }
            System.out.println("\nВы проиграли. Загаданное слово было: " + game.getAnswer().toUpperCase());
            log.println("Попытки закончились. Проигрыш.");
        } catch (IOException e) {
            System.err.println("Ошибка работы с файлами: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла критическая ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
