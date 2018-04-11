#include <jni.h>
#include <android/log.h>
#include <string>

using namespace std;

void kernel();

void kernel_log(string, int, int);

extern "C" JNIEXPORT void

JNICALL
Java_ryuunoakaihitomi_javacppperfcomparison_MainActivity_cpp(
        JNIEnv *,
        jobject /* this */) {
    kernel();
}

void kernel() {
    int input_num = 100000;
    int pp_count = 0;
    for (int each = 2; each <= input_num; each++) {
        int factorization_lst = 0;
        for (int factor = 1; factor <= each; factor++)
            /*Expression can be simplified to 'factor <= each / factor' less... (Ctrl+F1)
            This inspection finds the part of the code that can be simplified, e.g. constant conditions, identical if branches, pointless boolean expressions, etc.*/
            if (each % factor == 0 && factor <= each / factor)
                factorization_lst++;
        if (factorization_lst == 1) {
            int antitone = 0, each_cpy = each;
            while (each_cpy) {
                antitone = antitone * 10 + each_cpy % 10;
                each_cpy /= 10;
            }
            if (antitone == each) {
                pp_count++;
                kernel_log("c", pp_count, each);
            }
        }
    }
}

void kernel_log(string t, int c, int e) {
    __android_log_print(ANDROID_LOG_DEBUG, "JCPC", "%s %d:%d", t.c_str(), c, e);
}

extern "C"
JNIEXPORT void JNICALL
Java_ryuunoakaihitomi_javacppperfcomparison_Java_ResultPrint(JNIEnv *, jobject, jint c,
                                                             jint e) {
    kernel_log("j", c, e);
}