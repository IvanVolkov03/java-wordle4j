package ru.yandex.practicum.exception;

public class InvalidWordLengthException extends Exception {
    public InvalidWordLengthException() {
        super("Слово должно состоять ровно из 5 букв.");
    }
}
