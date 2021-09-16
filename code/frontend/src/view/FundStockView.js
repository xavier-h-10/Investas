import React, {useState} from 'react'
import {SafeAreaView, ScrollView, StyleSheet, useWindowDimensions, View} from 'react-native'
import {Card, Image, Text} from 'react-native-elements'
import {theme} from '../core/theme'
import {Caption, Title} from "react-native-paper";
import {numberColor, numberFormat} from "../helpers/numberColor";
import Header from "../components/Header";
import BackButton from "../components/firebase/BackButton";
import {HeaderBackButton} from "@react-navigation/stack";
import FusionTreeMap from "../components/charts/FusionTreeMap";
import {getFundStock, getFundView} from "../service/FundService";
import MyStatusBar from "../components/MyStatusBar";

const StockDescriptionItem = (props) => {
    return (
        <View style={{flexDirection: 'row', alignItems: 'center', marginTop: -10}}>
            <View style={{width: '50%'}}>
                <Text style={{fontSize: 13}}>{props.stock.name}</Text>
                <Caption style={{fontSize: 11}}>{props.stock.id}</Caption>
            </View>
            <Text style={{width: '25%', color: numberColor(props.stock.value), textAlign: 'center'}}>
                {numberFormat(props.stock.value) + '%'}
            </Text>
            <Text style={{width: '25%', color: 'grey', textAlign: 'center'}}>
                {props.stock.proportion + '%'}
            </Text>
        </View>
    )
}

export default function FundStockView({route, navigation}) {
    const {code} = route.params
    const [stockList, setStockList] = useState(null);
    const window = useWindowDimensions()

    React.useEffect(() => {
        const unsubscribe = navigation.addListener('focus', () => {
            getFundStock(code, (data) => {
                if (data.data && data.data.list) {
                    setStockList(data.data.list)
                }
            })
        });
        // return unsubscribe;
    }, [navigation]);
    return (
        <SafeAreaView>
            <Header
                headerLeft={<HeaderBackButton onPress={navigation.goBack} tintColor={"white"}/>}
            >
                重仓持股
            </Header>
            <MyStatusBar/>
            <ScrollView>
                {
                    stockList === null ? null : (
                        stockList.length === 0 ?
                            <View style={{marginBottom: 120, alignItems: 'center', justifyContent: 'center'}}>
                                <Image
                                    source={require("../../resources/image/no_data.png")}
                                    style={{
                                        height: 150,
                                        width: 150,
                                    }}
                                    containerStyle={{
                                        marginTop: window.height * 0.2
                                    }}
                                />
                                <Text style={{color: '#b7b1b1', fontSize: 20, paddingTop: 20}}>重仓股票空空如也~</Text>
                            </View>
                            :
                            <View>
                                <Card containerStyle={{borderRadius: 10}}>
                                    <FusionTreeMap data={stockList}/>
                                </Card>
                                <View style={{marginBottom: 80}}>
                                    <Card containerStyle={{borderRadius: 10}}>
                                        {/*<View style={{marginTop: -10, marginBottom: 4}}>*/}
                                        {/*    <Title style={{fontSize: 16}}>重仓持股</Title>*/}
                                        {/*</View>*/}
                                        {/*<Card.Divider/>*/}
                                        <View style={{
                                            flexDirection: 'row',
                                            alignItems: 'center',
                                            marginTop: -10,
                                            marginBottom: 4
                                        }}>
                                            <Caption style={{width: '50%'}}>
                                                股票名称
                                            </Caption>
                                            <Caption style={{width: '25%', textAlign: 'center'}}>
                                                涨跌幅
                                            </Caption>
                                            <Caption style={{width: '25%', textAlign: 'center'}}>
                                                占净值比例
                                            </Caption>
                                        </View>
                                        {
                                            stockList.map((stock, index) => (
                                                <View key={index}>
                                                    <Card.Divider/>
                                                    <StockDescriptionItem stock={stock}/>
                                                </View>
                                            ))
                                        }
                                    </Card>
                                </View>
                            </View>
                    )

                }
            </ScrollView>
        </SafeAreaView>
    )
}
