import {SafeAreaView, ScrollView, View} from "react-native";
import React from "react";
import Header from "../components/Header";
import {HeaderBackButton} from "@react-navigation/stack";
import PerformanceDetailCard from "../components/PerformanceDetailCard";
import MyStatusBar from "../components/MyStatusBar";


export default function FundPerformanceView({route, navigation}) {
    const {historyPerformance, fundRateRank, fundRateTotalCount} = route.params
    return (
        <SafeAreaView>
            <Header
                headerLeft={<HeaderBackButton onPress={navigation.goBack} tintColor={"white"}/>}
            >
                历史业绩
            </Header>
            <MyStatusBar/>
            <ScrollView>
                <View style={{marginBottom: 80}}>
                    <PerformanceDetailCard historyPerformance={historyPerformance} fundRateRank={fundRateRank}
                                           fundRateTotalCount={fundRateTotalCount}/>
                </View>
            </ScrollView>
        </SafeAreaView>
    )
}
