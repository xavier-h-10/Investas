import React from 'react'
import {StyleSheet, View} from 'react-native'
import {Text, Caption} from 'react-native-paper'
import TabCard from "./TabCard";
import message from "react-native-message/index";
import PerformanceCard from "./PerformanceCard";
import NAVCard from "./NAVCard";

export default function FundHistory(props) {
    const {historyPerformance, fundRateRank, fundRateTotalCount, code, type} = props
    let content = [
        {
            header: '历史业绩',
            body: <PerformanceCard historyPerformance={historyPerformance} fundRateRank={fundRateRank}
                                   fundRateTotalCount={fundRateTotalCount}/>
        },
        {
            header: type === 6 ? '历史七日年化' : '历史净值',
            body: <NAVCard code={code} type={type}/>
        },
    ]
    if (type === 6) {
        content = [
            {
                header: type === 6 ? '历史七日年化' : '历史净值',
                body: <NAVCard code={code} type={type}/>
            },
        ]
    }
    if (historyPerformance !== undefined && fundRateRank !== undefined && fundRateTotalCount !== undefined
        && code !== undefined && type !== undefined) {
        return (
            <View>
                <TabCard
                    content={content}
                    onChange={(index) => {
                    }}
                />
            </View>
        )
    } else {
        return null
    }
}

const styles = StyleSheet.create({
    caption: {
        fontSize: 14,
    },
})