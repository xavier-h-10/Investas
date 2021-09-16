import {Card, Icon, Text} from "react-native-elements";
import React, {useState} from "react";
import {getFundPrediction} from "../service/FundService";
import {View} from "react-native";
import {Caption, Title} from "react-native-paper";
import SimpleLineIcons from "react-native-vector-icons/SimpleLineIcons";
import {numberColor, numberFormat} from "../helpers/numberColor";

export default function FundPredictionCard({code}) {

    const [didMount, setDidMount] = useState(false)
    const [data, setData] = useState(null)

    if (didMount === false) {
        getFundPrediction(code, (res) => {
            setData(res.data)
            console.log('prediction', res.data)
        })
        setDidMount(true)
    }

    if (data === null) {
        return null
    } else {
        return (
            <Card containerStyle={{borderRadius: 10}}>
                <View style={{flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center'}}>
                    <Title style={{fontWeight: 'bold', fontSize: 16}}>净值预测</Title>
                </View>
                <View style={{flexDirection: 'row', justifyContent: 'space-around', alignItems: 'center'}}>
                    <View style={{alignItems: 'center'}}>
                        <Caption>后1天</Caption>
                        <Text style={{fontWeight: 'bold'}}>
                            {data.futureOneDayNAV}
                        </Text>
                        <Text style={{fontWeight: 'bold', color: numberColor(data.futureOneDayChange)}}>
                            {numberFormat(data.futureOneDayChange) + '%'}
                        </Text>
                    </View>
                    <View style={{alignItems: 'center'}}>
                        <Caption>后2天</Caption>
                        <Text style={{fontWeight: 'bold'}}>
                            {data.futureTwoDaysNAV}
                        </Text>
                        <Text style={{fontWeight: 'bold', color: numberColor(data.futureTwoDaysChange)}}>
                            {numberFormat(data.futureTwoDaysChange) + '%'}
                        </Text>
                    </View>
                    <View style={{alignItems: 'center'}}>
                        <Caption>后3天</Caption>
                        <Text style={{fontWeight: 'bold'}}>
                            {data.futureThreeDaysNAV}
                        </Text>
                        <Text style={{fontWeight: 'bold', color: numberColor(data.futureThreeDaysChange)}}>
                            {numberFormat(data.futureThreeDaysChange) + '%'}
                        </Text>
                    </View>
                </View>
                <View style={{flexDirection:'row',margin:'auto',paddingTop:30}}>
                    <Icon
                        name='ios-information-circle-outline'
                        type='ionicon'
                        size={15}
                        color={'#5383ba'}
                        containerStyle={{}}
                    />
                    <Text style={{color:'#9c9c9c',fontSize:11,textAlign:'center'}}>
                        净值预测数据仅供参考，实际以日后基金公司披露净值为准。
                    </Text>
                </View>
            </Card>
        )
    }
}