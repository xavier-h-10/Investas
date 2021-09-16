import React, {Component, useState} from 'react';
import {TouchableOpacity, StyleSheet, View, SafeAreaView} from 'react-native';
import {Text} from 'react-native-paper';
import {theme} from '../core/theme';
import {HeaderBackButton} from "@react-navigation/stack";
import Header from "../components/Header";
import UserSettingViewList from "../components/UserSettingViewList";
import {useNavigation} from "@react-navigation/native";
import {Input, Button} from "react-native-elements";
import {updateUserInfo} from "../service/UserService";
import message from 'react-native-message';
import {useRoute} from "@react-navigation/core";
import MyStatusBar from "../components/MyStatusBar";
import storage from "../components/LocalStorage";

const SettingNicknameView = () => {
  const navigation = useNavigation();
  const [enabled, setEnabled] = useState(false);
  const [nowValue, setNowValue] = useState("");
  const input = React.createRef();

  function changeText(value) {
    if (value == undefined || value == null || value == "") {
      setEnabled(false);
    } else {
      let regex = /^[a-zA-Z][a-zA-Z0-9_]{2,14}$/g;   //正则表达式,限定3-15个字母、数字、下划线
      if (regex.test(value)) {
        setEnabled(true);
      } else {
        setEnabled(false);
      }
    }
    setNowValue(value);
  }

  const receiveData = (data) => {
    console.log("data:", data);
    if (data && data.status == 0) {
      storage.save({
        key: 'loginState',
        data: {
          nickname: nowValue,
        }
      }).then(r => {
        console.log("SettingNicknameView: loginState stored");
        navigation.navigate("UserSetting",
            {status: 0, message: "修改昵称成功!", timestamp: new Date()});
      }).catch(err => {
        console.log("SettingNicknameView: loginState not stored ---", err);
        navigation.navigate("UserSetting",
            {status: 0, message: "修改昵称成功!", timestamp: new Date()});
      })
    } else {
      message.error("修改失败!");
    }
  }

  function renderPress() {
    if (enabled == false) {
      input.current.shake();
    } else {
      let m = new Map();
      m.set("nickname", nowValue);
      updateUserInfo(m, receiveData);
    }
  }

  return (
      <SafeAreaView style={{backgroundColor: '#fefefe'}}>
        {/*<Header*/}
        {/*    headerLeft={<HeaderBackButton onPress={navigation.goBack}/>}*/}
        {/*>*/}
        {/*  个人资料*/}
        {/*</Header>*/}
        <MyStatusBar color="#fefefe"/>
        <View style={{paddingLeft: 5, paddingTop: 20, width: 50}}>
          <HeaderBackButton onPress={navigation.goBack}/>
        </View>
        <View style={styles.container}>
          <Text style={styles.title}>请输入昵称</Text>
          {/*<Text style={noticed ? styles.subTitleNotice : styles.subTitle}>3~15个字母、数字</Text>*/}
          <Text style={styles.subTitle}>字母开头，3-15个字母、数字、下划线</Text>
          <Text style={styles.text}>昵称</Text>
          <Input
              containerStyle={styles.inputContainer}
              inputStyle={styles.input}
              onChangeText={value => changeText(value)}
              ref={input}
              autoCapitalize="none"
              inputContainerStyle={styles.inputLine}
          />
          <View style={{height: 20}}/>
          <View style={{alignItems: 'center', left: 5}}>
            <Button
                title="保存"
                buttonStyle={enabled ? styles.buttonEnableContainer
                    : styles.buttonDisableContainer}
                containerStyle={styles.buttonContainer}
                titleStyle={enabled ? styles.buttonEnableTitle
                    : styles.buttonDisableTitle}
                onPress={() => renderPress()}
            />
          </View>
        </View>
      </SafeAreaView>
  )
}

export default SettingNicknameView;

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
  },
  buttonDisableContainer: {
    backgroundColor: '#d6d6d6',
    height: 50,
    width: '90%',
    borderRadius: 30,
  },
  buttonDisableTitle: {
    color: '#84848d',
    fontSize: 17,
    fontWeight: '400',
    textAlign: 'center',
  },
  buttonEnableContainer: {
    backgroundColor: '#d33d37',
    height: 50,
    width: '90%',
    borderRadius: 30,
  },
  buttonEnableTitle: {
    color: 'white',
    fontSize: 17,
    fontWeight: '400',
  },
  buttonContainer: {
    borderRadius: 30,
    height: 50,
    width: '90%',
  },
  inputLine: {
    borderColor: '#d6d6d6',
    borderBottomWidth: 0.5,
  },
})
