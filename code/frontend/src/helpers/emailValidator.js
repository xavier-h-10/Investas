export function emailValidator(email) {
    // const re = /\S+@\S+\.\S+/
    const re = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
    if (!email) return "邮箱不能为空"
    if (!re.test(email)) return '邮箱格式不正确'
    return ''
}
