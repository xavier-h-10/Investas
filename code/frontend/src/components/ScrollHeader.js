import React from 'react';
import {StyleSheet, useWindowDimensions, View} from 'react-native'
import {Text} from 'react-native-paper'

export default function ScrollHeader(props) {
  const window = useWindowDimensions();
  if(props.title==undefined) return null;
  const renderText=()=>{
    if(props.subTitle==undefined || props.subTitle=="") {
      return (
          <View style={[styles.headerView, {height: window.height * 0.06}]}>
          <View style={{width: '20%'}}>
            {
              props.headerLeft ? props.headerLeft : null
            }
          </View>
          <View style={{width: '60%'}}>
            <Text style={styles.headerText}>{props.title}</Text>
          </View>
          </View>
      )
    }
      else {
        return (
            <View style={[styles.headerView, {height: window.height * 0.06}]}>
              <View style={{width: '15%'}}>
                {
                  props.headerLeft ? props.headerLeft : null
                }
              </View>
            <View style={{width: '70%'}}>
              <Text style={styles.title} numberOfLines={1}> {props.title} </Text>
              <Text style={styles.subTitle}> {props.subTitle} </Text>
            </View>
            </View>
        )
      }
    }

  return (
      renderText()
  )
}

const styles = StyleSheet.create({
  headerView: {
    flexDirection: 'row',
    alignItems: 'center',
    // backgroundColor: '#FFFFFF',
    backgroundColor:'#2867ff',
  },
  headerText: {
    fontSize: 18,
    // fontWeight: 'bold',
    paddingVertical: 12,
    alignSelf: 'center',
    color:'#ffffff',
  },
  title:{
    fontSize: 15,
    // fontWeight: 'bold',
    alignSelf: 'center',
    color:'#ffffff',
  },
  subTitle:{
    fontSize:13,
    alignSelf: 'center',
    // paddingTop:0,
    // marginTop:0,
    color:'#ffffff',
  }
})
