import React from 'react'
import {StatusBar, StyleSheet} from 'react-native'

const MyStatusBar = (props) => {
  let color1 = "";
  let bar = "";
  if (props.color == undefined || props.color == null) {
    color1 = "#2867ff";
    bar = 'light-content';
  } else {
    color1 = props.color;
    bar = 'dark-content';
  }
  return (
      <StatusBar
          animated={true}
          hidden={false}
          backgroundColor={color1}
          translucent={false}
          barStyle={bar}
      />
  );
}

export default MyStatusBar;
