package model;

import java.util.ArrayList;
import java.util.Random;

public class Quiz {

    private final String[] noteNames = {"C", "C#/Db", "D", "D#/Eb", "E", "F", "F#/Gb", "G", "G#/Ab", "A", "A#/Bb", "B"};
    private ArrayList<Integer> unanswered; // keyNumbers of notes unanswered
    private Note currNote; // current note
    private Random random;
    private int score = 0;
    private int answered = 0;
    private boolean finished = false;
    private MidiSynth soundPlayer;

    public Quiz() {
        soundPlayer = new MidiSynth();
        soundPlayer.open();
        currNote = null;
        random = new Random();
        unanswered = new ArrayList<>();
        for (int i = 21; i <= 108; i++) {
            unanswered.add(i);
        }
    }

    public void nextQuestion() {
        if (finished) {
            return;
        }
        if (currNote != null) {
            unanswered.remove((Integer) currNote.getKeyNumber());
            addAnswered();
        }
        if (!unanswered.isEmpty()) {
            currNote = new Note(unanswered.get(random.ints(0, unanswered.size()).findFirst().getAsInt()));
        } else {
            finished = true;
        }
    }

    public void addScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public void addAnswered() {
        answered++;
    }

    public int getAnswered() {
        return answered;
    }

    public Note getCurrNote() {
        return currNote;
    }

    public boolean checkAnswer(String name, int octave) {
        return (getCurrNote().getNoteID().getKey().equals(name)) && (getCurrNote().getNoteID().getValue() == octave);
    }

    public String[] getNoteNames() {
        return noteNames;
    }

    public boolean isFinished() {
        return finished;
    }

    public void play() {
        soundPlayer.play(0, currNote.getKeyNumber(), 64);
    }
}
