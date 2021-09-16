export function confirmPasswordValidator(password, confirm) {
    if (password !== confirm) return "两次密码输入不同"
    return ''
}
