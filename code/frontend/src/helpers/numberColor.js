export function numberColor(number) {
    if (Math.abs(number) < 1e-6) {
        return '#444444';
    }
    return number > 0 ? '#eb394a' : '#36ab8c';
}

//修改：保留2位小数 20210806
export function numberFormat(number, fractionDigits = 2) {
    let res=parseFloat(number).toFixed(fractionDigits);
    return res > 0 ? '+' + res : '' + res
}

export function numberColor1(number,hide) {
    if (Math.abs(number) < 1e-6 || hide==true) {
        return '#444444';
    }
    return number > 0 ? '#eb394a' : '#36ab8c';
}
