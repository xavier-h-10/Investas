//'债券型-混合债': 0, '混合型-偏股': 1, '指数型-股票': 2, '混合型-灵活': 3, 'QDII': 4, '债券型-长债': 5,
//                  '货币型': 6, '股票型': 7, '混合型-偏债': 8, '混合型-平衡': 9, '债券型-中短债': 10, '商品（不含QDII）': 11,
//                  '债券型-可转债': 12, 'REITs': 13

export const fundTypeToText=(type)=>{
    switch(type)
    {
        case 0:
            return '债券型-混合债';
        case 1:
            return '混合型-偏股';
        case 2:
            return '指数型-股票';
        case 3:
            return '混合型-灵活';
        case 4:
            return 'QDII';
        case 5:
            return '债券型-长债';
        case 6:
            return '货币型';
        case 7:
            return '股票型';
        case 8:
            return '混合型-平衡';
        case 9:
            return '债券型-中短债';
        case 10:
            return '商品(不含QDII)';
        case 11:
            return '债券型-可转债';
        case 12:
            return 'REITs';
        default:
            return '';
    }

}

export const fundTypetoRiskText=(type)=>{
    switch(type)
    {
        case 0:
        case 5:
        case 6:
        case 9:
        case 10:
        case 12:
            return '低风险';
        case 4:
        case 8:
        case 11:
        case 13:
            return '中低风险';
        default:
            return '中高风险';
    }
}
