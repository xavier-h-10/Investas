import {
  View,
  Text,
  StyleSheet, Dimensions,
} from "react-native";
import React, {useEffect, useState} from "react";
import {Divider, ListItem} from "react-native-elements";
import MyStatusBar from "../components/MyStatusBar";
import Header from "../components/Header";
import {HeaderBackButton} from "@react-navigation/stack";

const QuestionView = ({route, navigation}) => {
  const list = [
    {
      title: '为什么我的账号显示无法登录?',
      content: '一般该问题发生都是因为网络问题较差，这边建议您切换至4G网络/流畅WIFI后，重新登录。',
    },
    {
      title: '为什么登录账号时显示信息错误?',
      content: '首先请您再次确认是否输入了正确的用户名和密码，如果依旧出现该问题，请您拨打021-34204124，联系我们的客服解决该问题。',
    },
    {
      title: '忘记密码怎么办?',
      content: '请您进入忘记密码页面，发送验证码给注册邮箱并验证通过后，可重置您的密码。',
    },
    {
      title: '什么是模拟组合?',
      content: '模拟组合，即模拟投资组合，就是一个模拟炒基账户。您是投资组合的管理人，投资组合是您投资思路最直接的反应，也是您发现投资机会的最新方式。每个账号的初始虚拟基金默认为100万元，可买卖指定的基金。',
    },
  ]
  const width = Dimensions.get("window").width;
  const height = Dimensions.get("window").height;

  const [show,setShow]=useState([false,false,false,false]);

  const renderContent=(key,content)=>{
    if(!show[key]) {
      return null;
    }
    else {
      return (
          <View style={{backgroundColor:'#fefefe'}}>
          <View style={{width:'95%',paddingLeft:'5%',paddingVertical:'2%'}}>
            <Text style={{
              fontSize:15,
              lineHeight:25,
              textAlign:'justify',
            }}>
              {content}
            </Text>
          </View>
          <Divider/>
          </View>

      )
    }
  }

  return (
      // <Background>
      <View>
        <View style={{alignItems: 'center'}}>
          <MyStatusBar/>
          <Header
              headerLeft={<HeaderBackButton onPress={navigation.goBack} tintColor={"#fefefe"}/>}
          >
            常见问题
          </Header>
          <View style={{width: width,paddingTop:20}}>
            {
              list.map((l, i) => (
                  <View>
                    <ListItem key={i} bottomDivider
                              containerStyle={{display: 'flex'}}
                              onPress={() => {
                                let tmp = show;
                                tmp[i] = !tmp[i];
                                setShow([...tmp]);   //此处防止不渲染 20210912
                              }}
                    >
                      <ListItem.Content style={{flex: 1}}>
                        <ListItem.Title>{l.title}</ListItem.Title>
                      </ListItem.Content>
                      <ListItem.Chevron/>
                    </ListItem>
                    {renderContent(i,l.content)}
                  </View>
              ))
            }
          </View>
        </View>
      </View>
  )
}

export default QuestionView;

const page = StyleSheet.create({
  bottomText: {
    color: '#9f9f9f',
    fontSize: 13,
    lineHeight: 20,
  }
})
