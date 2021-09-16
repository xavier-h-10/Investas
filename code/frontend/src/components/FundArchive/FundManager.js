import React from 'react'
import {StyleSheet, View} from 'react-native'
import {Avatar, Card, Image, Text} from 'react-native-elements'
import {theme} from '../../core/theme'
import {Caption, Title} from "react-native-paper";
import SimpleLineIcons from "react-native-vector-icons/SimpleLineIcons";
import Paragraph from "../firebase/Paragraph";
import {numberColor, numberFormat} from "../../helpers/numberColor";
import tenureCalculator from "../../helpers/tenureCalculator";
import MyReadMore from "../MyReadMore";

export default function FundManager(props) {
    const {index, manager} = props
    console.log(props)
    return (
        <View>
            {
                index > 0 ? <Card.Divider/> : null
            }
            <View style={{flexDirection: 'row', alignItems: 'flex-start'}}>
                <View style={{width: '80%'}}>
                    <View>
                        <Text style={{fontWeight: 'bold', fontSize: 14}}>{manager.name}</Text>
                        <Caption style={{fontSize: 13}}>{'从业' + tenureCalculator(manager.tenure)}</Caption>
                    </View>
                    <View style={{flexDirection: 'row'}}>
                        <View style={{width: '62.5%'}}>
                            <Caption style={{fontSize: 14}}>本基金任职</Caption>
                            <Text style={{fontWeight: 'bold'}}>{manager.start + '至今'}</Text>
                        </View>
                        <View style={{width: '37.5%'}}>
                            <Caption style={{fontSize: 14}}>任期回报</Caption>
                            <Text style={{fontWeight: 'bold', color: numberColor(manager.repay)}}>
                                {numberFormat(manager.repay) + '%'}
                            </Text>
                        </View>
                    </View>
                </View>
                <View style={{width: '20%'}}>
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
                </View>
            </View>
            <View style={{marginTop: 16,paddingDown:5}}>
                <MyReadMore
                    numberOfLines={7}
                    component={
                        <Paragraph style={{
                            fontSize: 13,
                            backgroundColor: '#FFEBCD',
                            lineHeight: 20,
                        }}>
                            {manager.description}
                        </Paragraph>
                    }
                />
            </View>
        </View>
    )
}
