import React, {Component, useState} from 'react';
import {RefreshControl, SafeAreaView, ScrollView, Text, useWindowDimensions, View} from 'react-native';
import HoldList from "../components/HoldList";
import Header from "../components/Header";
import Ionicons from "react-native-vector-icons/Ionicons";
import {Divider, Image, SearchBar} from "react-native-elements";
import {HeaderBackButton} from "@react-navigation/stack";
import {getPositionFund} from "../service/UserPositionService";
import WrapHomeHistoryRank from "../components/WrapHomeHistoryRank";
import WrapHomePredictionRank from "../components/WrapHomePredictionRank";
import {Caption} from "react-native-paper";
import MyStatusBar from "../components/MyStatusBar";

export default function PositionView({navigation, route}) {

    const [refreshing, setRefreshing] = useState(false)
    const [origin, setOrigin] = useState([])
    const [fundList, setFundList] = useState([])
    const [search, setSearch] = useState('')
    const [preDate, setPreDate] = useState('')
    const [curDate, setCurDate] = useState('')
    const window = useWindowDimensions()


    const updateFundList = (data) => {
        if (data.status === -1) {
            setOrigin([])
            setFundList([])
            setPreDate('')
            setCurDate('')
            return
        }
        setOrigin(data.data.fundList)
        setFundList(data.data.fundList)
        setPreDate(data.data.preDate)
        setCurDate(data.data.curDate)
    }


    const onRefreshHandle = () => {
        setRefreshing(true)
        getPositionFund((data) => {
            updateFundList(data)
            setRefreshing(false)
        })
    }

    const updateSearch = (search) => {
        let fundList = []
        origin.forEach((item, index) => {
            if (item.code.indexOf(search) !== -1 || item.name.indexOf(search) !== -1) {
                fundList.push(item)
            }
        })
        setSearch(search)
        setFundList(fundList)
    }

    const updateList = () => {
        getPositionFund((data) => {
            updateFundList(data)
        })
    }

    React.useEffect(() => {
        const unsubscribe = navigation.addListener('focus', () => {
            getPositionFund((data) => {
                updateFundList(data)
            })
        });
        return unsubscribe;
    }, [navigation]);

    if (origin.length === 0) {
        return (
            <SafeAreaView>
                <Header>基金自选</Header>
                <SafeAreaView>
                    <ScrollView
                        refreshControl={
                            <RefreshControl
                                refreshing={refreshing}
                                onRefresh={() => {
                                    onRefreshHandle()
                                }}
                            />
                        }
                    >
                        <MyStatusBar/>
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
                            <Text style={{color: '#b7b1b1', fontSize: 20,paddingTop:20}}>您的自选空空如也~</Text>
                        </View>
                    </ScrollView>
                </SafeAreaView>
            </SafeAreaView>
        )
    } else {
        return (
            <SafeAreaView>
                <Header>基金自选</Header>
                <SafeAreaView>
                    <ScrollView
                        refreshControl={
                            <RefreshControl
                                refreshing={refreshing}
                                onRefresh={() => {
                                    onRefreshHandle()
                                }}
                            />
                        }
                    >
                        <MyStatusBar/>
                        <View style={{marginBottom: 120}}>
                            <View style={{marginTop: 0}}>
                                <SearchBar
                                    placeholder="搜索代码/基金名称"
                                    lightTheme={true}
                                    round={true}
                                    platform={"android"}
                                    onChangeText={updateSearch}
                                    value={search}
                                />
                                <Divider/>
                                <HoldList
                                    fundList={fundList}
                                    preDate={preDate}
                                    curDate={curDate}
                                    refresh={() => updateList()}
                                />
                            </View>
                        </View>
                    </ScrollView>
                </SafeAreaView>
            </SafeAreaView>
        )
    }
}
