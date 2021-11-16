export function addDays(date, days) {
    let result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
}

export function getEpochTimeInDays(date: Date) {
    return Math.floor((date.getTime() - date.getTimezoneOffset() * 60000) / 8.64e7);
}