import React, {Component, useState} from "react";
import {Text, View} from "react-native";
import {AnimatedCircularProgress} from 'react-native-circular-progress';

//圆形进度条
const CircularProgress = (props) => {
  const [value, setValue] = useState(props.value);

  return (
      <View style={{paddingTop:5,alignItems:'center'}}>
        <AnimatedCircularProgress
            size={55}
            width={6}
            fill={value}
            tintColor="#65aeff"
            backgroundColor="#3d5875">
          {
            (value) => (
                <Text style={{fontSize: 15, fontWeight: '500'}}>
                  {value}%
                </Text>
            )
          }
        </AnimatedCircularProgress>
      </View>
  )
}
export default CircularProgress;
