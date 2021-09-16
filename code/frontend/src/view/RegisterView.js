import React, {Component} from 'react';
import message from 'react-native-message';
import {
    Alert,
    StyleSheet,
    Text,
    TextInput,
    TouchableOpacity,
    View
} from 'react-native';
import {register} from "../service/LoginService";
import BackButton from "../components/firebase/BackButton";
import Header from "../components/firebase/Header";

export default class RegisterView extends Component {
    constructor(props, context) {
        super(props, context);
        this.state = {
            username: '',
            password: '',
            confirmPassword: '',
            email: '',
        }
    }

    /**
     * 当用户名输入框值改变时，保存改变的值
     * @param  {[type]} newUsername [输入的用户名]
     */
    onUsernameChanged = (newUsername) => {
        console.log(newUsername);
        this.setState({
            username: newUsername,
        })
    };

    /**
     * 当密码输入框值改变时，保存改变的值
     * @param  {[type]} newUsername [输入的密码]
     */
    onPasswordChanged = (newPassword) => {
        console.log(newPassword);
        this.setState({
            password: newPassword,
        })
    };

    /**
     * 当确认密码输入框值改变时，保存改变的值
     * @param  {[type]} newUsername [输入的确认密码]
     */
    onConfirmPasswordChanged = (newConfirmPassword) => {
        console.log(newConfirmPassword);
        this.setState({
            confirmPassword: newConfirmPassword,
        })
    }

    register_callback = (value) => {
        console.log(value['status']);
        if (value['status'] === 0) {
            const {goBack} = this.props.navigation;
            Alert.alert("注册成功", "返回登陆", [{
                text: '确定', onPress: () => {
                    goBack();
                }
            }])
        } else if (value['status'] === -1) {
            Alert.alert("注册失败", value['message']);
        }

    }
    onPressCallback = () => {
        console.log("button pressed");
        let tmp = {};
        tmp["username"] = this.state.username;
        tmp["password"] = this.state.password;
        login(json);
    }

    /**
     * 注册按钮，根据输入的内容判断注册是否成功
     */
    register = () => {
        if (this.state.username !== '' && this.state.password !== '') {
            if (this.state.password === this.state.confirmPassword) {
                const register_data = {
                    username: this.state.username,
                    password: this.state.password,
                }
                register(register_data, this.register_callback)

            } else {
                Alert.alert("注册失败", "密码与确认密码不同");
            }
        } else {
            Alert.alert("注册失败", "用户名或密码不能为空");
        }
    };

    render() {
        return (
            <TouchableOpacity
                activeOpacity={1.0}  //设置背景被点击时，透明度不变
                onPress={this.blurTextInput}
            >
                <View
                    style={styles.inputBox}>
                    <TextInput
                        ref="username"  //添加描述
                        onChangeText={this.onUsernameChanged}  //添加值改变事件
                        style={styles.input}
                        autoCapitalize='none'  //设置首字母不自动大写
                        underlineColorAndroid={'transparent'}  //将下划线颜色改为透明
                        placeholderTextColor={'#ccc'}  //设置占位符颜色
                        placeholder={'用户名'}  //设置占位符
                    />
                </View>
                <View
                    style={styles.inputBox}>
                    <BackButton goBack={this.props.navigation.goBack}/>
                    <Header>注册</Header>
                    <TextInput
                        ref="password"  //添加描述
                        onChangeText={this.onPasswordChanged}  //添加值改变事件
                        style={styles.input}
                        secureTextEntry={true}  //设置为密码输入框
                        autoCapitalize='none'  //设置首字母不自动大写
                        underlineColorAndroid={'transparent'}  //将下划线颜色改为透明
                        placeholderTextColor={'#ccc'}  //设置占位符颜色
                        placeholder={'密码'}  //设置占位符
                    />
                </View>
                <View
                    style={styles.inputBox}>
                    <TextInput
                        ref="confirmPassword"  //添加描述
                        onChangeText={this.onConfirmPasswordChanged}  //添加值改变事件
                        style={styles.input}
                        secureTextEntry={true}  //设置为密码输入框
                        autoCapitalize='none'  //设置首字母不自动大写
                        underlineColorAndroid={'transparent'}  //将下划线颜色改为透明
                        placeholderTextColor={'#ccc'}  //设置占位符颜色
                        placeholder={'确认密码'}  //设置占位符
                    />
                </View>
                <TouchableOpacity
                    onPress={this.register}  //添加点击事件
                    style={styles.button}>
                    <Text
                        style={styles.btText}>注册</Text>
                </TouchableOpacity>
            </TouchableOpacity>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    input: {
        width: 200,
        height: 40,
        fontSize: 17,
        color: '#000000',//输入框输入的文本为白色
    },
    inputBox: {
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
        width: 320,
        height: 50,
        borderRadius: 8,
        backgroundColor: '#FFFFFF',
        marginBottom: 8,
    },
    button: {
        height: 50,
        width: 320,
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 8,
        backgroundColor: '#3366FF',
        marginTop: 20,
    },
    btText: {
        color: '#fff',
        fontSize: 20,
    }
});
