import React, {useEffect, useState} from 'react';
import {
  StyleSheet,
  View,
  KeyboardAvoidingView,
  ScrollView,
  Text, TouchableOpacity
} from 'react-native';
import BackButton from '../components/firebase/BackButton';
import SMSVerifyCode from 'react-native-sms-verifycode';
import {Button, Icon, Input} from 'react-native-elements';
import Ionicons from "react-native-vector-icons/Ionicons";
import {HeaderBackButton} from "@react-navigation/stack";
import {getStatusBarHeight} from "react-native-status-bar-height";
import GetDeviceInfo from "../components/GetDeviceInfo";
import {checkAuth, getVerification} from "../service/UserService";
import {EasyLoading, Loading} from "../components/EasyLoading";

export default function SendVerificationView({route, navigation}) {
  let email = "";
  if (route.params != undefined && route.params.email != undefined) {
    email = route.params.email;
    console.log(email);
  }

  // const [now, setNow] = useState(0);
  // const [finishTime, setFinishTime] = useState(0);
  const [title, setTitle] = useState('');
  const [finished, setFinished]= useState(false);
  const [message, setMessage] = useState('');
  const input1 = React.createRef();

  const createInterval=()=>{
    let tmp = Date.now();
    let tmp1 = tmp + 20 * 1000 + 100; //过期时间戳+容错(100ms)
    // this.interval && clearInterval(this.interval);
    if(this.interval!=undefined && this.interval!=null) {
      clearInterval(this.interval);
    }
    this.interval = setInterval(() => {
      let tmp2 = Date.now();
      console.log("tmp2-tmp1=",tmp2-tmp1);
      if (tmp2-tmp1>=100) {
        setTitle('重新获取');
        setFinished(true);
        if(this.interval!=undefined && this.interval!=null) {
          clearInterval(this.interval);
        }
        // this.interval && clearInterval(this.interval);
      } else {
        let tmp3 = Math.round((tmp1 - tmp2) / 1000) + 's';
        setTitle(tmp3);
      }
    }, 1000);
  }

  useEffect(() => {
    if (route.params != undefined && route.params.email != undefined) {
      email = route.params.email;
      console.log(email);
    }
    createInterval();
  }, []);

  const renderPress = () => {
    if(!finished) return;
    setFinished(false);
    createInterval();
  }

  const receiveData = (data) => {
    EasyLoading.dismiss();
    if (data == undefined || data == null) {
      input1.current.shake();
      setMessage("网络错误!");
    } else if (data.status == undefined || data.status == -1) {
      input1.current.shake();
      setMessage("验证码错误!");
    }
    else {
      navigation.navigate('ResetPassword',{email:email});
    }

  }

  const onInputCompleted = (text) => {
    let m = new Map();
    let deviceId= GetDeviceInfo();
    m.set("device_id",deviceId);
    m.set("code",text);
    EasyLoading.show();
    checkAuth(m,receiveData);

  }

  return (
      <ScrollView style={{backgroundColor: '#fefefe'}}>
        {/*<KeyboardAvoidingView behavior="padding">*/}
        <Loading/>
        <View style={styles.container}>
          <Button
              containerStyle={styles.iconContainer}
              buttonStyle={styles.buttonContainer}
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
        </View>
        <View style={styles.view1}>
          <Text style={styles.title}>请输入邮箱验证码</Text>
          <Text style={styles.subTitle}>邮箱验证码已发送至{email}</Text>
          <View style={{paddingTop: 10}}></View>
          <Input
              disabled={true}
              ref={input1}
              placeholder={message}
              inputContainerStyle={{borderBottomWidth:0}}
              inputStyle={{color:'#bb0a04',fontSize:15}}
              placeholderTextColor='#bb0a04'
          >
          </Input>
          <View style={{paddingTop: 20}}/>
        </View>
        <SMSVerifyCode
            verifyCodeLength={6}
            autoFocus={true}
            containerPaddingLeft={30}
            containerPaddingRight={30}
            codeViewBorderColor="#efefef"
            onInputCompleted={onInputCompleted}
            codeFontSize={20}
            // codeViewHeight={50}
            // codeViewWidth={50}
        />
        <View style={styles.view2}>
          <Button
              title={title}
              buttonStyle={styles.buttonContainer}
              titleStyle={styles.buttonTitle}
              onPress={() => {
                renderPress();
              }}
          />
        </View>
        {/*</KeyboardAvoidingView>*/}
      </ScrollView>
  )
}

const styles = StyleSheet.create({
  container: {
    paddingLeft: 20,
    paddingTop: 20,
    width: '100%',
    alignItems: 'flex-start',
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
  view2: {
    alignItems: 'flex-end',
    paddingRight: 20,
    paddingTop: 20,
  },
  buttonContainer: {
    backgroundColor: '#fefefe',
  },
  buttonTitle: {
    color: 'black',
    fontSize: 14,
    // fontWeight:"300",
  },
  iconContainer: {
    paddingTop: 20,
  }
});
