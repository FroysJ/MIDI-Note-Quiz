package model;

import java.util.ArrayList;
import java.util.Random;

public class Quiz {

    private ArrayList<Integer> unanswered; // keyNumbers of notes unanswered
    private Note currNote; // current note
    private Random random;
    //private SoundPlayer soundPlayer;

    public Quiz() {
        currNote = null;
        random = new Random();
        unanswered = new ArrayList<>();
        for (int i = 21; i <= 108; i++) {
            unanswered.add(i);
        }
    }

    public void nextQuestion() {
        if (currNote != null) {
            unanswered.remove((Integer) currNote.getKeyNumber());
        }
        if (!unanswered.isEmpty()) {
            currNote = new Note(unanswered.get(random.ints(0, unanswered.size()).findFirst().getAsInt()));
        } else {
            System.out.println("All answered.");
            return;
        }
        System.out.println(currNote.getKeyNumber());
    }

}
