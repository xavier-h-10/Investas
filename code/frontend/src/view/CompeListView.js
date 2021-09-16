import React, {useEffect, useState} from "react";
import {SafeAreaView, ScrollView, StyleSheet, Text, useWindowDimensions, View} from "react-native";
import {getFundCompeList, getFundCompeRank, getFundCompeSimple, getUserCompeHold} from "../service/FundCompeService";
import Header from "../components/Header";
import {HeaderBackButton} from "@react-navigation/stack";
import Loading from "../components/Loading";
import {Button, Card} from "react-native-elements";
import {fundTypetoRiskText, fundTypeToText} from "../utils/typeConvert";
import {useNavigation} from "@react-navigation/native";
import MyStatusBar from "../components/MyStatusBar";

const test={
    "end_date": "2021-09-16",
    "number": 1,
    "competition_name": "股票型基金周赛",
    "allowed_fund_type": [
        0,1,2,3
    ],
    "competition_id": 1,
    "competition_description": "no",
    "start_date": "2021-09-01",
    "capacity": 100
}

const CompetitionCard=(props)=>{
    const {competition}= props
    const navigation = useNavigation();

    const makeTag=(type)=>{
        return (
            <Card containerStyle={page.box}
                     wrapperStyle={{padding: 0, margin: 0}}
                  index={type}
            >
                <Text style={page.yearText}>{fundTypeToText(type)}</Text>
            </Card>
        )
    }

    const renderTags=(allowed_fund_type)=>{
        if(allowed_fund_type==null)
            return null;

        if(allowed_fund_type.length>4)
        {
            const len=allowed_fund_type.length;
            let lines=[];
            let i=0
            for(;i+4<=len;i+=4)
            {
                let line=[];
                let type1=allowed_fund_type[i];
                let type2=allowed_fund_type[i+1];
                let type3=allowed_fund_type[i+2];
                let type4=allowed_fund_type[i+3];
                line.push(makeTag(type1));line.push(makeTag(type2));line.push(makeTag(type3));line.push(makeTag(type4));
                lines.push(
                    <View style={{paddingTop: 3, flexDirection: 'row'}} key={i}>
                        {line}
                    </View>
                );

            }
            if(i+1<len)
            {
                let remain=[];
                for(;i<len;++i)
                {
                    remain.push(makeTag(allowed_fund_type[i]));
                }
                lines.push(
                    <View style={{paddingTop: 2, flexDirection: 'row'}} key={i}>
                        {remain}
                    </View>
                );

            }

            return lines;

        }
        else
        {
            let tagArray=[];
            competition.allowed_fund_type.map((type)=>{
                tagArray.push(
                    makeTag(type)
                )});
            return(
                <View style={{paddingTop: 2, flexDirection: 'row'}}>
                    {tagArray}
                </View>
            )
        }
    }


    return(
        <Card containerStyle={{borderRadius: 10,borderWidth:0}}>
            <View style={{flexDirection: 'column', alignItems: 'flex-start',paddingLeft:5}}>
                <View style={{flexDirection: 'row'}}>
                    <Text style={{fontWeight: 'bold',fontSize:20}} numberOfLines={1}>{competition.competition_name}</Text>
                </View>
                <Text style={{color:'#808080',fontSize:14,paddingTop:5}} numberOfLines={1}>
                    本期时间:
                    <Text style={{color:'red',fontSize:14}}>{"   "+competition.start_date+" "}</Text>
                    至
                    <Text style={{color:'red',fontSize:14}}>{" "+competition.end_date}</Text>
                </Text>
                <Text style={{color:'#808080',fontSize:14,paddingTop:5}} numberOfLines={1}>
                    已报人数:
                    <Text style={{color:'red',fontSize:14}}>{" "+competition.number+"   "}</Text>
                    容量:
                    <Text style={{color:'red',fontSize:14}}>{" "+competition.capacity}</Text>
                </Text>
            </View>
            <View>
                <Text style={{color:'#808080',fontSize:14,paddingTop:5,paddingLeft:5}} numberOfLines={1}>本场比赛允许购买的基金类型如下：</Text>
                {
                    renderTags(competition.allowed_fund_type)
                }
            </View>


            <View style={{paddingTop: 15}}>
                <Button
                    title={'参与比赛'}
                    onPress={() => {
                        navigation.navigate('CompeJoin',{competition:competition})
                    }}
                    type={'outline'}
                    buttonStyle={page.buttonEnableContainer}
                    titleStyle={page.buttonEnableTitle}
                    // buttonStyle={{backgroundColor: 'white', borderRadius: 10,height:30}}
                    // titleStyle={{color: '#0080FF',fontSize:9}}
                    // containerStyle={{width: '70%'}}
                />
            </View>

        </Card>
    );
}


const CompeListView=({route, navigation})=>{
    const [compeList,setCompeList]=useState(null);

    const window = useWindowDimensions()

    const getCompeListCallback=(data)=>{
        console.log(data)
        if(data.status!==100||data.data===null|data.data.list===null)
            return;
        console.log(data.data.list)
        setCompeList(data.data.list);

    }



    useEffect(() => {
        getFundCompeList(getCompeListCallback);
    }, [] );


    return (
        <SafeAreaView>
            <Header
                headerLeft={<HeaderBackButton onPress={navigation.goBack} tintColor={"white"}/>}
            >
                所有比赛
            </Header>
            <MyStatusBar/>
            <ScrollView  style={{height: window.height - window.height * 0.1}}>
                {
                    (compeList===null)?null:(

                        compeList.map((competition,index)=>{
                            return <CompetitionCard competition={competition} key={index}/>
                        })
                    )
                }
            </ScrollView>

        </SafeAreaView>


    );
};

export default CompeListView

const page = StyleSheet.create({
    box: {
        backgroundColor: '#eef5fe',
        borderRadius: 2,
        borderWidth: 0,
        margin: 0,
        marginLeft: 5,
        marginTop: 5,
        padding: 0,
        paddingLeft: 5,
        paddingRight: 5,
        paddingTop: 1,
        paddingBottom: 1,
        height: 17,
    },
    yearText: {
        color: '#65a7fa',
        fontSize: 10,
        // fontWeight: 'bold',
    },
    buttonEnableContainer: {
        backgroundColor: '#d33d37',
        height: 45,
        width: '85%',
        // marginLeft:'15%',
        marginLeft:'7%',
        marginTop:'5%',
        borderRadius: 30,
    },
    buttonEnableTitle: {
        color: 'white',
        fontSize: 15,
        fontWeight: '400',
    },
});
