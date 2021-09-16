import React, {Component, useState} from "react";
import {
    Rating,
    Card,
    ListItem,
    Button,
    Icon,
    Overlay
} from 'react-native-elements';
import {View, StyleSheet, Text, Pressable, SafeAreaView} from "react-native";
import {numberColor, numberFormat} from "../helpers/numberColor";
import SimpleLineIcons from "react-native-vector-icons/SimpleLineIcons";
import {useNavigation} from "@react-navigation/native";

//参数说明 直接传时间区间，涨跌幅，排名

const PerformanceCard = (props) => {
    const [visible, setVisible] = useState(false);
    const index = ["lastOneWeek", "lastOneMonth", "lastThreeMonths", "lastSixMonths", "lastOneYear"]
    const {historyPerformance, fundRateRank, fundRateTotalCount} = props
    // if (historyPerformance === null) return null
    const navigation = useNavigation()

    const toggleOverlay = () => {
        setVisible(!visible);
    }

    const dataWrapper = (data) => {
        if (data == null)
            return "--";
        else
            return data;
    }

    return (
        <SafeAreaView>
            <View style={{flexDirection: 'row', justifyContent: 'space-between'}}>
                <View style={{width: '30%', flexDirection: 'column'}}>
                    <Text style={page.tableLeftHeader}>时间区间</Text>
                    <Text style={page.tableLeftContent}>近1周</Text>
                    <Text style={page.tableLeftContent}>近1月</Text>
                    <Text style={page.tableLeftContent}>近3月</Text>
                    <Text style={page.tableLeftContent}>近6月</Text>
                    <Text style={page.tableLeftContent}>近1年</Text>
                </View>
                <View style={{width: '30%', flexDirection: 'column'}}>
                    <Text style={page.tableHeader}>涨跌幅</Text>
                    {
                        index.map((str, idx) => {
                            if (historyPerformance == null || historyPerformance[str] === undefined ||
                                historyPerformance[str] === null || historyPerformance[str] === -1.00) {
                                fundRateRank[index[idx]] = fundRateTotalCount[index[idx]] = null;
                                return (
                                    <Text style={page.tableNullContent} key={idx}>
                                        --/--
                                    </Text>
                                )
                            } else {
                                return (
                                    <Text
                                        style={[{color: numberColor(historyPerformance[str])}, page.tableContent]}
                                        key={idx}
                                    >
                                        {numberFormat(historyPerformance[str]) + '%'}
                                    </Text>
                                )
                            }
                        })
                    }
                </View>
                <View style={{width: '40%', flexDirection: 'column', alignItems: 'flex-end'}}>
                    <View style={{flexDirection: 'row'}}>
                        <Text style={{lineHeight: 20, color: '#a5a5a5', fontSize: 13, marginLeft: 10}}>业绩排名</Text>
                        <Icon
                            name='infocirlceo'
                            type='antdesign'
                            color='#b8b8b8'
                            size={15}
                            // containerStyle={{padding: 0, margin: 0, height: 15, width: 15}}
                            onPress={toggleOverlay}
                            containerStyle={{marginTop:2.5,marginLeft:2}}
                        />
                    </View>
                    <Text style={page.tableContent}>
                        {dataWrapper(fundRateRank.lastOneWeek)}&nbsp;&nbsp;&nbsp;
                        <Text style={page.tableGrey}>
                            /{dataWrapper(fundRateTotalCount.lastOneWeek)}
                        </Text>
                    </Text>
                    <Text style={page.tableContent}>
                        {dataWrapper(fundRateRank.lastOneMonth)}&nbsp;&nbsp;&nbsp;
                        <Text style={page.tableGrey}>
                            /{dataWrapper(fundRateTotalCount.lastOneMonth)}
                        </Text>
                    </Text>
                    <Text style={page.tableContent}>
                        {dataWrapper(fundRateRank.lastThreeMonths)}&nbsp;&nbsp;&nbsp;
                        <Text style={page.tableGrey}>
                            /{dataWrapper(fundRateTotalCount.lastThreeMonths)}
                        </Text>
                    </Text>
                    <Text style={page.tableContent}>
                        {dataWrapper(fundRateRank.lastSixMonths)}&nbsp;&nbsp;&nbsp;
                        <Text style={page.tableGrey}>
                            /{dataWrapper(fundRateTotalCount.lastSixMonths)}
                        </Text>
                    </Text>
                    <Text style={page.tableContent}>
                        {dataWrapper(fundRateRank.lastOneYear)}&nbsp;&nbsp;&nbsp;
                        <Text style={page.tableGrey}>
                            /{dataWrapper(fundRateTotalCount.lastOneYear)}
                        </Text>
                    </Text>
                </View>
                <Overlay
                    overlayStyle={{borderRadius: 5, width: '70%'}}
                    isVisible={visible}
                    onBackdropPress={toggleOverlay}
                >
                    <View style={{padding: 10}}>
                        <Text style={{lineHeight: 25, fontSize: 15}}>平台采用最新的业绩排名，在原一级分类基础上按照资产维度向下细分，用更加科学的方法在<Text
                            style={{fontWeight: 'bold', fontSize: 15}}>同一维度内</Text>进行对比。例如：混合型-偏股；混合型-偏债。</Text>
                    </View>
                </Overlay>
            </View>

            <Pressable
                onPress={() => {
                    navigation.push('FundPerformance', {
                        historyPerformance: historyPerformance,
                        fundRateRank: fundRateRank,
                        fundRateTotalCount: fundRateTotalCount
                    })
                }}
            >
                <View style={{alignSelf: 'center', marginTop: 10, flexDirection: 'row', alignItems: 'center'}}>
                    <Text style={{color: '#006FFF', marginRight: 5}}>
                        更多数据
                    </Text>
                    <SimpleLineIcons name='arrow-right' size={10} color={'#00AFFF'}/>
                </View>
            </Pressable>
        </SafeAreaView>
    )
}
export default PerformanceCard;

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
        minHeight: '40%',
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
        lineHeight: 23,
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
        paddingTop: 18,
        fontWeight: 'bold',
        textAlign: 'center',
    },
    tableLeftContent: {
        paddingTop: 18,
        fontWeight: 'bold',
        // textAlign: 'left',
    },

    tableGrey: {
        textAlign: 'right',
        fontWeight: 'bold',
        color: '#dcdcdc',
        paddingLeft: 5,
        marginLeft: 5,
        paddingTop: 18,
    },
    tableNullContent: {
        paddingTop: 18,
        fontWeight: 'bold',
        textAlign: 'center',
        color: 'grey',
    },
    bottomContent: {
        lineHeight: 20,
        color: '#c7c3c8',
        fontSize: 13,
    },
})
