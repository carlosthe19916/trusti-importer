package org.trusti;

import java.util.Date;

public record TaskDto(
        TaskState state,
        Date started,
        Date terminated,
        String error
) {

    public enum TaskState {
        Not_supported,
        Canceled,
        Created,
        Succeeded,
        Failed,
        Running,
        No_task,
        Ready,
        Pending,
        Postponed
    }
}
