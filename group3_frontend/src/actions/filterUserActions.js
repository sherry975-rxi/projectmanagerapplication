export function changeToALLUSERS() {
    console.log("teste")
    return { type: 'ALLUSERS_FILTER' };
    
}

export function changeToEMAIL() {
    return { type: 'EMAILUSERS_FILTER' };
}

export function changeToCOLLABORATOR() {
    return { type: 'ALLCOLLABORATORS_FILTER' };
}

export function changeToADMINISTRATOR() {

    return { type: 'ALLADMINISTRATOR_FILTER' };
}

export function changeToDIRECTOR() {

    return { type: 'ALLDIRECTORS_FILTER' };
}


