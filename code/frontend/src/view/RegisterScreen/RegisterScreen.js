import React, {Component, useState} from 'react'
import {View, StyleSheet, TouchableOpacity} from 'react-native'
import {Text} from 'react-native-paper'
import Background from '../../components/firebase/Background'
import Logo from '../../components/firebase/Logo'
import Header from '../../components/firebase/Header'
import Button from '../../components/firebase/Button'
import TextInput from '../../components/firebase/TextInput'
import BackButton from '../../components/firebase/BackButton'
import {theme} from '../../core/theme'
import {emailValidator} from '../../helpers/emailValidator'
import {codeValidator} from '../../helpers/codeValidator'
import Toast from '../../components/firebase/Toast'
import {usernameValidator} from "../../helpers/usernameValidator";
import {passwordValidator} from "../../helpers/passwordValidator";
import {confirmPasswordValidator} from "../../helpers/confirmPasswordValidator";
import {register} from "../../service/RegisterService";
import GetDeviceInfo from "../../components/GetDeviceInfo";
import MyStatusBar from "../../components/MyStatusBar";

export default class RegisterScreen extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: {value: '', error: ''},
            password: {value: '', error: ''},
            confirm: {value: '', error: ''},
            loading: false,
            error: '',
            info: '',
        };
        console.log("RegisterScreen created");
    }

    onPressed = () => {
        const usernameError = usernameValidator(this.state.username.value)
        const passwordError = passwordValidator(this.state.password.value)
        const confirmError = confirmPasswordValidator(this.state.password.value, this.state.confirm.value)
        if (usernameError || passwordError || confirmError) {
            let username = this.state.username
            let password = this.state.password
            let confirm = this.state.confirm
            username.error = usernameError
            password.error = passwordError
            confirm.error = confirmError
            this.setState({
                username: username,
                password: password,
                confirm: confirm,
            })
            return
        }

        const data = {
            email: this.props.route.params.email,
            username: this.state.username.value,
            password: this.state.password.value,
            device: GetDeviceInfo(),
        }
        this.setState({loading: true})

        register(data, this.registerCallback)
    }

    registerCallback = (value) => {
        console.log(value['status']);
        this.setState({sending: false})
        if (value['status'] === -1) {
            this.setState({error: value['message']})
        } else if (value['status'] === 0) {
            this.setState({sent: true, info: '注册成功'})
            this.props.navigation.navigate('Login')
        }
    }

    render() {
        return (
            <Background>
                <BackButton goBack={() => {this.props.navigation.navigate('Login')}} />
                <Logo/>
                <MyStatusBar color={'#fefefe'}/>
                <Text style={{
                    fontSize: 21,
                    fontWeight: '500',
                    paddingVertical: 12,
                }}>
                    输入用户名和密码
                </Text>
                <TextInput
                    label="用户名"
                    returnKeyType="next"
                    value={this.state.username.value}
                    onChangeText={(text) => this.setState({username: {value: text, error: ''}})}
                    error={!!this.state.username.error}
                    errorText={this.state.username.error}
                    autoCapitalize="none"
                    textContentType="username"
                    keyboardType="username"
                />
                <TextInput
                    label="密码"
                    returnKeyType="next"
                    value={this.state.password.value}
                    onChangeText={(text) => this.setState({password: {value: text, error: ''}})}
                    error={!!this.state.password.error}
                    errorText={this.state.password.error}
                    secureTextEntry
                />
                <TextInput
                    label="确认密码"
                    returnKeyType="done"
                    value={this.state.confirm.value}
                    onChangeText={(text) => this.setState({confirm: {value: text, error: ''}})}
                    error={!!this.state.confirm.error}
                    errorText={this.state.confirm.error}
                    secureTextEntry
                />
                <Button
                    loading={this.state.loading}
                    disabled={this.state.loading}
                    mode="contained"
                    onPress={this.onPressed}
                    style={{marginTop: 24,borderRadius:25,width:'90%'}}
                >
                    {this.state.loading ? '确认中' : '完成注册'}
                </Button>
                <Toast message={this.state.error} onDismiss={() => this.setState({error: ''})}/>
                <Toast type='info' message={this.state.info} onDismiss={() => this.setState({info: ''})}/>
            </Background>
        )
    }
}

const styles = StyleSheet.create({
    row: {
        flexDirection: 'row',
        marginTop: 4,
    },
    link: {
        fontWeight: 'bold',
        color: theme.colors.primary,
    },
})
