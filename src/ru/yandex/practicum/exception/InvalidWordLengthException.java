package ru.yandex.practicum.exception;

import ru.yandex.practicum.WordleGame;

public class InvalidWordLengthException extends Exception {
    public InvalidWordLengthException() {
        super("Слово должно состоять ровно из 5 букв.");
    }
}
