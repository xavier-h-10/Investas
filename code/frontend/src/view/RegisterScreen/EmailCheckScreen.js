import React, {Component, useState} from 'react'
import {View, StyleSheet, TouchableOpacity,SafeAreaView} from 'react-native'
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
import {check_verification, send_verification} from "../../service/RegisterService";
import GetDeviceInfo from "../../components/GetDeviceInfo";

export default class EmailCheckScreen extends Component {
    constructor(props) {
        super(props);
        this.state = {
            email: {value: '', error: ''},
            code: {value: '', error: ''},
            sending: false,
            checking: false,
            sent: false,
            error: '',
            info: '',
        };
        console.log("EmailCheckScreen created");
    }

    onSendPressed = () => {
        const emailError = emailValidator(this.state.email.value)
        if (emailError) {
            let email = this.state.email
            email.error = emailError
            this.setState({email: email})
            return
        }
        const data = {
            email: this.state.email.value,
            device_id: GetDeviceInfo(),
        }

        console.log(data)
        this.setState({sending: true})

        send_verification(data, this.sendCallback)
    }

    sendCallback = (value) => {
        console.log(value);
        this.setState({sending: false})
        if (value['status'] !== 0) {
            this.setState({error: value['message']})
        } else if (value['status'] === 0) {
            this.setState({sent: true, info: '发送成功，请在邮箱中查收'})
        }
    }

    onCheckPressed = () => {
        const codeError = codeValidator(this.state.code.value)
        if (codeError) {
            let code = this.state.code.value
            code.error = codeError
            this.setState({code: code})
            return
        }
        const data = {
            email: this.state.email.value,
            code: this.state.code.value,
            // TODO: What is the user's device code?
            // 20210804 completed
            device_id: GetDeviceInfo(),
        }
        this.setState({checking: true})

        check_verification(data, this.checkCallback)
    }

    checkCallback = (value) => {
        console.log(value['status']);
        //解决验证失败无法跳出的错误 20210806
        this.setState({checking:false});
        if (value['status'] === -1) {
            this.setState({error: value['message']})
        } else if (value['status'] === 0) {
            this.setState({info: '验证成功'})
            this.props.navigation.navigate('Register2', {email: this.state.email.value})
        }
    }

    render() {
        return (
            // <SafeAreaView style={{backgroundColor:'#fefefe'}}>
            //     <View style={styles.container}>
            <Background>
                <BackButton goBack={this.props.navigation.goBack} />
                <Text style={styles.title}>注册</Text>
                <TextInput
                    label="邮箱"
                    returnKeyType="next"
                    value={this.state.email.value}
                    onChangeText={(text) => this.setState({email: {value: text, error: ''}})}
                    error={!!this.state.email.error}
                    errorText={this.state.email.error}
                    autoCapitalize="none"
                    autoCompleteType="email"
                    textContentType="emailAddress"
                    keyboardType="email-address"
                />
                <TextInput
                    disabled={!this.state.sent}
                    label="验证码"
                    returnKeyType="done"
                    value={this.state.code.value}
                    onChangeText={(text) => this.setState({code: {value: text, error: ''}})}
                    error={!!this.state.code.error}
                    errorText={this.state.code.error}
                    keyboardType="number-pad"
                />
                <Button
                    loading={this.state.sent ? this.state.checking : this.state.sending}
                    disabled={this.state.sent ? this.state.checking : this.state.sending}
                    mode="contained"
                    onPress={this.state.sent ? this.onCheckPressed : this.onSendPressed}
                    style={{marginTop: 24,borderRadius:25}}
                >
                    {this.state.sent ? (this.state.checking ? '验证中' : '下一步') :
                        (this.state.sending ? '发送邮件中' : '发送验证码')}
                </Button>
                <Toast message={this.state.error} onDismiss={() => this.setState({error: ''})}/>
                <Toast type='info' message={this.state.info} onDismiss={() => this.setState({info: ''})}/>
            {/*    </View>*/}
            {/*</SafeAreaView>*/}
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
    title: {
        fontSize: 25,
        paddingTop: 75,
        paddingLeft: 10,
    },
    container: {
        paddingTop: 20,
        // paddingLeft: 50,
        width: '90%',
        paddingLeft:'10%',
        height: '100%',
        backgroundColor: '#fefefe',
    },
    // header:{
    //     fontSize:20,
    //
    // },
})
