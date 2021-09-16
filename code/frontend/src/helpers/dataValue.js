
export function valueNullHandler(value){
    if(value===null)
        return "暂无数据";
    return value;
}

export function valueNullDashHandler(value){
    if(value===null)
        return "--";
    return value;
}