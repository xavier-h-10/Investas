import React, {Component, useState} from 'react';
import {RefreshControl, ScrollView, StyleSheet, Text, View, SafeAreaView} from 'react-native';
import Header from "../components/Header";
import Ionicons from "react-native-vector-icons/Ionicons";
import WrapHomeHistoryRank from "../components/WrapHomeHistoryRank";
import WrapHomePredictionRank from "../components/WrapHomePredictionRank"
import HomeCarousel from "../components/HomeCarousel";
import WrapHomeIndicatorRank from "../components/WrapHomeIndicatorRank";
import {getFundView} from "../service/FundService";
import MyStatusBar from "../components/MyStatusBar";

export default function HomeScreen({navigation, route}) {
    const [refreshing, setRefreshing] = useState(false)
    const [updating, setUpdating] = useState(false)

    const onRefreshHandle = () => {
        setRefreshing(true)
        setTimeout(() => {
            setRefreshing(false)
            setUpdating(true)
            setTimeout(() => {
                setUpdating(false)
            }, 50)
        }, 1000);
    }

    React.useEffect(() => {
        const unsubscribe = navigation.addListener('focus', () => {
            setUpdating(true)
            setTimeout(() => {
                setUpdating(false)
            }, 50)
        });
        // return unsubscribe;
    }, [navigation]);

    return (
        <SafeAreaView>
            <Header
                headerRight={
                    <Ionicons
                        name='search'
                        color={'white'}
                        size={24}
                        onPress={() => {
                            navigation.navigate('Search')
                        }}
                    />
                }
            >
                交我赚
            </Header>
          <MyStatusBar/>

            <View>
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
                    <View style={{marginBottom: 120}}>
                        <HomeCarousel/>
                        {
                            updating ? null :
                                <View>
                                    <WrapHomeHistoryRank navigation={navigation}/>
                                    <WrapHomePredictionRank navigation={navigation}/>
                                    <WrapHomeIndicatorRank navigation={navigation}/>
                                </View>
                        }
                    </View>
                </ScrollView>
            </View>
        </SafeAreaView>

    )
}

const page = StyleSheet.create(
    {
        text: {}
        ,
        button: {
            textAlign: 'center',
        }
        ,
        divider: {
            backgroundColor: 'blue',
        }
    }
);

const offsetMargin = StyleSheet.create(
    {
        offsetTop: {
            marginTop: -70,
        },
        offsetBottom: {
            marginBottom: -30,
        },
    }
)
