export function passwordValidator(password) {
    if (!password) return "密码不能为空"
    if (password.length < 6) return '密码至少需要6位'
    return ''
}
