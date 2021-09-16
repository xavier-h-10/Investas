import {
  View,
  Text,
  StyleSheet, Dimensions,
} from "react-native";
import React, {useEffect, useState} from "react";
import {ListItem} from "react-native-elements";
import Background from "../components/firebase/Background";
import Logo from "../components/firebase/Logo";
import BackButton from "../components/firebase/BackButton";
import MyStatusBar from "../components/MyStatusBar";

const AboutView = ({route, navigation}) => {
  const list = [
    {
      title: '客服热线',
      right: '021-34204124'
    },
    {
      title: '交我赚中台地址',
      right: '121.36.207.248:8001',
    },
    {
      title: '版本号',
      right: '2.1.0',
    },
  ]
  const width = Dimensions.get("window").width;
  const height = Dimensions.get("window").height;

  return (
      <Background>
        <BackButton goBack={navigation.goBack}/>
        <View style={{alignItems: 'center'}}>
          <MyStatusBar color={"#fefefe"}/>
          <Logo/>
          <Text style={{
            fontSize: 21,
            fontWeight: 'bold',
            paddingTop: 2,
          }}>
            交我赚
          </Text>
          <Text style={{
            fontSize: 16,
            paddingTop: 2,
          }}>
            Version 2.1.0
          </Text>
          <View style={{width: width * 0.85}}>
            <ListItem bottomDivider/>
            {
              list.map((l, i) => (
                  <ListItem key={i} bottomDivider
                            containerStyle={{display: 'flex'}}>
                    <ListItem.Content style={{flex: 4}}>
                      <ListItem.Title>{l.title}</ListItem.Title>
                    </ListItem.Content>
                    <ListItem.Content style={{alignItems: 'flex-end', flex: 5}}>
                      <ListItem.Subtitle>{l.right}</ListItem.Subtitle>
                    </ListItem.Content>
                  </ListItem>
              ))
            }
          </View>
        </View>
        <View style={{alignItems: 'center', paddingTop: height * 0.25}}>
          <Text style={page.bottomText}>交我赚 版权所有</Text>
          <Text style={page.bottomText}>CopyRight © 2021-2021 JWZ.</Text>
          <Text style={page.bottomText}>All Rights Reserved.</Text>
        </View>
      </Background>
  )
}

export default AboutView;

const page = StyleSheet.create({
  bottomText: {
    color: '#9f9f9f',
    fontSize: 13,
    lineHeight: 20,
  }
})
