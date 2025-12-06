package com.sudip.framework.reporting;

/**
 * Stub for video recording. Currently logs to console.
 * Later you can integrate a real recording library here.
 */
public class VideoRecordingUtil {

    private static boolean recordingEnabled = false;

    public static void setRecordingEnabled(boolean enabled) {
        recordingEnabled = enabled;
    }

    public static boolean isRecordingEnabled() {
        return recordingEnabled;
    }

    public static void startRecording(String testName) {
        if (!recordingEnabled) return;

        // TODO: Implement with real video recording library
        System.out.println("[VIDEO] Start recording: " + testName);
    }

    public static void stopRecording(String testName) {
        if (!recordingEnabled) return;

        // TODO: Implement with real video recording library
        System.out.println("[VIDEO] Stop recording: " + testName);
    }
}
