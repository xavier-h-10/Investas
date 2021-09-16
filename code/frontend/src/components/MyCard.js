import React, {Component, useEffect, useState} from "react";
import {Avatar, Card, Divider, Icon, Overlay} from 'react-native-elements';
import {View, StyleSheet, Text, TouchableOpacity} from "react-native";
import {getUserInfo} from "../service/UserService";
import storage from "./LocalStorage";
import {useNavigation} from "@react-navigation/native";

//我的 card

export default function myCard(props) {
  let {refreshing, username, avatar, loginState} = props;
  const navigation = useNavigation();
  let defaultAvatar = require('../../resources/image/avatar.jpg');

  const renderAvatar = () => {
    if (loginState == true && avatar != '' && avatar!='data:') {
      console.log("----------");
      console.log(avatar);
    //  let str = 'data:image/png;base64,' + avatar.substring(5);
      let str= 'data:image/png;base64,'+avatar;
      return {uri: str};
    } else {
      return defaultAvatar;
    }
  }

  const renderHeader = () => {
    if (loginState == true && username != '') {
      return 'Hi, ' + username;
    } else {
      return '注册/登录';
    }
  }

  const renderPress = () => {
    if (loginState == false) {
      navigation.navigate('Login', {
        onGoBack:()=>{
          console.log("refresh!");
        },
      })
    }
    else {
      navigation.navigate('UserSetting',{})
    }
  }


  const renderCard = () => {
    console.log("renderCard");
    console.log(avatar);
    return (
        // <Card containerStyle={{borderRadius: 10}}>
        <View style={{width:'80%',paddingLeft:'8%',paddingTop:'5%'}}>
          <TouchableOpacity
              onPress={() =>
                  renderPress()}  //此处一定要用箭头函数
              style={{borderRadius: 10, flexDirection: 'row'}}
          >
            <Avatar
                size="large"
                rounded
                source={renderAvatar()}
            />
            <View style={{
              flexDirection: 'column',
              paddingLeft: 15,
              paddingTop: 5
            }}>
              <Text style={{fontSize: 20, fontWeight: 'bold',color:'#fefefe'}}>
                {renderHeader()}
              </Text>
              <View style={{
                borderRadius: 20,
                borderWidth: 1,
                marginTop: 10,
                width: 75,
                borderColor:'#fefefe'
              }}>
                <Text style={{textAlign: 'center',color:'#fefefe'}}>资料管理</Text>
              </View>
            </View>
          </TouchableOpacity>
        </View>
    );
  }

  return (
      renderCard()
  )
}

