import React, {useState} from "react";
import {
    Rating,
    Card,
    ListItem,
    Button,
    Icon,
    Divider
} from 'react-native-elements'
import {View, StyleSheet, Text} from "react-native";
import * as basicStyle from "../style/basicStyle";
import {checkPosition, delPosition, getPositionFund, putPositionFund} from "../service/UserPositionService";
import {getUserInfo} from "../service/UserService";
import {useNavigation} from "@react-navigation/native";
import {fundTypeToText, fundTypetoRiskText} from "../utils/typeConvert";
import {numberColor, numberFormat} from "../helpers/numberColor";


const FundCard = (props) => {
    const oneYearRate = props.fund.lastOneYearRate;
    const oneDayRate = props.fund.lastOneDayRate;
    const fromBeginningRate = props.fund.fromBeginningRate;
    const navigation = useNavigation();
    const rating = [[], [0], [0, 0], [0, 0, 0], [0, 0, 0, 0], [0, 0, 0, 0, 0]]
    const {fund, cancel, updateCancel, success, error} = props
    console.log('fundCard', fund)

    const renderTendencyColor = (value, fontSize) => {
        if (value === null) {
            return null
            // return (
            // <Text
            //     style={[fontSize, basicStyle.fontWeightStyle.boldWeight]}
            // >
            //     暂无数据
            // </Text>
            // )
        } else if (value > 0)
            return (
                <Text
                    style={[fontSize, basicStyle.fontWeightStyle.boldWeight, basicStyle.colorStyle.red]}
                >
                    {"+" + value.toFixed(2)}%
                </Text>
            )
        else if (value < 0)
            return (
                <Text
                    style={[fontSize, basicStyle.fontWeightStyle.boldWeight, basicStyle.colorStyle.green]}
                >
                    {value.toFixed(2)}%
                </Text>
            )
        else
            return (
                <Text
                    style={[fontSize, basicStyle.fontWeightStyle.boldWeight, basicStyle.colorStyle.black]}
                >
                    {value.toFixed(2)}%
                </Text>
            )
    }

    if (fund.fundType === 6) {
        return (
            <Card containerStyle={{borderRadius: 10, borderWidth:0}}>
                <View>
                    <Text style={basicStyle.textStyle.smallBlackBold}>{props.fund.fundName}</Text>
                    <View style={{paddingTop: 2, width: '100%', flexDirection: 'row'}}>
                        <Text style={{color: '#9e9e9e', fontSize: 14}}>{props.fund.fundCode}</Text>
                        <Card containerStyle={page.box}
                              wrapperStyle={{padding: 0, margin: 0}}>
                            <Text style={page.yearText}>{fundTypeToText(fund.fundType)}</Text>
                        </Card>
                        <Card containerStyle={page.box}
                              wrapperStyle={{padding: 0, margin: 0}}>
                            <Text style={page.yearText}>{fundTypetoRiskText(fund.fundType)}</Text>
                        </Card>
                    </View>
                </View>


                <View style={{flexDirection: 'row'}}>
                    <View style={{width: '50%'}}>
                        <Text style={{color: numberColor(fund.accumulateNAV), fontSize: 29, fontWeight: 'bold'}}>
                            {numberFormat(fund.accumulateNAV, 4) + '%'}
                        </Text>
                        <Text style={{color: 'grey'}}>
                            七日年化
                        </Text>
                    </View>
                    <View style={{width: '50%'}}>
                        <Text style={{color: 'black', fontSize: 29, fontWeight: 'bold'}}>
                            {fund.NAV.toFixed(4)}
                        </Text>
                        <Text style={{color: 'grey'}}>
                            {"万份收益 " + fund.updateDate}
                        </Text>
                    </View>
                </View>

                {
                    props.fund.fundRating ?
                        <View>
                            <Divider style={{paddingTop: 10, paddingBottom: 5}}/>

                            <View style={[basicStyle.layoutStyle.row, {paddingTop: 10}]}>
                                <View style={{alignItems: 'flex-start'}}>
                                    <View style={basicStyle.layoutStyle.row}>
                                        <Text style={{color: '#a0a0a0', paddingRight: 5}}>招商评级</Text>
                                        <Rating imageSize={15} readonly startingValue={props.fund.fundRating}/>
                                    </View>
                                </View>
                            </View>
                        </View>
                        :
                        null
                }
            </Card>
        )
    } else {
        return (
            <View>
                <Card containerStyle={{borderRadius: 10, borderWidth:0}}>
                    <View>
                        <Text style={basicStyle.textStyle.smallBlackBold}>{props.fund.fundName}</Text>
                        <View style={{paddingTop: 2, width: '100%', flexDirection: 'row'}}>
                            <Text style={{color: '#9e9e9e', fontSize: 14}}>{props.fund.fundCode}</Text>
                            {
                                (fund.fundType == null) ? null :
                                    (
                                        <Card containerStyle={page.box}
                                              wrapperStyle={{padding: 0, margin: 0}}>
                                            <Text style={page.yearText}>{fundTypeToText(fund.fundType)}</Text>
                                        </Card>
                                    )
                            }
                            <Card containerStyle={page.box}
                                  wrapperStyle={{padding: 0, margin: 0}}>
                                <Text style={page.yearText}>{fundTypetoRiskText(fund.fundType)}</Text>
                            </Card>
                        </View>
                    </View>


                    <View style={[basicStyle.layoutStyle.row, {paddingTop: 7}]}>
                        <View style={basicStyle.layoutStyle.spaceFreeColumn}>
                            {
                                oneYearRate === -1 ?
                                    <View>
                                        {
                                            (Math.abs(fromBeginningRate) >= 100) ?
                                                renderTendencyColor(fromBeginningRate, basicStyle.fontSizeStyle.smallSize) :
                                                renderTendencyColor(fromBeginningRate, basicStyle.fontSizeStyle.mediumSize)
                                        }
                                        <Text style={[basicStyle.textStyle.tinyGrey, {color: '#9c9897'}]}>成立以来涨跌幅</Text>
                                    </View>
                                    :
                                    <View>
                                        {
                                            (Math.abs(oneYearRate) >= 100) ?
                                                renderTendencyColor(oneYearRate, basicStyle.fontSizeStyle.smallSize) :
                                                renderTendencyColor(oneYearRate, basicStyle.fontSizeStyle.mediumSize)
                                        }
                                        <Text style={[basicStyle.textStyle.tinyGrey, {color: '#9c9897',textAlign:'center'}]}>近一年涨跌幅</Text>
                                    </View>
                            }
                                </View>
                                <View style={[basicStyle.layoutStyle.spaceFreeColumn]}>
                            {renderTendencyColor(oneDayRate, basicStyle.fontSizeStyle.smallSize)}
                                <Text style={[basicStyle.textStyle.tinyGrey, {color: '#9c9897'}]}>日涨跌幅</Text>
                                </View>
                                <View style={[basicStyle.layoutStyle.spaceFreeColumn, {alignItems: "center"}]}>
                                <Text
                                style={basicStyle.textStyle.smallBlackBold}>{parseFloat(props.fund.NAV).toFixed(4)}</Text>
                                <Text
                                style={[basicStyle.textStyle.tinyGrey, {color: '#9c9897'}]}>净值&nbsp;{props.fund.updateDate}</Text>
                                </View>
                                </View>

                            {
                                props.fund.fundRating ?
                                <View>
                                <Divider style={{paddingTop: 10, paddingBottom: 5}}/>

                                <View style={[basicStyle.layoutStyle.row, {paddingTop: 10}]}>
                                <View style={{alignItems: 'flex-start'}}>
                                <View style={basicStyle.layoutStyle.row}>
                                <Text style={{color: '#a0a0a0', paddingRight: 5}}>招商评级</Text>
                                <View style={{flexDirection: 'row'}}>
                            {rating[props.fund.fundRating].map(() => {
                                return (
                                <Icon name={'star'} type={'font-awesome'} color={'gold'} containerStyle={{paddingLeft:4}} size={13}/>
                                )
                            })}
                            {rating[5 - props.fund.fundRating].map(() => {
                                return (
                                <Icon name={'star-o'} type={'font-awesome'} color={'gold'} containerStyle={{paddingLeft:4}} size={13}/>
                                )
                            })}
                                </View>
                                </View>
                                </View>
                                </View>
                                </View>
                                :
                                null
                            }
                                </Card>
                                </View>

                                )
                            }

                            }

                            export default FundCard;

                            const page = StyleSheet.create({
                            box: {
                            backgroundColor: '#eef5fe',
                            borderRadius: 2,
                            borderWidth: 0,
                            margin: 0,
                            marginLeft: 5,
                            marginTop: 3,
                            padding: 0,
                            paddingLeft: 5,
                            paddingRight: 5,
                            paddingTop: 1,
                            paddingBottom: 1,
                            height: 17,
                        },
                            yearText: {
                            color: '#65a7fa',
                            fontSize: 10,
                            // fontWeight: 'bold',
                        },
                        });
