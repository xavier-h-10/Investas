import React, {Component, useState} from "react";
import {Divider, Icon, Overlay} from 'react-native-elements';
import {View, StyleSheet, Text} from "react-native";
import {numberColor, numberFormat} from "../helpers/numberColor";

//点击查看更多后，看到的历史业绩
//参数说明 直接传时间区间，涨跌幅，排名

const PerformanceDetailCard = (props) => {
    const index = ["lastOneWeek", "lastOneMonth", "lastThreeMonths", "lastSixMonths", "lastOneYear", "lastTwoYears",
        "lastThreeYears", "lastFiveYears", "fromThisYear", "fromBeginning"]
    let dataList = [
        [5.94, 118, 689],
        [-3.18, 477, 680],
        [-2.19, 458, 641],
        [-7.98, 445, 596],
        [+23.33, 166, 476],
        [111.97, 161, 379],
        [141.86, 134, 307],
        [183.97, 43, 175],
        [6.24, 311, 563],
        [167.50, null, null],
    ];
    const {historyPerformance, fundRateRank, fundRateTotalCount} = props

    index.forEach((str, i) => {
        dataList[i][0] = historyPerformance[str];
        dataList[i][1] = fundRateRank[str];
        dataList[i][2] = fundRateTotalCount[str];
    })

    dataList[9][1] = '--';
    dataList[9][2] = '--';


    const name = ['近一周', '近一月', '近三月', '近六月', '近一年', '近两年', '近三年', '近五年', '今年来', '成立以来'];

    const RenderColor = (props) => {
        if (props == '--')
            return <Text style={{flex: 3, fontSize: 13, textAlign: 'center'}}>{props}</Text>
        else if (props < 0)
            return <Text
                style={{flex: 3, fontWeight: 'bold', textAlign: 'center', color: '#36ab8c'}}>{props.toFixed(2)}%</Text>
        else if (props > 0)
            return <Text
                style={{flex: 3, fontWeight: 'bold', textAlign: 'center', color: '#eb394a'}}>+{props.toFixed(2)}%</Text>
    }

    const RenderData = (props) => {
        let res = [];
        const {data} = props;
        res.push(
            <View key={-1}>
                <Divider/>
                <View style={{flexDirection: 'row', backgroundColor: '#fefefe'}}>
                    <View style={{flexDirection: 'row', width: '100%', height: 40, padding: 10}}>
                        <Text style={{flex: 4, color: '#a5a5a5', fontSize: 13}}>时间区间</Text>
                        <Text style={{flex: 3, textAlign: 'center', color: '#a5a5a5', fontSize: 13}}>涨跌幅</Text>
                        <Text style={{
                            flex: 4,
                            textAlign: 'right',
                            paddingRight: 5,
                            color: '#a5a5a5',
                            fontSize: 13
                        }}>排名</Text>
                    </View>
                </View>
            </View>
        );
        let len = Math.min(data.length, 10);
        for (let i = 0; i < len; i++) {
            for (let j = 0; j < 3; j++) {
                if (data[i][j] === undefined || data[i][j] == null || data[i][j] === -1.00) {
                    data[i][0] = data[i][1] = data[i][2] = '--';
                }
            }
            res.push(
                <View style={{backgroundColor: '#fefefe'}} key={i}>
                    <Divider/>
                    <View style={{flexDirection: 'row', width: '100%', height: 50, padding: 10}}>
                        <Text style={{flex: 4, fontWeight: 'bold', fontSize: 13, padding: 'auto'}}>{name[i]}</Text>
                        {RenderColor(data[i][0])}
                        <View style={{flex: 4, paddingRight: 5, alignItems: 'flex-end'}}>
                            <Text style={{fontSize: 13, fontWeight: 'bold', textAlign: 'center'}}>
                                {data[i][1]}&nbsp;&nbsp;&nbsp;
                                <Text style={{color: '#dcdcdc', paddingLeft: 5}}>
                                    /{data[i][2]}
                                </Text>
                            </Text>
                        </View>
                    </View>
                </View>
            )
        }
        return res;
    }

    return (
        <View style={{flexDirection: 'column'}}>
            <RenderData data={dataList}/>
        </View>
    )
}

export default PerformanceDetailCard;

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
    tableRedContent: {
        paddingTop: 18,
        fontWeight: 'bold',
        textAlign: 'center',
        color: '#eb394a',
    },

    tableGreenContent: {
        paddingTop: 18,
        fontWeight: 'bold',
        textAlign: 'center',
        color: '#36ab8c',
    },
    bottomContent: {
        lineHeight: 20,
        color: '#c7c3c8',
        fontSize: 13,
    },
})
