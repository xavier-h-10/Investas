import {FlatList, SafeAreaView, ScrollView, Text, View} from "react-native";
import FundArchive from "../components/FundArchive/FundArchive";
import React, {useState} from "react";
import Header from "../components/Header";
import {HeaderBackButton} from "@react-navigation/stack";
import FundCard from "../components/FundCard";
import PerformanceDetailCard from "../components/PerformanceDetailCard";
import {getFundNAVPage, getFundView, getSimpFundInfo} from "../service/FundService";
import message from "react-native-message/index";
import {Caption} from "react-native-paper";
import {numberColor, numberFormat} from "../helpers/numberColor";
import Loading from "../components/Loading";
import {Card} from "react-native-elements";
import MyStatusBar from "../components/MyStatusBar";

export default function FundNAVView({route, navigation}) {
    const {code, type} = route.params
    const [data, setData] = useState(null);
    const [page, setPage] = useState(0);
    const [didMount, setDidMount] = useState(false)

    if (didMount === false) {
        getFundNAVPage(code, page, (data) => {
            setData(data.data.dailylist)
        });
        setDidMount(true)
    }

    const onEndReached = () => {
        getFundNAVPage(code, page + 1, (ret) => {
            setData(data.concat(ret.data.dailylist));
        });
        setPage(page + 1)
    }
    const calc = (data) => {
        data.forEach((item, index) => {
            if (index === data.length - 1) return
            data[index].change = Math.floor((data[index].nav / data[index + 1].nav - 1) * 10000) / 100
        })
        let newData = []
        data.forEach((item, index) => {
            if (index === data.length - 1) return
            newData.push(data[index])
        })
        return newData
    }
    const renderItem = ({item, index}) => {
        if (type === 6) {
            return (
                <View style={{flexDirection: 'row', alignItems: 'center', marginTop: 15}}>
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
        } else {
            return (
                <View style={{flexDirection: 'row', alignItems: 'center', marginTop: 15}}>
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
        }
    }

    return (
        <SafeAreaView>
            <Header
                headerLeft={<HeaderBackButton onPress={navigation.goBack} tintColor={"white"}/>}
            >
                历史净值
            </Header>
            <MyStatusBar/>
            <Card containerStyle={{borderRadius: 10, borderWidth:0}}>
                {
                    data === null ? <Loading/> :
                        <View>
                            {
                                type === 6 ?
                                    (
                                        <View style={{flexDirection: 'row', alignItems: 'center'}}>
                                            <Caption style={{width: '30%', textAlign: 'left', fontSize: 14}}>
                                                日期
                                            </Caption>
                                            <Caption style={{width: '35%', textAlign: 'center', fontSize: 14}}>
                                                万份收益
                                            </Caption>
                                            <Caption style={{width: '35%', textAlign: 'right', fontSize: 14}}>
                                                七日年化收益率
                                            </Caption>
                                        </View>
                                    ) :
                                    (
                                        <View style={{flexDirection: 'row', alignItems: 'center'}}>
                                            <Caption style={{width: '30%', textAlign: 'left', fontSize: 14}}>
                                                日期
                                            </Caption>
                                            <Caption style={{width: '23%', textAlign: 'center', fontSize: 14}}>
                                                单位净值
                                            </Caption>
                                            <Caption style={{width: '23%', textAlign: 'center', fontSize: 14}}>
                                                累计净值
                                            </Caption>
                                            <Caption style={{width: '24%', textAlign: 'right', fontSize: 14}}>
                                                日涨幅
                                            </Caption>
                                        </View>
                                    )
                            }
                            <FlatList
                                data={calc(data)}
                                renderItem={renderItem}
                                keyExtractor={(item, index) => index}
                                onEndReached={onEndReached}
                                onEndReachedThreshold={0.2}
                                style={{marginBottom: 199}}
                            />
                        </View>
                }
            </Card>
        </SafeAreaView>
    )
}
