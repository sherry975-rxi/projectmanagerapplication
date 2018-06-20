export function changeToFinished() {
    return { type: 'FINISHED' };
}

export function changeToUnfinished() {
    return { type: 'UNFINISHED' };
}

export function changeToStandBy() {
    return { type: 'STANDBY' };
}

export function changeToNotStarted() {
    return { type: 'NOTSTARTED' };
}

export function changeToExpired() {
    return { type: 'EXPIRED' };
}

export function changeToAllTasks() {
    return { type: 'ALLPROJECTTASKS' };
}
