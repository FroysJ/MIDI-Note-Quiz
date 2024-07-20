package model;

import java.util.AbstractMap;

public class Note {

    private final String[] noteNames = {"C", "C#/Db", "D", "D#/Eb", "E", "F", "F#/Gb", "G", "G#/Ab", "A", "A#/Bb", "B"};
    private int keyNumber; // [21,108], MIDI
    private AbstractMap.SimpleEntry<String, Integer> noteID; // A0 to C8

    // keyNumber must be in [21,108]
    public Note(int keyNumber) {
        this.keyNumber = keyNumber;
        String key = noteNames[keyNumber % 12];
        Integer octave = ((keyNumber / 12) - 1);
        this.noteID = new AbstractMap.SimpleEntry<>(key, octave);
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public void setKeyNumber(int keyNumber) {
        this.keyNumber = keyNumber;
    }

    public AbstractMap.SimpleEntry<String, Integer> getNoteID() {
        return noteID;
    }

    public void setNoteID(String key, Integer octave) {
        noteID = new AbstractMap.SimpleEntry(key, octave);
    }

    public String[] getNoteNames() {
        return noteNames;
    }
}
