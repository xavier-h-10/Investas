import {Alert, SafeAreaView, ScrollView, Text, TextInput, useWindowDimensions, View, StyleSheet} from "react-native";
import React from "react";
import Header from "../components/Header";
import {HeaderBackButton} from "@react-navigation/stack";
import {Button, Card} from "react-native-elements";
import {fundTypeToText} from "../utils/typeConvert";
import {valueNullDashHandler} from "../helpers/dataValue";
import {postJoinCompetition} from "../service/FundCompeService";
import MyStatusBar from "../components/MyStatusBar";

const JoinCompetitionCard=(props)=>{
    const {competition}= props

    const getFundTypeStr=()=>{
        let str="";
       if(competition.allowed_fund_type==null)
       {
           return null;
       }
       else {
           let len = competition.allowed_fund_type.length;
           if (len >= 11) {
               str = "全部";
           } else {
               competition.allowed_fund_type.map((type, index) => {
                   str += fundTypeToText(type);
                   if (index + 1 != len) {
                       str += ',';
                   }
               })
           }
       }
       return str;
    }

    return(
        <View>
            <View style={{flexDirection: 'column', alignItems: 'flex-start'}}>
                <Text style={{fontWeight: 'bold',fontSize:18,lineHeight:25}}>{valueNullDashHandler(competition.competition_name)}</Text>
                <Text style={{color:'#808080',fontSize:15,lineHeight:25}}>比赛ID:{"      "+valueNullDashHandler(competition.competition_id)}</Text>
                <Text style={{color:'#808080',fontSize:15,lineHeight:25}}>开始日期:{"  "+valueNullDashHandler(competition.start_date)}</Text>
                <Text style={{color:'#808080',fontSize:15,lineHeight:25}}>结束日期:{"  "+valueNullDashHandler(competition.end_date)}</Text>
                <Text style={{color:'#808080',fontSize:15,lineHeight:25}}>基金类型:{"  "+valueNullDashHandler(getFundTypeStr())}</Text>
                <Text style={{color:'#808080',fontSize:15,lineHeight:25}}>当前人数:{"  "+valueNullDashHandler(competition.number)}</Text>
                <Text style={{color:'#808080',fontSize:15,lineHeight:25}}>容量人数:{"  "+valueNullDashHandler(competition.capacity)}</Text>
                <Text style={{color:'#808080',fontSize:15,lineHeight:25}}>比赛说明:{"  "+valueNullDashHandler(competition.competition_description)}</Text>
            </View>
        </View>

    );
}

// const test={
//     "end_date": "2021-09-16",
//     "number": 1,
//     "competition_name": "股票型基金周赛",
//     "allowed_fund_type": [
//         0,1,2,3
//     ],
//     "competition_id": 123,
//     "competition_description": "no",
//     "start_date": "2021-09-01",
//     "capacity": 100
// }

const CompeJoinView=({route, navigation})=>{
    const {competition} = route.params

    const joinCompeCallback=(data)=>{
        console.log(data)
        if(data.status===-100||data.status===100)
        {
            Alert.alert(data.message)
        }
        else
        {
            Alert.alert("请求失败")
        }
    }

    const joinCompetition=()=>{
        data={
            "competitionId":competition.competition_id
        };
        postJoinCompetition(data,joinCompeCallback)
    }


    return(
        <SafeAreaView>
            <Header
                headerLeft={<HeaderBackButton onPress={navigation.goBack}
                                              tintColor={"white"}/>}
            >
                加入比赛
            </Header>
            <MyStatusBar/>
            <View style={{
                backgroundColor: '#fefefe',
                width: '100%',
                height: '100%'
            }}>
                <View style={{paddingLeft:10}}>
                    <View style={{marginHorizontal: 16, marginTop: 15}}>
                        <Text style={{
                            textAlign: 'left',
                            color: 'red',
                            fontSize: 18,
                            fontWeight: 'bold',
                            paddingBottom: 10
                        }}>请确认以下的比赛信息：</Text>
                        {
                            (competition===null)?null:(
                                <JoinCompetitionCard competition={competition}/>
                            )
                        }
                    </View>

                    <View style={{marginHorizontal: 16, marginTop: 20}}>
                        <Button
                            title="确认加入"
                            onPress={() => joinCompetition()}
                            buttonStyle={page.buttonEnableContainer}
                            titleStyle={page.buttonEnableTitle}
                        />
                    </View>
                </View>
            </View>

        </SafeAreaView>
    );
}

export default CompeJoinView;

const page=StyleSheet.create({
    buttonEnableContainer: {
        backgroundColor: '#d33d37',
        height: 45,
        width: '80%',
        // marginLeft:'15%',
        marginLeft:'10%',
        marginTop:'5%',
        borderRadius: 30,
    },
    buttonEnableTitle: {
        color: 'white',
        fontSize: 15,
        fontWeight: '400',
    },
})
