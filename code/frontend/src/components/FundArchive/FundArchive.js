//逻辑进行了修改 原来为type=6时进入重仓股，现在改为stocklist不为空时进入重仓股
import React from 'react'
import {Pressable, ScrollView, StyleSheet, View} from 'react-native'
import {Card, LinearProgress, Text} from 'react-native-elements'
import {theme} from '../../core/theme'
import {Caption, Title} from "react-native-paper";
import SimpleLineIcons from "react-native-vector-icons/SimpleLineIcons";
import FundManager from "./FundManager";
import TreeECharts from "../charts/TreeECharts";
import {useNavigation} from "@react-navigation/native";
import FusionTreeMap from "../charts/FusionTreeMap";

export default function FundArchive(props) {
    const {fund, type} = props;
    const navigation = useNavigation();
    // console.log("FundArchive", fund.stockList);
    if (fund) {
        return (
            <Card containerStyle={{borderRadius: 10,borderWidth:0}}>
                <Pressable
                    onPress={() => {
                        navigation.push('FundArchive', {code: fund.code})
                    }}
                >
                    <View style={{flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center'}}>
                        <Title style={{fontWeight: 'bold', fontSize: 16}}>基金档案</Title>
                        <View style={{flexDirection: 'row', alignItems: 'center'}}>
                            <Caption style={{fontSize: 14}}>概况、公告、持仓 </Caption>
                            <SimpleLineIcons name='arrow-right' size={10}/>
                        </View>
                    </View>
                    <View style={{flexDirection: 'row', alignItems: 'center'}}>
                        <View style={{width: '50%'}}>
                            <Caption style={{fontSize: 14}}>基金规模</Caption>
                            <Text style={{fontWeight: 'bold'}}>{fund.size + '亿'}</Text>
                        </View>
                        <View style={{width: '50%'}}>
                            <Caption style={{fontSize: 14}}>成立时间</Caption>
                            <Text style={{fontWeight: 'bold'}}>{fund.start}</Text>
                        </View>
                    </View>
                </Pressable>
                <Card.Divider style={{marginTop: 18}}/>
                <Pressable
                    onPress={() => {
                        navigation.push('ManagerList', {managerList: fund.managerList})
                    }}
                >
                    <View style={{flexDirection: 'row', alignItems: 'center', marginBottom: 18}}>
                        <Caption style={{fontSize: 14}}>基金经理 </Caption>
                        <SimpleLineIcons name='arrow-right' size={10}/>
                    </View>
                    {
                        fund.managerList.map((manager, index) => (
                            <View>
                                <FundManager index={index} manager={manager} key={index}/>
                                <View style={{paddingTop: 15}}/>
                            </View>
                        ))
                    }
                </Pressable>
                {/*{*/}
                {/*    fund.stockList === undefined || fund.stockList === null*/}
                {/*    || fund.stockList === {} || fund.stockList.length === 0 ? null : (*/}
                        <View>
                            <Card.Divider style={{marginTop: 18}}/>
                            <Pressable
                                onPress={() => {
                                    navigation.push('Stock', {code: fund.code})
                                }}
                            >
                                <View
                                    style={{flexDirection: 'row', alignItems: 'center'}}>
                                    <Caption style={{fontSize: 14}}>重仓股票 </Caption>
                                    <SimpleLineIcons name='arrow-right' size={10}/>
                                </View>
                            </Pressable>
                            {/*<FusionTreeMap code={fund.code}/>*/}
                        </View>
                    {/*)*/}
                {/*}*/}

            </Card>
        )
    }
}
