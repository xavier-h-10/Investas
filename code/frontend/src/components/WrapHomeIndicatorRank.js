import React, {Component} from "react";
import {ButtonGroup, Card, ListItem} from "react-native-elements";
import {Text, View, StyleSheet, Pressable} from "react-native";
import GoToFundView from "./GoToFundView";
import {Caption} from "react-native-paper";
import Top from "./Top";
import {numberColor, numberFormat} from "../helpers/numberColor";
import {getPredictionRank} from "../service/FundRateService";
import SimpleLineIcons from "react-native-vector-icons/SimpleLineIcons";
import {getUserInfo} from "../service/UserService";
import {getHomeIndicator} from "../service/FundAnalysisService";

export default class WrapHomeIndicatorRank extends Component {

    pro = [
        [30, 10, 10, 30],
        [30, 10, 10, 30],
        [25, 2, 2, 30],
        [25, -15, -15, 15],
        [25, -25, -25, 12],
        [15, -30, -30, 10]
    ]
    risk = ['', '保守型', '稳健型', '平衡型', '积极型', '激进型']

    constructor(props) {
        super(props);
        this.state = {
            list:[],
            riskLevel: 0
        }
        // console.log("WrapHomeIndicatorRank constructor")
    }


    componentDidMount() {
        // console.log("WrapHomeIndicatorRank didMount")
        getUserInfo((data) => {
            if (data.data === undefined || data.data.riskLevel === undefined) return
            const args = this.pro[data.data.riskLevel]
            this.setState({ riskLevel: data.data.riskLevel })
            getHomeIndicator(args[0], args[1], args[2], args[3], 0, 3, (data) => {
                if (data.data === undefined || data.data.list === undefined) return
                this.setState({ list: data.data.list })
            })
        })
    }


    render() {
        if (this.state.list.length === 0 || this.state.riskLevel === 0) {
            return (
                <View style={{paddingTop: 20}}/>
            )
        } else {
            console.log("indicator", this.state.list)
            return (
                <View>
                <Card containerStyle={{borderRadius: 10,borderWidth:0}}>
                    <View style={{flexDirection: 'row', alignItems: 'center'}}>
                        <Text style={{fontSize: 20, fontWeight: 'bold'}}>
                            基金推荐
                        </Text>
                        {
                            this.state.riskLevel === 0 ? null :
                            <Text style={{marginLeft: 10, color: '#a4a4a4'}}>
                                您的风险承受能力：
                                <Text style={{color: '#FF4500', fontSize: 18}}>
                                    {this.risk[this.state.riskLevel]}
                                </Text>
                            </Text>
                        }
                    </View>
                    <View>
                        {
                            this.state.list.map((fund, index) => (
                                <GoToFundView
                                    name={fund.name}
                                    code={fund.code}
                                    key={index}
                                >
                                    <View style={{flexDirection: 'row', marginTop: 10}}>
                                        <View style={{width: '50%', marginRight: '0%'}}>
                                            <Text numberOfLines={2}
                                                  style={{fontSize: 14, fontWeight: 'bold'}}>{fund.name}</Text>
                                            <Caption style={[styles.caption, {marginTop: -2}]}>{fund.code}</Caption>
                                        </View>
                                        <View style={{width: '25%'}}>
                                            <Text
                                                style={{
                                                    fontWeight: 'bold',
                                                    color: 'black',
                                                    textAlign: 'center'
                                                }}
                                            >
                                                {fund.maxRet.toFixed(2) + '%'}
                                            </Text>
                                            <Caption style={[styles.caption, {marginTop: -2, textAlign: 'center'}]}>
                                                最大回撤
                                            </Caption>
                                        </View>
                                        <View style={{width: '25%'}}>
                                            <Text
                                                style={{
                                                    fontWeight: 'bold',
                                                    color: numberColor(fund.yearRate),
                                                    textAlign: 'center'
                                                }}
                                            >
                                                {numberFormat(fund.yearRate) + '%'}
                                            </Text>
                                            <Caption style={[styles.caption, {marginTop: -2, textAlign: 'center'}]}>
                                                成立来年化
                                            </Caption>
                                        </View>
                                    </View>
                                </GoToFundView>
                            ))
                        }
                    </View>
                </Card>
                    <View style={{paddingBottom:10}}/>
                    <View style={{padding: 30, minHeight: 100}}>
                        <Text
                            style={page.bottomContent}>以上预测及基金推荐均仅供参考，不构成投资建议，交我赚不承担任何责任。</Text>
                        <Text style={page.bottomContent}>基金过往业绩不预示未来表现。市场有风险，投资需谨慎。</Text>
                    </View>
                </View>
            )
        }
    }
}

const page = StyleSheet.create({
    text: {
        color: '#a4a4a4',
    },
    bottomContent: {
        lineHeight: 20,
        color: '#c7c3c8',
        fontSize: 13,
        textAlign:'justify',
    },
});


const styles = StyleSheet.create({
    header: {
        height: 40,
        justifyContent: 'center',
    },
    bold: {
        fontSize: 15,
        fontWeight: 'bold',
    },
    caption: {
        fontSize: 13,
    }
})
