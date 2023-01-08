package com.fariseducation.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fariseducation.UIBase.UIAlert;

public class SpreadsheetReader {

    public static void readAndSave(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            int lineIndex = 0;
            boolean nextLineIsTutor = false;
            Tutor currentTutor = null;

            while(true) {
                String line = "";

                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    return;
                }

                if(lineIndex > 0) {
                    if(getAfterNextComma(getAfterNextComma(line)).charAt(0) == ',') {

                    }


                }

                lineIndex++;
            }

        } catch (FileNotFoundException e) {
            UIAlert.alert("Error reading file.");
        }
    }

    private static String getAfterNextComma(String s) {
        return s.substring(s.indexOf(','));
    }
}
