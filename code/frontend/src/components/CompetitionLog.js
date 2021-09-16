import {Card, Icon, Text} from "react-native-elements";
import React, {useState} from "react";
import {getFundPrediction} from "../service/FundService";
import {StyleSheet, View} from "react-native";
import {Caption, Title} from "react-native-paper";
import SimpleLineIcons from "react-native-vector-icons/SimpleLineIcons";
import {numberColor, numberFormat} from "../helpers/numberColor";
import {getLogs} from "../service/FundCompeService";

export default function CompetitionLog({code, competitionId}) {

    const [didMount, setDidMount] = useState(false)
    const [data, setData] = useState(null)

    if (didMount === false) {
        getLogs(competitionId, code, (data) => {
            if (data.data && data.data.list) {
                setData(data.data.list)
            }
        })
    }

    if (data === null) {
        return null
    } else if (data.length === 0) {
        return (
            <Card containerStyle={{borderRadius: 10}}>
                <Text style={[styles.caption, {fontSize: 18}]}>投资记录</Text>
                <Text style={[styles.caption, {fontSize: 20, marginTop: 20, textAlign: 'center'}]}>
                    暂时还没有交易记录哦~
                </Text>
            </Card>
        )
    } else {
        return (
            <Card containerStyle={{borderRadius: 10}}>
                <Text style={[styles.caption, {fontSize: 18}]}>投资记录</Text>
                <View style={{flexDirection: 'row', marginTop: 10}}>
                    <Text
                        style={{
                            width: '50%',
                            fontSize: 16,
                            textAlign: 'center'
                        }}
                    >
                        日期
                    </Text>
                    <Text
                        style={{
                            width: '50%',
                            fontSize: 16,
                            textAlign: 'center'
                        }}
                    >
                        金额
                    </Text>
                </View>
                {
                    data.map((item, index) => {
                        return (
                            <View style={{flexDirection: 'row', marginTop: 5}} key={index}>
                                <Text
                                    style={{
                                        width: '50%',
                                        fontSize: 16,
                                        textAlign: 'center'
                                    }}>
                                    {item.date}
                                </Text>
                                <Text
                                    style={{
                                        marginRight: '15%',
                                        width: '35%',
                                        color: numberColor(item.amount),
                                        fontSize: 16,
                                        textAlign: 'right'
                                    }}
                                >
                                    {numberFormat(item.amount) + '元'}
                                </Text>
                            </View>
                        )
                    })
                }
            </Card>
        )
    }
}


const styles = StyleSheet.create({
    caption: {
        color: 'grey',
    }
})
