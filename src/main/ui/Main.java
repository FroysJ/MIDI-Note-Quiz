package ui;

import model.Quiz;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting...");
        Quiz quiz = new Quiz();
        System.out.println("Quiz created...");
        for (int i = 21; i <= 108; i++) {
            System.out.println("Loop iteration " + i);
            quiz.nextQuestion();
        }
        System.out.println("Loop exited...");
        quiz.nextQuestion();
    }
}
