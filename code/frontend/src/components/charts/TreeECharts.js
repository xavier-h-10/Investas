import React, {Component} from "react";
import {ScrollView, StyleSheet, Text, View} from "react-native";
import RNEChartsPro from "react-native-echarts-pro";

export default class TreeECharts extends Component {
    constructor(props) {
        super(props);
        this.TreeOptions = {
            series: [{
                type: 'treemap',
                roam: false, //设置不能拖动
                nodeClick: false, //设置不能点击
                width: '90%',
                height: '90%',
                itemStyle: {
                    normal: {
                        borderWidth: 3,
                        fontWeight: 10,
                        fontSize: 10,
                        borderRadius: 7, //设置圆角
                        upperLabel: false,
                    }
                },
                label: {
                    overflow: 'break', //一定要设置换行，否则显示不全
                    rich: {
                        black: {
                            color: '#000000C8',
                            fontWeight: 400,
                            fontSize: 10,
                            position: 'insideBottomLeft',
                            padding: 5,
                        },
                        green: {
                            color: '#50A99BFF',
                            fontWeight: 400,
                            fontSize: 10,
                            position: 'insideBottomLeft',
                        },
                        red: {
                            color: '#BF7046FF',
                            fontWeight: 400,
                            fontSize: 10,
                            position: 'insideBottomLeft',
                        }
                    },
                },
                data: [{
                    // name: 'node',            // First tree
                    // value: 10,
                    children: [{
                        name: '{black|五粮液}{green|-1.37%}',
                        value: 4,
                        itemStyle: {color: '#d7fef1'},
                    }, {
                        name: ['{black|泸州老窖}', '{red|+1.37%}'].join('\n'),          // Second leaf of first tree
                        value: 6,
                        itemStyle: {color: '#ffeef4'},
                    }, {
                        name: ['{black|贵州茅台}', '{red|+5.00%}'].join('\n'),
                        value: 10,
                        itemStyle: {color: '#ffeef4'},
                    },
                        {
                            name: ['{black|云南白药}', '{red|+3.97%}'].join('\n'),
                            value: 15,
                            itemStyle: {color: '#ffeef4'},
                        },
                        {
                            name: ['{black|泸州老窖}', '{red|+1.37%}'].join('\n'),
                            value: 3,
                            itemStyle: {color: '#ffeef4'},
                        },
                        {
                            name: ['{black|上海制药}', '{red|+1.37%}'].join('\n'),
                            value: 20,
                            itemStyle: {color: '#ffeef4'},
                        },

                    ]
                }]
            }]
        }
    }

    render() {
        console.log("TreeMap rendered.")
        return (
            <ScrollView
                style={{height: 400, paddingTop: 25}}
                horizontal={true} // 横向
                // showsHorizontalScrollIndicator={false}  // 此属性为true的时候，显示一个水平方向的滚动条。
            >
                <RNEChartsPro height={400} option={this.TreeOptions}/>
            </ScrollView>
        );
    }
}
