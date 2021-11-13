function getObjValues(e: any): (number | string)[] {
    return Object.keys(e).map(k => e[k]);
}

export function getAllEnumValues<T extends number>(e: any): T[] {
    return getObjValues(e).filter(v => typeof v === "number") as T[];
}