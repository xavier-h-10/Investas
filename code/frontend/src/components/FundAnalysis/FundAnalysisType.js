import React, {Component, useState} from "react";
import {Rating, Card, ListItem, Button, Icon} from 'react-native-elements';
import {View, StyleSheet, Text} from "react-native";
// import CircularSlider from 'react-native-elements-universe';
import CircularProgress from "../CircularProgress";

// 基金分析指标最上面一部分。根据基金类型（混合型-偏股，偏债..)，给出引导式信息
// 参数说明: 1-混合型-偏股  2-混合型-偏债...
const FundAnalysisType = (props) => {
  const [type,setType] = useState(props.type);

  const renderCard=(type)=>{
    let res=null;
    if(type==1) {
      res=(
          <Card containerStyle={{borderRadius: 10}}>
            <View style={{flexDirection: 'column'}}>
              <Text style={page.title}>混合型-偏股</Text>
              <Text style={page.content}>该类基金主要考核产品超越股票市场的相对收益能力，通过打败基准概率、最大回撤和夏普比率等指标，用以衡量这只基金的收益能力、抗跌能力和综合性价比。</Text>
            </View>
          </Card>
      )
    }
    return res;
  }

  return (
      <View>
        {renderCard(type)}
      </View>
  )
}

export default FundAnalysisType;

const page = StyleSheet.create({
  container: {
    backgroundColor: '#f8f8f8',
    borderRadius: 5,
    borderWidth: 0,
    margin: 0,
    marginLeft: 8,
    marginTop: 10,
    padding: 10,
    // width:150,
  },
  title: {
    color: '#565656',
    textAlign: 'left',
    fontWeight: 'bold',
    fontSize: 16,
    paddingBottom:5,
  },
  content:{
    lineHeight:20,
  },
});
// color='#3099ff';
