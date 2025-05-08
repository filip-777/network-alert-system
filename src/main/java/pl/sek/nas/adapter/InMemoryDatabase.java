package pl.sek.nas.adapter;

import java.util.HashMap;
import java.util.LinkedList;

class InMemoryDatabase {
    static final HashMap<String, LinkedList<String>> DATABASE = new HashMap<>();

    private InMemoryDatabase() {
    }
}
