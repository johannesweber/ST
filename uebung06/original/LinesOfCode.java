package original;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class LinesOfCode {

    /**
     * Die Methode liefert die Anzahl "echter Programmzeilen" in einer
     * Java-Datei.
     * @param filename der Dateiname
     * @return die Anzahl echter Programmzeilen in der Datei filename,
     * wenn kein Fehler auftritt, oder einen negativen Fehlercode sonst
     */
    public static int countLines(String filename){
        // lokale Variablen
        int lineCounter = 0;
        String cache;
        String cacheRest;
        int comment1, comment2, comment3;
        boolean multiLineComment = false;

        // Ueberpruefung, ob der Dateiname existiert
        if (filename == null)
            return -1;
        // Ueberpruefung, ob Datei existiert
        if (!new File(filename).exists()){
            return -2;
        }
        // Ueberpruefung, ob die Datei lesbar ist
        if (!new File(filename).canRead()){
            return -3;
        }

		/*
		 * ab hier wird die Datei analysiert
		 */
        try {
            BufferedReader javaDatei = new BufferedReader(new FileReader(filename));
            while (javaDatei.ready()) {
                cache = javaDatei.readLine();
                cacheRest = deleteSpaces(cache);
                if (!cacheRest.equals("")) {
                    comment1 = strScan(cacheRest, "//");
                    comment2 = strScan(cacheRest, "/*");
                    if(comment2 == 0){
                        multiLineComment = true;
                    }
                    comment3 = strScan(cacheRest, "*/");
                    if(comment3 == 0){
                        multiLineComment = false;
                    }

                    if (comment1 != 0 && comment2 != 0 && comment3 != 0 && !multiLineComment) {
                        lineCounter++;
                    }
                }
            }
            javaDatei.close();
        } catch (IOException e) {
            return -4;
        }
        return lineCounter;
    }

    //Wenn vorhanden, loesche die Leerzeichen und Tabs
    static String deleteSpaces(String original) {
        int counter = 0;
        String rest = "";
        if(original.length() == 0){
            return "";
        }
        while (counter < original.length() && (original.charAt(counter) == '\t' || original.charAt(counter) == ' ')) {
            counter++;
        }
        while (counter < original.length()){
            rest = rest + original.charAt(counter);
            counter++;
        }

        return rest;
    }

    static int strScan( String original, String needle ){
        int zaehlerOriginal = 0;
        int zaehlerNeedle = 0;
        int stelleImString = 0;
        int laengeOriginal = original.length();
        int laengeNeedle = needle.length();
        boolean needleReset = false;


        while(zaehlerOriginal < laengeOriginal){
            while (zaehlerOriginal < laengeOriginal && original.charAt(zaehlerOriginal) == needle.charAt(zaehlerNeedle)){
                //Merken der ersten Stelle der gefundenen Zeichenkette zur Rueckgabe.
                if (zaehlerNeedle == 0){
                    stelleImString = zaehlerOriginal;
                }
                if (zaehlerNeedle == laengeNeedle-1){
                    return stelleImString;
                }
                zaehlerOriginal++;
                zaehlerNeedle++;
                needleReset = false;
            }
            if (needleReset != false){
                zaehlerOriginal++;
            }
            zaehlerNeedle = 0;
            needleReset = true;
        }
        return -1;
    }

}