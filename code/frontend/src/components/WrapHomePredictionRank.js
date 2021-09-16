import React, {Component} from "react";
import {ButtonGroup, Card, ListItem} from "react-native-elements";
import {Text, View, StyleSheet, Pressable} from "react-native";
import GoToFundView from "./GoToFundView";
import {Caption} from "react-native-paper";
import Top from "./Top";
import {numberColor, numberFormat} from "../helpers/numberColor";
import {getHistoryRank, getPredictionRank} from "../service/FundRateService";
import SimpleLineIcons from "react-native-vector-icons/SimpleLineIcons";
import storage from "./LocalStorage";

export default class WrapHomeHistoryRank extends Component {

    subtitles = ['后1天', '后2天', '后3天'];
    map = [1, 2, 3]

    constructor(props) {
        super(props);
        this.state = {
            selectedIndex: 0,
            fundList: [],
        }
        this.updateIndex = this.updateIndex.bind(this);
    }

    updateIndex(selectedIndex) {
        this.setState({
            selectedIndex: selectedIndex,
        })

        storage.load({
            key: 'PredictionRank',
            id: selectedIndex,
        })
        .then(ret => {
            console.log("find data in storage index=", selectedIndex);
            this.setState({
                fundList: ret.list,
            })
        })
        .catch(err => {
            getPredictionRank(0, 3, this.map[selectedIndex], (data) => {
                this.setState({
                    fundList: data.data.list
                })
                storage.save({
                    key: 'PredictionRank',
                    id: selectedIndex,
                    data: {
                        list: data.data.list,
                    },
                    expires: 1000 * 3600 * 1,    //设置1个小时过期
                })
                .then(ret => {
                    console.log("save completed", this.state.selectedIndex);
                })
            })
        })
    }

    componentDidMount() {
        storage.load({
            key:'PredictionRank',
            id:this.state.selectedIndex,
        })
        .then(ret=>{
            this.setState({
                fundList:ret.list,
            })
        })
        .catch(err => {
            getPredictionRank(0, 3, this.map[this.state.selectedIndex], (data) => {
                this.setState({
                    fundList: data.data.list
                })
                storage.save({
                    key: 'PredictionRank',
                    id: this.state.selectedIndex,
                    data: {
                        list: data.data.list,
                    },
                    expires: 1000 * 3600 * 1,    //设置1个小时过期
                })
                .then(ret => {
                    console.log("save completed", this.state.selectedIndex);
                })
            })
        })
        // getPredictionRank(0, 3, this.map[this.state.selectedIndex], (data) => {
        //     this.setState({
        //         fundList: data.data.list
        //     })
        // })
    }

    listContent = (selectedIndex) => {
        let subtitle = this.subtitles[selectedIndex]
        return (
            <View>
                {
                    (this.state.fundList!==null)? (
                        this.state.fundList.map((fund, index) => (
                            <GoToFundView
                                name={fund.name}
                                code={fund.code}
                                key={index}
                            >
                                <View style={{flexDirection: 'row', marginTop: 10}}>
                                    <View style={{width: '10%', marginRight: '3%'}}>
                                        <Top number={index + 1} style={{minWidth:38,marginLeft:-5,marginTop:2}}/>
                                    </View>
                                    <View style={{width: '61%', marginRight: '3%'}}>
                                        <Text numberOfLines={1}
                                              style={{fontSize: 14, fontWeight: 'bold',lineHeight:20}}>{fund.name}</Text>
                                        <Caption style={[styles.caption, {marginTop: -2}]}>{fund.code}</Caption>
                                    </View>
                                    <View style={{width: '23%'}}>
                                        <Text
                                            style={{
                                                fontWeight: 'bold',
                                                color: numberColor(fund.rate),
                                                textAlign: 'center',
                                                lineHeight: 20,
                                            }}
                                        >
                                            {numberFormat(fund.rate) + '%'}
                                        </Text>
                                        <Caption style={[styles.caption, {marginTop: -2, textAlign: 'center'}]}>
                                            {subtitle + '增长'}
                                        </Caption>
                                    </View>
                                </View>
                            </GoToFundView>
                        ))
                    ):null
                }
            </View>
        )
    }

    render() {
        const {selectedIndex} = this.state;
        return (
            <Card containerStyle={{borderRadius: 10,borderWidth:0}}>
                <View style={{flexDirection: 'row', alignItems: 'center'}}>
                    <Text style={{fontSize: 20, fontWeight: 'bold'}}>
                        快人一步
                    </Text>
                    <Text style={{marginLeft: 10, color: '#a4a4a4'}}>跟着岩神预测买基金</Text>
                </View>
                <View>
                    {/*<Text>{selectedIndex}</Text>*/}
                    {this.listContent(selectedIndex)}
                    <Pressable
                        onPress={() => {
                            this.props.navigation.push('HomeRankFund', {
                                isPrediction: true,
                                type: this.map[selectedIndex],
                                subtitle: this.subtitles[selectedIndex],
                            })
                        }}
                    >
                        <View style={{alignSelf: 'center', marginTop: 10, flexDirection: 'row', alignItems: 'center'}}>
                            <Text style={{color: '#006FFF', marginRight: 5}}>
                                更多数据
                            </Text>
                            <SimpleLineIcons name='arrow-right' size={10} color={'#00AFFF'}/>
                        </View>
                    </Pressable>
                    <ButtonGroup
                        onPress={this.updateIndex}
                        selectedIndex={selectedIndex}
                        buttons={this.subtitles}
                        buttonContainerStyle={{borderWidth: 0, borderRadius: 20}}
                        buttonStyle={{borderWidth: 0, borderRadius: 20}}
                        innerBorderStyle={{width: 0}}
                        containerStyle={{
                            height: 40,
                            borderRadius: 20,
                            borderWidth: 0,
                            paddingTop: 5,
                            alignSelf: 'center'
                        }}
                        selectedTextStyle={{color: '#6991c3'}}
                        textStyle={{color: '#a4a4a4'}}
                        selectedButtonStyle={{
                            backgroundColor: '#e9f3fe'
                        }}
                    />
                </View>
            </Card>
        );
    }
}

const page = StyleSheet.create({
    text: {
        color: '#a4a4a4',
    }
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
