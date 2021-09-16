import React, {Component} from "react";
import {StyleSheet, Text, View} from "react-native";
import RNEChartsPro from "react-native-echarts-pro";
import {ScrollView} from "react-native";
import {Card} from "react-native-elements";

export default class TryLineCharts extends Component {
    constructor(props) {
        super(props);
        this.pieOption = {
            label:{
                rich: {
                    black: {
                        color: '#070707',
                    },
                    green: {
                        color: '#46a08b',
                    },
                    red: {
                        color: '#e45e2e',
                    }
                },
            },
            legend: {
                // orient 设置布局方式，默认水平布局，可选值：'horizontal'（水平） ¦ 'vertical'（垂直）
                orient: 'horizontal',
                // x 设置水平安放位置，默认全图居中，可选值：'center' ¦ 'left' ¦ 'right' ¦ {number}（x坐标，单位px）
                x: 'left',
                // y 设置垂直安放位置，默认全图顶端，可选值：'top' ¦ 'bottom' ¦ 'center' ¦ {number}（y坐标，单位px）
                y: 'top',
                data: ['本基金', '同类均值', '沪深300'],
                left:'center',
            },

            animationDuration:500, //渲染时长调整

            //  图表距边框的距离,可选值：'百分比'¦ {number}（单位px）
            grid: {
                top: '16%',   // 等价于 y: '16%'
                left: '3%',
                right: '8%',
                bottom: '3%',
                containLabel: true
            },

            // 提示框
            tooltip: {
                trigger: 'axis'
            },

            xAxis: {
                type: 'category',
                axisLabel:{
                    showMinLabel:true,
                    showMaxLabel:true,
                    color:'#d1d1d1',
                },
                // boundaryGap值为false的时候，折线第一个点在y轴上
                boundaryGap: false,
                data: ['07-01','07-02','07-03','07-04','07-05','07-06','07-07'],
                axisLine:{
                    onZero:false, //x轴不能在0上
                    lineStyle:{
                        color:'#f4f4f4',
                        width:1,
                        //  shadowBlur:10,
                    }
                },
            },

            yAxis: {
                type: 'value',
                min: -100, // 设置y轴刻度的最小值
                max: 400,  // 设置y轴刻度的最大值
                splitNumber: 5,  // 设置y轴刻度间隔个数
                axisLine: {
                    lineStyle: {
                        // 设置y轴颜色
                        color: 'rgba(0,0,0,0.78)'
                    }
                },
                axisLabel:{
                    formatter:'{value}%',
                    color:'#d3d3d3',
                },
                splitLine:{
                    lineStyle:{
                        type:'dotted',
                    }
                }
            },

            series: [
                {
                    name: '本基金',
                    type: 'line',
                    symbol:'none',
                    //    smooth:true,  //此处不加平滑，效果不太理想
                    data: [120, 132, 101, 134, 90, 230, 210],
                    itemStyle:{
                        normal:{
                            lineStyle:{
                                width:2,
                            }
                        }
                    },
                    areaStyle:{
                        color: {
                            type: 'linear',
                            x: 0,
                            y: 0,
                            x2: 0,
                            y2: 1,
                            colorStops: [{
                                offset: 0, color: '#e7f0fd' // 0% 处的颜色
                            }, {
                                offset: 1, color: '#fdfdfd' // 100% 处的颜色
                            }],
                            global: false // 缺省为 false
                        },
                        origin:'start',
                    },
                    // tooltip: {
                    //     show:true,
                    //     trigger: 'axis', //触发类型；轴触发，axis则鼠标hover到一条柱状图显示全部数据，item则鼠标hover到折线点显示相应数据，
                    //     axisPointer: {  //坐标轴指示器，坐标轴触发有效，
                    //         type: 'line', //默认为line，line直线，cross十字准星，shadow阴影
                    //         crossStyle: {
                    //             color: '#fff'
                    //         }
                    //     }
                    // },
                },
                {
                    name: '同类均值',
                    type: 'line',
                    symbol:'none',
                    //    smooth:true,
                    data: [220, 182, 191, 234, 290, 330, 310],
                    itemStyle:{
                        normal:{
                            lineStyle:{
                                width:1,
                            }
                        },
                    }

                },
                {
                    name: '沪深300',
                    type: 'line',
                    symbol:'none',
                    //    smooth:true,
                    data: [150, 232, 201, 154, 190, 330, 410],
                    itemStyle:{
                        normal:{
                            lineStyle:{
                                width:1,
                            }
                        }
                    }
                },
            ],
            color: ['#3d76d1', '#2bb8d6', '#daae45'],
        };
    }

    render() {
        return (
            // <View style={{height: 300, paddingTop: 25}}>
            //     <RNEChartsPro height={250} option={this.pieOption}/>
            // </View>

            <View style={{flex:1,height:350,paddingLeft:50}}>
                <RNEChartsPro height={250} option={this.pieOption}/>
            </View>
        );
    }
}
