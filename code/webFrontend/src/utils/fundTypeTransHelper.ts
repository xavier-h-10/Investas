export function fundTypeTransHelper(param: any): string {
  switch (param) {
    case 0:
    case '0':
      return '债券型-混合债';
    case 1:
    case '1':
      return '混合型-偏股';
    case 2:
    case '2':
      return '指数型-股票';
    case 3:
    case '3':
      return '混合型-灵活';
    case 4:
    case '4':
      return 'QDII';
    case 5:
    case '5':
      return '债券型-长债';
    case 6:
    case '6':
      return '货币型';
    case 7:
    case '7':
      return '股票型';
    case 8:
    case '8':
      return '混合型-偏债';
    case 9:
    case '9':
      return '混合型-平衡';
    case 10:
    case '10':
      return '债券型-中短债';
    case 11:
    case '11':
      return '商品（不含QDII）';
    case 12:
    case '12':
      return '债券型-可转债';
    case 13:
    case '13':
      return 'REITs';
    default:
      return '其他';
  }
}
