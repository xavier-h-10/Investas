import {ScrollView, View, SafeAreaView, Text, Pressable, useWindowDimensions} from "react-native";
import React, {useEffect, useState} from "react";
import {Button, Card} from "react-native-elements";
import {Caption, Title} from "react-native-paper";
import {numberColor, numberColor1, numberFormat} from "../helpers/numberColor";
import {HeaderBackButton} from "@react-navigation/stack";
import Header from "../components/Header";
import {getFundCompeRank, getFundCompeSimple, getUserCompeHold} from "../service/FundCompeService";
import {valueNullHandler,valueNullDashHandler} from "../helpers/dataValue";
import TabCard from "../components/TabCard";
import Loading from "../components/Loading";
import Ionicons from "react-native-vector-icons/Ionicons";

const CompetitionHeader=(props)=>{

    const {competition}=props;

    return(
        <View style={{paddingTop:15}}>
            <View style={{flexDirection: 'column', alignItems: 'center',marginHorizontal:20}}>
                <View style={{flexDirection: 'row', alignItems: 'center'}}>
                    <View>
                        <Text style={{fontWeight: 'bold',fontSize:20}} numberOfLines={1}>{competition.competition_name}</Text>
                    </View>
                </View>
              <View style={{paddingTop:5}}>
                <Text style={{color:'#808080'}} numberOfLines={1}>本期时间:{" "+competition.start_date+" "}至{" "+competition.end_date}</Text>
              </View>
                {/*<View style={{width: '20%', alignItems: 'flex-end'}}>*/}
                {/*    <Text style={{color:'#0080FF',fontSize:13}}>比赛详情</Text>*/}
                {/*</View>*/}
            </View>
        </View>
    );
}

const renderText = (text, hide, isTitle) => {
  if (!hide) {
    return text;
  } else {
    let len = text.length;
    let tmp = "*";
    if (isTitle) {
      return tmp.repeat(len);
    }
    tmp = tmp.repeat(Math.max(0, len - 3)) + ".**";
    return tmp;
  }
}

const CompetitionSummaryCard=(props)=>{
    const {user_info}=props
    const change=user_info.total_assert-user_info.total_change;
    const [hide, setHide] = useState(false);
    return(
        <View>
          <Text style={{fontWeight: 'bold',fontSize:18,paddingLeft:20,paddingTop:10}}>我的战绩</Text>
            <Card containerStyle={{borderRadius: 10, borderWidth:0}}>
              <View>
                <Text style={{textAlign:'center',fontSize:15}}>
                  持仓盈亏&nbsp;
                  <Ionicons
                      name={hide? "eye-off" : "eye"}
                      size={15}
                      onPress={()=>setHide(!hide)}
                  />
                </Text>
              </View>
                        <View style={{alignItems: 'center',paddingBottom:10}}>
                          <Text style={{
                            fontSize: 35,
                            color: numberColor1(change,hide)
                          }}>
                            {
                              renderText(numberFormat(change.toFixed(2)),hide,true)
                            }
                          </Text>
                        </View>
                <Card.Divider/>

                <View style={{flexDirection: 'row', alignItems: 'center'}}>
                    <View style={{width: '33%', alignItems: 'center'}}>
                        <Caption style={{fontSize: 13}}>总资产</Caption>
                        <Text style={{fontWeight: 'bold'}}>
                          {
                            renderText(valueNullHandler(user_info.total_assert.toFixed(2)),hide,false)
                          }
                        </Text>
                    </View>
                    <View style={{width: '33%', alignItems: 'center'}}>
                        <Caption style={{fontSize: 13}}>剩余现金</Caption>
                        <Text style={{fontWeight: 'bold'}}>
                          {
                            renderText(valueNullHandler(user_info.surplus_money.toFixed(2)),hide,false)
                          }
                        </Text>
                    </View>
                    <View style={{width: '33%', alignItems: 'center'}}>
                        <Caption style={{fontSize: 13}}>初始资金</Caption>
                        <Text style={{fontWeight: 'bold'}}>
                          {
                            renderText(valueNullHandler(user_info.total_change.toFixed(2)),hide,false)
                          }
                        </Text>
                    </View>
                </View>
            </Card>
        </View>
    );
}

const renderHoldItem = (item) => {
    return (
        <View style={{flexDirection: 'row', alignItems: 'center', marginTop: -5, marginBottom: 10}}>
            <Text style={{width: '25%', color: 'grey', textAlign: 'left', fontSize: 13}}>{item.fund_code}</Text>

            <Text style={{width: '25%', color: 'grey', textAlign: 'left', fontSize: 13}}>
                {valueNullDashHandler(item.nav)}
            </Text>
            <Text style={{width: '25%', color: 'grey', textAlign: 'left', fontSize: 13}}>
                {valueNullDashHandler(item.amount)}
            </Text>
            {
                (item.one_day_rate)?(
                    <Text style={{width: '25%', color: numberColor(item.one_day_rate), textAlign: 'left', fontSize: 13}}>
                        {numberFormat(item.one_day_rate)+"%"}
                    </Text>
                ):(
                    <Text style={{width: '25%', color: 'grey', textAlign: 'left', fontSize: 13}}>
                        {"--"}
                    </Text>
                )
            }
        </View>
    )
}

const renderTransactionItem = (item) => {


    return (
        <View style={{flexDirection: 'row', alignItems: 'center', marginTop: -5, marginBottom: 10}}>
            <Text style={{width: '20%', color: 'grey', textAlign: 'left', fontSize: 13}}>{(item.amount==null)?'--':((item.amount>0)?'买入':'卖出')}</Text>
            <Text style={{width: '25%', color: 'grey', textAlign: 'left', fontSize: 13}}>{valueNullDashHandler(item.fund_code)}</Text>
            <Text style={{width: '30%', color: 'grey', textAlign: 'left', fontSize: 13}}>{valueNullDashHandler(item.amount)}</Text>
            <Text style={{width: '25%', color: 'grey', textAlign: 'left', fontSize: 13}}>{valueNullDashHandler(item.date)}</Text>
        </View>
    )
}

const renderRankItem = (item) => {
    return (
        <View style={{flexDirection: 'row', alignItems: 'center', marginTop: -5, marginBottom: 10}}>
            <View style={{width: '30%', color: 'grey', textAlign: 'left', fontSize: 13}}>
                <Text>{item.rank}</Text>
            </View>
            <View style={{width: '40%', color: 'grey', textAlign: 'center', fontSize: 13}}>
                <Text>{item.nickname}</Text>
            </View>
            <View style={{width: '30%', color: 'grey', textAlign: 'center', fontSize: 13}}>
                <Text style={{color: numberColor(item.rate)}}>{numberFormat(item.rate)}</Text>
            </View>
        </View>
    )
}

const FundHoldCard=(props)=>{
    const {fundHoldArray}=props
    return(
        <View>
            <View style={{flexDirection: 'column', alignItems: 'center',paddingTop:10}}>
                <View style={{flexDirection: 'row', marginTop: -14}}>
                    <Caption style={{fontSize: 12, width: '25%', textAlign: 'left'}}>基金代码</Caption>
                    <Caption style={{fontSize: 12, width: '25%', textAlign: 'left'}}>最新净值</Caption>
                    <Caption style={{fontSize: 12, width: '25%', textAlign: 'left'}}>持有</Caption>
                    <Caption style={{fontSize: 12, width: '25%', textAlign: 'left'}}>一日盈亏</Caption>
                </View>
                {
                    (fundHoldArray!==null)?(
                        fundHoldArray.map((item, index) => (
                            <View key={index}>
                                <Card.Divider/>
                                {renderHoldItem(item)}
                            </View>
                        ))
                        ):(
                         <View>
                             <Text>尚无基金数据，快去参加比赛吧</Text>
                         </View>
                    )
                }
            </View>
        </View>
    )
}

const TransactionRecordCard=(props)=>{
    const {transactionRecord}=props
    return(
        <View>
            <View style={{flexDirection: 'column', alignItems: 'center',paddingTop:10}}>
                <View style={{flexDirection: 'row', marginTop: -14}}>
                    <Caption style={{fontSize: 12, width: '20%',textAlign: 'left'}}>状态</Caption>
                    <Caption style={{fontSize: 12, width: '25%', textAlign: 'left'}}>基金代码</Caption>
                    <Caption style={{fontSize: 12, width: '35%', textAlign: 'left'}}>份数</Caption>
                    <Caption style={{fontSize: 12, width: '20%', textAlign: 'left'}}>日期</Caption>
                </View>
                {
                    (transactionRecord!==null)?(
                        transactionRecord.map((item, index) => (
                            <View key={index}>
                                <Card.Divider/>
                                {renderTransactionItem(item)}
                            </View>
                        ))
                    ):(
                        <View>
                            <Text>尚无交易，快去参加比赛吧</Text>
                        </View>
                    )
                }
            </View>
        </View>
    )
}

const getDate = () => {
  let today = new Date();
  let year = today.getFullYear().toString();
  let month = (today.getMonth() + 1).toString();
  if (month.length < 2) {
    month = "0" + month;
  }
  let day = today.getDate().toString();
  if (day.length < 2) {
    day = "0" + day;
  }
  let res = year + "-" + month + "-" + day;
  return res;
}


const CompetitionRankCard=(props)=>{
    const {competitionRank}=props
    return(
        <View>
        <View style={{flexDirection: 'column', alignItems: 'flex-start'}}>
          <Text style={{fontWeight: 'bold',fontSize:18,paddingLeft:20,paddingTop:20}}>排行榜TOP5</Text>
          <Text style={{color:'#808080',fontSize:13,paddingLeft:20,paddingTop:5}}>{"截止时间:  "+getDate()}</Text>
        </View>
        <Card containerStyle={{borderRadius: 10, borderWidth:0}}>

            <View style={{flexDirection: 'column', alignItems: 'center'}}>
                <View style={{flexDirection: 'row'}}>
                    <Caption style={{fontSize: 12, width: '30%',textAlign: 'left'}}>排名</Caption>
                    <Caption style={{fontSize: 12, width: '40%', textAlign: 'left'}}>昵称</Caption>
                    <Caption style={{fontSize: 12, width: '30%', textAlign: 'left'}}>累计收益</Caption>
                </View>
                {
                    (competitionRank!==null)?(
                        competitionRank.map((item, index) => (
                            <View key={index}>
                                <Card.Divider/>
                                {renderRankItem(item)}
                            </View>
                        ))
                    ):(
                        <View>
                            <Text>尚无排名</Text>
                        </View>
                    )
                }
            </View>
        </Card>
        </View>
    )
}

const CompeTabCard=(props)=>{
    const {fundHoldArray,transactionRecord}=props
    const content = [
        {
            header: '持仓',
            body: <FundHoldCard fundHoldArray={fundHoldArray}/>
        },
        {
            header: '交易记录',
            body: <TransactionRecordCard transactionRecord={transactionRecord}/>
        },
    ]

    return(
        <View>
            <TabCard
                content={content}
                onChange={(index) => {
                }}
            />
        </View>
    );
}


const UserCompeDetailView=({route, navigation})=>{
    const {competitionId} = route.params
    const [userInfo,setUserInfo]=useState(null);
    const [transLog,setTransLog]=useState(null);
    const [competition,setCompetition]=useState(null);
    const [hold,setHold]=useState(null);
    const [rank,setRank]=useState(null);
    const window = useWindowDimensions()

    const getHoldCallback=(data)=>{
        if(data.status!==100)
            return;
        setUserInfo(data.data.user_info);
        setHold(data.data.items);
        setTransLog(data.data.log);
    }

    const getFundCompeSimpleCallback=(data)=>{
        if(data.status!==100)
            return;
        setCompetition(data.data);
    }

    const getFundCompeRankCallback=(data)=>{
        if(data.status!==100)
            return;
        setRank(data.data.rank_list);
    }

    useEffect(() => {
        getFundCompeSimple(competitionId,getFundCompeSimpleCallback);
        getUserCompeHold(competitionId, getHoldCallback);
        getFundCompeRank(competitionId,getFundCompeRankCallback);

    }, [] );





    return (
        <SafeAreaView>
            <Header
                headerLeft={<HeaderBackButton onPress={navigation.goBack} tintColor={"white"}/>}
            >
                比赛战绩
            </Header>
            <ScrollView style={{height: window.height- window.height * 0.11}}>
                {competition === null ? <Loading/>:<CompetitionHeader competition={competition}/>}
                {userInfo === null ? <Loading/>:<CompetitionSummaryCard user_info={userInfo}/>}
                {(hold === null|transLog==null) ? <Loading/>:<CompeTabCard fundHoldArray={hold} transactionRecord={transLog}/>}
                {rank === null ? <Loading/>:<CompetitionRankCard competitionRank={rank}/>}
            </ScrollView>
        </SafeAreaView>
    );
};

export default UserCompeDetailView
