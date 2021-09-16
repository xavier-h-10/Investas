import {Badge, Card, Overlay, Icon, ButtonGroup} from 'react-native-elements';
import React, {Component, useEffect, useState} from 'react';
import {
    View,
    Text,
    ScrollView,
    StyleSheet,
    SafeAreaView,
    Image
} from 'react-native';
import {Caption, Headline, Title, Divider} from "react-native-paper";
// import MathView, {MathText} from 'react-native-math-view';
import FundAnalysisCard from "../components/FundAnalysis/FundAnalysisCard";
import FundAnalysisType from "../components/FundAnalysis/FundAnalysisType";
import Header from "../components/Header";
import {HeaderBackButton} from "@react-navigation/stack";
import {
    getFundAnalysis,
    getFundIndicator
} from "../service/FundAnalysisService";
import Loading from "../components/Loading";
import MyStatusBar from "../components/MyStatusBar";

//基金分析指标页
//前端页面传参：code：基金代码，name：基金名称
export default function FundAnalysisView({route, navigation}) {
    let {fundAnalysis} = route.params;
    let code = '000689';
    let name = '前海开源新经济混合A';
    let profit = 50;
    let risk = 50;
    let cost = 50;
    if (fundAnalysis != null && fundAnalysis != undefined) {
        code = fundAnalysis.code;
        name = fundAnalysis.name;
        profit = fundAnalysis.profit;
        risk = fundAnalysis.risk;
        cost = fundAnalysis.cost;
        // console.log("haha",profit, risk, cost);
    }
    if (profit == undefined || profit == null) {
        profit = 50;
    }
    if (risk == undefined || risk == null) {
        risk = 50;
    }
    if (cost == undefined || cost == null) {
        cost = 50;
    }
    if (code == undefined || code == null) {
        code = '000689';
    }
    if (name == undefined || name == null) {
        name = '前海开源新经济混合A';
    }
    const [visible_1, setVisible_1] = useState(false);
    const [visible_2, setVisible_2] = useState(false);
    const [visible_3, setVisible_3] = useState(false);
    const [visible_4, setVisible_4] = useState(false);
    const [visible_5, setVisible_5] = useState(false);
    const [visible_6, setVisible_6] = useState(false);

    const [Data, setData] = useState(null);
    const [Average, setAverage] = useState(null);
    const [didMount, setDidMount] = useState(false);
    const [mode, setMode] = useState(0);
    const [mode1, setMode1] = useState(0);
    const [mode2, setMode2] = useState(0);
    const [card, setCard] = useState("近一年");
    const [card2, setCard2] = useState("近一年");
    const [card1, setCard1] = useState("近三个月");

    const [MAXRETRA, setMAXRETRA] = useState("--");
    const [MAXRETRA_AVERAGE, setMAXRETRA_AVERAGE] = useState("--");
    const [MAXRETRA_RANK, setMAXRETRA_RANK] = useState("--");
    const [MAXRETRA_NFSC, setMAXRETRA_NFSC] = useState("--");

    const [PROFIT, setPROFIT] = useState("--");
    const [PROFIT_AVERAGE, setPROFIT_AVERAGE] = useState("--");
    const [PROFIT_RANK, setPROFIT_RANK] = useState("--");
    const [PROFIT_NFSC, setPROFIT_NFSC] = useState("--");

    const [SHARP, setSHARP] = useState("--");
    const [SHARP_AVERAGE, setSHARP_AVERAGE] = useState("--");
    const [SHARP_RANK, setSHARP_RANK] = useState("--");
    const [SHARP_NFSC, setSHARP_NFSC] = useState("--");

    const [STDDEV, setSTDDEV] = useState("--");
    const [STDDEV_AVERAGE, setSTDDEV_AVERAGE] = useState("--");
    const [STDDEV_RANK, setSTDDEV_RANK] = useState("--");
    const [STDDEV_NFSC, setSTDDEV_NFSC] = useState("--");

    const [received, setReceived] = useState(false);

    //此处已改造为从数据库中调数据  20210904
    if (didMount == false) {
        getFundIndicator(code,receiveData);
        setDidMount(true);
    }

    //query存后端拿过来的数据
    let query = null;

    function receiveData(data) {
        // console.log("receiveData called");
        if (data.message == undefined || data.message == null) {
            return;
        }
        let tmp = JSON.parse(data.message);
        if (tmp[0] == undefined || tmp[0] == null || tmp[1] == undefined
            || tmp[1] == null) {
            return;
        }
        // console.log(tmp[0]);
        // console.log(tmp[1]);
        setData(tmp[0]);
        setAverage(tmp[1]);
        setReceived(true);
        // setMode(0);
        // setMode1(0);
        // setMode2(0);
    }

    //query更改的时候，重新调用后端api，读取数据
    useEffect(() => {
        // getFundAnalysis(fundAnalysis.id, getData);
        // console.log("useEffect called id=", fundAnalysis.id);
    }, [query]);

    //设置三个mode: 1年 3年 5年 20210904
    //对于历史盈利概率，只有近三个月/近一年的数据，所以作出修改
    useEffect(()=>{
        if(Data == undefined || Data == null || Average == undefined || Average == null) return;
        if(mode1 == 0) {
            setCard1("近三个月");
            if(Data.pROFIT_Y) setPROFIT(Data.pROFIT_Y.toFixed(2)+"%"); else setPROFIT("--");
            if(Data.pROFIT_YRANK) setPROFIT_RANK(Data.pROFIT_YRANK); else setPROFIT_RANK("  --");
            if(Average.pROFIT_YFSC) setPROFIT_NFSC(Average.pROFIT_YFSC); else setPROFIT_NFSC("--");
            if(Average.pROFIT_Y_AVERAGE) setPROFIT_AVERAGE(Average.pROFIT_Y_AVERAGE.toFixed(2)+"%"); else setPROFIT_AVERAGE("--");
        }
        else {
            setCard1("近一年");
            if(Data.pROFIT_1N) setPROFIT(Data.pROFIT_1N.toFixed(2)+"%"); else setPROFIT("--");
            if(Data.pROFIT_1NRANK) setPROFIT_RANK(Data.pROFIT_1NRANK); else setPROFIT_RANK("  --");
            if(Average.pROFIT_1NFSC) setPROFIT_NFSC(Average.pROFIT_1NFSC); else setPROFIT_NFSC("--");
            if(Average.pROFIT_1N_AVERAGE) setPROFIT_AVERAGE(Average.pROFIT_1N_AVERAGE.toFixed(2)+"%"); else setPROFIT_AVERAGE("--");
        }

        if (mode == 0) {
            setCard("近一年");
            if(Data.mAXRETRA1) setMAXRETRA(Data.mAXRETRA1.toFixed(2)+"%"); else setMAXRETRA("--");
            if(Data.mAXRETRA_1NRANK) setMAXRETRA_RANK(Data.mAXRETRA_1NRANK); else setMAXRETRA_RANK("  --");
            if(Average.mAXRETRA_1NFSC) setMAXRETRA_NFSC(Average.mAXRETRA_1NFSC); else setMAXRETRA_NFSC("--");
            if(Average.mAXRETRA1_AVERAGE) setMAXRETRA_AVERAGE(Average.mAXRETRA1_AVERAGE.toFixed(2)+"%"); else setMAXRETRA_AVERAGE("--");

            if(Data.sTDDEV1) setSTDDEV(Data.sTDDEV1.toFixed(2)+"%"); else setSTDDEV("--");
            if(Data.sTDDEV_1NRANK) setSTDDEV_RANK(Data.sTDDEV_1NRANK); else setSTDDEV_RANK("  --");
            if(Average.sTDDEV_1NFSC) setSTDDEV_NFSC(Average.sTDDEV_1NFSC); else setSTDDEV_NFSC("--");
            if(Average.sTDDEV1_AVERAGE) setSTDDEV_AVERAGE(Average.sTDDEV1_AVERAGE.toFixed(2)+"%"); else setSTDDEV_AVERAGE("--");
        }
        else if(mode == 1) {
            setCard("近三年");
            if(Data.mAXRETRA3) setMAXRETRA(Data.mAXRETRA3.toFixed(2)+"%"); else setMAXRETRA("--");
            if(Data.mAXRETRA_3NRANK) setMAXRETRA_RANK(Data.mAXRETRA_3NRANK); else setMAXRETRA_RANK("  --");
            if(Average.mAXRETRA_3NFSC) setMAXRETRA_NFSC(Average.mAXRETRA_3NFSC); else setMAXRETRA_NFSC("--");
            if(Average.mAXRETRA3_AVERAGE) setMAXRETRA_AVERAGE(Average.mAXRETRA3_AVERAGE.toFixed(2)+"%"); else setMAXRETRA_AVERAGE("--");

            if(Data.sTDDEV3) setSTDDEV(Data.sTDDEV3.toFixed(2)+"%"); else setSTDDEV("--");
            if(Data.sTDDEV_3NRANK) setSTDDEV_RANK(Data.sTDDEV_3NRANK); else setSTDDEV_RANK("  --");
            if(Average.sTDDEV_3NFSC) setSTDDEV_NFSC(Average.sTDDEV_3NFSC); else setSTDDEV_NFSC("--");
            if(Average.sTDDEV3_AVERAGE) setSTDDEV_AVERAGE(Average.sTDDEV3_AVERAGE.toFixed(2)+"%"); else setSTDDEV_AVERAGE("--");
        }
        else {
            setCard("近五年");
            if(Data.mAXRETRA5) setMAXRETRA(Data.mAXRETRA5.toFixed(2)+"%"); else setMAXRETRA("--");
            if(Data.mAXRETRA_5NRANK) setMAXRETRA_RANK(Data.mAXRETRA_5NRANK); else setMAXRETRA_RANK("  --");
            if(Average.mAXRETRA_5NFSC) setMAXRETRA_NFSC(Average.mAXRETRA_5NFSC); else setMAXRETRA_NFSC("--");
            if(Average.mAXRETRA5_AVERAGE) setMAXRETRA_AVERAGE(Average.mAXRETRA5_AVERAGE.toFixed(2)+"%"); else setMAXRETRA_AVERAGE("--");

            if(Data.sTDDEV5) setSTDDEV(Data.sTDDEV5.toFixed(2)+"%"); else setSTDDEV("--");
            if(Data.sTDDEV_5NRANK) setSTDDEV_RANK(Data.sTDDEV_5NRANK); else setSTDDEV_RANK("  --");
            if(Average.sTDDEV_5NFSC) setSTDDEV_NFSC(Average.sTDDEV_5NFSC); else setSTDDEV_NFSC("--");
            if(Average.sTDDEV5_AVERAGE) setSTDDEV_AVERAGE(Average.sTDDEV5_AVERAGE.toFixed(2)+"%"); else setSTDDEV_AVERAGE("--");
        }

        if (mode2 == 0) {
            setCard2("近一年");
            if(Data.sHARP1) setSHARP(Data.sHARP1); else setSHARP("--");
            if(Data.sHARP_1NRANK) setSHARP_RANK(Data.sHARP_1NRANK); else setSHARP_RANK("  --");
            if(Average.sHARP_1NFSC) setSHARP_NFSC(Average.sHARP_1NFSC); else setSHARP_NFSC("--");
            if(Average.sHARP1_AVERAGE) setSHARP_AVERAGE(Average.sHARP1_AVERAGE); else setSHARP_AVERAGE("--");
        }
        else if(mode2 == 1) {
            setCard2("近三年");
            if(Data.sHARP3) setSHARP(Data.sHARP3); else setSHARP("--");
            if(Data.sHARP_3NRANK) setSHARP_RANK(Data.sHARP_3NRANK); else setSHARP_RANK("  --");
            if(Average.sHARP_3NFSC) setSHARP_NFSC(Average.sHARP_3NFSC); else setSHARP_NFSC("--");
            if(Average.sHARP3_AVERAGE) setSHARP_AVERAGE(Average.sHARP3_AVERAGE); else setSHARP_AVERAGE("--");
        }
        else {
            setCard2("近五年");
            if(Data.sHARP5) setSHARP(Data.sHARP5); else setSHARP("--");
            if(Data.sHARP_5NRANK) setSHARP_RANK(Data.sHARP_5NRANK); else setSHARP_RANK("  --");
            if(Average.sHARP_5NFSC) setSHARP_NFSC(Average.sHARP_5NFSC); else setSHARP_NFSC("--");
            if(Average.sHARP5_AVERAGE) setSHARP_AVERAGE(Average.sHARP5_AVERAGE); else setSHARP_AVERAGE("--");
        }
    },[mode, mode1,mode2,received]);

    //获取上一工作日
    const getLastWorkDate = () => {
        let yesterday = new Date();
        if (yesterday.getDay() == 0) {
            yesterday.setDate(yesterday.getDate() - 2);
        } else if (yesterday.getDay() == 1) {
            yesterday.setDate(yesterday.getDate() - 3);
        } else {
            yesterday.setDate(yesterday.getDate() - 1);
        }
        let year = yesterday.getFullYear().toString();
        let month = (yesterday.getMonth() + 1).toString();
        if (month.length < 2) {
            month = "0" + month;
        }
        let day = yesterday.getDate().toString();
        if (day.length < 2) {
            day = "0" + day;
        }
        let res = year + "-" + month + "-" + day;
        // console.log(res);
        return res;
    }

    //设置overlay的可见性
    const toggleOverlay_1 = () => {
        setVisible_1(!visible_1);
    }

    const toggleOverlay_2 = () => {
        setVisible_2(!visible_2);
    }

    const toggleOverlay_3 = () => {
        setVisible_3(!visible_3);
    }

    const toggleOverlay_4 = () => {
        setVisible_4(!visible_4);
    }

    const toggleOverlay_5 = () => {
        setVisible_5(!visible_5);
    }

    const toggleOverlay_6 = () => {
        setVisible_6(!visible_6);
    }

    if (Data == undefined || Data == null || Average == undefined || Average
        == null || Average.typeDescription == undefined || Average.typeName
        == undefined) {
        return <Loading/>;
    } else return (
        <SafeAreaView>
            <Header
                headerLeft={<HeaderBackButton onPress={navigation.goBack} tintColor={"white"}/>}
            >
                基金分析指标
            </Header>
            <MyStatusBar/>
            <ScrollView style={{
                flexDirection: 'column',
                marginBottom: 50}}>
                <Card containerStyle={{borderRadius: 10,borderWidth:0}}>
                    <View style={{flexDirection: 'column'}}>
                        <Text style={page.typeTitle}>{Average.typeName}</Text>
                        <Text style={page.typeContent}>{Average.typeDescription}</Text>
                    </View>
                </Card>
                <Card containerStyle={{borderRadius: 10, borderWidth:0}}>
                    <View style={{flexDirection: 'column'}}>
                        <Text style={page.subTitle}>{name}</Text>
                        <FundAnalysisCard style={page.container}
                                          profit={profit}
                                          risk={risk}
                                          cost={cost}
                                          title={false}
                                          name={name}
                                          code={code}
                        />
                    </View>
                </Card>
                <Card containerStyle={{borderRadius: 10, borderWidth:0}}>
                    <View style={{justifyContent: 'space-between', flexDirection: 'row'}}>
                        <View style={{flexDirection: 'row'}}>
                            <Text style={page.subTitle}>
                                收益能力
                            </Text>
                            <Card containerStyle={page.box}
                                  wrapperStyle={{padding: 0, margin: 0}}>
                                <Text style={page.yearText}>{card1}</Text>
                            </Card>
                        </View>
                        <Text style={page.date}>{getLastWorkDate()}</Text>
                    </View>
                    <Text style={page.content}>
                        统计盈利性指标，衡量基金获取收益的能力。该类基金建议主要参考盈利概率与击败基准比率。
                    </Text>

                    <Divider style={page.divider}/>
                    <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
                        <View style={{flex: 4}}>
                            <Text style={page.tableLeftHeader}>指标名称</Text>
                            <Text style={page.tableLeftContent}>击败基准比率</Text>
                            <View style={{paddingTop: 5}}>
                                <Icon
                                    name='infocirlceo'
                                    type='antdesign'
                                    color='#b8b8b8'
                                    size={15}
                                    containerStyle={{padding: 0, margin: 0, height: 25, width: 25}}
                                    onPress={toggleOverlay_1}
                                />
                            </View>
                        </View>
                        <View style={{flex: 3}}>
                            <Text style={page.tableHeader}>本基金</Text>
                            <Text style={page.tableContent}>{PROFIT}</Text>
                        </View>
                        <View style={{flex: 3}}>
                            <Text style={page.tableHeader}>指标均值</Text>
                            <Text style={page.tableContent}>{PROFIT_AVERAGE}</Text>
                        </View>
                        <View style={{flex: 4}}>
                            <View style={{flexDirection: 'row'}}>
                                <Text style={page.tableHeader}>同类指标排名</Text>
                                <View>
                                    <Icon
                                        name='infocirlceo'
                                        type='antdesign'
                                        color='#b8b8b8'
                                        size={15}
                                        containerStyle={{
                                            padding: 0,
                                            margin: 0,
                                            height: 20,
                                            width: 20,
                                            // marginLeft: -2,
                                            marginTop: 2
                                        }}
                                        onPress={toggleOverlay_4}
                                    />
                                </View>
                            </View>
                            <View style={{flexDirection: 'row'}}>
                                <Text style={page.tableContent1}>{PROFIT_RANK}</Text>
                                <Text style={page.tableGrey1}>/ {PROFIT_NFSC}</Text>
                            </View>
                        </View>
                    </View>
                    <ButtonGroup
                        onPress={(selectedIndex)=>{
                            // console.log("want to see selected", selectedIndex);
                            setMode1(selectedIndex);
                        }}
                        selectedIndex={mode1}
                        buttons={['近三个月','近一年']}
                        buttonContainerStyle={{borderWidth: 0, borderRadius: 20}}
                        buttonStyle={{borderWidth: 0, borderRadius: 20,width:75}}
                        innerBorderStyle={{width: 0}}
                        containerStyle={{
                            height: 40,
                            borderRadius: 20,
                            borderWidth: 0,
                            paddingTop:15,
                            paddingLeft:50,
                        }}
                        selectedTextStyle={{color: '#6991c3'}}
                        textStyle={{color: '#a4a4a4'}}
                        selectedButtonStyle={{
                            backgroundColor: '#e9f3fe'
                        }}
                    />
                </Card>

                <Card containerStyle={{borderRadius: 10, borderWidth:0}}>
                    <View style={{justifyContent: 'space-between', flexDirection: 'row'}}>
                        <View style={{flexDirection: 'row'}}>
                            <Text style={page.subTitle}>
                                抗风险波动
                            </Text>
                            <Card containerStyle={page.box}
                                  wrapperStyle={{padding: 0, margin: 0}}>
                                <Text style={page.yearText}>{card}</Text>
                            </Card>
                        </View>
                        <Text style={page.date}>{getLastWorkDate()}</Text>
                    </View>
                    <Text style={page.content}>
                        统计防守性指标，衡量基金的抗风险波动能力。该类基金建议主要参考最大回撤。
                    </Text>

                    <Divider style={page.divider}/>
                    <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
                        <View style={{flex: 4}}>
                            <Text style={page.tableLeftHeader}>指标名称</Text>
                            <Text style={page.tableLeftContent}>最大回撤</Text>
                            <View style={{paddingTop: 5}}>
                                <Icon
                                    name='infocirlceo'
                                    type='antdesign'
                                    color='#b8b8b8'
                                    size={15}
                                    containerStyle={{padding: 0, margin: 0, height: 25, width: 25}}
                                    onPress={toggleOverlay_3}
                                />
                            </View>
                        </View>
                        <View style={{flex: 3}}>
                            <Text style={page.tableHeader}>本基金</Text>
                            <View style={{flexDirection: 'column'}}>
                                <Text style={page.tableContent}>{MAXRETRA}</Text>
                                {/*<Icon name='thumbs-up' type='font-awesome-5' size={14} color='#e23b48' style={{paddingLeft:2}} solid={true}/>*/}
                            </View>
                        </View>
                        <View style={{flex: 3}}>
                            <Text style={page.tableHeader}>指标均值</Text>
                            <Text style={page.tableContent}>{MAXRETRA_AVERAGE}</Text>
                        </View>
                        <View style={{flex: 4}}>
                            <View style={{flexDirection: 'row'}}>
                                <Text style={page.tableHeader}>同类指标排名</Text>
                                <View>
                                    <Icon
                                        name='infocirlceo'
                                        type='antdesign'
                                        color='#b8b8b8'
                                        size={15}
                                        containerStyle={{
                                            padding: 0,
                                            margin: 0,
                                            height: 20,
                                            width: 20,
                                            // marginLeft: 3,
                                            marginTop: 2
                                        }}
                                        onPress={toggleOverlay_4}
                                    />
                                </View>
                            </View>
                            <View style={{flexDirection: 'row'}}>
                                <Text style={page.tableContent1}>{MAXRETRA_RANK}</Text>
                                <Text style={page.tableGrey1}>/ {MAXRETRA_NFSC}</Text>
                            </View>
                        </View>
                    </View>
                    <Divider style={page.divider}/>
                    <Text style={page.content}>以下为辅助指标</Text>
                    <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
                        <View style={{flex: 4}}>
                            <Text style={page.tableLeftContent}>波动率</Text>
                            <View style={{paddingTop: 5}}>
                                <Icon
                                    name='infocirlceo'
                                    type='antdesign'
                                    color='#b8b8b8'
                                    size={15}
                                    containerStyle={{padding: 0, margin: 0, height: 25, width: 25}}
                                    onPress={toggleOverlay_5}
                                />
                            </View>
                        </View>
                        <View style={{flex: 3}}>
                            <View style={{flexDirection: 'column'}}>
                                <Text style={page.tableContent}>{STDDEV}</Text>
                            </View>
                        </View>
                        <View style={{flex: 3}}>
                            <Text style={page.tableContent}>{STDDEV_AVERAGE}</Text>
                        </View>
                        <View style={{flex: 4}}>
                            <View style={{flexDirection: 'row'}}>
                                <Text style={page.tableContent}>{STDDEV_RANK}</Text>
                                <Text style={page.tableGrey}>/ {STDDEV_NFSC}</Text>
                            </View>
                        </View>
                    </View>
                    <ButtonGroup
                        onPress={(selectedIndex)=>{
                            setMode(selectedIndex);
                        }}
                        selectedIndex={mode}
                        buttons={['近一年','近三年','近五年']}
                        buttonContainerStyle={{borderWidth: 0, borderRadius: 20}}
                        buttonStyle={{borderWidth: 0, borderRadius: 20,width:75}}
                        innerBorderStyle={{width: 0}}
                        containerStyle={{
                            height: 40,
                            borderRadius: 20,
                            borderWidth: 0,
                            paddingTop:15,
                            paddingLeft:25,
                        }}
                        selectedTextStyle={{color: '#6991c3'}}
                        textStyle={{color: '#a4a4a4'}}
                        selectedButtonStyle={{
                            backgroundColor: '#e9f3fe'
                        }}
                    />
                </Card>

                <Card containerStyle={{borderRadius: 10, borderWidth:0}}>
                    <View style={{justifyContent: 'space-between', flexDirection: 'row'}}>
                        <View style={{flexDirection: 'row'}}>
                            <Text style={page.subTitle}>
                                投资性价比
                            </Text>
                            <Card containerStyle={page.box}
                                  wrapperStyle={{padding: 0, margin: 0}}>
                                <Text style={page.yearText}>{card2}</Text>
                            </Card>
                        </View>
                        <Text style={page.date}>{getLastWorkDate()}</Text>
                    </View>
                    <Text style={page.content}>
                        综合统计盈利性及防守性指标，衡量基金承担风险获取利益的性价比，最通用的指标为夏普比率。
                    </Text>

                    <Divider style={page.divider}/>
                    <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
                        <View style={{flex: 4}}>
                            <Text style={page.tableLeftHeader}>指标名称</Text>
                            <Text style={page.tableLeftContent}>夏普比率</Text>
                            <View style={{paddingTop: 5}}>
                                <Icon
                                    name='infocirlceo'
                                    type='antdesign'
                                    color='#b8b8b8'
                                    size={15}
                                    containerStyle={{padding: 0, margin: 0, height: 25, width: 25}}
                                    onPress={toggleOverlay_2}
                                />
                            </View>
                        </View>
                        <View style={{flex: 3}}>
                            <Text style={page.tableHeader}>本基金</Text>
                            <View style={{flexDirection: 'column'}}>
                                <Text style={page.tableContent}>{SHARP}</Text>
                            </View>
                        </View>
                        <View style={{flex: 3}}>
                            <Text style={page.tableHeader}>指标均值</Text>
                            <Text style={page.tableContent}>{SHARP_AVERAGE}</Text>
                        </View>
                        <View style={{flex: 4}}>
                            <View style={{flexDirection: 'row'}}>
                                <Text style={page.tableHeader}>同类指标排名</Text>
                                <View>
                                    <Icon
                                        name='infocirlceo'
                                        type='antdesign'
                                        color='#b8b8b8'
                                        size={15}
                                        containerStyle={{
                                            padding: 0,
                                            margin: 0,
                                            height: 20,
                                            width: 20,
                                            // marginLeft: 3,
                                            marginTop: 2
                                        }}
                                        onPress={toggleOverlay_4}
                                    />
                                </View>
                            </View>
                            <View style={{flexDirection: 'row'}}>
                                <Text style={page.tableContent1}>{SHARP_RANK}</Text>
                                <Text style={page.tableGrey1}>/ {SHARP_NFSC} </Text>
                            </View>
                        </View>
                    </View>
                    <ButtonGroup
                        onPress={(selectedIndex)=>{
                            setMode2(selectedIndex);
                        }}
                        selectedIndex={mode2}
                        buttons={['近一年','近三年','近五年']}
                        buttonContainerStyle={{borderWidth: 0, borderRadius: 20}}
                        buttonStyle={{borderWidth: 0, borderRadius: 20,width:75}}
                        innerBorderStyle={{width: 0}}
                        containerStyle={{
                            height: 40,
                            borderRadius: 20,
                            borderWidth: 0,
                            paddingTop:15,
                            paddingLeft:25,
                        }}
                        selectedTextStyle={{color: '#6991c3'}}
                        textStyle={{color: '#a4a4a4'}}
                        selectedButtonStyle={{
                            backgroundColor: '#e9f3fe'
                        }}
                    />
                </Card>

                <View style={{padding: 30, minHeight: 100}}>
                    <Text
                        style={page.bottomContent}>因基金净值更新时间不及时，可能导致本页面各项基金指标和排名结果在日间出现细微偏差，前述指标和排名均仅供参考，不构成投资建议。</Text>
                    <Text style={page.bottomContent}>基金过往业绩不预示未来表现。市场有风险，投资需谨慎。</Text>
                </View>

                <Overlay
                    overlayStyle={page.overlay}
                    isVisible={visible_1}
                    onBackdropPress={toggleOverlay_1}
                >
                    <Badge status="primary"/>
                    <View style={{width: '95%', paddingTop: 10, paddingLeft: 15}}>
                        <Text
                            style={{
                                fontSize: 30,
                                fontWeight: 'bold',
                                paddingBottom: 5
                            }}
                            numberOfLines={1}>
                            击败基准比率
                        </Text>
                        <Text style={page.overlaySubTitle}>数值越大越好</Text>
                        <Text
                            style={page.overlayContent}>统计所选区间内，基金收益率打败沪深300的概率。</Text>
                        <Text style={page.overlayContent}>数值越大越好，表示基金收益能力越强。</Text>
                        <Image
                            source={require("../../resources/image/profit.jpeg")}
                            resizeMode="contain"
                            style={{width:'100%'}}
                        />
                    </View>
                    {/*<MathView*/}
                    {/*    math={'击败基准比率（日） = {收益率击败沪深300的天数 \\over {总交易日天数}}'}*/}
                    {/*    style={page.overlayMathView}*/}
                    {/*/>*/}
                </Overlay>

                <Overlay
                    overlayStyle={page.overlay}
                    isVisible={visible_2}
                    onBackdropPress={toggleOverlay_2}
                >
                    <Badge status="primary"/>
                    <View style={{width: '95%', paddingTop: 10, paddingLeft: 15}}>
                        <Text style={page.overlayTitle}>夏普比率</Text>
                        <Text style={page.overlaySubTitle}>年化值，数值为正时越大越好</Text>
                        <Text
                            style={page.overlayContent}>夏普比率衡量基金每承受以单位风险，可获得的超额收益。</Text>
                        <Text style={page.overlayContent}>数值为正时越大越好，表示基金性价比越高。</Text>
                        <Text
                            style={page.overlayContent}>夏普比率为负时参考价值有限，表示基金超额收益为负，请结合波动率等指标综合考虑。</Text>
                    </View>
                    {/*<MathView*/}
                    {/*    math={'年化夏普比率 = {(基金复权净值平均收益率 - 平均无风险收益率) \\times \\sqrt{250}\\over {基金复权净值波动率}}'}*/}
                    {/*    style={page.overlayMathView}*/}
                    {/*/>*/}
                    <Image
                        source={require("../../resources/image/sharp.jpeg")}
                        resizeMode="contain"
                        style={{width:'100%'}}
                    />
                </Overlay>

                <Overlay
                    overlayStyle={page.overlay}
                    isVisible={visible_3}
                    onBackdropPress={toggleOverlay_3}
                >
                    <Badge status="primary"/>
                    <View style={page.overlayView}>
                        <Text style={page.overlayTitle}>最大回撤</Text>
                        <Text style={page.overlaySubTitle}>数值越小越好</Text>
                        <Text
                            style={page.overlayContent}>最大回撤衡量所选区间内，买入该基金可能出现的最糟糕的情况。</Text>
                        <Text style={page.overlayContent}>数值越小越好，表示基金抗风险能力越强。</Text>
                    </View>
                    {/*<MathView*/}
                    {/*    math={'最大回撤 = {max(第i天的基金复权净值 - 第i天后面某天基金复权净值) \\over {第i天的基金复权净值}}'}*/}
                    {/*    style={page.overlayMathView}*/}
                    {/*/>*/}
                    <Image
                        source={require("../../resources/image/maxretra.jpeg")}
                        resizeMode="contain"
                        style={{width:'100%'}}
                    />
                </Overlay>

                <Overlay
                    overlayStyle={{borderRadius: 5, width: '70%'}}
                    isVisible={visible_4}
                    onBackdropPress={toggleOverlay_4}
                >
                    <View style={{padding: 10}}>
                        <Text style={{lineHeight: 20}}>部分基金可能无法计算出某项指标数据，导致参与排名的基金总数不同。请投资者注意，指标排名仅供参考，不构成投资建议，<Text
                            style={{lineHeight: 20, fontWeight: 'bold'}}>交我赚不承担一切责任。</Text></Text>
                    </View>
                </Overlay>

                <Overlay
                    overlayStyle={page.overlay}
                    isVisible={visible_5}
                    onBackdropPress={toggleOverlay_5}
                >
                    <Badge status="primary"/>
                    <View style={page.overlayView}>
                        <Text style={page.overlayTitle}>波动率</Text>
                        <Text style={page.overlaySubTitle}>年化值，数值越小越好</Text>
                        <Text
                            style={page.overlayContent}>波动率衡量基金收益的波动情况。</Text>
                        <Text style={page.overlayContent}>数值越小越好，代表基金抗风险波动能力越强。</Text>
                    </View>
                    {/*<MathView*/}
                    {/*    math={'年化波动率 = {区间日收益标准差 \\times \\sqrt {250}}'}*/}
                    {/*    style={page.overlayMathView}*/}
                    {/*/>*/}
                    <Image
                        source={require("../../resources/image/std_dev.jpeg")}
                        resizeMode="contain"
                        style={{width:'100%'}}
                    />
                </Overlay>

                <Overlay
                    overlayStyle={page.overlay}
                    isVisible={visible_6}
                    onBackdropPress={toggleOverlay_6}
                >
                    <Badge status="primary"/>
                    <View style={page.overlayView}>
                        <Text style={page.overlayTitle}>收益回撤比</Text>
                        <Text style={page.overlaySubTitle}>年化值，数值为正时越大越好</Text>
                        <Text style={page.overlayContent}>投资回撤比衡量基金每承受一单位回车，可获得的超额收益。</Text>
                        <Text style={page.overlayContent}>数值为正时越大越好，表示基金性价比越高。</Text>
                        <Text style={page.overlayContent}>收益回撤比为负时参考价值有限，表示基金超额收益为负，请结合最大回撤等指标综合考虑。</Text>
                    </View>
                    {/*<MathView*/}
                    {/*    math={'年化投资回撤比 = {{(基金复权净值平均收益率-平均无风险收益率) \\times \\sqrt {250}}\\over 最大回撤}'}*/}
                    {/*    style={page.overlayMathView}*/}
                    {/*/>*/}
                </Overlay>
            </ScrollView>
        </SafeAreaView>

    )
}

const page = StyleSheet.create({
    text: {},
    button: {
        textAlign: 'center',
    },
    container: {
        padding: 0,
        margin: 0,
    },
    divider: {
        marginTop: 20,
        marginBottom: 15,
    },
    title: {
        fontSize: 17,
        fontWeight: '600',
    },
    date: {
        textAlign: 'right',
        fontWeight: 'bold',
        color: '#dcdcdc',
        padding: 0,
        // alignItems: 'flex-end',
    },
    subTitle: {
        color: '#565656',
        textAlign: 'left',
        fontWeight: 'bold',
        fontSize: 16,
        paddingBottom: 5,
    },
    overlay: {
        // minHeight: '40%',
        width: '80%',
        borderStyle: 'solid',
        borderWidth: 10,
        borderColor: '#277bfa',
    },
    overlayView: {
        width: '95%',
        paddingTop: 10,
        paddingLeft: 15,
    },
    overlayTitle: {
        fontSize: 30,
        fontWeight: 'bold',
        paddingBottom: 5,
    },
    overlaySubTitle: {
        color: '#b8b8b8',
        paddingBottom: 10,
    },
    overlayContent: {
        textAlign: 'justify',
        lineHeight: 25,
    },
    overlayMathView: {
        paddingTop: 20,
    },
    yearText: {
        color: '#a1a1a1',
        fontSize: 10,
        fontWeight: '600',
    },
    box: {
        backgroundColor: '#f6f6f6',
        borderRadius: 2,
        borderWidth: 0,
        margin: 0,
        marginLeft: 5,
        marginTop: 3,
        padding: 0,
        paddingLeft: 5,
        paddingRight: 5,
        paddingTop: 1,
        paddingBottom: 1,
        height: 17,
    },
    content: {
        lineHeight: 20,
        color: '#c7c3c8',
        fontSize: 13,
    },
    tableLeftHeader: {
        lineHeight: 20,
        color: '#a5a5a5',
        fontSize: 13,
    },
    tableHeader: {
        lineHeight: 20,
        color: '#a5a5a5',
        fontSize: 13,
        textAlign: 'center',
    },
    tableContent: {
        paddingTop: 5,
        fontWeight: 'bold',
        textAlign: 'center',
    },
    tableContent1: {
        paddingTop: 3,
        fontWeight: 'bold',
        textAlign: 'center',
    },
    tableLeftContent: {
        paddingTop: 5,
        fontWeight: 'bold',
        // textAlign: 'left',
    },
    tableGrey: {
        textAlign: 'right',
        fontWeight: 'bold',
        color: '#dcdcdc',
        paddingLeft: 5,
        paddingTop: 5,
    },
    tableGrey1: {
        textAlign: 'right',
        fontWeight: 'bold',
        color: '#dcdcdc',
        paddingLeft: 5,
        paddingTop: 3,
    },
    tableRedContent: {
        paddingTop: 5,
        fontWeight: 'bold',
        textAlign: 'center',
        color: '#eb394a',
    },
    bottomContent: {
        lineHeight: 20,
        color: '#c7c3c8',
        fontSize: 13,
    },
    typeTitle: {
        color: '#565656',
        textAlign: 'left',
        fontWeight: 'bold',
        fontSize: 16,
        paddingBottom:5,
    },
    typeContent:{
        lineHeight:20,
        textAlign:'justify',
    },
})
