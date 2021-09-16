import React, {Component} from 'react';
import {TouchableOpacity, StyleSheet, View, SafeAreaView} from 'react-native';
import {Text} from 'react-native-paper';
import {theme} from '../core/theme';
import {HeaderBackButton} from "@react-navigation/stack";
import Header from "../components/Header";
import UserSettingViewList from "../components/UserSettingViewList";

export default function UserSettingView({route, navigation}){
  const {username, avatar} =route.params;


    return (
        <SafeAreaView>
          <Header
              headerLeft={<HeaderBackButton onPress={navigation.goBack} tintColor={"white"}/>}
          >
            个人资料
          </Header>
          <UserSettingViewList/>
        </SafeAreaView>
    )
}

const styles = StyleSheet.create({
  forgotPassword: {
    width: '100%',
    alignItems: 'flex-end',
    marginBottom: 24,
  },
  row: {
    flexDirection: 'row',
    marginTop: 4,
  },
  forgot: {
    fontSize: 13,
    color: theme.colors.secondary,
  },
  link: {
    fontWeight: 'bold',
    color: theme.colors.primary,
  },
})
