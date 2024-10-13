package org.CCT.EnumFiles;

public enum FileType {

    TXT, CSV, JSON;

    public static FileType fromExtension(String extension) {
        return FileType.valueOf(extension.toUpperCase());
    }
}