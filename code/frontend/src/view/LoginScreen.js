import React, {Component} from 'react'
import {TouchableOpacity, StyleSheet, View} from 'react-native'
import {Text} from 'react-native-paper'
import Background from '../components/firebase/Background'
import Logo from '../components/firebase/Logo'
import Header from '../components/firebase/Header'
import Button from '../components/firebase/Button'
import TextInput from '../components/firebase/TextInput'
import BackButton from '../components/firebase/BackButton'
import {theme} from '../core/theme'
import {usernameValidator} from '../helpers/usernameValidator'
import {passwordValidator} from '../helpers/passwordValidator'
import Toast from '../components/firebase/Toast'
import {login} from "../service/LoginService";
import Icon from "react-native-vector-icons/Ionicons";
import {TextInput as Input} from 'react-native-paper';
import MyStatusBar from "../components/MyStatusBar";

export default class LoginScreen extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: {value: '', error: ''},
            password: {value: '', error: ''},
            loading: false,
            error: '',
            success: '',
            visible: false,
        }
        console.log("LoginScreen created");
    }

    onLoginPressed = () => {
        console.log("button pressed");
        const usernameError = usernameValidator(this.state.username.value)
        const passwordError = passwordValidator(this.state.password.value)
        if (usernameError || passwordError) {
            let username = this.state.username
            let password = this.state.password
            username.error = usernameError
            password.error = passwordError
            this.setState({
                username: username,
                password: password,
                visible: this.state.visible,
            })
            return
        }
        const data = {
            username: this.state.username.value,
            password: this.state.password.value,
        }
        this.setState({
            loading: true,
            visible: this.state.visible,
        })
        // console.log(data);
        login(data, this.login_callback);
    }

    login_callback = (value) => {
        console.log(value['status'])

        this.setState({
            loading: false,
            visible: this.state.visible,
        })

        if (value['status'] === -1) {
            this.setState({
                error: value['message'],
                visible: this.state.visible,
            })
        } else if (value['status'] === 0) {
            this.setState({
                // 已修复 20210831
                // error: value['message'],
                success: '登录成功',
                visible: this.state.visible,
            })
        }
    }

    render() {
        return (
            <Background>
                <BackButton goBack={this.props.navigation.goBack}/>
                <Logo/>
                <MyStatusBar color={'#fefefe'}/>
                <Text style={{
                    fontSize: 21,
                    fontWeight: '500',
                    paddingVertical: 12,
                }}>
                    欢迎来到交我赚
                </Text>
                <Text style={{
                    color: '#d6d6d6',
                    fontSize: 15,
                    fontWeight: '600',
                }}>
                    您的基金投资专家
                </Text>
                <TextInput
                    label="用户名"
                    returnKeyType="next"
                    value={this.state.username.value}
                    onChangeText={(text) => this.setState({username: {value: text, error: ''},visible: this.state.visible,})}
                    error={!!this.state.username.error}
                    errorText={this.state.username.error}
                    autoCapitalize="none"
                    textContentType="username"
                />
                <TextInput
                    label="密码"
                    returnKeyType="done"
                    value={this.state.password.value}
                    onChangeText={(text) => this.setState({password: {value: text, error: ''},visible: this.state.visible,})}
                    error={!!this.state.password.error}
                    errorText={this.state.password.error}
                    secureTextEntry
                    // secureTextEntry={!this.state.visible}
                    // right={
                    //     this.state.visible ?
                    //         <TextInput.Icon
                    //             name="eye-sharp"
                    //             backgroundColor='#fefefe'
                    //             color='black'
                    //             onPress={()=>{setVisible(false);}}
                    //             iconStyle={{marginRight:0}}
                    //         />
                    //         :
                    //         <Icon.Button
                    //             name="eye-off"
                    //             backgroundColor='#fefefe'
                    //             color='black'
                    //             onPress={()=>{setVisible(true);}}
                    //             iconStyle={{marginRight:0}}
                    //         />
                    // }

                />
                <View style={styles.forgotPassword}>
                    <TouchableOpacity
                        onPress={() => this.props.navigation.navigate('ForgetPassword',{})}
                    >
                        <Text style={styles.forgot}>忘记密码？</Text>
                    </TouchableOpacity>
                </View>
                <Button loading={this.state.loading} mode="contained" onPress={this.onLoginPressed} style={{borderRadius:25,width:'90%'}}>
                    开赚！
                </Button>
                <View style={styles.row}>
                    <Text>还没有账户？ </Text>
                    <TouchableOpacity onPress={() => this.props.navigation.navigate('Register')}>
                        <Text style={styles.link}>注册</Text>
                    </TouchableOpacity>
                </View>
                <Toast message={this.state.error} onDismiss={() => this.setState({error: '',visible: this.state.visible,})}/>
                <Toast
                    duration={500}
                    type={'success'}
                    message={this.state.success}
                    onDismiss={() => {
                        this.setState({success: '',visible: this.state.visible,});
                        // 此处进行修改 跳转到用户页
                        // this.props.navigation.goBack();
                        this.props.navigation.navigate("MainTab",{timestamp: new Date()});
                    }}/>
            </Background>
        )
    }
}

const styles = StyleSheet.create({
    forgotPassword: {
        width: '100%',
        alignItems: 'flex-end',
        marginBottom: 24,
    },
    row: {
        flexDirection: 'row',
        marginTop: 4,
    },
    forgot: {
        fontSize: 13,
        color: theme.colors.secondary,
    },
    link: {
        fontWeight: 'bold',
        color: theme.colors.primary,
    },
})
