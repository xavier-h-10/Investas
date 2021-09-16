export function codeValidator(code) {
    if (!code) return "验证码不能为空"
    if (code.length !== 6) return '验证码应该为6位的数字'
    return ''
}
