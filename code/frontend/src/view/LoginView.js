import React, {Component} from 'react';
import {Alert} from 'react-native';
import {StyleSheet, Text, View} from 'react-native';

import {Button, Input} from 'react-native-elements';
import FontAwesome5 from "react-native-vector-icons/FontAwesome5";
import FontAwesome5Pro from "react-native-vector-icons/FontAwesome5Pro";
import {login} from "../service/LoginService";
import storage from "../components/LocalStorage";

export default class LoginView extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            avatar:'',
        };
    }

    updateUsername(text) {
        console.log("username " + text);
        this.setState((state) => {
            return {
                username: text,
            };
        });
    }

    updatePassword(text) {
        console.log("password " + text);
        this.setState((state) => {
            return {
                password: text,
            };
        });
    }


    onPressCallback = () => {
        console.log("button pressed");
        const data = {
            username: this.state.username,
            password: this.state.password,
        }
        login(data, this.login_callback);
    }

    login_callback = (value) => {
        console.log(value['status']);
        if (value['status'] === -1) {
            Alert.alert(
                "登陆失败",
                value['message'],
                [
                    {
                        text: "Cancel",
                        onPress: () => console.log("Cancel Pressed"),
                        style: "cancel"
                    },
                    {text: "OK", onPress: () => console.log("OK Pressed")}
                ]
            );
        } else if (value['status'] === 0) {
            storage.save({
                key:'loginState',
                data:{
                    username:this.state.username,
                    avatar:this.state.avatar,
                }
            })
            .then(ret=>{
                console.log("successfully save username");
            })
            .catch(err=>{
                console.warn(err.message);
            });
            Alert.alert(
                "登陆成功",
                value['data']['userAuth'],
                [
                    {
                        text: "Cancel",
                        onPress: () => console.log("Cancel Pressed"),
                        style: "cancel"
                    },
                    {text: "OK", onPress: () => console.log("OK Pressed")}
                ]
            );
            //Todo
            this.props.navigation.goBack();
        }
    }

    render() {
        return (
            <View>
                <View style={{marginTop: 80}}>
                    <Input
                        placeholder='请输入用户名'
                        leftIcon={{
                            type: 'font-awesome-5',
                            name: 'user',
                            size: 24,
                            color: 'black'
                        }}
                        onChangeText={(text) => this.updateUsername(text)}
                    />
                    <Input
                        placeholder='请输入密码'
                        leftIcon={{
                            type: 'font-awesome-5',
                            name: 'lock',
                            size: 24,
                            color: 'black',
                        }}
                        secureTextEntry={true}
                        onChangeText={(text) => this.updatePassword(text)}
                    />
                    <Text style={{color: "#4A90E2", textAlign: 'center', marginTop: 10}}>忘记密码？</Text>
                    <Button
                        style={page.button}
                        title="登录"
                        onPress={this.onPressCallback}
                    />
                </View>
            </View>
        )
    }

    //跳转到第二个页面去
    // onLoginSuccess(){
    //   const { navigator } = this.props;
    //   if (navigator) {
    //     navigator.push({
    //       name : 'LoginSuccess',
    //       component : LoginSuccess,
    //     });
    //   }
    // }
}

const page = StyleSheet.create({
    container: {},
    text: {},
    button: {
        textAlign: 'center',
    },
})
