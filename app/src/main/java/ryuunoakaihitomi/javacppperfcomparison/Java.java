package ryuunoakaihitomi.javacppperfcomparison;

public class Java {

    static {
        System.loadLibrary("native-lib");
    }

    static void kernel() {
        int iInputNumber = 100000;
        int iPalprimeCount = 0;
        for (int iEach = 2; iEach <= iInputNumber; iEach++) {
            int iFactorizationList = 0;
            for (int iFactor = 1; iFactor <= iEach; iFactor++)
                if (iEach % iFactor == 0 && !(iFactor > iEach / iFactor))
                    iFactorizationList++;
            if (iFactorizationList == 1) {
                int iAntitone = 0, iEachCopy = iEach;
                while (iEachCopy != 0) {
                    iAntitone = iAntitone * 10 + iEachCopy % 10;
                    iEachCopy /= 10;
                }
                if (iAntitone == iEach) {
                    iPalprimeCount++;
                    ResultPrint(iPalprimeCount, iEach);
                }
            }
        }
    }

    public static native void ResultPrint(int c, int e);
}