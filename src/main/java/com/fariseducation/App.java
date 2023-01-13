package com.fariseducation;

import com.fariseducation.Data.ObservedData.DataManager;
/**
 * Hello world!
 */
public final class App {
    /**
     * Says hello to the world.
     * @param args The arguments of the program.
     */
    public static void main(String[] args) {
        DataManager.getInstance();
        new PrimaryWindow();
    }
}
