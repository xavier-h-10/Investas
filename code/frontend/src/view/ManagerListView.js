import {Avatar, Image, ListItem} from 'react-native-elements'
import React from 'react';
import {View, Text, ScrollView, SafeAreaView} from 'react-native';
import {Caption} from "react-native-paper";
import {numberColor, numberFormat} from "../helpers/numberColor";
import Header from "../components/Header";
import {HeaderBackButton} from "@react-navigation/stack";
import tenureCalculator from "../helpers/tenureCalculator";
import MyStatusBar from "../components/MyStatusBar";

export default function ManagerListView({route, navigation}) {
    const {managerList} = route.params

    return (
        <SafeAreaView>
            <Header
                headerLeft={<HeaderBackButton onPress={navigation.goBack} tintColor={"white"}/>}
            >
                基金经理
            </Header>
            <MyStatusBar/>
            <ScrollView>
                {
                    managerList.map((manager, index) => (
                        <ListItem
                            key={index}
                            bottomDivider
                            onPress={() => {
                                navigation.push('Manager', {id: manager.id})
                            }}
                        >
                            <ListItem.Content>
                                <View style={{flexDirection: 'row', justifyContent: 'space-between', width: '100%'}}>
                                    <View style={{flexDirection: 'row'}}>
                                        {/*<Image*/}
                                        {/*    source={{*/}
                                        {/*        uri: manager.url,*/}
                                        {/*    }}*/}
                                        {/*    style={{width: 45, height: 45, borderRadius: 20}}*/}
                                        {/*    resizeMode={'contain'}*/}
                                        {/*/>*/}
                                      <Avatar
                                        rounded
                                        source={{
                                          uri:manager.url
                                        }}
                                        size={45}
                                        // containerStyle={{
                                        //   height:35,
                                        //   width:35,
                                        //   marginTop:5,
                                        // }}
                                      />
                                        <View style={{marginLeft: 12}}>
                                            <Text style={{fontSize: 15, fontWeight: 'bold'}}>{manager.name}</Text>
                                            <Caption>{'从业' + tenureCalculator(manager.tenure)}</Caption>
                                        </View>
                                    </View>
                                    <View>
                                        <Text style={{fontWeight: 'bold', color: numberColor(manager.repay)}}>
                                            {numberFormat(manager.repay) + '%'}
                                        </Text>
                                        <Caption style={{fontSize: 14}}>任期回报</Caption>
                                    </View>
                                </View>
                            </ListItem.Content>
                        </ListItem>
                    ))
                }
            </ScrollView>
        </SafeAreaView>
    )
}
