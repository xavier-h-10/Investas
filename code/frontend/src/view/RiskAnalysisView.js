import React, {useEffect, useState} from 'react'
import {SafeAreaView, ScrollView, StyleSheet, Text, View} from 'react-native'
import Header from "../components/Header";
import {HeaderBackButton} from "@react-navigation/stack";
import Loading from "../components/Loading";
import {Button, Card} from "react-native-elements";
import {getRiskAssessment} from "../service/UserService";

export default function RiskAnalysisView({route, navigation}) {
  const [total, setTotal] = useState(14);
  const [now, setNow] = useState(1);
  const [data, setData] = useState(null);
  const [enabled, setEnabled] = useState(false);
  const [ans, setAns] = useState([]);

  const receiveData = (data) => {
    if (data == undefined || data == null || data.status != 100 || data.message
        == undefined || data.message == null) {
      return;
    }
    let tmp = JSON.parse(data.message);
    setTotal(tmp.length);
    setNow(1);
    setData(tmp);
    let tmp1 = [];
    for (let i = 0; i < tmp.length; i++) {
      tmp1.push([-1, -1]);
    }
    setAns(tmp1);
    setEnabled(false);
  }

  const renderNow = () => {
    if (now < 10) {
      return "0" + String(now);
    } else {
      return String(now);
    }
  }

  const renderQuestion = () => {
    if (data[now - 1] == undefined || data[now - 1] == null || data[now
        - 1].question
        == undefined || data[now - 1].question == null) {
      return "";
    }
    return data[now - 1].question;
  }

  const renderButton = () => {
    if (now < total) {
      return null;
    }
    return (
        <View style={{paddingTop: 20}}>
          <Button
              title="确认并提交"
              buttonStyle={enabled ? page.buttonEnableContainer
                  : page.buttonDisableContainer}
              titleStyle={enabled ? page.buttonEnableTitle
                  : page.buttonDisableTitle}
              onPress={() => {
                let score=0;
                for(let i=0; i< ans.length; i++) {
                  score+=ans[i][1];
                }
                console.log(score);
                navigation.navigate('RiskAnalysisResult',{score:score});
              }}
          />
        </View>
    );
  }

  const renderPre = () => {
    if (now == 1) {
      return (
          <View style={{marginTop: 30}}/>
      )
    }
    return (
        <View style={page.view3}>
          <Button
              title="上一题"
              buttonStyle={page.buttonContainer}
              titleStyle={page.buttonTitle}
              onPress={() => {
                setNow(now - 1);
              }}
          >
          </Button>
        </View>
    )
  }

  const renderOptionPress=(key,score)=>{
    let ans1 = ans;
    ans1[now - 1] = [key,score];
    setAns(ans1);
    console.log("want to see",ans[now-1],key==ans[now-1][0]);
    if (now < total) {
      setNow(now + 1);
    } else {
      setEnabled(true);
    }
    // this.forceUpdate();
  }

  const renderOption = () => {
    if (data[now - 1] == undefined || data[now - 1] == null) {
      return null;
    }
    let tmp = data[now - 1];
    console.log(tmp);
    let info = [];
    for (let i = 1; i <= 5; i++) {
      let option = "option_" + i;
      let score = "score_" + i;
      if (tmp[option] != undefined && tmp[option] != null && tmp[score]
          != undefined && tmp[score] != null) {
        info.push({
          option: tmp[option],
          score: tmp[score],
        })
      }
    }
    let res = info.map((item, key) => {
      if (ans[now - 1] == undefined || ans[now - 1][0] == undefined || ans[now
      - 1][0] == null) {
        return null;
      }
      return (
          <Button
              title={item.option}
              buttonStyle={key == ans[now - 1][0] ? page.buttonEnableContainer1
                  : page.buttonDisableContainer1}
              titleStyle={key == ans[now - 1][0] ? page.buttonEnableTitle1
                  : page.buttonDisableTitle1}
              onPress={()=>renderOptionPress(key,item.score)}></Button>
      )
    })

    return (
        <View style={page.view2}>
          {res}
        </View>
    );
  }

  useEffect(() => {
    getRiskAssessment(receiveData);
  }, []);

  if (data == null) {
    return <Loading/>;
  }

  return (
      <SafeAreaView>
        <Header
            headerLeft={<HeaderBackButton onPress={navigation.goBack}/>}
        >
          风险评测
        </Header>
        <View style={{height: '100%', backgroundColor: '#fefefe'}}>
          <Card containerStyle={page.card}>
            <View style={page.view1}>
              <Text style={page.text1}>{renderNow()}</Text>
              <Text style={page.text2}>/{total}</Text>
              <Text style={page.text3}>{renderQuestion()}</Text>
            </View>
            {renderOption()}
            {renderPre()}
          </Card>
          {renderButton()}
          <Text style={page.text5}>请根据您的实际情况评测风险承受能力，以便您做出理性的投资决策</Text>
        </View>
      </SafeAreaView>
  )
}

const page = StyleSheet.create({
  card: {
    borderRadius: 10,
    // paddingBottom:25,
    paddingBottom: 0,
  },
  view1: {
    flexDirection: 'row',
    // alignItems: 'flex-end',
    width: '80%',
  },
  text1: {
    color: '#d43e34',
    fontSize: 25,
  },
  text2: {
    color: '#bbbcbf',
    fontSize: 13,
    paddingLeft: 5,
    paddingTop: 9,
  },
  text3: {
    color: 'black',
    fontWeight: 'bold',
    fontSize: 17,
    marginLeft: 15,
    paddingTop: 5,
    lineHeight: 25,
    textAlign: 'justify',
  },
  text4: {
    color: '#81838c',
    fontSize: 10,
  },
  buttonEnableContainer: {
    backgroundColor: '#d33d37',
    height: 50,
    // paddingTop:0,
    width: '90%',
    borderRadius: 30,
    alignSelf: 'center',
  },
  buttonEnableTitle: {
    color: 'white',
    fontSize: 17,
    fontWeight: '400',
  },
  buttonDisableContainer: {
    backgroundColor: '#d6d6d6',
    height: 50,
    width: '90%',
    borderRadius: 30,
    alignSelf: 'center',
  },
  buttonDisableTitle: {
    color: '#84848d',
    fontSize: 17,
    fontWeight: '400',
  },
  text5: {
    width: '90%',
    // alignSelf: 'center',
    paddingTop: 20,
    fontSize: 13,
    lineHeight:20,
    color: '#d5d6d7',
    textAlign:'center',
  },
  view2: {
    paddingTop: 40,
  },
  buttonEnableContainer1: {
    width: '85%',
    minHeight: 45,
    alignSelf: 'center',
    marginTop: 10,
    backgroundColor: '#fefefc',
    borderColor: '#d44036',
    borderWidth: 1,
    borderRadius: 0,
  },
  buttonEnableTitle1: {
    color: '#d44036',
    fontSize: 13,
    fontWeight: '400',
    textAlign: 'left',
    textAlignVertical: 'center',
    width: '95%',
    lineHeight: 20,
  },
  buttonDisableContainer1: {
    width: '85%',
    minHeight: 45,
    alignSelf: 'center',
    marginTop: 10,
    backgroundColor: '#f4f7fc',
    borderRadius: 0,
  },
  buttonDisableTitle1: {
    color: '#84848d',
    fontSize: 13,
    fontWeight: '400',
    textAlign: 'left',
    textAlignVertical: 'center',
    width: '95%',
    lineHeight: 20,
  },
  view3: {
    width: '100%',
    alignItems: 'center',
  },
  buttonContainer: {
    backgroundColor: '#fefefc',
    // backgroundColor: 'black',
    marginTop: 5,
    marginBottom: 5,
  },
  buttonTitle: {
    color: '#646464',
    fontSize: 13,
    textAlign: 'center',
    textAlignVertical: 'center',
  },
})
