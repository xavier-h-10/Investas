import React, {Component, useState} from 'react';
import {TouchableOpacity, StyleSheet, View, SafeAreaView} from 'react-native';
import {Text} from 'react-native-paper';
import {theme} from '../core/theme';
import {HeaderBackButton} from "@react-navigation/stack";
import Header from "../components/Header";
import {useNavigation} from "@react-navigation/native";
import {Input, Button} from "react-native-elements";
import message from "react-native-message/index";
import {updateUserInfo} from "../service/UserService";
import Icon from "react-native-vector-icons/Ionicons";
import {EasyLoading, Loading} from "../components/EasyLoading";
import MyStatusBar from "../components/MyStatusBar";

const SettingPasswordView = () => {
    const navigation = useNavigation();
    const [enabled, setEnabled] = useState(false);
    const [visible, setVisible] = useState(false);
    const [visible1, setVisible1] = useState(false);
    const [nowValue, setNowValue] = useState("");
    const [nowValue1, setNowValue1] = useState("");
    const input = React.createRef();
    const input1 = React.createRef();

    function changeText(value, id) {
        if (value == undefined || value == null || value == "") {
            setEnabled(false);
        } else {
            if (id == 1 && value != nowValue1) {
                setEnabled(false);
            } else if (id == 2 && value != nowValue) {
                setEnabled(false);
            } else {
                let regex = /^(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)(?!([^(0-9a-zA-Z)])+$).{6,16}$/g;   //正则表达式,限定6-16个字母、数字、下划线
                if (regex.test(value)) {
                    setEnabled(true);
                } else {
                    setEnabled(false);
                }
            }
        }
        if (id == 1) {
            setNowValue(value);
        } else {
            setNowValue1(value);
        }

    }

    const receiveData = (data) => {
        console.log(data);
        EasyLoading.dismiss();
        if (data && data.status == 0) {
            navigation.navigate("UserSetting", {status: 0, message: "修改密码成功!", timestamp: new Date()});
        } else {
            message.error("修改失败!");
        }

    }

    function renderPress() {
        if (enabled == false) {
            input.current.shake();
            input1.current.shake();
        } else {
            let m = new Map();
            m.set("password", nowValue);
            EasyLoading.show();
            updateUserInfo(m, receiveData);
        }
    }


    return (
        <SafeAreaView style={{backgroundColor:'#fefefe'}}>
            {/*<Header*/}
            {/*    headerLeft={<HeaderBackButton onPress={navigation.goBack}/>}*/}
            {/*>*/}
            {/*    个人资料*/}
            {/*</Header>*/}
            <MyStatusBar color="#fefefe"/>
            <View style={{paddingLeft: 5, paddingTop: 20, width: 50}}>
                <HeaderBackButton onPress={navigation.goBack}/>
            </View>
            <Loading/>
            <View style={styles.container}>
                <Text style={styles.title}>修改您的登录密码</Text>
                <Text style={styles.subTitle}>· 密码须为6-16位</Text>
                <Text style={styles.subTitle}>· 且须包含数字、字母、符号中的任意2种</Text>
                <View style={{paddingTop:30}}></View>
                <Input
                    containerStyle={styles.inputContainer}
                    inputStyle={styles.input}
                    inputContainerStyle={styles.inputLine}
                    placeholder="请输入密码"
                    placeholderTextColor="#d6d6d6"
                    onChangeText={(text)=>{
                        changeText(text,1);
                    }}
                    autoCapitalize="none"
                    rightIcon={
                      visible ?
                          <Icon.Button
                              name="eye-sharp"
                              backgroundColor='#fefefe'
                              color='black'
                              onPress={()=>{setVisible(false);}}
                              iconStyle={{marginRight:0}}
                          />
                          :
                          <Icon.Button
                              name="eye-off"
                              backgroundColor='#fefefe'
                              color='black'
                              onPress={()=>{setVisible(true);}}
                              iconStyle={{marginRight:0}}
                          />
                    }
                    secureTextEntry={!visible}
                    ref={input}
                />
                <Input
                    containerStyle={styles.inputContainer}
                    inputStyle={styles.input}
                    inputContainerStyle={styles.inputLine}
                    placeholder="请确认您的密码"
                    placeholderTextColor="#d6d6d6"
                    onChangeText={(text)=>{
                        changeText(text,2);
                    }}
                    rightIcon={
                        visible1 ?
                            <Icon.Button
                                name="eye-sharp"
                                backgroundColor='#fefefe'
                                color='black'
                                onPress={()=>{setVisible1(false);}}
                                iconStyle={{marginRight:0}}
                            />
                            :
                            <Icon.Button
                                name="eye-off"
                                backgroundColor='#fefefe'
                                color='black'
                                onPress={()=>{setVisible1(true);}}
                                iconStyle={{marginRight:0}}
                            />
                    }
                    secureTextEntry={!visible1}
                    ref={input1}
                />
                <View style={{paddingTop:30}}></View>
                <Button
                    title="保存"
                    buttonStyle={enabled ? styles.buttonEnableContainer : styles.buttonDisableContainer}
                    titleStyle={enabled ? styles.buttonEnableTitle : styles.buttonDisableTitle}
                    onPress={()=>{
                        renderPress()
                    }}
                />
            </View>
        </SafeAreaView>
    )
}

export default SettingPasswordView;

const styles = StyleSheet.create({
    container: {
        paddingTop: 20,
        paddingLeft: 20,
        width: '100%',
        height: '100%',
        backgroundColor: '#fefefe',
        // borderTopWidth: 0.4,
        // borderTopColor: '#dedede',
    },
    title: {
        fontSize: 25,
        paddingTop: 20,
        paddingLeft: 10,
    },
    subTitle: {
        color: '#d6d6d6',
        paddingTop: 16,
        paddingLeft: 12,
        fontSize: 15,
        fontWeight: '600',
    },
    text: {
        color: '#838383',
        fontSize: 15,
        paddingTop: 30,
        paddingLeft: 12,
        fontWeight: '400',
    },
    inputContainer: {
        width: '95%',
    },
    input: {
        borderColor: '#f8f8f8',
        fontSize:15,
    },
    inputLine:{
        borderColor:'#d6d6d6',
        borderBottomWidth:0.5,
    },
    buttonEnableContainer: {
        backgroundColor: '#d33d37',
        height: 50,
        width: '90%',
        marginLeft:10,
        borderRadius: 30,
    },
    buttonEnableTitle: {
        color: 'white',
        fontSize: 17,
        fontWeight: '400',
    },
    buttonDisableContainer: {
        backgroundColor: '#d6d6d6',
        height: 50,
        width: '90%',
        marginLeft:10,
        borderRadius:30,
    },
    buttonDisableTitle: {
        color: '#84848d',
        fontSize: 17,
        fontWeight: '400',
    },
})
