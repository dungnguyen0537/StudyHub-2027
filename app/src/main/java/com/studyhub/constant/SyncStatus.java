package com.studyhub.constant;

public class SyncStatus {
    public static final int SYNCED = 0;
    public static final int PENDING_INSERT = 1;
    public static final int PENDING_UPDATE = 2;
    public static final int PENDING_DELETE = 3;

    private SyncStatus() {}
}
