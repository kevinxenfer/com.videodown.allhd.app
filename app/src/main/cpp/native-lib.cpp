#include <jni.h>
#include <string>
#include <unistd.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_videodown_allhd_app_Ads_Constant_getMain(
        JNIEnv *env,
        jobject /* this */) {

    std::string hello = "http://165.22.216.111/kk/AllVideoCBX0105KK/V1/";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_videodown_allhd_app_Ads_Encrypt_DecryptEncrypt_encryptionKey(
        JNIEnv *env, jclass clazz) {
// TODO: implement getURL()
    std::string hello = "";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_videodown_allhd_app_Ads_Encrypt_DecryptEncrypt_zipencryptionkey(
        JNIEnv *env, jclass clazz) {
// TODO: implement getURL()
    std::string hello = "";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_videodown_allhd_app_Ads_Encrypt_DecScript_getDecKey1(
        JNIEnv *env, jclass clazz) {
// TODO: implement getURL()
    std::string hello = "267F73ED25B46B59E8177872E2B7E5860CC8EE72A457C3AAA35F79627FD13728";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_videodown_allhd_app_Ads_Encrypt_DecScript_getDecKey2(
        JNIEnv *env, jclass clazz) {
// TODO: implement getURL()
    std::string hello = "BF0453BABE4F2BBF303A7BEE566186300CC8EE72A457C3AAA35F79627FD13728";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_videodown_allhd_app_Ads_Encrypt_EncScript_getEncKey1(
        JNIEnv *env, jclass clazz) {
// TODO: implement getURL()
    std::string hello = "267F73ED25B46B59E8177872E2B7E5860CC8EE72A457C3AAA35F79627FD13728";
    return env->NewStringUTF(hello.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_videodown_allhd_app_Ads_Encrypt_EncScript_getEncKey2(
        JNIEnv *env, jclass clazz) {
// TODO: implement getURL()
    std::string hello = "BF0453BABE4F2BBF303A7BEE566186300CC8EE72A457C3AAA35F79627FD13728";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_videodown_allhd_app_ASplash_SplashActivity_setParameter(JNIEnv *env, jobject thiz,
                                                                    jstring version,
                                                                    jstring packagename,
                                                                    jstring androidid,
                                                                    jstring referrer) {
    // TODO: implement setParameter()

    const char *version1 = env->GetStringUTFChars(version, 0);
    const char *packagename1 = env->GetStringUTFChars(packagename, 0);
    const char *androidId1 = env->GetStringUTFChars(androidid, 0);
    const char *referrer1 = env->GetStringUTFChars(referrer, 0);

    std::string hello = "";

    pid_t pid = getpid();
    char path[64] = {0};
    sprintf(path, "/proc/%d/cmdline", pid);
    FILE *cmdline = fopen(path, "r");
    jobjectArray ret = nullptr;
    if (cmdline) {
        char application_id[64] = {0};
        fread(application_id, sizeof(application_id), 1, cmdline);
        const char *package = "com.videodown.allhd.app";

        if (strcmp(package, application_id) == 0) {
            hello = "{";

            hello.append("\"Referral\":");
            hello.append("\"");
            hello.append(referrer1);

            hello.append("\"");
            hello.append(",\"AndroidId\":");
            hello.append("\"");
            hello.append(androidId1);

            hello.append("\"");
            hello.append(",\"VersionCode\":");
            hello.append("\"");
            hello.append(version1);

            hello.append("\"");
            hello.append(",\"PkgName\":");
            hello.append("\"com.videodown.allhd.app\"");

            hello.append("}");
        }
    }

    env->ReleaseStringUTFChars(version, version1);
    env->ReleaseStringUTFChars(packagename, packagename1);
    env->ReleaseStringUTFChars(androidid, androidId1);
    env->ReleaseStringUTFChars(referrer, referrer1);

    return env->NewStringUTF(hello.c_str());

}