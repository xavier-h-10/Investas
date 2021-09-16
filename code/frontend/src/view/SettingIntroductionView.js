import React, {useEffect, useState} from 'react';
import {
    StyleSheet,
    View,
    SafeAreaView,
    TextInput,
    Dimensions
} from 'react-native';
import {Text} from 'react-native-paper';
import {HeaderBackButton} from "@react-navigation/stack";
import Header from "../components/Header";
import {useNavigation, useRoute} from "@react-navigation/native";
import {Input, Button} from "react-native-elements";
import {updateUserInfo} from "../service/UserService";
import MyStatusBar from "../components/MyStatusBar";
import storage from "../components/LocalStorage";

const {width, height}=Dimensions.get("window");

const SettingIntroductionView = () => {
    const navigation = useNavigation();
    const route= useRoute();
    const [introduction, setIntroduction] = useState("");
    const [enabled, setEnabled] = useState(false);
    const [nowValue, setNowValue] = useState("");

    useEffect(() => {
        console.log("useEffect",route.params?.introduction);
        if (route.params?.introduction == undefined
            || route.params?.introduction == null) {
            setIntroduction("");
        } else {
            setIntroduction(route.params?.introduction);
        }
    },[route.params?.introduction]);

    function changeText(value) {
        if (value == undefined || value == null || value == "") {
            setEnabled(false);
        } else {
            let len = value.length;
            if (len <= 0 || len > 200) {
                setEnabled(false);
            } else {
                setEnabled(true);
            }

        }
        setNowValue(value);
    }

    const receiveData = (data) => {
        console.log("setting introduction receiveData:");
        console.log(data);
        if (data && data.status == 0) {
            storage.save({
                key: 'loginState',
                data: {
                    introduction: nowValue,
                }
            }).then(r => {
                console.log("SettingIntroductionView: loginState stored");
                navigation.navigate("UserSetting", {status: 0, message: "修改简介成功!", timestamp: new Date()});
            }).catch(err => {
                console.log("SettingIntroductionView: loginState not stored ---", err);
                navigation.navigate("UserSetting", {status: 0, message: "修改简介成功!", timestamp: new Date()});
            })
        } else {
            if (data.status == -1) {
                navigation.navigate("UserSetting", {status: -1, message: '您已退出登录!', timestamp: new Date()});
            } else {
                navigation.navigate("UserSetting", {status: -1, message: "修改简介失败!", timestamp: new Date()});
            }
        }

    }

    function renderPress() {
        if (enabled == true) {
            let m = new Map();
            m.set("introduction", nowValue);
            updateUserInfo(m, receiveData);
        }
    }

    return (
        <SafeAreaView>
            {/*<Header*/}
            {/*    headerLeft={<HeaderBackButton onPress={navigation.goBack}/>}*/}
            {/*>*/}
            {/*    个人资料*/}
            {/*</Header>*/}
            <MyStatusBar color="#fefefe"/>
            <View style={styles.container}>
                <View style={{paddingLeft: 5, paddingTop: 20, width: 50}}>
                    <HeaderBackButton onPress={navigation.goBack}/>
                </View>
                <TextInput
                    multiline={true}
                    style={styles.inputContainer}
                    defaultValue={introduction}
                    onChangeText={value => changeText(value)}
                    textAlignVertical={"top"}
                    autoCapitalize="none"
                />
                <Text style={styles.numberContainer}>{nowValue.length}/200</Text>
                <Button
                    title="保存"
                    buttonStyle={enabled ? styles.buttonEnableContainer
                        : styles.buttonDisableContainer}
                    titleStyle={enabled ? styles.buttonEnableTitle
                        : styles.buttonDisableTitle}
                    onPress={() => renderPress()}
                />
            </View>
        </SafeAreaView>
    )
}

export default SettingIntroductionView;

const styles = StyleSheet.create({
    container: {
        width: '100%',
        height: '100%',
        backgroundColor: '#eceded',
        borderTopWidth: 0.4,
        borderTopColor: '#dedede',
    },
    title: {
        fontSize: 25,
        paddingTop: 20,
        paddingLeft: 10,
    },
    subTitle: {
        color: '#d6d6d6',
        paddingTop: 14,
        paddingLeft: 12,
        fontSize: 15,
        fontWeight: '600',
    },
    subTitleNotice: {
        color: '#d33d37',
        paddingTop: 14,
        paddingLeft: 12,
        fontSize: 15,
        fontWeight: '500',
    },
    //此处高度不能按照20%来设置，否则有键盘没键盘时高度不一样 20210902
    inputContainer: {
        width: '100%',
        height: height*0.3,
        marginTop:5,
        backgroundColor: '#ffffff',
        borderLeftWidth: 15,
        borderTopWidth: 10,
        borderRightWidth: 15,
        borderColor: '#ffffff',
    //此处paddingLeft可能在不同机型上不一样，需要再修改 20210903
        paddingLeft:10,
    },
    input: {
        borderColor: '#f8f8f8',
    },
    buttonDisableContainer: {
        backgroundColor: '#d6d6d6',
        height: 50,
        width: '90%',
        borderRadius: 30,
        marginTop: 20,
        alignSelf: 'center',
    },
    buttonDisableTitle: {
        color: '#84848d',
        fontSize: 17,
        fontWeight: '400',
    },
    buttonEnableContainer: {
        backgroundColor: '#d33d37',
        height: 50,
        width: '90%',
        borderRadius: 30,
        marginTop: 20,
        alignSelf: 'center',
    },
    buttonEnableTitle: {
        color: 'white',
        fontSize: 17,
        fontWeight: '400',
    },
    numberContainer: {
        position: 'absolute',
        right: '5%',
        top: 0.29*height,
        color:'#2c2c2c',
    }
})
