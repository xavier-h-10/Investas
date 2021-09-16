import {Pressable, ScrollView, Text, View} from "react-native";
import React, {useState} from "react";
import {getFundNAVPage, getFundView} from "../service/FundService";
import Loading from "../components/Loading";
import {Caption} from "react-native-paper";
import {numberColor, numberFormat} from "../helpers/numberColor";
import SimpleLineIcons from "react-native-vector-icons/SimpleLineIcons";
import {useNavigation} from "@react-navigation/native";

const renderList = (data, code, type) => {
    const navigation = useNavigation()
    let list = []
    data.forEach((item, index) => {
        if (index === data.length - 1) return
        if (type !== 6) {
            data[index].change = Math.floor((data[index].nav / data[index + 1].nav - 1) * 10000) / 100
        }
        if (index < 5) {
            list.push(item)
        }
    })
    list.reverse()
    if (type === 6) {
        return (
            <View>
                <View style={{flexDirection: 'row', alignItems: 'center'}}>
                    <Caption style={{width: '30%', textAlign: 'left', fontSize: 14}}>日期</Caption>
                    <Caption style={{width: '35%', textAlign: 'center', fontSize: 14}}>万份收益</Caption>
                    <Caption style={{width: '35%', textAlign: 'right', fontSize: 14}}>七日年化收益率</Caption>
                </View>
                <View style={{flexDirection: 'column-reverse'}}>
                    {
                        list.map((item, index) => {
                            return (
                                <View style={{flexDirection: 'row', alignItems: 'center', marginTop: 15}} key={index}>
                                    <Text style={{width: '30%', textAlign: 'left', fontWeight: 'bold'}}>
                                        {item.updateDate}
                                    </Text>
                                    <Text style={{width: '35%', textAlign: 'center', fontWeight: 'bold'}}>
                                        {item.nav.toFixed(4)}
                                    </Text>
                                    <Text
                                        style={{
                                            width: '35%', textAlign: 'right', color: numberColor(item.accumulateNAV),
                                            fontWeight: 'bold'
                                        }}
                                    >
                                        {numberFormat(item.accumulateNAV, 4) + '%'}
                                    </Text>
                                </View>
                            )
                        })
                    }
                </View>

                <Pressable
                    onPress={() => {
                        navigation.push('FundNAV', {code: code, type: type})
                    }}
                >
                    <View style={{alignSelf: 'center', marginTop: 10, flexDirection: 'row', alignItems: 'center'}}>
                        <Text style={{color: '#006FFF', marginRight: 5}}>
                            更多数据
                        </Text>
                        <SimpleLineIcons name='arrow-right' size={10} color={'#00AFFF'}/>
                    </View>
                </Pressable>
            </View>
        )
    } else {
        return (
            <View>
                <View style={{flexDirection: 'row', alignItems: 'center'}}>
                    <Caption style={{width: '30%', textAlign: 'left', fontSize: 14}}>日期</Caption>
                    <Caption style={{width: '23%', textAlign: 'center', fontSize: 14}}>单位净值</Caption>
                    <Caption style={{width: '23%', textAlign: 'center', fontSize: 14}}>累计净值</Caption>
                    <Caption style={{width: '24%', textAlign: 'right', fontSize: 14}}>日涨幅</Caption>
                </View>
                <View style={{flexDirection: 'column-reverse'}}>
                    {
                        list.map((item, index) => {
                            return (
                                <View style={{flexDirection: 'row', alignItems: 'center', marginTop: 15}} key={index}>
                                    <Text style={{width: '30%', textAlign: 'left', fontWeight: 'bold'}}>
                                        {item.updateDate}
                                    </Text>
                                    <Text style={{width: '23%', textAlign: 'center', fontWeight: 'bold'}}>
                                        {item.nav.toFixed(4)}
                                    </Text>
                                    <Text style={{width: '23%', textAlign: 'center', fontWeight: 'bold'}}>
                                        {item.accumulateNAV.toFixed(4)}
                                    </Text>
                                    <Text
                                        style={{
                                            width: '24%', textAlign: 'right', color: numberColor(item.change),
                                            fontWeight: 'bold'
                                        }}
                                    >
                                        {numberFormat(item.change) + '%'}
                                    </Text>
                                </View>
                            )
                        })
                    }
                </View>

                <Pressable
                    onPress={() => {
                        navigation.push('FundNAV', {code: code, type: type})
                    }}
                >
                    <View style={{alignSelf: 'center', marginTop: 10, flexDirection: 'row', alignItems: 'center'}}>
                        <Text style={{color: '#006FFF', marginRight: 5}}>
                            更多数据
                        </Text>
                        <SimpleLineIcons name='arrow-right' size={10} color={'#00AFFF'}/>
                    </View>
                </Pressable>
            </View>
        )
    }
}

export default function NAVCard(props) {
    const {code, type} = props
    const [data, setData] = useState(null)
    const [didMount, setDidMount] = useState(false)

    if (didMount === false) {
        getFundNAVPage(code, 0, (data) => {
            setData(data.data.dailylist)
        });
        setDidMount(true)
    }
    return (
        <View>
            { data === null ? <Loading/> : renderList(data, code, type) }
        </View>
    )
}
