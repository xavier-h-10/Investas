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
import {fundTypeToText,fundTypetoRiskText} from "../utils/typeConvert";


const FundViewPredictionCard = (props) => {
    const {fund, fundPrediction} = props
    console.log('fundViewPredictionCard', fund)

    if(fundPrediction===undefined)
        return <View/>;

    const renderTendencyColor = (value, fontSize) => {
        if (value === null) {
            return  null;
        }
        else if (value > 0)
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

    const renderNAV = (value, fontSize) => {
        if (value === null) {
            return null;
        }
        else
            return (
                <Text
                    style={[fontSize, predictionTextStyle.ValueNAV]}
                >
                    {value.toFixed(2)}
                </Text>
            )
    }


    return (
        <View>
            <Card containerStyle={{borderRadius: 10, borderWidth:0}}>
                <View>
                    <View style={{paddingTop: 2, width: '100%', flexDirection: 'row'}}>
                        <Text style={basicStyle.textStyle.smallBlackBold}>{"交AI 预测"}</Text>
                        {
                            (fundPrediction===null)?null:(
                                (fundPrediction.hasModel === true)?
                                    (
                                        <Card containerStyle={page.box}
                                              wrapperStyle={{padding: 0, margin: 1}}>
                                            <Text style={page.modelTip}>{"本基金模型为量身定制"}</Text>
                                        </Card>
                                    ):
                                    (
                                        <Card containerStyle={page.box}
                                              wrapperStyle={{padding: 0, margin: 0}}>
                                            <Text style={page.modelTip}>{"本基金模型为其他模型适配"}</Text>
                                        </Card>
                                    )
                            )
                        }
                    </View>

                    <View style={{paddingTop: 2, width: '100%', flexDirection: 'row'}}>
                        <Text style={{color: '#9e9e9e', fontSize: 14}}>{props.fund.fundCode}</Text>

                    </View>
                </View>


                <View style={[basicStyle.layoutStyle.row, {paddingTop: 7}]}>
                    <View style={basicStyle.layoutStyle.spaceFreeColumn}>
                        {
                            (fundPrediction===null || fundPrediction.oneDayQuote===null)?null:
                                (
                                    (Math.abs(fundPrediction.oneDayQuote)>=100)?
                                        renderTendencyColor(fundPrediction.oneDayQuote, predictionTextStyle.smallSize):
                                        renderTendencyColor(fundPrediction.oneDayQuote, predictionTextStyle.mediumSize)
                                )

                        }
                        <Text style={[basicStyle.textStyle.tinyGrey, {color: '#9c9897'}]}>下一交易日</Text>
                        {/*{*/}
                        {/*    (Math.abs(fundPrediction.oneDayNAV)>=100)?*/}
                        {/*        renderNAV(futureOneDayRate, predictionTextStyle.smallSize):*/}
                        {/*        renderNAV(futureOneDayRate, predictionTextStyle.mediumSize)*/}
                        {/*}*/}
                    </View>
                    <View style={basicStyle.layoutStyle.spaceFreeColumn}>
                        {
                            (fundPrediction===null || fundPrediction.twoDayQuote===null)?null:
                                (
                                    (Math.abs(fundPrediction.twoDayQuote)>=100)?
                                        renderTendencyColor(fundPrediction.twoDayQuote, predictionTextStyle.smallSize):
                                        renderTendencyColor(fundPrediction.twoDayQuote, predictionTextStyle.mediumSize)
                                )

                        }
                        <Text style={[basicStyle.textStyle.tinyGrey, {color: '#9c9897'}]}>第二交易日</Text>
                    </View>
                    <View style={basicStyle.layoutStyle.spaceFreeColumn}>
                        {
                            (fundPrediction===null|| fundPrediction.threeDayQuote===null)?null:
                                (
                                    (Math.abs(fundPrediction.threeDayQuote)>=100)?
                                        renderTendencyColor(fundPrediction.threeDayQuote, predictionTextStyle.smallSize):
                                        renderTendencyColor(fundPrediction.threeDayQuote, predictionTextStyle.mediumSize)
                                )
                        }
                        <Text style={[basicStyle.textStyle.tinyGrey, {color: '#9c9897'}]}>第三交易日</Text>
                    </View>

                    <View style={[basicStyle.layoutStyle.spaceFreeColumn]}>
                        <Text style={predictionTextStyle.description}>
                            {(fundPrediction===null)?null:fundPrediction.text}
                        </Text>
                    </View>
                </View>
            </Card>
        </View>

    )

}

export default FundViewPredictionCard;

const page = StyleSheet.create({
    box: {
        backgroundColor: '#6495ED',
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
    modelTip: {
        color: '#ffffff',
        fontSize: 10,
        // fontWeight: 'bold',
    },
});

const predictionTextStyle = StyleSheet.create({
    smallSize:{
        fontSize:14,
    },

    mediumSize:{
        fontSize:18,
    },

    description:{
        margin: 10,
        fontSize:12,
        color: "#708090",

    },

    ValueNAV:{
        color: "#000111",
    }
})
