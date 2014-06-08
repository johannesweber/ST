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
     * Wenn ein Fehler auftritt, oder es keine "echten Programmzeilen" gibt, wird
     * -1 zurueck gegeben.
     */
    public static int countRealCodeLines(String filename){

        int RealCodeLinesCounter = 0;

        //moegliche Fehlerquellen abfangen
        if (filename == null){
            RealCodeLinesCounter = -1;
        }else{
            if (!new File(filename).exists()){
                RealCodeLinesCounter = -1;
            }

            if (!new File(filename).canRead()){
                RealCodeLinesCounter = -1;
            }
        }
        if(RealCodeLinesCounter == -1){
            return RealCodeLinesCounter;
        }

		/*
		 * ab hier wird die Datei analysiert
		 */
        try {
            BufferedReader file = new BufferedReader(new FileReader(filename));

            String oneLine;
            String oneLineWithoutSpaces;
            boolean multiLineComment = false;
            int comment1, comment2, comment3;

            while (file.ready()) {
                oneLine = file.readLine();
                oneLineWithoutSpaces = deleteSpaces(oneLine);
                if (!oneLineWithoutSpaces.equals("")){
                    comment1 = analyzeString(oneLineWithoutSpaces, "//");
                    comment2 = analyzeString(oneLineWithoutSpaces, "/*");
                    comment3 = analyzeString(oneLineWithoutSpaces, "*/");
                    if(comment2 == 1){
                        multiLineComment = true;
                    }
                    if(comment3 == 1){
                        multiLineComment = false;
                    }

                    if (comment1 != 1 && comment2 != 1 && comment3 != 1 && !multiLineComment) {
                        RealCodeLinesCounter++;
                    }
                }
            }
            file.close();

        }catch (IOException e) {
            RealCodeLinesCounter = -1;
        }
        return RealCodeLinesCounter;
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