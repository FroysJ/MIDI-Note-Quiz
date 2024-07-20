package model;

public class Note {

    private int keyNumber; // [1,88], 20 less than MIDI note number
    private String noteName; // A0 to C8

    public Note(int keyNumber) {
        this.keyNumber = keyNumber;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(int keyNumber) {
        this.keyNumber = keyNumber;
    }

    public String getNoteName() {
        return noteName;
    }

    public void setNoteName(String noteName) {
        this.noteName = noteName;
    }
}
