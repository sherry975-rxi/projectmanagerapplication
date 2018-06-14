export function changeToFinished() {
    return { type: 'FINISHED' };
}

export function changeToOnGoing() {
    return { type: 'ONGOING' };
}

export function changeToStandBy() {
    return { type: 'STANDBY' };
}

export function changeToNotStarted() {
    return { type: 'NOTSTARTED' };
}

export function changeToAllTasks() {
    return { type: 'ALLPROJECTTASKS' };
}
