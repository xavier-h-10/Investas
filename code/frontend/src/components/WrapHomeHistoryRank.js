import React, {Component} from "react";
import {ButtonGroup, Card, ListItem} from "react-native-elements";
import {Text, View, StyleSheet, Pressable} from "react-native";
import GoToFundView from "./GoToFundView";
import {Caption} from "react-native-paper";
import Top from "./Top";
import {numberColor, numberFormat} from "../helpers/numberColor";
import {getHistoryRank} from "../service/FundRateService";
import SimpleLineIcons from "react-native-vector-icons/SimpleLineIcons";
import {useNavigation} from "@react-navigation/native";
import storage from "./LocalStorage";

export default class WrapHomeHistoryRank extends Component {

    subtitles = ['近1月', '近3月', '近6月', '近1年', '近3年'];
    map = [2, 3, 4, 5, 7]

    constructor(props) {
        super(props);
        this.state = {
            selectedIndex: 3,
            fundList: [],
        }
        this.updateIndex = this.updateIndex.bind(this);
    }

    updateIndex(selectedIndex) {
        this.setState({
            selectedIndex: selectedIndex,
        })
        storage.load({
            key: 'HistoryRank',
            id: selectedIndex,
        })
        .then(ret => {
            console.log("find HistoryRank in storage index=",selectedIndex);
            this.setState({
                fundList: ret.list,
            })
        })
        .catch(err => {
            getHistoryRank(0, 3, this.map[selectedIndex], (data) => {
                this.setState({
                    fundList: data.data.list
                })
                storage.save({
                    key: 'HistoryRank',
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
        // getHistoryRank(0, 3, this.map[selectedIndex], (data) => {
        //     this.setState({
        //         fundList: data.data.list
        //     })
        // })
    }

    componentDidMount() {
        // getHistoryRank(0, 3, this.map[this.state.selectedIndex], (data) => {
        //     this.setState({
        //         fundList: data.data.list
        //     })
        // })

        storage.load({
            key:'HistoryRank',
            id:this.state.selectedIndex,
        })
        .then(ret=>{
            this.setState({
                fundList:ret.list,
            })
        })
        .catch(err=> {
            getHistoryRank(0, 3, this.map[this.state.selectedIndex], (data) => {
                this.setState({
                    fundList: data.data.list
                })
                storage.save({
                    key: 'HistoryRank',
                    id: this.state.selectedIndex,
                    data: {
                        list: data.data.list,
                    },
                    expires: 1000 * 3600 * 1,    //设置1个小时过期
                })
                .then(ret=>{
                    console.log("save completed", this.state.selectedIndex);
                })
            })
        })
    }

    listContent = (selectedIndex) => {
        let subtitle = this.subtitles[selectedIndex]
        if(this.state.fundList==null)
            return;
        return (
            <View>
                {
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
                                            lineHeight:20,
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
                        长得飞快
                    </Text>
                    <Text style={{marginLeft: 10, color: '#a4a4a4'}}>跟着历史数据买基金</Text>
                </View>
                <View>
                    {/*<Text>{selectedIndex}</Text>*/}
                    {this.listContent(selectedIndex)}
                    <Pressable
                        onPress={() => {
                            this.props.navigation.push('HomeRankFund', {
                                isPrediction: false,
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
