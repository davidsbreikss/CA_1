package org.CCT.EnumFiles;

import org.CCT.Loggers.Logger;

public enum FileType {

    TXT, CSV, JSON;

    public static FileType fromExtension(String extension) {
        Logger logger = new Logger();
        try {
            return FileType.valueOf(extension.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.log(FileType.class.getSimpleName(), Logger.LogLevel.ERROR, "Unsupported file: " + extension);
            throw new IllegalArgumentException("Unsupported file type: " + extension);
        }
    }
}
