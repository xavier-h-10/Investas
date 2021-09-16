import React, {useState} from 'react'
import {SafeAreaView, ScrollView, StyleSheet, View} from 'react-native'
import {Card, Text} from 'react-native-elements'
import {Caption} from "react-native-paper";
import MyReadMore from "../components/MyReadMore";
import Header from "../components/Header";
import {HeaderBackButton} from "@react-navigation/stack";
import Loading from "../components/Loading";
import {getManager} from "../service/ManagerService";
import tenureCalculator from "../helpers/tenureCalculator";
import {getFundArchiveDetail} from "../service/FundArchiveDetailService";
import {getFundView} from "../service/FundService";
import MyStatusBar from "../components/MyStatusBar";


const DescriptionItem = (props) => {
    return (
        <View style={{flexDirection: 'row'}}>
            <Text style={{fontSize: 13, width: '25%',lineHeight:20}}>{props.title}</Text>
            <Text style={{fontSize: 13, width: '75%',lineHeight:20}}>{props.content}</Text>
        </View>
    )
}

const LongDescriptionItem = (props) => {
    return (
        <Card containerStyle={{borderRadius: 10}}>
            <View style={{marginTop: -10, marginBottom: 4}}>
                <Caption style={{fontSize: 12}}>{props.title}</Caption>
            </View>
            <Card.Divider/>
            <View>
                <MyReadMore
                    numberOfLines={5}
                    component={
                      <Text style={{
                        fontSize: 13,
                        lineHeight: 20
                      }}>{props.content}</Text>
                    }
                />
            </View>
        </Card>
    )
}
const toPlainText = (managerList) => {
    let manager = ''
    managerList.forEach((one) => {
        manager = manager + one
    })
    return manager
}
const ViewContent = (props) => {
    const {fund} = props
    return (
        <View style={{marginBottom: 80}}>
            <Card containerStyle={{borderRadius: 10}}>
                <View style={{marginTop: -10, marginBottom: 4}}>
                    <Caption style={{fontSize: 12}}>基金信息</Caption>
                </View>
                <Card.Divider/>
                <View style={{flexDirection: 'row'}}>
                    <DescriptionItem title='基金全称' content={fund.name}/>
                </View>
                <View style={{flexDirection: 'row'}}>
                    <DescriptionItem title='基金代码' content={fund.code}/>
                </View>
                <View style={{flexDirection: 'row'}}>
                    <DescriptionItem title='成立日期' content={fund.start}/>
                </View>
                <View style={{flexDirection: 'row'}}>
                    <DescriptionItem title='资产规模' content={fund.size + '亿'}/>
                </View>
                <View style={{flexDirection: 'row'}}>
                    <DescriptionItem title='基金管理人' content={fund.company}/>
                </View>
                <View style={{flexDirection: 'row'}}>
                    <DescriptionItem title='基金托管人' content={fund.custodian}/>
                </View>
            </Card>
            <Card containerStyle={{borderRadius: 10}}>
                <View style={{
                    flexDirection: 'row',
                    justifyContent: 'space-between',
                    alignItems: 'center',
                    marginTop: -10,
                    marginBottom: 4
                }}>
                    <Caption style={{fontSize: 12}}>基金经理</Caption>
                </View>
                <Card.Divider/>
                <View>
                  <MyReadMore
                      numberOfLines={5}
                      component={
                        <Text
                            style={{fontSize: 13, lineHeight: 20}}>
                          {toPlainText(fund.managerList)}
                        </Text>
                      }
                  />
                </View>
            </Card>
            {
                fund.descriptionList.map((description, index) => (
                    <LongDescriptionItem title={description.title} content={description.content} key={index}/>
                ))
            }
        </View>
    )
}

export default function FundArchiveDetailView({route, navigation}) {
    const {code} = route.params
    const [fund, setFund] = useState(null)
    const [didMount, setDidMount] = useState(false)

    if (didMount === false) {
        getFundArchiveDetail(code, (data) => {
            setFund(data.data)
        })
        setDidMount(true)
    }
    return (
        <SafeAreaView>
            <Header
                headerLeft={<HeaderBackButton onPress={navigation.goBack} tintColor={"white"}/>}
            >
                基金档案
            </Header>
            <MyStatusBar/>
            <ScrollView>
                {fund === null ? <Loading/> : <ViewContent fund={fund}/>}
            </ScrollView>
        </SafeAreaView>
    )
}
