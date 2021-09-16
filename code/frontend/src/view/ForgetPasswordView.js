import React, {useState} from 'react';
import {
  StyleSheet,
  View,
  KeyboardAvoidingView,
  ScrollView,
  Text
} from 'react-native';
import BackButton from '../components/firebase/BackButton';
import {Input, Button} from "react-native-elements";
import {getVerification} from "../service/UserService";
import GetDeviceInfo from "../components/GetDeviceInfo";
// import message from "react-native-message";
import Toast from '../components/firebase/Toast'
import {theme} from "../core/theme";
import {Snackbar} from "react-native-paper";
import {getStatusBarHeight} from "react-native-status-bar-height";
import {EasyLoading,Loading} from '../components/EasyLoading';
import Ionicons from "react-native-vector-icons/Ionicons";
import MyStatusBar from "../components/MyStatusBar";

export default function ForgetPasswordView({route, navigation}) {

  const [email, setEmail] = useState("");
  const [enabled, setEnabled] = useState(false);
  const [message, setMessage] = useState("");
  const input = React.createRef();
  const input1 = React.createRef();

  function changeText(value) {
    console.log(value);
    if (value == undefined || value == null || value == "") {
      setEnabled(false);
    } else {
      let regex = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      if (regex.test(value)) {
        setEnabled(true);
      } else {
        setEnabled(false);
      }
    }
    setEmail(value);
  }

  function receiveData(data) {
    console.log(data);
    EasyLoading.dismiss();
    if (data == undefined || data == null) {
      input1.current.shake();
      setMessage('网络错误');
    } else if (data.status == null || data.status != 0) {
      input1.current.shake();
      setMessage('该用户不存在!');
    } else {
      navigation.navigate('SendVerification', {email: email});
    }
  }

  function renderPress() {
    if (enabled == false) {
      input.current.shake();
    } else {
      // let m=new Map();
      // m.set("nickname",nowValue);
      // updateUserInfo(m, receiveData);
      let m = new Map();
      let deviceId = GetDeviceInfo();
      m.set("device_id", deviceId);
      m.set("email", email);
      EasyLoading.show();
      getVerification(m, receiveData);
      //navigation.navigate('SendVerification',{email:email});
    }
  }

  return (
      <ScrollView style={{backgroundColor: '#fefefe'}}>
        <KeyboardAvoidingView behavior="padding">
          <View style={styles.container1}>
            <Button
                containerStyle={styles.iconContainer}
                buttonStyle={styles.buttonContainer1}
                icon={
                  <Ionicons
                      name="chevron-back-outline"
                      size={26}
                  />
                }
                onPress={() => {
                  navigation.goBack();
                }}
            />
            <MyStatusBar color={"#fefefe"}/>
          </View>
          <Loading/>
          <View style={styles.view1}>
            <Text style={styles.title}>请输入您的邮箱</Text>
            <Text style={styles.subTitle}>请确保邮箱可正常接收邮件</Text>
            <View style={{paddingTop: 15}}></View>
          </View>
          <View style={{paddingLeft: 20}}>
            <Input
                disabled={true}
                ref={input1}
                placeholder={message}
                inputContainerStyle={{borderBottomWidth:0}}
                inputStyle={{color:'#bb0a04',fontSize:15}}
                placeholderTextColor='#bb0a04'
            >
            </Input>
            <Input
                containerStyle={styles.inputContainer}
                inputContainerStyle={styles.inputLine}
                inputStyle={styles.input}
                onChangeText={value => changeText(value)}
                ref={input}
            />
            <View style={{paddingTop:10}}></View>
            <Button
                title="下一步"
                buttonStyle={enabled ? styles.buttonEnableContainer
                    : styles.buttonDisableContainer}
                containerStyle={styles.buttonContainer}
                titleStyle={enabled ? styles.buttonEnableTitle : styles.buttonDisableTitle}
                onPress={()=>renderPress()}
            />
          </View>
        </KeyboardAvoidingView>
      </ScrollView>
  )
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingLeft: 20,
    paddingTop: 20,
    width: '100%',
    maxWidth: 340,
    alignSelf: 'center',
    alignItems: 'center',
    justifyContent: 'center',
  },
  view1: {
    paddingLeft: 20,
    width: '100%',
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
  buttonDisableContainer: {
    backgroundColor: '#d6d6d6',
    height: 50,
    width: '90%',
    // alignSelf: 'center',
    borderRadius:30,
  },
  buttonDisableTitle: {
    color: '#84848d',
    fontSize: 17,
    fontWeight: '400',
  },
  buttonContainer: {
    borderRadius: 30,
    height: 50,
    width: '90%',
    marginLeft: 25,
  },
  inputContainer: {
    width: '95%',
  },
  input: {
    borderColor: '#f8f8f8',
  },
  inputLine:{
    borderColor:'#d6d6d6',
    borderBottomWidth:0.5,
  },
  snackbar:{
    position:'absolute',
    top: 80 + getStatusBarHeight(),
    width: '35%',
    left:'30%',
  },
  buttonContainer1: {
    backgroundColor: '#fefefe',
  },
  iconContainer: {
    paddingTop: 20,
  },
  container1: {
    paddingLeft: 20,
    paddingTop: 20,
    width: '100%',
    alignItems: 'flex-start',
  },
});
