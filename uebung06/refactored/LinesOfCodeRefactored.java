package refactored;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class LinesOfCodeRefactored {
    /**
     * Die Methode liefert die Anzahl "echter Programmzeilen" in einer
     * Java-Datei.
     * @param filename die Datei, welche ueberpreft werden soll.
     * @return die Anzahl echter Programmzeilen in der uebergebenen Datei.
     * Wenn ein Fehler auftritt, oder es keine "echten Programmzeilen" gitb, gibt
     * sie -1 zurueck.
     */
    public static int countRealCodeLines(String filename){

        int linesOfCodeCounter = 0;

        //moegliche Fehlerquellen abfangen
        if (filename == null){
            linesOfCodeCounter = -1;
        }else{
            if (!new File(filename).exists()){
                linesOfCodeCounter = -1;
            }

            if (!new File(filename).canRead()){
                linesOfCodeCounter = -1;
            }
        }
        if(linesOfCodeCounter == -1){
            return linesOfCodeCounter;
        }

		/*
		 * ab hier wird die Datei analysiert
		 */
        try {
            BufferedReader file = new BufferedReader(new FileReader(filename));

            String oneLine;
            String oneCodeLineWithoutSpaces;
            boolean multiLineComment = false;
            int comment1, comment2, comment3;

            while (file.ready()) {
                oneLine = file.readLine();
                oneCodeLineWithoutSpaces = deleteSpaces(oneLine);
                if (!oneCodeLineWithoutSpaces.equals("")){
                    comment1 = analyzeString(oneCodeLineWithoutSpaces, "//");
                    comment2 = analyzeString(oneCodeLineWithoutSpaces, "/*");
                    comment3 = analyzeString(oneCodeLineWithoutSpaces, "*/");
                    if(comment2 == 1){
                        multiLineComment = true;
                    }
                    if(comment3 == 1){
                        multiLineComment = false;
                    }

                    if (comment1 != 1 && comment2 != 1 && comment3 != 1 && !multiLineComment) {
                        linesOfCodeCounter++;
                    }
                }
            }
            file.close();

        }catch (IOException e) {
            linesOfCodeCounter = -1;
        }
        return linesOfCodeCounter;
    }

    //Wenn vorhanden, loesche die Leerzeichen und Tabs
    static String deleteSpaces(String original) {

        String refactoredOriginal;
        String refactoredOriginalCache;

        refactoredOriginalCache = original.replace("\t", "");
        refactoredOriginal = refactoredOriginalCache.replace(" ", "");

        return refactoredOriginal;
    }

    static int analyzeString(String original, String search){
        int realCode = -1;
        int lengthOrignal = original.length();
        int lengthSearch = search.length();

        if (lengthOrignal >= lengthSearch){
            if(original.charAt(0) == search.charAt(0) && original.charAt(1) == search.charAt(1)){
                realCode = 1;
            }
        }

        return realCode;
    }
}