import {Avatar, Card, Image, ListItem} from 'react-native-elements'
import React, {Component, useState} from 'react';
import {View, Text, ScrollView, Pressable} from 'react-native';
import {Caption, Title} from "react-native-paper";
import {numberColor, numberFormat} from "../helpers/numberColor";
import MyReadMore from "../components/MyReadMore";
import Header from "../components/Header";
import {HeaderBackButton} from "@react-navigation/stack";
import Loading from "../components/Loading";
import {getManager} from "../service/ManagerService";
import tenureCalculator from "../helpers/tenureCalculator";
import {useNavigation} from "@react-navigation/native";
import GoToFundView from "../components/GoToFundView";
import MyStatusBar from "../components/MyStatusBar";

const FundDescriptionItem = (props) => {
    return (
        <View style={{flexDirection: 'row', alignItems: 'center', marginTop: -5, marginBottom: 10}}>
            <View style={{width: '50%'}}>
                <Text style={{fontSize: 13}} numberOfLines={1}>{props.fund.name}</Text>
            </View>
            <Text style={{width: '30%', color: 'grey', textAlign: 'right', fontSize: 10}}>
                {props.fund.start + '至今'}
            </Text>
            <Text style={{width: '20%', color: numberColor(props.fund.repay), textAlign: 'right', fontSize: 13}}>
                {numberFormat(props.fund.repay) + '%'}
            </Text>
        </View>
    )
}
const ScrollViewContent = (props) => {
    const {manager} = props
    const navigation = useNavigation()
    return (
        <View style={{marginBottom: 80}}>
            <Card containerStyle={{borderRadius: 10}}>
                <View style={{flexDirection: 'row', alignItems: 'center', height: 85, marginTop: -20}}>
                    {/*<Image*/}
                    {/*    source={{*/}
                    {/*        uri: manager.url,*/}
                    {/*    }}*/}
                    {/*    style={{width: 60, height: 60, borderRadius: 5}}*/}
                    {/*    resizeMode={'stretch'}*/}
                    {/*/>*/}
                  <Avatar
                      rounded
                      source={{
                        uri:manager.url
                      }}
                      size={60}
                  />
                    <View style={{marginLeft: 12}}>
                        <Text style={{fontSize: 15, fontWeight: 'bold'}}>{manager.name}</Text>
                        <Caption>{'从业' + tenureCalculator(manager.start)}</Caption>
                    </View>
                </View>
                <Card.Divider/>
                <MyReadMore
                    numberOfLines={5}
                    component={
                        <Text
                            style={{fontSize: 15, color: '#444444', marginTop: -5,lineHeight: 20}}>{manager.description}</Text>
                    }
                />
            </Card>
            <Card containerStyle={{borderRadius: 10}}>
                <Text style={{fontSize: 14, color: '#444444', marginTop: -7, marginBottom: 6}}>在任情况</Text>
                <Card.Divider/>
                <View style={{flexDirection: 'row', marginTop: -14}}>
                    <Caption style={{fontSize: 12, width: '60%'}}>基金名称</Caption>
                    <Caption style={{fontSize: 12, width: '20%', textAlign: 'right'}}>任期</Caption>
                    <Caption style={{fontSize: 12, width: '20%', textAlign: 'right'}}>回报</Caption>
                </View>
                {
                    manager.fundList.map((fund, index) => (
                        <View key={index}>
                            <Card.Divider/>
                            <GoToFundView push code={fund.code} name={fund.name}>
                                <FundDescriptionItem fund={fund}/>
                            </GoToFundView>
                        </View>
                    ))
                }
            </Card>
        </View>
    )
}

export default function ManagerView({route, navigation}) {
    const {id} = route.params
    const [manager, setManager] = useState(null)
    const [didMount, setDidMount] = useState(false)

    if (didMount === false) {
        getManager(id, (data) => {
            setManager(data.data)
        })
        setDidMount(true)
    }

    return (
        <View>
            <Header
                headerLeft={<HeaderBackButton onPress={navigation.goBack} tintColor={"white"}/>}
            >
                基金经理
            </Header>
            <MyStatusBar/>
            <ScrollView>
                {manager === null ? <Loading/> : <ScrollViewContent manager={manager}/>}
            </ScrollView>
        </View>
    )
}
