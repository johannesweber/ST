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

        if (filename == null)
            linesOfCodeCounter =  -1;

        if (!new File(filename).exists()){
            linesOfCodeCounter = -1;
        }

        if (!new File(filename).canRead()){
            linesOfCodeCounter = -1;
        }

		/*
		 * ab hier wird die Datei analysiert
		 */
         try {
            BufferedReader javaDatei = new BufferedReader(new FileReader(filename));

            String oneLine;
            String oneCodeLineWithoutSpaces;
            boolean realCodeLine;

            while (javaDatei.ready()) {
                oneLine = javaDatei.readLine();
                oneCodeLineWithoutSpaces = deleteSpaces(oneLine);

                realCodeLine = analyzeString(oneCodeLineWithoutSpaces);

                if(realCodeLine){
                    linesOfCodeCounter++;
                }
            }
            javaDatei.close();

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

    static boolean analyzeString(String original){
        boolean isRealCode = true;

        if(original.length() != 0){
            if(original.charAt(0) == '/'){
                isRealCode = false;
            }

            if(original.charAt(0) == '*'){
                isRealCode = false;
            }
        }else{
            isRealCode = false;
        }

        return isRealCode;
    }
}
