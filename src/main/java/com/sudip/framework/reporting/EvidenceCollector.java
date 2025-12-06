package com.sudip.framework.reporting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Collects screenshot paths and notes per thread/scenario.
 */
public class EvidenceCollector {
    private static final ThreadLocal<List<String>> SCREENSHOT_PATHS =
            ThreadLocal.withInitial(ArrayList::new);
    private static final ThreadLocal<List<String>> NOTES =
            ThreadLocal.withInitial(ArrayList::new);
    public static void addScreenshotPath(String path) {
        if (path != null && !path.isEmpty()) {
            SCREENSHOT_PATHS.get().add(path);
        }
    }

    public static void addNote(String note) {
        if (note != null && !note.isEmpty()) {
            NOTES.get().add(note);
        }
    }

    public static List<String> getScreenshotPaths() {
        return Collections.unmodifiableList(SCREENSHOT_PATHS.get());
    }

    public static List<String> getNotes() {
        return Collections.unmodifiableList(NOTES.get());
    }

    public static void clear() {
        SCREENSHOT_PATHS.get().clear();
        NOTES.get().clear();
    }




}
