/* 风险评测结果显示 20210908
 */

import React, {useEffect, useState} from 'react'
import {SafeAreaView, ScrollView, StyleSheet, Text, View} from 'react-native'
import Header from "../components/Header";
import {HeaderBackButton} from "@react-navigation/stack";
import Loading from "../components/Loading";
import {Button, Card} from "react-native-elements";
import {updateUserInfo} from "../service/UserService";

export default function RiskAnalysisResult({route, navigation}) {
  const {score, riskLevel} = route.params;
  const [riskNumber, setRiskNumber] = useState(3);
  const [riskDescription, setRiskDescription] = useState("平衡型");

  useEffect(() => {
    if ((score == undefined || score == null) && (riskLevel == undefined
        || riskLevel == null)) {
      return <Loading/>;
    }
    // console.log("hello use effect",score,riskLevel);
    if (riskLevel != undefined && riskLevel != null) {
      setRiskNumber(riskLevel);
      switch (riskLevel) {
        case 1:
          setRiskDescription("保守型");
          break;
        case 2:
          setRiskDescription("稳健型");
          break;
        case 4:
          setRiskDescription("积极型");
          break;
        case 5:
          setRiskDescription("激进型");
          break;
        default:
          setRiskDescription("平衡型");
      }
    } else {
      let m = new Map();
      if (score < 20) {
        setRiskNumber(1);
        m.set("risk_level", 1);
        setRiskDescription("保守型");
      } else if (score < 30) {
        setRiskNumber(2);
        m.set("risk_level", 2);
        setRiskDescription("稳健型");
      } else if (score < 40) {
        setRiskNumber(3);
        m.set("risk_level", 3);
        setRiskDescription("平衡型");
      } else if (score < 50) {
        setRiskNumber(4);
        m.set("risk_level", 4);
        setRiskDescription("积极型");
      } else {
        setRiskNumber(5);
        m.set("risk_level", 5);
        setRiskDescription("激进型");
      }
      updateUserInfo(m);
    }
  }, [score, riskLevel]);

  const renderProduct = () => {
    switch (riskNumber) {
      case 1:
        return "R1(低风险)";
      case 2:
        return "R1(低风险)、R2(中低风险)";
      case 3:
        return "R1(低风险)、R2(中低风险)、R3(中高风险)";
      case 4:
        return "R1(低风险)、R2(中低风险)、R3(中高风险)、R4(高风险)";
      default:
        return "R1(低风险)、R2(中低风险)、R3(中高风险)、R4(高风险)、R5(极高风险)";
    }
  }

  return (
      <SafeAreaView>
        <Header
            headerLeft={<HeaderBackButton onPress={navigation.goBack}/>}
        >
          风险评测
        </Header>
        <View style={page.background}>
          <Card containerStyle={page.card}>
            <View style={page.container}>
              <View>
                <Text style={page.text1}>您的风险类型是:</Text>
                <Text style={page.text2}>{riskDescription}</Text>
              </View>
              <Text style={page.textContainer}>
                <Text style={page.text3}>您属于普通投资者，您的风险承受能力为</Text>
                <Text style={page.text4}>"{riskDescription}"。</Text>
                <Text
                    style={page.text3}>根据投资者和产品/服务风险等级匹配的原则，与您风险承受能力等级相匹配的产品为: </Text>
                <Text style={page.text4}>{renderProduct()}</Text>
                <Text style={page.text3}>等级产品。</Text>
              </Text>
            </View>
          </Card>
          <View style={page.buttonContainer}>
            <Button
                title="完成"
                buttonStyle={page.buttonContainer1}
                titleStyle={page.buttonTitle1}
                onPress={()=>{
                  navigation.navigate("MainTab",{});
                }}
            />
            <Button
                title="重新评测"
                buttonStyle={page.buttonContainer2}
                titleStyle={page.buttonTitle2}
                onPress={()=>{
                  navigation.navigate("RiskAnalysis",{});
                }}
            />
          </View>
        </View>
      </SafeAreaView>
  )
}

const page = StyleSheet.create({
  background:{
    height:'100%',
    width:'100%',
    backgroundColor:'#fefefc',
  },
  card: {
    borderRadius: 10,
    paddingBottom: 0,
  },
  container:{
    width:'98%',
    paddingLeft:'2%',
  },
  text1: {
    color: '#949494',
    fontSize: 14,
    marginTop: 5,
  },
  text2: {
    color: 'black',
    fontSize: 25,
    marginTop: 12,
  },
  text3: {
    color: 'black',
  },
  text4: {
    color: '#ec362d',
    // fontWeight:'bold',
  },
  textContainer: {
    marginTop: 15,
    marginBottom:20,
    lineHeight: 22,
    // textAlign:'justify',
  },
  buttonContainer:{
    marginTop:35,
  },
  buttonContainer1: {
    backgroundColor: '#d33d37',
    height: 50,
    // paddingTop:0,
    width: '90%',
    borderRadius: 30,
    alignSelf: 'center',
  },
  buttonTitle1: {
    color: 'white',
    fontSize: 17,
    fontWeight: '400',
  },
  buttonContainer2: {
    backgroundColor: '#fefefc',
    height: 50,
    marginTop:10,
    width: '90%',
    borderRadius: 30,
    borderWidth:1,
    borderColor: '#d33d37',
    alignSelf: 'center',
  },
  buttonTitle2: {
    color: '#d33d37',
    fontSize: 17,
    fontWeight: '400',
  },

})
