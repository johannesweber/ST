package refactored;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class LinesOfCodeRefactored {

    static final int NOTFOUND = -1;
    static final int FOUND = 1;

    /**
     * @param filename the file in which we want to count the lines
     * @return the number of real code lines within the given file
     */
    public static int countRealCodeLines(String filename){

        int realCodeLinesCounter = 0;
        String oneLine;
        String oneLineWithoutSpaces;
        boolean multiLineComment = false;
        int comment, blockCommentStart, blockCommentEnd;

        try {
            new File(filename).exists();
            new File(filename).canRead();
            BufferedReader file = new BufferedReader(new FileReader(filename));

            while (file.ready()) {
                oneLine = file.readLine();
                oneLineWithoutSpaces = deleteSpaces(oneLine);
                if (!oneLineWithoutSpaces.equals("")){
                    comment = searchString(oneLineWithoutSpaces, "//");
                    blockCommentStart = searchString(oneLineWithoutSpaces, "/*");
                    blockCommentEnd = searchString(oneLineWithoutSpaces, "*/");
                    if(blockCommentStart == FOUND){
                        multiLineComment = true;
                    }
                    if(blockCommentEnd == FOUND){
                        multiLineComment = false;
                    }

                    if (comment == NOTFOUND &&
                        blockCommentStart == NOTFOUND &&
                        blockCommentEnd == NOTFOUND &&
                        !multiLineComment) {
                        realCodeLinesCounter++;
                    }
                }
            }
            file.close();

        }catch (IOException e) {
            realCodeLinesCounter = NOTFOUND;
        }
        return realCodeLinesCounter;
    }

    /**
     *
     * @param original the string from which we remove the spaces
     * @return the new string without any spaces
     */
    static String deleteSpaces(String original) {

        String refactoredOriginal;
        String refactoredOriginalCache;

        refactoredOriginalCache = original.replace("\t", "");
        refactoredOriginal = refactoredOriginalCache.replace(" ", "");

        return refactoredOriginal;
    }

    /**
     *
     * @param original the string in which we are searching for
     * @param search   the string we are lookig for
     * @return if "search" was found or not
     */
    static int searchString(String original, String search){
        int realCode = NOTFOUND;
        int lengthOrignal = original.length();
        int lengthSearch = search.length();

        // Query to prevend a StringIndexOutOfBounds Exception
        if (lengthOrignal >= lengthSearch){
            if(original.charAt(0) == search.charAt(0) && original.charAt(1) == search.charAt(1)){
                realCode = FOUND;
            }
        }
        return realCode;
    }
}